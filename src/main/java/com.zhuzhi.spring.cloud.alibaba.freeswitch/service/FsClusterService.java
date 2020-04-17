package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsCluster;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.fscluster.FsCluterSearch;

import java.util.List;

public interface FsClusterService{

    int create(FsCluster fsCluster);

    int update(FsCluster fsCluster);

    int delete(FsCluster fsCluster);

    PageInfo<FsCluster> listByPage(FsCluterSearch fsCluterSearch);

    List<FsCluster> byClusterList(FsCluster fsCluster);
}
