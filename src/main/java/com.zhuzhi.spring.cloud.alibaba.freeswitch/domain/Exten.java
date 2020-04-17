package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Table(name = "exten")
public class Exten implements Serializable {

    private static final long serialVersionUID = -4718870325403783666L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "`password`")
    private String password;

    @Column(name = "callgroup")
    private String callgroup;

    @Column(name = "effective_caller_id_name")
    private String effective_caller_id_name;

    @Column(name = "cdr_push_url")
    private String cdr_push_url;

    @Column(name = "pop_win_url")
    private String pop_win_url;

    @Column(name = "businessType")
    private String businessType;

    @Column(name = "enabled")
    private String enabled;

    @Column(name = "enabled_hand")
    private String enabled_hand;

    @Column(name = "exten_user")
    private String exten_user;

    @Column(name = "gateway")
    private String gateway;

    @Column(name = "timeout")
    private String timeout;

    @Column(name = "prephone")
    private String prephone;

    @Column(name = "ccrphone")
    private String ccrphone;

    @Column(name = "aftphone")
    private String aftphone;

    @Column(name = "backup_field1")
    private String backup_field1;

    @Column(name = "backup_field2")
    private String backup_field2;

    @Column(name = "backup_field3")
    private String backup_field3;

    @Column(name = "backup_field4")
    private String backup_field4;

    @Column(name = "backup_field5")
    private String backup_field5;

    @Column(name = "backup_field6")
    private String backup_field6;

    @Column(name = "addTime")
    private Date addTime;

    @Column(name = "editTime")
    private Date editTime;

    public Exten() {
    }

    public Exten(String userId) {
        this.userId = userId;
    }
}