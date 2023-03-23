package com.zyw.recommend_system.logic.network.service;

import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryService {
    @GET("/history")
    Call<BaseResponse<ArticleList>> getHistory(
            @Query("size") String size, @Query("page") String page, @Query("order") String order);
}
