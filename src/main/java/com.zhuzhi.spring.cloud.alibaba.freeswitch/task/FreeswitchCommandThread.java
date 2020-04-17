package com.zhuzhi.spring.cloud.alibaba.freeswitch.task;

import com.alibaba.fastjson.JSONObject;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsAttached;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsCluster;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.FsClusterService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.FsClusterShared;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.GsonUtils;
import javassist.expr.NewArray;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>FreeswitchCommandThread</p>
 *
 * <p>Freeswitch命令处理线程(有断线重连机制)</p>
 */
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程*/
@Slf4j
public class FreeswitchCommandThread {

    private static String ip;

    @Autowired
    private FsClusterService fsClusterService;

    @Qualifier("redisson")
    @Autowired
    private RedissonClient redissonClient;

    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 定时任务运行方法
     */
    @Async
    @Scheduled(fixedDelay = 5000)  //间隔5秒
    public void first() {
        List<FsCluster> fsClusterList = new ArrayList<>();
        try {
            fsClusterList = fsClusterService.byClusterList(new FsCluster("1"));
        } catch (Exception e) {
            log.error("zhuz: 数据库查询异常----", e);
            return;
        }

        //删除无效的ESL连接
        Map<Integer, Client> eslMap = new HashMap<Integer, Client>();
        eslMap.putAll(FsClusterShared.fsClientMap);
        for (Integer id : eslMap.keySet()) {
            Boolean isuse = false;
            for (FsCluster fsCluster : fsClusterList) {
                if (fsCluster.getId() == id) {
                    isuse = true;
                }
            }
            if (!isuse) {
                try {
                    CommandResponse commandResponse = FsClusterShared.fsClientMap.get(id).close();
                    log.info("zhuz: 关闭esl连接成功与否:" + commandResponse.isOk());
                } catch (Exception e) {
                    log.error("zhuz: 关闭ESL连接失败---", e);
                } finally {
                    FsClusterShared.fsClientMap.remove(id);
                    FsClusterShared.fsClusterMap.remove(id);
                }
            }
        }
        eslMap.clear();
        eslMap = null;

        //监控所有fs-client的ESL连接   存在的检查其可用性  不存在的新建
        Map<Integer, FsCluster> fsClusterMap = new HashMap<Integer, FsCluster>();
        for (FsCluster fsCluster : fsClusterList) {
            fsClusterMap.put(fsCluster.getId(), fsCluster);
            if (FsClusterShared.fsClientMap.get(fsCluster.getId()) == null) {
                Client client = new Client();
                if (tryConnect(client, fsCluster)) {
                    //将连接上的client和fsCluster均存入临界资源  fsCluster用于账号变更
                    FsClusterShared.fsClientMap.put(fsCluster.getId(), client);
                    FsClusterShared.fsClusterMap.put(fsCluster.getId(), fsCluster);
                }
            } else {
                if (FsClusterShared.fsClientMap.get(fsCluster.getId()).canSend()) {
                    //do nothing
                    FsCluster fsClusterOld = FsClusterShared.fsClusterMap.get(fsCluster.getId());
                    //只要账号信息有变更 更新旧的client连接句柄
                    if (!fsClusterOld.getIp().equals(fsCluster.getIp()) || !fsClusterOld.getPort().equals(fsCluster.getPort()) || !fsClusterOld.getPassword().equals(fsCluster.getPassword())) {
                        //删除旧的连接信息
                        try {
                            CommandResponse commandResponse = FsClusterShared.fsClientMap.get(fsCluster.getId()).close();
                            log.info("zhuz: 关闭esl连接成功与否:" + commandResponse.isOk());
                        } catch (Exception e) {
                            log.error("zhuz: 关闭ESL连接失败---", e);
                        } finally {
                            FsClusterShared.fsClientMap.remove(fsCluster.getId());
                            FsClusterShared.fsClusterMap.remove(fsCluster.getId());
                        }
                        //更新连接信息
                        Client client = new Client();
                        if (tryConnect(client, fsCluster)) {
                            //将连接上的client和fsCluster均存入临界资源  fsCluster用于账号变更
                            FsClusterShared.fsClientMap.put(fsCluster.getId(), client);
                            FsClusterShared.fsClusterMap.put(fsCluster.getId(), fsCluster);
                        }
                    }
                } else {
                    if (tryConnect(FsClusterShared.fsClientMap.get(fsCluster.getId()), fsCluster)) {
                        FsClusterShared.fsClusterMap.put(fsCluster.getId(), fsCluster);
                    }
                }
            }
        }

        //获取当前服务在nacos注册中心注册的ip和端口
        if (ip == null) {
            try {
                JSONObject json = restTemplate.getForObject("http://127.0.0.1:" + port + "/actuator/nacos-discovery", JSONObject.class);
                Map<String, Object> obj = (Map<String, Object>) json.get("NacosDiscoveryProperties");
                ip = (String) obj.get("ip");
                log.info("zhuz: ip is " + ip);
            } catch (Exception e) {
                log.error("zhuz: 获取nacos注册中心中的本服务ip失败:", e);
            }
        }

        //存redis 并设置过期时间
        for (Integer id : FsClusterShared.fsClientMap.keySet()) {
            RBucket<Object> result = redissonClient.getBucket(FsClusterShared.FS_ESL_KEY + ip + "_" + port + "_" + id);
            /*if (!result.isExists()) {*/
            FsAttached fsAttached = new FsAttached();
            fsAttached.setLocal_ip(ip);
            fsAttached.setLocal_port(port);
            fsAttached.setId(id);
            fsAttached.setName(fsClusterMap.get(id).getName());
            fsAttached.setIp(fsClusterMap.get(id).getIp());
            fsAttached.setPort(fsClusterMap.get(id).getPort());
            fsAttached.setPassword(fsClusterMap.get(id).getPassword());
            fsAttached.setIsItOnLine(fsClusterMap.get(id).getIsItOnLine());
                /*fsAttached.setAddTime(fsClusterMap.get(id).getAddTime());
                fsAttached.setEditTime(fsClusterMap.get(id).getEditTime());*/
            result.set(GsonUtils.toJson(fsAttached), 30, TimeUnit.SECONDS);
            /*}*/
        }

        //清内存
        fsClusterList.clear();
        fsClusterList = null;
        fsClusterMap.clear();
        fsClusterMap = null;
    }

