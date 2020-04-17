package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.aiError;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;
import java.io.Serializable;

@Data
public class AiErrorSearch extends ReqPageInfo implements Serializable {
    private static final long serialVersionUID = 2331110455822015160L;

    private String callid;
    private String error_ip;
    private String startTime; //起始时间
    private String endTime; //结束时间
}
