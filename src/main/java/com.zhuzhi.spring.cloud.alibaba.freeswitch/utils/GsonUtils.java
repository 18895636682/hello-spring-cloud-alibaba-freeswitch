package com.zhuzhi.spring.cloud.alibaba.freeswitch.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Gson 工具
 * <p>
 * Json格式数据生成与解析
 * </p>
 */
@Slf4j
public class GsonUtils {
    
    private static Gson gson;

    static {
        JsonSerializer<Date> dateSerializer = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, java.lang.reflect.Type typeOfSrc,
                                         JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };
        //日期格式处理
        //gson = new GsonBuilder().serializeNulls().setDateFormat(DateFormat.FULL, DateFormat.FULL).registerTypeAdapter(Date.class, dateSerializer).create();
        gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(Date.class, dateSerializer).create();
        log.info("zhuz: Gson serializeNulls DateFormat.FULL");
    }

    /**
     * 转换成单引号的json字符串
     *
     * @return
     */
    public static String toSingleQuoteJson(Object object) {
        if (object == null) {
            return null;
        }

        String result = gson.toJson(object);
        result = result.replaceAll("\"", "'");
        return result;
    }

    /**
     * 转换成json字符串
     *
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }

        String result = gson.toJson(object);
        return result;
    }

    /**
     * 从json转换为对象
     *
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (StringUtils.isBlank(json) || classOfT == null) {
            return null;
        }

        return gson.fromJson(json, classOfT);
    }

    /**
     * 从json转换为复杂对象
     * 此处仅仅为一个示例
     *
     * @return
     */
    public static List<Date> fromJsonToDateList(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        //GsonUtil.getGson().fromJson(json,new TypeToken<List<Date>>(){}.getType());
        List<Date> dateList = gson.fromJson(json, new TypeToken<List<Date>>() {
        }.getType());
        return dateList;
    }

    /**
     * 取得gson对象
     *
     * @return
     */
    public static Gson getGson() {
        return gson;
    }
}