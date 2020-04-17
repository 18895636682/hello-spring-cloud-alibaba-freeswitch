package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.asr;

import lombok.Data;

@Data
public class AsrMeta {
    private String recordFileName;

    public AsrMeta() {
    }

    public AsrMeta(String recordFileName) {
        this.recordFileName = recordFileName;
    }
}