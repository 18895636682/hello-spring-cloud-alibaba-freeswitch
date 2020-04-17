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
import java.util.Objects;

@Getter
@Setter
@ToString
@Table(name = "pushManage")
public class PushManage implements Serializable {
    private static final long serialVersionUID = 6900162323958312586L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "pushType")
    private String pushType;              //cdr:话单推送  popwin:弹屏推送  AI:AI决策(此时pushStrategy为exten无效)

    @Column(name = "pushStrategy")
    private String pushStrategy;          //consumerId:客户ID  gateway:外线  exten:分机

    @Column(name = "pushMethod")
    private String pushMethod;            //http:HTTP  https:HTTPS

    @Column(name = "pushUrl")
    private String pushUrl;

    @Column(name = "consumerId")
    private String consumerId;

    @Column(name = "gateway")
    private String gateway;

    @Column(name = "exten")
    private String exten;

    @Column(name = "remarks")
    private String remarks;

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

    public PushManage() {
    }

    public PushManage(String pushStrategy, String strategy) {
        if ("consumerId".equals(pushStrategy)) {
            this.consumerId = strategy;
        } else if ("gateway".equals(pushStrategy)) {
            this.gateway = strategy;
        } else if ("exten".equals(pushStrategy)) {
            this.exten = strategy;
        }
    }

    public PushManage(String pushType, String pushStrategy, String strategy) {
        this.pushType = pushType;
        this.pushStrategy = pushStrategy;

        if ("consumerId".equals(pushStrategy)) {
            this.consumerId = strategy;
        } else if ("gateway".equals(pushStrategy)) {
            this.gateway = strategy;
        } else if ("exten".equals(pushStrategy)) {
            this.exten = strategy;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PushManage)) return false;
        PushManage that = (PushManage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pushType, that.pushType) &&
                Objects.equals(pushStrategy, that.pushStrategy) &&
                Objects.equals(pushMethod, that.pushMethod) &&
                Objects.equals(pushUrl, that.pushUrl) &&
                Objects.equals(consumerId, that.consumerId) &&
                Objects.equals(gateway, that.gateway) &&
                Objects.equals(exten, that.exten) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(backup_field1, that.backup_field1) &&
                Objects.equals(backup_field2, that.backup_field2) &&
                Objects.equals(backup_field3, that.backup_field3) &&
                Objects.equals(backup_field4, that.backup_field4) &&
                Objects.equals(backup_field5, that.backup_field5) &&
                Objects.equals(backup_field6, that.backup_field6) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(editTime, that.editTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pushType, pushStrategy, pushMethod, pushUrl, consumerId, gateway, exten, remarks, backup_field1, backup_field2, backup_field3, backup_field4, backup_field5, backup_field6, addTime, editTime);
    }
}