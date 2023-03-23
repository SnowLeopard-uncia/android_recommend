package com.zyw.recommend_system.utils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 此类用于封装json格式数据
 */
public class JsonParams {
    JSONObject params = new JSONObject();

    public JsonParams put(String key,Object value){
        try {
            params.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
    //return this用来返回当前对象的引用 返回类的当前对象

    /**
     * 发送Json格式数据
     * @return
     */
    public RequestBody create(){
        return RequestBody.create(MediaType.parse("application/json"), params.toString());
    }
}
