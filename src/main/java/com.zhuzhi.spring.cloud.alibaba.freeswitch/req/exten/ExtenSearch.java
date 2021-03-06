package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExtenSearch extends ReqPageInfo implements Serializable {

    private static final long serialVersionUID = 2970397707679430678L;

    private String userId;
}
