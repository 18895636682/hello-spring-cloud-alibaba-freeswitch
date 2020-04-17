package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Table(name = "sip_registrations")
public class SipRegistrations implements Serializable {
    private static final long serialVersionUID = 8680506647635048856L;

    @Column(name = "call_id")
    private String call_id;

    @Column(name = "sip_user")
    private String sip_user;

    @Column(name = "sip_host")
    private String sip_host;

    @Column(name = "presence_hosts")
    private String presence_hosts;

    @Column(name = "contact")
    private String contact;

    @Column(name = "`status`")
    private String status;

    @Column(name = "ping_status")
    private String ping_status;

    @Column(name = "ping_count")
    private Integer ping_count;

    @Column(name = "ping_time")
    private Long ping_time;

    @Column(name = "force_ping")
    private Integer force_ping;

    @Column(name = "rpid")
    private String rpid;

    @Column(name = "expires")
    private Long expires;

    @Column(name = "ping_expires")
    private Integer ping_expires;

    @Column(name = "user_agent")
    private String user_agent;

    @Column(name = "server_user")
    private String server_user;

    @Column(name = "server_host")
    private String server_host;

    @Column(name = "profile_name")
    private String profile_name;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "network_ip")
    private String network_ip;

    @Column(name = "network_port")
    private String network_port;

    @Column(name = "sip_username")
    private String sip_username;

    @Column(name = "sip_realm")
    private String sip_realm;

    @Column(name = "mwi_user")
    private String mwi_user;

    @Column(name = "mwi_host")
    private String mwi_host;

    @Column(name = "orig_server_host")
    private String orig_server_host;

    @Column(name = "orig_hostname")
    private String orig_hostname;

    @Column(name = "sub_host")
    private String sub_host;

    public SipRegistrations() {
    }

    public SipRegistrations(String sip_user) {
        this.sip_user = sip_user;
    }
}