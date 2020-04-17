package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ZhuzCdr;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.ZhuzCdrMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.cdr.ZhuzCdrSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.ZhuzCdrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;

@Service
public class ZhuzCdrServiceImpl implements ZhuzCdrService {

    @Resource
    private ZhuzCdrMapper zhuzCdrMapper;

    @Override
    public int create(ZhuzCdr zhuzCdr) {
        return zhuzCdrMapper.insertSelective(zhuzCdr);
    }

    @Override
    public PageInfo<ZhuzCdr> listByPage(ZhuzCdrSearch zhuzCdrSearch) {
        Example example = new Example(ZhuzCdr.class);
        example.setOrderByClause("createtime desc");
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(zhuzCdrSearch.getCallerNumber()) || StringUtils.isNotBlank(zhuzCdrSearch.getCalleeNumber())) {
            String callerNumber = null;
            String calleeNumber = null;
            if (StringUtils.isNotBlank(zhuzCdrSearch.getCallerNumber()) && StringUtils.isNotBlank(zhuzCdrSearch.getCallerNumber().trim())) {
                callerNumber = zhuzCdrSearch.getCallerNumber().trim();
                criteria.andEqualTo("callerNumber", callerNumber);

            }

            if (StringUtils.isNotBlank(zhuzCdrSearch.getCalleeNumber()) && StringUtils.isNotBlank(zhuzCdrSearch.getCalleeNumber().trim())) {
                calleeNumber = zhuzCdrSearch.getCalleeNumber().trim();
                criteria.andEqualTo("calleeNumber", calleeNumber);
            }

            int count = zhuzCdrMapper.selectCount(new ZhuzCdr(callerNumber, calleeNumber));

            int limitCount = Integer.parseInt(zhuzCdrSearch.getLimit());
            int currentPage = 0;
            if ((count % limitCount) > 0) {
                currentPage = (count / limitCount) + 1;
            } else {
                currentPage = count / limitCount;
            }

            if (Integer.parseInt(zhuzCdrSearch.getPage()) > currentPage) {
                zhuzCdrSearch.setPage("1");
            }
        }
        PageHelper.startPage(Integer.parseInt(zhuzCdrSearch.getPage()), Integer.parseInt(zhuzCdrSearch.getLimit()));
        PageInfo<ZhuzCdr> pageInfoZhuzCdr = new PageInfo<>(zhuzCdrMapper.selectByExample(example));

        return pageInfoZhuzCdr;
    }
}
