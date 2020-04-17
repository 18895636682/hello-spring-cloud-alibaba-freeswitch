package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.dial;

import lombok.Data;

import java.io.Serializable;

@Data
public class AutoDialMeta implements Serializable {

    private static final long serialVersionUID = -6921440934120603174L;

    private String authkey;
    private String sid;
    private String tel;
    private String gateway;
    private String dest;
    private String ext_field;
    private String ignore_early_media;
}
