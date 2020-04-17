package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.mobileloc;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;
import java.io.Serializable;

@Data
public class MobileLocSearch extends ReqPageInfo implements Serializable {

    private static final long serialVersionUID = -6131957072012364239L;
    private String mobileNumber;
}
