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
@Table(name = "ai_error")
public class AiError implements Serializable {
    private static final long serialVersionUID = 985284450717495557L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "callid")
    private String callid;

    @Column(name = "callerid")
    private String callerid;

    @Column(name = "calleeid")
    private String calleeid;

    @Column(name = "flowdata")
    private String flowdata;

    @Column(name = "`notify`")
    private String notify;

    @Column(name = "errorcode")
    private String errorcode;

    @Column(name = "message")
    private String message;

    @Column(name = "error_ip")
    private String error_ip;

    @Column(name = "error_system_name")
    private String error_system_name;

    @Column(name = "modelId")
    private String modelId;

    @Column(name = "currentTime")
    private String currentTime;

    @Column(name = "addTime")
    private Date addTime;
}