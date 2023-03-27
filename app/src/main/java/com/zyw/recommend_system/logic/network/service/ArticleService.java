package com.zyw.recommend_system.logic.network.service;

import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.model.UserLogin;
import com.zyw.recommend_system.logic.model.params.ArticlePost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    //查看论文列表  用于以时间顺序查看分类（科学/技术）、搜索  order =1
    @GET("/article/list")
    Call<BaseResponse<ArticleList>> getSearchArticleList(@Query("keyword") String keyword,
                                                         @Query("tag") String tag,
                                                         @Query("size") String size,
                                                         @Query("page") String page,
                                                         @Query("order") String order);

    //推荐论文 用于上传
//    @FormUrlEncoded  //token
//    @POST("/post/new")
//    Call<BaseResponse<UserLogin>> postArticle(@Field("title")String title,
//                                              @Field("author")String author,
//                                              @Field("summary")String summary,
//                                              @Field("keyWord")String keyWord,
//                                              @Field("link")String link,
//                                              @Field("classify")String classify,
//                                              @Field("pubtime")String pubTime);
    //推荐论文 用于上传 json版
    @POST("/article/recommend")
    Call<BaseResponse<UserLogin>> postArticle(@Body ArticlePost articlePost);

    //查看文章 用于统计历史记录
    @GET("/article/{articleId}")
    Call<BaseResponse<Article>> getArticleDetail(@Path("articleId") String articleId);

    //个性化推荐（首页）
    @GET("/article/userRecommend")
    Call<BaseResponse<ArticleList>> getRecommendArticleList(@Query("size") String size, @Query("page") String page);

    //分类热门推荐
    @GET("/article/hotRecommend")
    Call<BaseResponse<ArticleList>> getHotRecommendArticleList(@Query("tag") String tag,@Query("size") String size, @Query("page") String page);

    //首次推荐
    @GET("/article/firstRecommend")
    Call<BaseResponse<ArticleList>> getFirstRecommendArticleList(@Query("tag") String tag,@Query("size") String size, @Query("page") String page);


}

