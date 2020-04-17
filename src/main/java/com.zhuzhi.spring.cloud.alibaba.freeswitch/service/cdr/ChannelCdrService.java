package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ChannelCdr;

import java.util.List;

public interface ChannelCdrService {

    int create(ChannelCdr channelCdr);

    List<ChannelCdr> findListByZhuzSameCallUuid(String zhuz_same_call_uuid);
}
