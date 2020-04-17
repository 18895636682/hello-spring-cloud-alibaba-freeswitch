package com.zhuzhi.spring.cloud.alibaba.freeswitch.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * com.whale.tiger.dto.BaseResponse.java
 *
 * @param
 * @author com.zhuzhi
 * @date 2020年02月28日
 */
@Api(tags = "响应对象")
@JsonInclude(value = Include.NON_NULL) // 去掉null数据
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 7034025793550778981L;

    /**
     * @Fields data : 数据体
     */
    @ApiModelProperty(value = "数据对象")
    private T data;
    /**
     * @Fields status : 返回状态，3-未登录 2-异常 1:失败， 0：成功
     */
    @ApiModelProperty(value = "0:成功,1:失败,2异常")
    private Integer code = 0;
    /**
     * @Fields msg :提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String msg;


    /***
     * 成功，不带数据
     *
     * @return
     */
    public static <T> BaseResponse<T> success() {
        return set(C2cConst.SUCCESS_CODE, "success");
    }


    /***
     * 成功,带提示消息
     *
     * @return
     */
    public static <T> BaseResponse<T> success(String message) {
        return set(C2cConst.SUCCESS_CODE, message);
    }

    /***
     * 成功，带数据返回
     *
     * @param data
     *            数据
     * @return
     */
    public static <T> BaseResponse<T> successData(T data) {
        BaseResponse<T> res = success();
        res.setData(data);
        return res;
    }

    /***
     * 错误
     *
     * @param msg
     *            错误信息
     * @return
     */
    public static <T> BaseResponse<T> failed(String msg) {
        return set(C2cConst.FAILED_CODE, msg);
    }
    /***
     * 自定义错误
     *
     * @param msg
     *            错误信息
     * @return
     */
    public static <T> BaseResponse<T> failed(int code,String msg) {
        return set(code, msg);
    }


    /***
     * 异常
     *
     * @param msg
     *            异常信息
     * @return
     */
    public static <T> BaseResponse<T> exception(String msg) {
        return set(C2cConst.EXCEPTION_CODE, msg);
    }

    /***
     * 异常
     *
     * @return
     */
    public static <T> BaseResponse<T> exception() {
        return set(C2cConst.EXCEPTION_CODE, "exception.");
    }

    protected static <T> BaseResponse<T> set(int status, String msg) {
        BaseResponse<T> res = new BaseResponse<T>();
        res.setCode(status);
        res.setMsg(msg);
        return res;
    }

}
