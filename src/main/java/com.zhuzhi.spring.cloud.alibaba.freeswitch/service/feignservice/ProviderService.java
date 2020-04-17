package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.feignservice;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mobileloc.MobileLoc;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.asr.AsrMeta;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.mobileloc.MobileLocSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.feignservice.fallback.ProviderServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-provider", fallback = ProviderServiceFallback.class)
public interface ProviderService {

    @GetMapping(value = "/api/mobileloc/list")
    PageResponse<?> listByPage(@RequestBody MobileLocSearch mobileLocSearch);

    @RequestMapping(value = "/api/mobileloc/selectOne", method = RequestMethod.POST)
    MobileLoc selectOne(@RequestBody MobileLoc mobileloc);

    @RequestMapping(value = "/api/asr/aliAsr", method = RequestMethod.POST)
    BaseResponse<String> aliAsr(@RequestBody AsrMeta asrMeta);
}