    /**
     * 尝试连接
     *
     * @param
     * @return
     */
    private Boolean tryConnect(Client client, FsCluster fsCluster) {
        try {
            client.connect(fsCluster.getIp(), Integer.parseInt(fsCluster.getPort()), fsCluster.getPassword(), 5);
            log.info("zhuz: " + fsCluster.getIp() + ":" + fsCluster.getPort() + " connect successful!");
            return true;
        } catch (Exception e) {
            log.warn("zhuz: " + fsCluster.getIp() + ":" + fsCluster.getPort() + " connect failed,reconnect 5s later!");
            return false;
        }
    }

    /**
     * 发送同步命令
     *
     * @param command
     * @param arg
     * @return
     */
    public static EslMessage sendSyncApiCommand(String command, String arg) {
        log.info("zhuz: sync - " + command);

        //fs集群中选一个
        List<Integer> il = new ArrayList<>();
        for (Integer key : FsClusterShared.fsClientMap.keySet()) {
            il.add(key);
        }

        Client client = FsClusterShared.fsClientMap.get(il.get(new Random().nextInt(il.size())));
        return client.canSend() ? client.sendSyncApiCommand(command, arg) : null;
    }

    /**
     * 发送异步命令
     *
     * @param command
     * @param arg
     * @return
     */
    public static String sendAsyncApiCommand(String command, String arg) {
        log.info("zhuz: async - " + command);

        //fs集群中选一个
        List<Integer> il = new ArrayList<>();
        for (Integer key : FsClusterShared.fsClientMap.keySet()) {
            il.add(key);
        }
        Client client = FsClusterShared.fsClientMap.get(il.get(new Random().nextInt(il.size())));

        return client.canSend() ? client.sendAsyncApiCommand(command, arg) : null;
    }

}
