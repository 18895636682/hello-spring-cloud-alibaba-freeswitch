package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mq.ChannelHangup;
import org.springframework.transaction.annotation.Transactional;

public interface CdrService {
    /**
     * 保存单通道话单
     *
     * @param channelCdr
     */
    @Transactional
    public void saveChannelCdr(ChannelHangup channelHangup);

    /**
     * 保存最终话单
     *
     * @param ZhuzCdr
     */
    @Transactional
    public void saveZhuzCdr(String zhuz_same_call_uuid);
}
