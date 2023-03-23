package com.zyw.recommend_system.logic.network.service;

import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LikeService {

    //点赞文章  token
    @POST("/article/like/{articleId}")
    Call<BaseResponse<Integer>> addLike(@Path("articleId") String articleId);

    //用户取消点赞
//    @FormUrlEncoded token
    @DELETE("/article/like/{articleId}")
    Call<BaseResponse<Integer>> deleteLike(@Path("articleId") String articleId);

    //查看
    @GET("/likes")
    Call<BaseResponse<ArticleList>> getLikesList(@Query("size") String size, @Query("page") String page, @Query("order") String order);

}
