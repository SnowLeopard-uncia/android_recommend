package com.zyw.recommend_system.logic.paging_service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.zyw.recommend_system.logic.LoadDataInterface;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.network.ServiceCreator;
import com.zyw.recommend_system.logic.network.service.ArticleService;
import com.zyw.recommend_system.logic.network.service.LikeService;

import java.util.List;

import retrofit2.Call;

public class ExecutorLike implements LoadDataInterface<Article> {
    ListeningExecutorService executorService;

    private static LikeService likeService = ServiceCreator.create(LikeService.class);

    public ExecutorLike(ListeningExecutorService executorService) {
        this.executorService=executorService;
    }
    @Override
    public ListenableFuture<List<Article>> load(int pageNum) {
        return executorService.submit(() -> {
            //  耗时操作 请求列表
            Call<BaseResponse<ArticleList>> call = likeService.getLikesList("15",Integer.toString(pageNum),"1");
            BaseResponse<ArticleList> articleListResponse = call.execute().body();
            List<Article> list = articleListResponse.getData().getArticleList();
            return list;
        });
    }
}
