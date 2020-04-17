package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ZhuzCdr;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.cdr.ZhuzCdrSearch;

public interface ZhuzCdrService {

    int create(ZhuzCdr zhuzCdr);

    PageInfo<ZhuzCdr> listByPage(ZhuzCdrSearch zhuzCdrSearch);
}
