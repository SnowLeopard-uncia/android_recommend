package com.zyw.recommend_system.logic.network.service;


import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CollectionService {

    //用户添加收藏  token
    @POST("/article/star/{articleId}")
    Call<BaseResponse<Integer>> addCollection(@Path("articleId") String articleId);

    //用户取消收藏
//    @FormUrlEncoded
    @DELETE("/article/star/{articleId}")
    Call<BaseResponse<Integer>> deleteCollection(@Path("articleId") String articleId);

    //查看收藏夹 token
    @GET("/star")
    Call<BaseResponse<ArticleList>> getCollection( @Query("size") String size, @Query("page") String page, @Query("order") String order);
}
