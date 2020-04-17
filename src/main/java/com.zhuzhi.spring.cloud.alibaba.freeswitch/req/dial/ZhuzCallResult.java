package com.zhuzhi.spring.cloud.alibaba.freeswitch.req.dial;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ZhuzCallResult implements Serializable {
    private static final long serialVersionUID = -3723910012830389298L;

    public static Integer SUCCESS = 0;                //成功返回
    public static Integer PARAM_ERROR = 101;            //参数错误
    public static Integer BIZ_ERROR = 201;            //业务错误
    public static Integer UNEXPECTED_ERROR = 500;    //未知异常

    private Integer code = ZhuzCallResult.SUCCESS;
    private Object result;
    private String errmsg;

    //是否是正常返回
    public Boolean isNormalReturn() {
        return ZhuzCallResult.SUCCESS == code;
    }
}
