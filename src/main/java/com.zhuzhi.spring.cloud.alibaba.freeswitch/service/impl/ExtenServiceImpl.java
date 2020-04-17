package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Exten;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.ExtenMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.exten.ExtenSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.ExtenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ExtenServiceImpl implements ExtenService {

    @Resource
    private ExtenMapper extenMapper;

    @Override
    public int create(Exten exten) {
        exten.setAddTime(new Date());
        return extenMapper.insertSelective(exten);
    }

    @Override
    public int update(Exten exten) {
        exten.setEditTime(new Date());
        return extenMapper.updateByPrimaryKeySelective(exten);
    }

    @Override
    public int delete(Exten exten) {
        return extenMapper.delete(exten);
    }

    @Override
    public PageInfo<Exten> listByPage(ExtenSearch extenSearch) {
        Example example = new Example(Exten.class);
        example.setOrderByClause("addTime desc");
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(extenSearch.getUserId()) && StringUtils.isNotBlank(extenSearch.getUserId().trim())) {

            criteria.andEqualTo("userId", extenSearch.getUserId().trim());
            int count = extenMapper.selectCount(new Exten(extenSearch.getUserId().trim()));

            int limitCount = Integer.parseInt(extenSearch.getLimit());
            int currentPage = 0;
            if ((count % limitCount) > 0) {
                currentPage = (count / limitCount) + 1;
            } else {
                currentPage = count / limitCount;
            }

            if (Integer.parseInt(extenSearch.getPage()) > currentPage) {
                extenSearch.setPage("1");
            }
        }
        PageHelper.startPage(Integer.parseInt(extenSearch.getPage()), Integer.parseInt(extenSearch.getLimit()));
        PageInfo<Exten> pageInfoExten = new PageInfo<>(extenMapper.selectByExample(example));

        return pageInfoExten;
    }

    @Override
    public Exten findByExten(Exten exten) {
        return extenMapper.selectOne(exten);
    }

}
