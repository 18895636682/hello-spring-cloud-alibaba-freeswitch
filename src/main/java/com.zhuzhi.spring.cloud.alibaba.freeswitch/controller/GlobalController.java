package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Global;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.GlobalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/global/")
@Slf4j
@Api(tags = "FS全局配置")
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @RequestMapping(value = "/addGlobal", method = RequestMethod.POST)
    @ApiOperation("FS全局配置增改")
    public BaseResponse<?> addGlobal(@RequestBody Global global) {

        int success = -1;
        if (global.getId() == null) {
            success = globalService.create(global);
        } else {
            success = globalService.update(global);
        }

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("FS全局配置查询")
    public PageResponse<?> listByPage() {
        PageInfo<Global> pageInfoGlobal = globalService.listByPage();
        Global global = new Global();
        if (pageInfoGlobal.getList() != null && !pageInfoGlobal.getList().isEmpty()) {
            global = pageInfoGlobal.getList().get(0);
        }
        return PageResponse.successPageData(global, pageInfoGlobal.getTotal(), pageInfoGlobal.getPageNum());
    }
}
