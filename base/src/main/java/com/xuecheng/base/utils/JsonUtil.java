/**
 * @(#)JsonUtil.java 2014-2-23 下午5:44:19
 */
package com.xuecheng.base.utils;


import com.alibaba.fastjson2.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson2.JSONWriter.Feature.PrettyFormat;


public class JsonUtil {

    public static String objectTojson(Object object) {
        return JSON.toJSONString(object);
    }


    public static String listTojson(List list) {
        return JSON.toJSONString(list);
    }

    /**
     * 字符串Json格式转换为对象Map
     *
     * @param strJson {"username":"sxb"}
     * @return 根据json转换为Map对象
     */
    public static Map<String, Object> jsonToMap(String strJson) {
        Map<String, Object> jsoMap = new HashMap<String, Object>();
        try {
            jsoMap = JSONObject.parseObject(strJson, Map.class);
        } catch (JSONException e) {
            System.out.println("json转换Map出错：" + e.getMessage());
        }

        return jsoMap;
    }

    public static <T> T jsonToObject(String strJson, Class<T> tClass) {
        try {
            return JSON.parseObject(strJson, tClass);
        } catch (JSONException e) {
            System.out.println("json转换Map出错：" + e.getMessage());
        }
        return null;
    }


    /**
     * 字符串Json 转换为对象List
     *
     * @param strJson [{"username":"sxb"}]
     * @return 根据json转换List
     */
    public static <T> List<T> jsonToList(String strJson, Class<T> tClass) {
        try {
            return JSONArray.parseArray(strJson, tClass);
        } catch (JSONException e) {
            System.out.println("json转换List出错：" + e.getMessage());
        }
        return null;
    }


}
