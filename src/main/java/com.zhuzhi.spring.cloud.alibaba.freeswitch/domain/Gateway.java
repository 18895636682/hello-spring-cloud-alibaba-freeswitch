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
@Table(name = "gateway")
public class Gateway implements Serializable {

    private static final long serialVersionUID = -4117992333617686263L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "areacode")
    private String areacode;

    @Column(name = "realm")
    private String realm;

    @Column(name = "`password`")
    private String password;

    @Column(name = "register")
    private String register;

    @Column(name = "port")
    private String port;

    @Column(name = "timeout")
    private String timeout;

    @Column(name = "incoming_type")
    private String incoming_type;

    @Column(name = "incoming_ivr")
    private String incoming_ivr;

    @Column(name = "incoming_exten")
    private String incoming_exten;

    @Column(name = "incoming_callcenter")
    private String incoming_callcenter;

    @Column(name = "effective_caller_id_name")
    private String effective_caller_id_name;

    @Column(name = "force_effective_caller_id_name")
    private String force_effective_caller_id_name;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "backup_field1")
    private String backupField1;

    @Column(name = "backup_field2")
    private String backupField2;

    @Column(name = "backup_field3")
    private String backupField3;

    @Column(name = "backup_field4")
    private String backupField4;

    @Column(name = "backup_field5")
    private String backupField5;

    @Column(name = "backup_field6")
    private String backupField6;

    @Column(name = "addTime")
    private Date addtime;

    @Column(name = "editTime")
    private Date edittime;

    public Gateway() {
    }

    public Gateway(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Gateway{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", areacode='" + areacode + '\'' +
                ", realm='" + realm + '\'' +
                ", password='" + password + '\'' +
                ", register='" + register + '\'' +
                ", port='" + port + '\'' +
                ", timeout='" + timeout + '\'' +
                ", incoming_type='" + incoming_type + '\'' +
                ", incoming_ivr='" + incoming_ivr + '\'' +
                ", incoming_exten='" + incoming_exten + '\'' +
                ", incoming_callcenter='" + incoming_callcenter + '\'' +
                ", effective_caller_id_name='" + effective_caller_id_name + '\'' +
                ", force_effective_caller_id_name='" + force_effective_caller_id_name + '\'' +
                ", remarks='" + remarks + '\'' +
                ", backupField1='" + backupField1 + '\'' +
                ", backupField2='" + backupField2 + '\'' +
                ", backupField3='" + backupField3 + '\'' +
                ", backupField4='" + backupField4 + '\'' +
                ", backupField5='" + backupField5 + '\'' +
                ", backupField6='" + backupField6 + '\'' +
                ", addtime=" + addtime +
                ", edittime=" + edittime +
                '}';
    }
}