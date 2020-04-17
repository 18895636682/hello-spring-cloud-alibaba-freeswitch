package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.fscluster;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import lombok.Data;
import java.io.Serializable;

@Data
public class FsCluterSearch extends ReqPageInfo implements Serializable {

    private static final long serialVersionUID = -1475780076302437066L;

    private String name;

    private String ip;
}
