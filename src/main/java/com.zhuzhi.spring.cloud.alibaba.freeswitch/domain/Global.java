package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "`global`")
public class Global implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "popWinMethod")
    private String popWinMethod;

    @Column(name = "popWinUrl")
    private String popWinUrl;

    @Column(name = "aiVoicePushMethod")
    private String aiVoicePushMethod;

    @Column(name = "aiVoicePushUrl")
    private String aiVoicePushUrl;

    @Column(name = "recordGetMethod")
    private String recordGetMethod;

    @Column(name = "recordGetUrl")
    private String recordGetUrl;

    @Column(name = "cdrPushMethod")
    private String cdrPushMethod;

    @Column(name = "cdrPushUrl")
    private String cdrPushUrl;

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

    private static final long serialVersionUID = 1L;
}