package com.zhuzhi.spring.cloud.alibaba.freeswitch.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mq.ChannelHangup;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.CdrService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.FsClusterShared;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "zhuz.queue.hangup")
@Slf4j
public class FsHangupEventConsumer {

    @Autowired
    @Qualifier("redisson")
    private RedissonClient redissonClient;

    @Autowired
    private CdrService cdrService;

    @RabbitHandler
    public void process(String str) {

        try {
            ChannelHangup channelHangup = JSON.parseObject(str, new TypeReference<ChannelHangup>() {
            });
            if (channelHangup != null) {

                //话单处理 先找到zhuz_same_call_uuid 没有则等于uniqueId
                String zhuz_same_call_uuid = channelHangup.getVariable_zhuz_same_call_uuid();
                String uniqueId = channelHangup.getUnique_id();
                if (zhuz_same_call_uuid == null) {
                    channelHangup.setVariable_zhuz_same_call_uuid(uniqueId);
                }

                //存单通道话单
                cdrService.saveChannelCdr(channelHangup);

                //取出redis中的zhuz_same_call_uuid关联的uniqueId集
                RSet<Object> rs = redissonClient.getSet(FsClusterShared.FS_CHANNEL_CREATE_KEY + zhuz_same_call_uuid);
                if (rs != null) {
                    rs.remove(uniqueId);
                    if (rs.readAll().size() == 0) {
                        //存最终话单
                        cdrService.saveZhuzCdr(zhuz_same_call_uuid);

                        redissonClient.getKeys().deleteAsync(FsClusterShared.FS_CHANNEL_CREATE_KEY + zhuz_same_call_uuid);
                    }
                } else {
                    //存最终话单
                    cdrService.saveZhuzCdr(zhuz_same_call_uuid);
                    log.warn("zhuz: Can't find redissonClient from " + FsClusterShared.FS_CHANNEL_CREATE_KEY + " cache! zhuz_same_call_uuid:" + zhuz_same_call_uuid);
                }

            } else {
                log.error("zhuz: --------channel hangup params is null------");
            }

            //log.info("Receiver:" + channelHangup.toString());
        } catch (Exception e) {
            log.error("zhuz:error", e);
        }
    }
}
