package com.zhuzhi.spring.cloud.alibaba.freeswitch.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    //#Redisson配置
    //singleServerConfig:
    @Value("${singleServerConfig.address}")
    private String address;

    //address: "redis://192.168.1.65:6379"
    @Value("${singleServerConfig.password}")
    private String password;
    //password: cs2020

    @Value("${singleServerConfig.clientName}")
    private String clientName;
    //clientName: null

    //database: 0 #选择使用哪个数据库0~15
    @Value("${singleServerConfig.db.db1}")
    private Integer db1;

    @Value("${singleServerConfig.db.db2}")
    private Integer db2;

    @Value("${singleServerConfig.idleConnectionTimeout}")
    private Integer idleConnectionTimeout;
    //idleConnectionTimeout: 10000

    @Value("${singleServerConfig.pingTimeout}")
    private Integer pingTimeout;
    //pingTimeout: 1000

    @Value("${singleServerConfig.connectTimeout}")
    private Integer connectTimeout;
    //connectTimeout: 10000

    @Value("${singleServerConfig.timeout}")
    private Integer timeout;
    //timeout: 3000

    @Value("${singleServerConfig.retryAttempts}")
    private Integer retryAttempts;
    //retryAttempts: 3

    @Value("${singleServerConfig.retryInterval}")
    private Integer retryInterval;
    //retryInterval: 1500

    @Value("${singleServerConfig.reconnectionTimeout}")
    private Integer reconnectionTimeout;
    //reconnectionTimeout: 3000

    @Value("${singleServerConfig.failedAttempts}")
    private Integer failedAttempts;
    //failedAttempts: 3

    @Value("${singleServerConfig.subscriptionsPerConnection}")
    private Integer subscriptionsPerConnection;
    //subscriptionsPerConnection: 5

    @Value("${singleServerConfig.subscriptionConnectionMinimumIdleSize}")
    private Integer subscriptionConnectionMinimumIdleSize;
    //subscriptionConnectionMinimumIdleSize: 1

    @Value("${singleServerConfig.subscriptionConnectionPoolSize}")
    private Integer subscriptionConnectionPoolSize;
    //subscriptionConnectionPoolSize: 50

    @Value("${singleServerConfig.connectionMinimumIdleSize}")
    private Integer connectionMinimumIdleSize;
    //connectionMinimumIdleSize: 32

    @Value("${singleServerConfig.connectionPoolSize}")
    private Integer connectionPoolSize;
    //connectionPoolSize: 64

    @Value("${singleServerConfig.dnsMonitoringInterval}")
    private Integer dnsMonitoringInterval;
    //dnsMonitoringInterval: 5000
    //#dnsMonitoring: false

    @Value("${threads}")
    private Integer threads;
    //threads: 0

    @Value("${nettyThreads}")
    private Integer nettyThreads;
    //nettyThreads: 0
    //nettyThreads: 0
    //transportMode: "NIO"*/

    @Bean(name = "redisson")
    public RedissonClient redisson() throws IOException {
        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        Config config = new Config();
        config.setThreads(threads);
        config.setNettyThreads(nettyThreads);
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress(address)
                .setPassword(password)
                .setClientName(clientName)
                .setDatabase(db1)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setConnectTimeout(connectTimeout)
                .setTimeout(timeout)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setReconnectionTimeout(reconnectionTimeout)
                .setFailedAttempts(failedAttempts)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDnsMonitoringInterval(dnsMonitoringInterval);

        return Redisson.create(config);
    }

    @Bean(name = "redisson1")
    public RedissonClient redisson1() throws IOException {
        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        Config config = new Config();
        config.setThreads(threads);
        config.setNettyThreads(nettyThreads);
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress(address)
                .setPassword(password)
                .setClientName(clientName)
                .setDatabase(db2)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setConnectTimeout(connectTimeout)
                .setTimeout(timeout)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setReconnectionTimeout(reconnectionTimeout)
                .setFailedAttempts(failedAttempts)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDnsMonitoringInterval(dnsMonitoringInterval);

        return Redisson.create(config);
    }
}
