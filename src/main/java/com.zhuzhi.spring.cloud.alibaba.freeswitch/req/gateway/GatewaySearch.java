package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.gateway;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class GatewaySearch extends ReqPageInfo implements Serializable {
    private static final long serialVersionUID = -4424912353943207304L;

    private String userName;
}
