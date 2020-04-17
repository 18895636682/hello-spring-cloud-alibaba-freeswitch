package com.zhuzhi.spring.cloud.alibaba.freeswitch.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mq.ChannelCreate;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.FsClusterShared;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = "zhuz.queue.create")
@Slf4j
public class FsCreateEventConsumer {

    @Qualifier("redisson")
    @Autowired
    private RedissonClient redissonClient;

    @RabbitHandler
    public void process(String str) {
        try {
            ChannelCreate channelCreate = JSON.parseObject(str, new TypeReference<ChannelCreate>() {
            });

            if (channelCreate != null) {
                String zhuz_same_call_uuid = channelCreate.getVariable_zhuz_same_call_uuid();
                String uniqueId = channelCreate.getUnique_id();

                if (StringUtils.isBlank(zhuz_same_call_uuid)) {
                    zhuz_same_call_uuid = uniqueId;
                }

                RSet<Object> rs = redissonClient.getSet(FsClusterShared.FS_CHANNEL_CREATE_KEY + zhuz_same_call_uuid);
                if (!rs.isExists() || rs.isEmpty()) {
                    rs.add(uniqueId);
                    //rs.addAsync(uniqueId);
                    rs.expire(1440, TimeUnit.MINUTES);
                } else {
                    rs.add(uniqueId);
                    //rs.addAsync(uniqueId);
                }

            } else {
                log.error("zhuz: --------channel create params is null------");
            }
        } catch (Exception e) {
            log.error("zhuz:error", e);
        }

    }

}
