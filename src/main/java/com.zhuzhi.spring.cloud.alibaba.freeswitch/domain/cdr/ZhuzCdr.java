package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "zhuz_cdr")
public class ZhuzCdr implements Serializable {
    private static final long serialVersionUID = 8715723128827093948L;

    @Column(name = "id")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "directiontype")
    private Integer directiontype;

    @Column(name = "dialtype")
    private Integer dialtype;

    @Column(name = "isoriginate")
    private Boolean isOriginate;

    @Column(name = "isCaller")
    private Boolean isCaller;

    @Column(name = "bridgestatustype")
    private Integer bridgeStatusType;

    @Column(name = "zhuzsamecalluuid")
    private String zhuzSameCallUuid;

    @Column(name = "extfield")
    private String extField;

    @Column(name = "callerani")
    private String callerani;

    @Column(name = "callerid")
    private String callerid;

    @Column(name = "callernumber")
    private String callerNumber;

    @Column(name = "calleenumber")
    private String calleeNumber;

    @Column(name = "destinationnumber")
    private String destinationNumber;

    @Column(name = "dialedextension")
    private String dialedextension;

    @Column(name = "origcallernumer")
    private String origcallernumer;

    @Column(name = "digitsdialed")
    private String digitsdialed;

    @Column(name = "vasgateway")
    private String vasgateway;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "answertime")
    private Date answerTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "bridgetime")
    private Date bridgeTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "hanguptime")
    private Date hangupTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "eventtime")
    private Date eventtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "queueansweredtime")
    private Date queueansweredtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "queuejoinedtime")
    private Date queuejoinedtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "otherlegcreatetime")
    private Date otherlegcreatetime;

    @Column(name = "fullduration")
    private Integer fullDuration;

    @Column(name = "realduration")
    private Integer realduration;

    @Column(name = "routesec")
    private Integer routesec;

    @Column(name = "ringingsec")
    private Integer ringingsec;

    @Column(name = "bridgesec")
    private Integer bridgeSec;

    @Column(name = "holdaccumsec")
    private Integer holdaccumsec;

    @Column(name = "recordsec")
    private Integer recordsec;

    @Column(name = "samecalluuid")
    private String samecalluuid;

    @Column(name = "uniqueidzhuz")
    private String uniqueidzhuz;

    @Column(name = "calleruniquid")
    private String callerUniquId;

    @Column(name = "calleeuniquid")
    private String calleeUniquId;

    @Column(name = "callerchannelname")
    private String callerchannelname;

    @Column(name = "calleechannelname")
    private String calleechannelname;

    @Column(name = "hangupcause")
    private String hangupCause;

    @Column(name = "hangupdisposition")
    private String hangupdisposition;

    @Column(name = "ccagent")
    private String ccagent;

    @Column(name = "ccqueue")
    private String ccqueue;

    @Column(name = "ccside")
    private String ccside;

    @Column(name = "recordfilename")
    private String recordFilename;

    @Column(name = "callercontext")
    private String callercontext;

    @Column(name = "lastapp")
    private String lastapp;

    @Column(name = "lastarg")
    private String lastarg;

    @Column(name = "callerreadcodec")
    private String callerreadcodec;

    @Column(name = "calleereadcodec")
    private String calleereadcodec;

    @Column(name = "callerwritecodec")
    private String callerwritecodec;

    @Column(name = "calleewritecodec")
    private String calleewritecodec;

    @Column(name = "callerdtmftype")
    private String callerdtmftype;

    @Column(name = "calleedtmftype")
    private String calleedtmftype;

    @Column(name = "callersipuseragent")
    private String callersipuseragent;

    @Column(name = "calleesipuseragent")
    private String calleesipuseragent;

    @Column(name = "callersipnetworkip")
    private String callersipnetworkip;

    @Column(name = "callersipnetworkport")
    private String callersipnetworkport;

    @Column(name = "calleesipnetworkip")
    private String calleesipnetworkip;

    @Column(name = "calleesipnetworkport")
    private String calleesipnetworkport;

    @Column(name = "callerremotemediaip")
    private String callerremotemediaip;

    @Column(name = "callerremotemediaport")
    private String callerremotemediaport;

    @Column(name = "calleeremotemediaip")
    private String calleeremotemediaip;

    @Column(name = "calleeremotemediaport")
    private String calleeremotemediaport;

    @Column(name = "consumerId")
    private String consumerId;

    public ZhuzCdr() {
    }

    public ZhuzCdr(String callerNumber, String calleeNumber) {
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
    }
}