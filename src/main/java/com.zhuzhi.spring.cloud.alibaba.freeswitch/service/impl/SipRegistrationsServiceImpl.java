package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.SipRegistrations;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapperfs.SipRegistrationsMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten.SipRegistrationsSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.SipRegistrationsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class SipRegistrationsServiceImpl implements SipRegistrationsService{

    @Resource
    private SipRegistrationsMapper sipRegistrationsMapper;

    @Override
    public PageInfo<SipRegistrations> listByPage(SipRegistrationsSearch sipRegistrationsSearch) {
        Example example = new Example(SipRegistrations.class);
        example.setOrderByClause("expires desc");
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(sipRegistrationsSearch.getSip_user()) && StringUtils.isNotBlank(sipRegistrationsSearch.getSip_user().trim())) {

            criteria.andEqualTo("sip_user", sipRegistrationsSearch.getSip_user().trim());
            int count = sipRegistrationsMapper.selectCount(new SipRegistrations(sipRegistrationsSearch.getSip_user().trim()));

            int limitCount = Integer.parseInt(sipRegistrationsSearch.getLimit());
            int currentPage = 0;
            if ((count % limitCount) > 0) {
                currentPage = (count / limitCount) + 1;
            } else {
                currentPage = count / limitCount;
            }

            if (Integer.parseInt(sipRegistrationsSearch.getPage()) > currentPage) {
                sipRegistrationsSearch.setPage("1");
            }
        }

        PageHelper.startPage(Integer.parseInt(sipRegistrationsSearch.getPage()), Integer.parseInt(sipRegistrationsSearch.getLimit()));
        PageInfo<SipRegistrations> pageInfoSipRegistrations = new PageInfo<>(sipRegistrationsMapper.selectByExample(example));
        return pageInfoSipRegistrations;
    }
}
