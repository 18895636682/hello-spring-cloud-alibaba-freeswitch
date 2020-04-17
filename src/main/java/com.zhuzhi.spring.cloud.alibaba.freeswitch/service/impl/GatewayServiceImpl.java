package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Gateway;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.GatewayMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.gateway.GatewaySearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.GatewayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class GatewayServiceImpl implements GatewayService {

    @Resource
    private GatewayMapper gatewayMapper;

    @Override
    public int create(Gateway gateway) {
        gateway.setAddtime(new Date());

        if (StringUtils.isBlank(gateway.getIncoming_type())) {
            gateway.setIncoming_ivr("");
            gateway.setIncoming_exten("");
            gateway.setIncoming_callcenter("");
        } else if ("ivr".equals(gateway.getIncoming_type())) {
            if (StringUtils.isBlank(gateway.getIncoming_ivr())) {
                return -100;
            }
            gateway.setIncoming_exten("");
            gateway.setIncoming_callcenter("");

        } else if ("exten".equals(gateway.getIncoming_type())) {
            if (StringUtils.isBlank(gateway.getIncoming_exten())) {
                return -101;
            }
            gateway.setIncoming_ivr("");
            gateway.setIncoming_callcenter("");
        } else if ("callcenter".equals(gateway.getIncoming_type())) {
            if (StringUtils.isBlank(gateway.getIncoming_callcenter())) {
                return -102;
            }
            gateway.setIncoming_ivr("");
            gateway.setIncoming_exten("");
        }

        return gatewayMapper.insertSelective(gateway);
    }

    @Override
    public int update(Gateway gateway) {
        gateway.setEdittime(new Date());

        if (StringUtils.isBlank(gateway.getIncoming_type())) {
            gateway.setIncoming_ivr("");
            gateway.setIncoming_exten("");
            gateway.setIncoming_callcenter("");
        } else if ("ivr".equals(gateway.getIncoming_type())) {
            if (StringUtils.isBlank(gateway.getIncoming_ivr())) {
                return -100;
            }
            gateway.setIncoming_exten("");
            gateway.setIncoming_callcenter("");

        } else if ("exten".equals(gateway.getIncoming_type())) {
            if (StringUtils.isBlank(gateway.getIncoming_exten())) {
                return -101;
            }
            gateway.setIncoming_ivr("");
            gateway.setIncoming_callcenter("");
        } else if ("callcenter".equals(gateway.getIncoming_type())) {
            if (StringUtils.isBlank(gateway.getIncoming_callcenter())) {
                return -102;
            }
            gateway.setIncoming_ivr("");
            gateway.setIncoming_exten("");
        }

        return gatewayMapper.updateByPrimaryKeySelective(gateway);
    }

    @Override
    public int delete(Gateway gateway) {
        return gatewayMapper.delete(gateway);
    }

    @Override
    public PageInfo<Gateway> listByPage(GatewaySearch gatewaySearch) {
        Example example = new Example(Gateway.class);
        example.setOrderByClause("addTime desc");
        Example.Criteria criteria = example.createCriteria();
        // 添加查询条件
        if (StringUtils.isNotBlank(gatewaySearch.getUserName()) && StringUtils.isNotBlank(gatewaySearch.getUserName().trim())) {

            criteria.andEqualTo("userName", gatewaySearch.getUserName().trim());
            int count = gatewayMapper.selectCount(new Gateway(gatewaySearch.getUserName().trim()));

            int limitCount = Integer.parseInt(gatewaySearch.getLimit());
            int currentPage = 0;
            if ((count % limitCount) > 0) {
                currentPage = (count / limitCount) + 1;
            } else {
                currentPage = count / limitCount;
            }

            if (Integer.parseInt(gatewaySearch.getPage()) > currentPage) {
                gatewaySearch.setPage("1");
            }
        }
        PageHelper.startPage(Integer.parseInt(gatewaySearch.getPage()), Integer.parseInt(gatewaySearch.getLimit()));
        PageInfo<Gateway> pageInfoGateway = new PageInfo<>(gatewayMapper.selectByExample(example));

        return pageInfoGateway;
    }

    @Override
    public Gateway findByUserName(Gateway gateway) {
        return gatewayMapper.selectOne(gateway);
    }
}
