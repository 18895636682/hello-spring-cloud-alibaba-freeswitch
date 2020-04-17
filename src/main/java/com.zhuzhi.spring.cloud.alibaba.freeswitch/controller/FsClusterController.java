package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsCluster;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.fscluster.FsCluterSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.FsClusterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/fscluster")
@Slf4j
@Api(tags = "FS集群管理")
public class FsClusterController {

    @Autowired
    private FsClusterService fsClusterService;

    @RequestMapping(value = "/addFsCluster", method = RequestMethod.POST)
    @ApiOperation("FS集群增改")
    public BaseResponse<?> addMobileloc(@RequestBody FsCluster fsCluster) {

        int success = -1;
        if (fsCluster.getId() == null) {
            success = fsClusterService.create(fsCluster);
        } else {
            success = fsClusterService.update(fsCluster);
        }

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/deleteFsCluster", method = RequestMethod.POST)
    @ApiOperation("FS集群删除")
    public BaseResponse<?> deleteMobileloc(@RequestBody FsCluster fsCluster) {

        int success = -1;
        success = fsClusterService.delete(fsCluster);

        if (success > 0) {
            return BaseResponse.success("操作成功0.0");
        } else {
            return BaseResponse.failed("操作失败~_~");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("FS集群查询")
    public PageResponse<?> listByPage(@RequestBody FsCluterSearch fsCluterSearch) {
        PageInfo<FsCluster> pageInfoFsCluster = fsClusterService.listByPage(fsCluterSearch);
        return PageResponse.successPageData(pageInfoFsCluster.getList(), pageInfoFsCluster.getTotal(), pageInfoFsCluster.getPageNum());
    }
}
