package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Global;

public interface GlobalService {


    int create(Global global);

    int update(Global global);

    PageInfo<Global> listByPage();

    Global getGlobal();
}
