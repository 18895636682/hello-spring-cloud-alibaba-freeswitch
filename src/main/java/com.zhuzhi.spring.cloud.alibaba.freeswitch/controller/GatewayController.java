package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Gateway;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.gateway.GatewaySearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.GatewayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/gateway")
@Slf4j
@Api(tags = "FS外线管理")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @RequestMapping(value = "/addGateway", method = RequestMethod.POST)
    @ApiOperation("FS外线增改")
    public BaseResponse<?> addGateway(@RequestBody Gateway gateway) {

        int success = -1;
        if (gateway.getId() == null) {
            success = gatewayService.create(gateway);
        } else {
            success = gatewayService.update(gateway);
        }

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            String message = "操作失败~_~";
            if (success == -100) {
                message = "呼入ivr不能为空";
            } else if (success == -101) {
                message = "呼入分机不能为空";
            } else if (success == -101) {
                message = "呼入队列不能为空";
            }
            return BaseResponse.failed(message);
        }
    }

    @RequestMapping(value = "/deleteGateway", method = RequestMethod.POST)
    @ApiOperation("FS外线删除")
    public BaseResponse<?> deleteGateway(@RequestBody Gateway gateway) {

        int success = -1;
        success = gatewayService.delete(gateway);

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("FS外线查询")
    public PageResponse<?> listByPage(@RequestBody GatewaySearch gatewaySearch) {
        PageInfo<Gateway> pageInfoGateway = gatewayService.listByPage(gatewaySearch);
        return PageResponse.successPageData(pageInfoGateway.getList(), pageInfoGateway.getTotal(), pageInfoGateway.getPageNum());
    }
}
