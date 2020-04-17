package com.zhuzhi.spring.cloud.alibaba.freeswitch.shared;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.PushManage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PushManageShared {

    //将推送规则存入内存  供当前服务调用
    public static volatile Map<Integer, PushManage> pushManageMap = new ConcurrentHashMap<Integer, PushManage>();

    //将推送规则存入redis  供所有程序调用  固定key如下
    public static volatile String push_manage_key = "PUSH_MANAGE_KEY:";

}
