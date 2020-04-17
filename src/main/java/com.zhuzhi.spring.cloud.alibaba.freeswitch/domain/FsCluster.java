package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table(name = "fsCluster")
public class FsCluster implements Serializable {

    private static final long serialVersionUID = -1611392735878962155L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private String port;

    @Column(name = "`password`")
    private String password;

    @Column(name = "addTime")
    private Date addTime;

    @Column(name = "editTime")
    private Date editTime;

    @Column(name = "isItOnLine")
    private String isItOnLine;

    public FsCluster() {
    }

    public FsCluster(String isItOnLine) {
        this.isItOnLine = isItOnLine;
    }

    public FsCluster(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public FsCluster(String name, String ip, String port, String password, Date addTime, Date editTime, String isItOnLine) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.password = password;
        this.addTime = addTime;
        this.editTime = editTime;
        this.isItOnLine = isItOnLine;
    }
}