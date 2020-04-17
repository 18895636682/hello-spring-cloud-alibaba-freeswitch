package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.feignservice.fallback;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mobileloc.MobileLoc;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.asr.AsrMeta;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.mobileloc.MobileLocSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.feignservice.ProviderService;

public class ProviderServiceFallback implements ProviderService {

    @Override
    public PageResponse<?> listByPage(MobileLocSearch mobileLocSearch) {
        return PageResponse.fusing();
    }

    @Override
    public MobileLoc selectOne(MobileLoc mobileloc) {
        return new MobileLoc();
    }

    @Override
    public BaseResponse<String> aliAsr(AsrMeta asrMeta) {
        return BaseResponse.failed("feignser error");
    }
}
