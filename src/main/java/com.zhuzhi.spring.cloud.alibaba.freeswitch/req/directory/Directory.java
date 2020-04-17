package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.directory;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Data
@Setter
@Getter
@ToString
public class Directory implements Serializable {
    private static final long serialVersionUID = -6833509931403298696L;
    private String ip;
    private String user;
    private String domain;
    private String action;
}
