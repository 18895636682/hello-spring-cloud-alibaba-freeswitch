package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Data
@Setter
@Getter
@ToString
public class FsAttached extends FsCluster implements Serializable {

    private static final long serialVersionUID = 6815150537987138686L;

    private String local_ip;
    private String local_port;

    public FsAttached() {
    }

}
