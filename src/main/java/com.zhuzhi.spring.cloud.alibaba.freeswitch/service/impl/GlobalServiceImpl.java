package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Global;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.GlobalMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.GlobalService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class GlobalServiceImpl implements GlobalService {

    @Resource
    private GlobalMapper globalMapper;

    @Override
    public int create(Global global) {
        global.setAddTime(new Date());
        Integer count = globalMapper.selectCount(new Global());
        if (count == 1) {
            global.setEditTime(new Date());
            return globalMapper.updateByPrimaryKeySelective(global);
        } else {
            return globalMapper.insertSelective(global);
        }
    }

    @Override
    public int update(Global global) {
        global.setEditTime(new Date());
        return globalMapper.updateByPrimaryKeySelective(global);
    }

    @Override
    public PageInfo<Global> listByPage() {
        Example example = new Example(Global.class);
        example.setOrderByClause("addTime desc");
        Example.Criteria criteria = example.createCriteria();
        PageHelper.startPage(1, 10);
        PageInfo<Global> pageInfoGlobal = new PageInfo<>(globalMapper.selectByExample(example));
        return pageInfoGlobal;
    }

    @Override
    public Global getGlobal() {
        List<Global> globalList = globalMapper.selectAll();
        if (globalList == null || globalList.size() == 0 || globalList.size() > 1) {
            return null;
        } else {
            return globalList.get(0);
        }
    }
}
