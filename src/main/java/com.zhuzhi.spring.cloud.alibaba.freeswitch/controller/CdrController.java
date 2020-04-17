package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Global;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ZhuzCdr;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.cdr.ZhuzCdrSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.GlobalService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.ZhuzCdrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cdr/")
@Slf4j
@Api(tags = "FS话单处理")
public class CdrController {

    @Autowired
    private ZhuzCdrService zhuzCdrService;

    @Autowired
    private GlobalService globalService;

    /*@RequestMapping(value = "/jsonCdr", method = {RequestMethod.POST})
    @ApiOperation("处理json格式的话单")
    public BaseResponse<?> jsonCdr(Root root) {
        Cdr cdr = JSON.parseObject(root.getCdr(), new TypeReference<Cdr>() {
        });
        log.info("zhuz: " + cdr);
        return BaseResponse.success("操作成功0.0");
    }*/

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("话单查询")
    public PageResponse<?> listByPage(@RequestBody ZhuzCdrSearch zhuzCdrSearch) {
        Global global = globalService.getGlobal();
        PageInfo<ZhuzCdr> pageInfoZhuzCdr = zhuzCdrService.listByPage(zhuzCdrSearch);
        if (global != null && StringUtils.isNotBlank(global.getRecordGetMethod()) && StringUtils.isNotBlank(global.getRecordGetUrl())) {
            if (pageInfoZhuzCdr.getList() != null && pageInfoZhuzCdr.getList().size() > 0) {
                for (ZhuzCdr zhuzCdr : pageInfoZhuzCdr.getList()) {
                    //封装录音试听全URL
                    zhuzCdr.setRecordFilename(global.getRecordGetUrl() + zhuzCdr.getRecordFilename());
                }
            }
        }
        return PageResponse.successPageData(pageInfoZhuzCdr.getList(), pageInfoZhuzCdr.getTotal(), pageInfoZhuzCdr.getPageNum());
    }
}
