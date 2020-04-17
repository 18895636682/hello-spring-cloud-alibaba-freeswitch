package com.zhuzhi.spring.cloud.alibaba.freeswitch.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqPageInfo implements Serializable {

    private static final long serialVersionUID = 7623046496248389480L;

    private String page;

    private String limit;

    private String varLable;

    private String varName;

    private String token;

}
