package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.impl;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ChannelCdr;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.ChannelCdrMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.ChannelCdrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChannelCdrServiceImpl implements ChannelCdrService {

    @Resource
    private ChannelCdrMapper channelCdrMapper;

    @Override
    public int create(ChannelCdr channelCdr) {
        return channelCdrMapper.insertSelective(channelCdr);
    }

    @Override
    public List<ChannelCdr> findListByZhuzSameCallUuid(String zhuz_same_call_uuid) {
        ChannelCdr channelCdr = new ChannelCdr();
        channelCdr.setVariable_zhuz_same_call_uuid(zhuz_same_call_uuid);
        return channelCdrMapper.select(channelCdr);
    }
}
