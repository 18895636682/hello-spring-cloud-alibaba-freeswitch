package com.zhuzhi.spring.cloud.alibaba.freeswitch.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static String IGNORE = "unknown", POST_METHOD = "POST";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        BaseResponse<String> response = BaseResponse.exception("exception.");
        String ipAddr = getIpAddr(req);
        String params = getParamsValue(req);
        log.error("exception---ip:{},url:{},params:{},exceptionMsg:{}", ipAddr, req.getRequestURL(), params, e);
        return response;
    }

    /**
     * 获取登录用户远程主机ip地址
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || IGNORE.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IGNORE.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IGNORE.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getParamsValue(HttpServletRequest request) {
        try {
            String method = request.getMethod();
            String paramsValue=getValue(request);
            if(StringUtils.isNotBlank(paramsValue)) {
                return paramsValue;
            }
            if (POST_METHOD.equals(method)) {
                return readAsChars2(request);
            }
            Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return paramsMap.toString();
        } catch (Exception ex) {

        }
        return null;
    }

    private String getValue(HttpServletRequest request) {
        StringBuffer sb=new StringBuffer();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            sb.append(key).append(":").append(value).append(".");
        }
        return sb.toString();
    }

    private String readAsChars2(HttpServletRequest request) {
        String result = "";
        InputStream is = null;
        try {
            is = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;) {
                sb.append(new String(b, 0, n));
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
