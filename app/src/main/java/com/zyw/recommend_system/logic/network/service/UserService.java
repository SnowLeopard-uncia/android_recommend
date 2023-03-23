package com.zyw.recommend_system.logic.network.service;

import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.model.RealName;
import com.zyw.recommend_system.logic.model.UserLogin;
import com.zyw.recommend_system.logic.model.params.ArticleId;
import com.zyw.recommend_system.logic.model.params.PostRealName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

//前面的GET、POST、DELETE、PUT，是请求方法，加上注解之后，Retrofit会用这些方法请求（注解的原理）
//这里只需要传入请求地址的相对路径，因为前面初始化会初始化base url
//方法的返回值必须声明成Retrofit中内置的Call类型
// 并通过泛型来指定[服务器响应的数据]应该转换成什么对象。
// 由于服务器响应的是一个包含App数据的JSON数组
// 因此这里我们将泛型声明成List<App>。 Call内的是服务器返回的数据

public interface UserService {

//@Query注解对它们进行声明,当发起网络请求的时候，Retrofit就会自动按照带参数GET请求的格式将这两个参数构建到请求地址当中
    //查看用户实名信息  token
    @GET("/user/realName")
    Call<BaseResponse<RealName>> getUserRealName();

    //用户登录
//    @FormUrlEncoded  //用@Filed要搭配这个使用
//    @POST("/user")
//    Call<BaseResponse<UserLogin>> userLogin(@Field("phoneId")String GUID);

    //用户实名
//    @FormUrlEncoded
//    @POST("/user")
//    Call<BaseResponse<RealName>> userRealName (@Field("idNo")String idNo,@Field("name")String name);

    //用户实名 json版
    @POST("/user/realName")
    Call<BaseResponse<RealName>> userRealName (@Body PostRealName postRealName);

    //用户登录 json版
    @POST("/user")
    Call<BaseResponse<UserLogin>> userLogin(@Body ArticleId articleId);
}
