package com.zyw.recommend_system.utils;

import com.zyw.recommend_system.logic.dao.UserDao;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//添加拦截器，设置统一的header，给okhttp设置了BaseInterceptor
public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //添加请求头
        Request request = original.newBuilder()
                .addHeader("Content-type", "application/json")
//                .addHeader("Content-type", "application/x-www-form-urlencoded")
                .addHeader("token", UserDao.getToken())
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);

    }
}
