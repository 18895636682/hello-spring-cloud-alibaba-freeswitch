package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Exten;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten.ExtenSearch;

public interface ExtenService {

    int create(Exten exten);

    int update(Exten exten);

    int delete(Exten exten);

    PageInfo<Exten> listByPage(ExtenSearch extenSearch);

    Exten findByExten(Exten exten);
}
