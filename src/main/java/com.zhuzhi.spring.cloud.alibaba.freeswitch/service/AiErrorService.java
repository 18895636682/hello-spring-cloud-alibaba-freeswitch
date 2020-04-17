package com.zhuzhi.spring.cloud.alibaba.freeswitch.service;

import com.github.pagehelper.PageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.AiError;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.aiError.AiErrorSearch;

public interface AiErrorService {

    PageInfo<AiError> listByPage(AiErrorSearch aiErrorSearch);
}
