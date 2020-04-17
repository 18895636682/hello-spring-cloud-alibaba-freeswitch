package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ZhuzCdrVo implements Serializable {
    private static final long serialVersionUID = -334750669114937147L;

    private String callerNumber;				//主叫号码
    private String destinationNumber;			//拨号方案目的号码
    private Integer bridgeStatusType;           //接通状态
    private String calleeNumber;				//被叫号码
    private Boolean isOriginate;                //呼叫方式{true为调用接口发起呼叫}
    private Integer bridgeSec;					//双方建立通话的时长
    private Integer fullDuration;				//通道生存时长【接口呼出时，从通道创建开始计时】
    private String recordFilename;				//录音文件名称
    private Date createTime;					//通道创建
    private Date answerTime;					//应答时间
    private Date hangupTime;					//挂断时间
    private Date bridgeTime;					//注意：该值每次转接，这个bridge时间都会发生变化
    private String callerUniquId;				//主叫唯一标识
    private String calleeUniquId;				//被叫唯一标识
    private String zhuzSameCallUuid;			//自定义的-唯一标识【如果该记录是根据主叫通道创建的，那么该值 = 主叫通道的 UniqueId, 否则 = 被叫通道的 UniqueId】
    private String extField;                    //扩展字段
    private String hangupCause;                 //挂断原因
    private String consumerId;                  //客户ID
    private String vasgateway;                  //线路号码
}
