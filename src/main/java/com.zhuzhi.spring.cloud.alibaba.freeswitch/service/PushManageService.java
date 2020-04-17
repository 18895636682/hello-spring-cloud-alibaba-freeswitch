package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.PushManage;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.pushmanage.PushManageSearch;

import java.util.List;

public interface PushManageService {


    int create(PushManage pushManage);

    int update(PushManage pushManage);

    int delete(PushManage pushManage);

    PageInfo<PushManage> listByPage(PushManageSearch pushManageSearch);

    List<PushManage> selectAll();
}
