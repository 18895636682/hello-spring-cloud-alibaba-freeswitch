package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.PushManage;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.pushmanage.PushManageSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.PushManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pushmanage/")
@Slf4j
@Api(tags = "推送管理")
public class PushManageController {

    @Autowired
    private PushManageService pushManageService;

    @RequestMapping(value = "/addPushManage", method = RequestMethod.POST)
    @ApiOperation("推送管理增改")
    public BaseResponse<?> addPushManage(@RequestBody PushManage pushManage) {

        int success = -1;
        if (pushManage.getId() == null) {
            success = pushManageService.create(pushManage);
        } else {
            success = pushManageService.update(pushManage);
        }

        if (success == -100) {
            return BaseResponse.failed("客户ID不能为空~_~");
        } else if (success == -101) {
            return BaseResponse.failed("外线不能为空~_~");
        } else if (success == -102) {
            return BaseResponse.failed("分机不能为空~_~");
        } else if (success == -103) {
            return BaseResponse.failed("推送规则已存在~_~");
        } else if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }

    }

    @RequestMapping(value = "/deletePushManage", method = RequestMethod.POST)
    @ApiOperation("推送管理删除")
    public BaseResponse<?> deletePushManage(@RequestBody PushManage pushManage) {

        int success = -1;
        success = pushManageService.delete(pushManage);

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("推送管理查询")
    public PageResponse<?> listByPage(@RequestBody PushManageSearch pushManageSearch) {
        PageInfo<PushManage> pageInfoPushManage = pushManageService.listByPage(pushManageSearch);
        return PageResponse.successPageData(pageInfoPushManage.getList(), pageInfoPushManage.getTotal(), pageInfoPushManage.getPageNum());
    }
}
