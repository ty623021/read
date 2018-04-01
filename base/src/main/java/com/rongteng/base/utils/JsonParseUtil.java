package com.rongteng.base.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2017/5/24.
 * 财富接口数据解析，与成功失败的判断
 */
public class JsonParseUtil {
    static Gson gson = new Gson();

    /**
     * 数据解析   当解析的数据格式非集合时，在调用此方法时需要添加 try catch
     *
     * @param result
     * @param typeToken
     * @param <T>
     * @return
     */
    public static <T> T parse(String result, TypeToken typeToken) throws Exception{
        return gson.fromJson(result, typeToken.getType());
    }

}
