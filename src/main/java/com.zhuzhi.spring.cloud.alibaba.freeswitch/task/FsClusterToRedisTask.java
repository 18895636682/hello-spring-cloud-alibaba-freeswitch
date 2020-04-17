package com.zhuzhi.spring.cloud.alibaba.freeswitch.task;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsCluster;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.FsClusterService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.FsClusterShared;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程*/
@Slf4j
public class FsClusterToRedisTask {

    @Qualifier("redisson")
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private FsClusterService fsClusterService;

    @Async
    @Scheduled(fixedDelay = 5000)  //间隔5秒
    public void addFsclusterToRedis() {

        /*List<FsCluster> fsClusterList = new ArrayList<>();
        try {
            fsClusterList = fsClusterService.byClusterList(new FsCluster("1"));
        } catch (Exception e) {
            log.error("zhuz: 数据库查询异常----", e);
            return;
        }

        for (Integer id:FsClusterShared.fsClientMap.keySet()) {

        }
        RBucket<String> keyObj = redissonClient.getBucket("k1" + string);
        keyObj.set(string);*/
    }
}
