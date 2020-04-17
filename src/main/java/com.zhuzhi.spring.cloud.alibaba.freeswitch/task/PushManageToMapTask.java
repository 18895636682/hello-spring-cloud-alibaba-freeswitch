package com.zhuzhi.spring.cloud.alibaba.freeswitch.task;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.PushManage;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.pushmanage.PushManageSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.FsClusterService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.PushManageService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.FsClusterShared;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.MobilelocShared;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.PushManageShared;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
@Slf4j
public class PushManageToMapTask {

    @Autowired
    private PushManageService pushManageService;

    @Qualifier("redisson")
    @Autowired
    private RedissonClient redissonClient;

    @Async
    @Scheduled(fixedDelay = 10000)  //间隔5秒
    public void addPushManageToMap() {
        try {
            //获取有效的策略
            List<PushManage> pushManageList = new ArrayList<>();
            List<PushManage> pushManageAllList = pushManageService.selectAll();
            if (pushManageAllList != null && pushManageAllList.size() > 0) {
                for (PushManage pushManage : pushManageAllList) {
                    if (StringUtils.isNotBlank(pushManage.getPushMethod()) && StringUtils.isNotBlank(pushManage.getPushUrl())) {
                        pushManageList.add(pushManage);
                    }
                }
                if (pushManageAllList != null) {
                    pushManageAllList.clear();
                }
            }

            //删除内存中不存在的key
            Map<Integer, PushManage> pushManageMap = new HashMap<>();
            pushManageMap.putAll(PushManageShared.pushManageMap);
            for (PushManage pushManage : pushManageMap.values()) {
                if (!pushManageList.contains(pushManage)) {
                    PushManageShared.pushManageMap.remove(pushManage.getId());
                }
            }
            //清缓存
            if (pushManageMap != null) {
                pushManageMap.clear();
            }

            //删除redis中不存在的key 只处理AI类型
            Iterable<String> push_manage_redis_ai_list = redissonClient.getKeys().getKeysByPattern(PushManageShared.push_manage_key + "*");
            List<String> pList = new ArrayList<>();
            for (PushManage pushManage : pushManageList) {
                if ("AI".equals(pushManage.getPushType())) {
                    if ("consumerId".equals(pushManage.getPushStrategy())) {
                        pList.add(PushManageShared.push_manage_key + pushManage.getPushMethod() + "_consumerId_" + pushManage.getConsumerId());
                    } else if ("gateway".equals(pushManage.getPushStrategy())) {
                        pList.add(PushManageShared.push_manage_key + pushManage.getPushMethod() + "_gateway_" + pushManage.getGateway());
                    }
                }
            }
            for (String str : push_manage_redis_ai_list) {
                if (!pList.contains(str)) {
                    redissonClient.getBucket(str).delete();
                }
            }
            //清缓存
            if (push_manage_redis_ai_list != null) {
                push_manage_redis_ai_list = null;
            }
            if (pList != null) {
                pList.clear();
            }


            //新增或更新已存在的key
            if (pushManageList != null && pushManageList.size() > 0) {
                for (PushManage pushManage : pushManageList) {

                    //处理本地内存增改
                    if (pushManage.equals(PushManageShared.pushManageMap.get(pushManage.getId()))) {
                        //log.info("zhuz:pushManage无变化!!不需要更新!!");
                    } else {
                        PushManageShared.pushManageMap.put(pushManage.getId(), pushManage);
                    }

                    //处理redis增改
                    if ("AI".equals(pushManage.getPushType())) {
                        if ("consumerId".equals(pushManage.getPushStrategy())) {
                            RBucket<Object> result = redissonClient.getBucket(PushManageShared.push_manage_key + pushManage.getPushMethod() + "_consumerId_" + pushManage.getConsumerId());
                            if (!result.isExists() || !pushManage.getPushUrl().equals(result.get() + "")) {
                                result.set(pushManage.getPushUrl());
                            }
                        } else if ("gateway".equals(pushManage.getPushStrategy())) {
                            RBucket<Object> result = redissonClient.getBucket(PushManageShared.push_manage_key + pushManage.getPushMethod() + "_gateway_" + pushManage.getGateway());
                            if (!result.isExists() || !pushManage.getPushUrl().equals(result.get() + "")) {
                                result.set(pushManage.getPushUrl());
                            }
                        }
                    }
                }
            }
            //清缓存
            if (pushManageList != null) {
                pushManageList.clear();
            }
        } catch (Exception e) {
            log.error("zhuz: 处理异常----", e);
            return;
        }

        //log.info("zhuz: PushManageShared.pushManageMap is " + PushManageShared.pushManageMap);
    }
}
