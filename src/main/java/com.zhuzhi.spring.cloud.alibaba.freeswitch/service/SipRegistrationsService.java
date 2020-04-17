package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.SipRegistrations;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten.SipRegistrationsSearch;

public interface SipRegistrationsService {

    PageInfo<SipRegistrations> listByPage(SipRegistrationsSearch sipRegistrationsSearch);
}
