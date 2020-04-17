package com.zhuzhi.spring.cloud.alibaba.freeswitch.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mobileloc.MobileLoc;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.mobileloc.MobileLocSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.feignservice.ProviderService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.MobilelocShared;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 手机号码归属地配置文件
 *
 * @author zhuz
 */
@Slf4j
@Component
public class MobileLocUtil { // Spring bean
    private static Pattern phoneNumberPattern = Pattern.compile("(1\\d{10})$");

    // 判断是否开启
    @Value("${mobile.mobileloc}")
    private String mobileloc;

    // 强制不识别的区号
    @Value("${mobile.notRecognize}")
    private String notRecognize;

    /*@Autowired
    private ProviderService providerService;*/

    @Qualifier("redisson1")
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 电话号码处理
     *
     * @return
     */
    public String process(String phoneNumber, String areacode) {
        // //旧的电话号码
        String oldPhoneNumber = phoneNumber;

        // 如果关闭自动识别归属地则不识别
        if (!"true".equals(mobileloc)) {
            // 为空不处理
            return phoneNumber;
        }

        // 判断areacode为空或强制关闭则不识别
        if ("".equals(areacode) || notRecognize.equals(areacode)) {
            return oldPhoneNumber;
        }

        // 提取出匹配的手机号码
        Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
        if (matcher.find()) {
            phoneNumber = matcher.group();
        } else {
            return phoneNumber;
        }

        try {
            // 查找指定手机号码
            String abbresiveNumber = StringUtils.left(phoneNumber, 7);
            //MobileLoc mobileLoc = providerService.selectOne(new MobileLoc(abbresiveNumber));
            RBucket<Object> result = redissonClient.getBucket(MobilelocShared.mobileloc_key + abbresiveNumber);
            String areacodeResult = result.get().toString();

            if (StringUtils.isBlank(areacodeResult) || areacode.equals(areacodeResult)) {
                return phoneNumber;
            } else {
                return "0" + phoneNumber;
            }
        } catch (Exception e) {
            log.error("zhuz: error-" + e);
            return phoneNumber;
        }
    }

}