package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsCluster;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.fscluster.FsCluterSearch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.FsClusterMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.FsClusterService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class FsClusterServiceImpl implements FsClusterService {

    @Resource
    private FsClusterMapper fsClusterMapper;

    @Override
    public int create(FsCluster fsCluster) {
        fsCluster.setAddTime(new Date());
        return fsClusterMapper.insertSelective(fsCluster);
    }

    @Override
    public int update(FsCluster fsCluster) {
        fsCluster.setEditTime(new Date());
        return fsClusterMapper.updateByPrimaryKeySelective(fsCluster);
    }

    @Override
    public int delete(FsCluster fsCluster) {
        return fsClusterMapper.delete(fsCluster);
    }

    @Override
    public PageInfo<FsCluster> listByPage(FsCluterSearch fsCluterSearch) {
        Example example = new Example(FsCluster.class);
        example.setOrderByClause("addtime desc");
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(fsCluterSearch.getName()) || StringUtils.isNotBlank(fsCluterSearch.getIp())) {
            String name = null;
            String ip = null;
            if (StringUtils.isNotBlank(fsCluterSearch.getName()) && StringUtils.isNotBlank(fsCluterSearch.getName().trim())) {
                name = fsCluterSearch.getName().trim();
                criteria.andEqualTo("name", name);

            }

            if (StringUtils.isNotBlank(fsCluterSearch.getIp()) && StringUtils.isNotBlank(fsCluterSearch.getIp().trim())) {
                ip = fsCluterSearch.getIp().trim();
                criteria.andEqualTo("ip", ip);
            }

            int count = fsClusterMapper.selectCount(new FsCluster(name, ip));

            int limitCount = Integer.parseInt(fsCluterSearch.getLimit());
            int currentPage = 0;
            if ((count % limitCount) > 0) {
                currentPage = (count / limitCount) + 1;
            } else {
                currentPage = count / limitCount;
            }

            if (Integer.parseInt(fsCluterSearch.getPage()) > currentPage) {
                fsCluterSearch.setPage("1");
            }
        }
        PageHelper.startPage(Integer.parseInt(fsCluterSearch.getPage()), Integer.parseInt(fsCluterSearch.getLimit()));
        PageInfo<FsCluster> pageInfoFsCluster = new PageInfo<>(fsClusterMapper.selectByExample(example));

        return pageInfoFsCluster;
    }

    @Override
    public List<FsCluster> byClusterList(FsCluster fsCluster) {
        List<FsCluster> byClusterList = fsClusterMapper.select(fsCluster);
        return byClusterList;
    }
}
