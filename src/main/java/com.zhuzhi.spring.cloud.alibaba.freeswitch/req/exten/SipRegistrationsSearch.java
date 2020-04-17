package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;
import java.io.Serializable;

@Data
public class SipRegistrationsSearch extends ReqPageInfo implements Serializable {
    private static final long serialVersionUID = 2814826754385380031L;

    private String sip_user;
}
