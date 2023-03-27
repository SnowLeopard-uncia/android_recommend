package com.zyw.recommend_system.logic.paging_service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.zyw.recommend_system.logic.LoadDataInterface;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.network.ServiceCreator;
import com.zyw.recommend_system.logic.network.service.ArticleService;

import java.util.List;

import retrofit2.Call;


public class ExecutorHotRecommend implements LoadDataInterface<Article> {
    ListeningExecutorService executorService;
    private String tag;

    private static ArticleService articleService = ServiceCreator.create(ArticleService.class);

    public ExecutorHotRecommend(ListeningExecutorService executorService, String tag) {
        this.executorService=executorService;
        this.tag = tag;
    }

    //请求参数
    @Override
    public ListenableFuture<List<Article>> load(int pageNum) {
        return executorService.submit(() -> {
            //  耗时操作 请求列表
          Call<BaseResponse<ArticleList>> call = articleService.getHotRecommendArticleList(tag,"15",Integer.toString(pageNum));
          BaseResponse<ArticleList> articleListResponse = call.execute().body();
          List<Article> list = articleListResponse.getData().getArticleList();
            return list;
        });
    }
}
