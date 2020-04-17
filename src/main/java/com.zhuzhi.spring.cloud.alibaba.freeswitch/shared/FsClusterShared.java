package com.zhuzhi.spring.cloud.alibaba.freeswitch.shared;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsCluster;
import org.freeswitch.esl.client.inbound.Client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FsClusterShared {

    /**
     * 存储不同esl账号id对应的FsClient实例
     */
    public static volatile Map<Integer, Client> fsClientMap = new ConcurrentHashMap<Integer, Client>();

    /**
     * 存储esl账号id对应的最新的可连接的esl账号
     */
    public static volatile Map<Integer, FsCluster> fsClusterMap = new ConcurrentHashMap<Integer, FsCluster>();

    /**
     * 存储redis中各实例注册的esl连接的key头
     */
    public static volatile String FS_ESL_KEY = "FS_ESL_KEY:";

    /**
     * 存储redis中通道创建事件中相关联通道的uuid 为话单处理服务的key头
     */
    public static volatile String FS_CHANNEL_CREATE_KEY = "FS_CHANNEL_CREATE_KEY:";
}
