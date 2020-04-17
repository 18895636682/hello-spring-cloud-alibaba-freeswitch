package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.PushManage;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.PushManageMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.pushmanage.PushManageSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.PushManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PushManageServiceImpl implements PushManageService {

    @Resource
    private PushManageMapper pushManageMapper;

    @Override
    public int create(PushManage pushManage) {
        int result = compare(pushManage);
        if (result == 0) {
            pushManage.setAddTime(new Date());
            pushManage.setPushUrl(pushManage.getPushUrl().trim());
            return pushManageMapper.insertSelective(pushManage);
        } else {
            return result;
        }
    }

    @Override
    public int update(PushManage pushManage) {

        PushManage pushManageNew = pushManageMapper.selectByPrimaryKey(pushManage);
        if (pushManageNew.getPushType().equals(pushManage.getPushType()) && pushManageNew.getPushStrategy().equals(pushManage.getPushStrategy())) {
            if ("consumerId".equals(pushManage.getPushStrategy()) && (pushManageNew.getConsumerId().equals(pushManage.getConsumerId()) || pushManageNew.getConsumerId().equals(pushManage.getConsumerId().trim()))) {
                pushManage.setConsumerId(pushManage.getConsumerId().trim());
                pushManage.setGateway("");
                pushManage.setExten("");
                pushManage.setEditTime(new Date());
                pushManage.setPushUrl(pushManage.getPushUrl().trim());
                return pushManageMapper.updateByPrimaryKeySelective(pushManage);
            } else if ("gateway".equals(pushManage.getPushStrategy()) && (pushManageNew.getGateway().equals(pushManage.getGateway()) || pushManageNew.getGateway().equals(pushManage.getGateway().trim()))) {
                pushManage.setConsumerId("");
                pushManage.setGateway(pushManage.getGateway().trim());
                pushManage.setExten("");
                pushManage.setEditTime(new Date());
                pushManage.setPushUrl(pushManage.getPushUrl().trim());
                return pushManageMapper.updateByPrimaryKeySelective(pushManage);
            } else if ("exten".equals(pushManage.getPushStrategy()) && (pushManageNew.getExten().equals(pushManage.getExten()) || pushManageNew.getExten().equals(pushManage.getExten().trim()))) {
                pushManage.setConsumerId("");
                pushManage.setGateway("");
                pushManage.setExten(pushManage.getExten().trim());
                pushManage.setEditTime(new Date());
                pushManage.setPushUrl(pushManage.getPushUrl().trim());
                return pushManageMapper.updateByPrimaryKeySelective(pushManage);
            }
        }

        int result = compare(pushManage);
        if (result == 0) {
            pushManage.setEditTime(new Date());
            pushManage.setPushUrl(pushManage.getPushUrl().trim());
            return pushManageMapper.updateByPrimaryKeySelective(pushManage);
        } else {
            return result;
        }
    }

    @Override
    public int delete(PushManage pushManage) {
        return pushManageMapper.delete(pushManage);
    }

    @Override
    public PageInfo<PushManage> listByPage(PushManageSearch pushManageSearch) {
        Example example = new Example(PushManage.class);
        example.setOrderByClause("addTime desc");
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(pushManageSearch.getCge()) && StringUtils.isNotBlank(pushManageSearch.getCge().trim())) {

            criteria.orEqualTo("consumerId", pushManageSearch.getCge().trim());
            criteria.orEqualTo("gateway", pushManageSearch.getCge().trim());
            criteria.orEqualTo("exten", pushManageSearch.getCge().trim());

            int count1 = pushManageMapper.selectCount(new PushManage("consumerId", pushManageSearch.getCge().trim()));
            int count2 = pushManageMapper.selectCount(new PushManage("gateway", pushManageSearch.getCge().trim()));
            int count3 = pushManageMapper.selectCount(new PushManage("exten", pushManageSearch.getCge().trim()));
            int count = count1 + count2 + count3;

            int limitCount = Integer.parseInt(pushManageSearch.getLimit());
            int currentPage = 0;
            if ((count % limitCount) > 0) {
                currentPage = (count / limitCount) + 1;
            } else {
                currentPage = count / limitCount;
            }

            if (Integer.parseInt(pushManageSearch.getPage()) > currentPage) {
                pushManageSearch.setPage("1");
            }
        }
        PageHelper.startPage(Integer.parseInt(pushManageSearch.getPage()), Integer.parseInt(pushManageSearch.getLimit()));
        PageInfo<PushManage> pageInfoPushManage = new PageInfo<>(pushManageMapper.selectByExample(example));

        return pageInfoPushManage;
    }

    private int compare(PushManage pushManage) {
        PushManage pushManageNew = null;
        if ("consumerId".equals(pushManage.getPushStrategy())) {
            if (StringUtils.isBlank(pushManage.getConsumerId()) || StringUtils.isBlank(pushManage.getConsumerId().trim())) {
                return -100;
            } else {
                pushManage.setConsumerId(pushManage.getConsumerId().trim());
                pushManage.setGateway("");
                pushManage.setExten("");
            }
            pushManageNew = pushManageMapper.selectOne(new PushManage(pushManage.getPushType(), pushManage.getPushStrategy(), pushManage.getConsumerId()));
        } else if ("gateway".equals(pushManage.getPushStrategy())) {
            if (StringUtils.isBlank(pushManage.getGateway()) || StringUtils.isBlank(pushManage.getGateway().trim())) {
                return -101;
            } else {
                pushManage.setConsumerId("");
                pushManage.setGateway(pushManage.getGateway().trim());
                pushManage.setExten("");
            }
            pushManageNew = pushManageMapper.selectOne(new PushManage(pushManage.getPushType(), pushManage.getPushStrategy(), pushManage.getGateway()));
        } else if ("exten".equals(pushManage.getPushStrategy())) {
            if (StringUtils.isBlank(pushManage.getExten()) || StringUtils.isBlank(pushManage.getExten().trim())) {
                return -102;
            } else {
                pushManage.setConsumerId("");
                pushManage.setGateway("");
                pushManage.setExten(pushManage.getExten().trim());
            }
            pushManageNew = pushManageMapper.selectOne(new PushManage(pushManage.getPushType(), pushManage.getPushStrategy(), pushManage.getExten()));
        }

        if (pushManageNew == null) {
            return 0;
        } else {
            return -103;
        }
    }

    @Override
    public List<PushManage> selectAll() {
        return pushManageMapper.selectAll();
    }
}
