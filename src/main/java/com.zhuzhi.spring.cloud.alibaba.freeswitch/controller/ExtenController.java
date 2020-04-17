package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Exten;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.SipRegistrations;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten.ExtenSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten.SipRegistrationsSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.ExtenService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.SipRegistrationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/exten")
@Slf4j
@Api(tags = "FS分机管理")
public class ExtenController {

    @Autowired
    private ExtenService extenService;

    @Autowired
    private SipRegistrationsService sipRegistrationsService;

    @RequestMapping(value = "/addExten", method = RequestMethod.POST)
    @ApiOperation("FS分机增改")
    public BaseResponse<?> addExten(@RequestBody Exten exten) {

        int success = -1;
        if (exten.getId() == null) {
            success = extenService.create(exten);
        } else {
            success = extenService.update(exten);
        }

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/deleteExten", method = RequestMethod.POST)
    @ApiOperation("FS分机删除")
    public BaseResponse<?> deleteExten(@RequestBody Exten exten) {

        int success = -1;
        success = extenService.delete(exten);

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("FS分机查询")
    public PageResponse<?> listByPage(@RequestBody ExtenSearch extenSearch) {
        PageInfo<Exten> pageInfoExten = extenService.listByPage(extenSearch);
        return PageResponse.successPageData(pageInfoExten.getList(), pageInfoExten.getTotal(), pageInfoExten.getPageNum());
    }


    @RequestMapping(value = "/sipRegistrations", method = RequestMethod.POST)
    @ApiOperation("FS分机注册状态查询")
    public PageResponse<?> sipRegistrations(@RequestBody SipRegistrationsSearch sipRegistrationsSearch) {
        PageInfo<SipRegistrations> pageInfoSipRegistrations = sipRegistrationsService.listByPage(sipRegistrationsSearch);
        return PageResponse.successPageData(pageInfoSipRegistrations.getList(), pageInfoSipRegistrations.getTotal(), pageInfoSipRegistrations.getPageNum());
    }
}
