package com.zyw.recommend_system.logic.network;


import com.zyw.recommend_system.utils.BaseInterceptor;
import com.zyw.recommend_system.utils.CustomGsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Retrofit单例类
 * @param <T>
 */
public class ServiceCreator<T> {
    private static final String BASE_URL="http://47.120.3.115:8082";

   static OkHttpClient.Builder builder = new OkHttpClient()
           .newBuilder()
           .addInterceptor(new BaseInterceptor());
//
   static OkHttpClient client = builder.build();

    private static Retrofit retrofit = new Retrofit.Builder()  //使用Retrofit.Builder构建一个Retrofit对象
          .baseUrl(BASE_URL)  //baseUrl()方法用于指定所有Retrofit请求的根路径
          .addConverterFactory(CustomGsonConverterFactory.create())  //addConverterFactory()方法用于指定Retrofit在解析数据时所使用的转换库
          .client(client)
            .build();
//提供了一个外部可见的create()方法，并接收一个Class类型的参数。当在外部调用这个方法时，实际上就是调用了Retrofit对象的create()方法，从而创建出相应Service接口的动态代理对象
    public static <T>T create(Class<T> serviceClass){
      return retrofit.create(serviceClass);
  }

}
