package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Gateway;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.gateway.GatewaySearch;

public interface GatewayService {

    int create(Gateway gateway);

    int update(Gateway gateway);

    int delete(Gateway gateway);

    PageInfo<Gateway> listByPage(GatewaySearch gatewaySearch);

    Gateway findByUserName(Gateway gateway);
}
