package com.zyw.recommend_system.logic.paging_service;

import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.zyw.recommend_system.logic.LoadDataInterface;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.network.ServiceCreator;
import com.zyw.recommend_system.logic.network.service.ArticleService;
import com.zyw.recommend_system.logic.network.service.HistoryService;

import java.util.List;

import retrofit2.Call;

public class ExecutorHistory implements LoadDataInterface<Article> {

    ListeningExecutorService executorService;

    private static HistoryService historyService = ServiceCreator.create(HistoryService.class);

    public ExecutorHistory(ListeningExecutorService executorService) {
        this.executorService=executorService;
    }

    //请求参数
    @Override
    public ListenableFuture<List<Article>> load(int pageNum) {
        return executorService.submit(() -> {
            //  耗时操作 请求列表
            Call<BaseResponse<ArticleList>> call = historyService.getHistory("15",Integer.toString(pageNum),"1");
            BaseResponse<ArticleList> articleListResponse = call.execute().body();
            List<Article> list = articleListResponse.getData().getArticleList();
            Log.e("ExecutorHistory", "load: "+list);
            return list;
        });
    }
}
