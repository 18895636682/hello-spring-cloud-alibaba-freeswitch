package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.AiError;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.aiError.AiErrorSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.AiErrorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ai/")
@Slf4j
@Api(tags = "POWER报错推送")
public class AiErrorController {

    @Autowired
    private AiErrorService  aiErrorService;

    @RequestMapping(value = "/aiError", method = RequestMethod.POST)
    @ApiOperation("POWER报错推送查询")
    public PageResponse<?> aiError(@RequestBody AiErrorSearch aiErrorSearch) {
        PageInfo<AiError> pageInfoAiError = aiErrorService.listByPage(aiErrorSearch);
        return PageResponse.successPageData(pageInfoAiError.getList(), pageInfoAiError.getTotal(), pageInfoAiError.getPageNum());
    }
}
