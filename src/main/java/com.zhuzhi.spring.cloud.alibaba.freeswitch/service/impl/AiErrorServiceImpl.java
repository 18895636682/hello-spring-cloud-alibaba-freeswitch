package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.impl;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.AiError;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.mapper.AiErrorMapper;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.aiError.AiErrorSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.AiErrorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AiErrorServiceImpl implements AiErrorService {

    @Resource
    private AiErrorMapper aiErrorMapper;

    @Override
    public PageInfo<AiError> listByPage(AiErrorSearch aiErrorSearch) {
        return null;
    }
}
