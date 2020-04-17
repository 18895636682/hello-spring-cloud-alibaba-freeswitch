package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.cdr;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class ZhuzCdrSearch extends ReqPageInfo implements Serializable {
    private static final long serialVersionUID = 8766051453206097717L;

    private String callerNumber;

    private String calleeNumber;
}
