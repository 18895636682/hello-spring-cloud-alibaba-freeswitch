package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.pushmanage;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class PushManageSearch extends ReqPageInfo implements Serializable {
    private static final long serialVersionUID = 3052473167693787196L;

    private String cge;
}
