package com.zyw.recommend_system.ui.like;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.zyw.recommend_system.logic.ArticleDataSource;
import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.paging_service.ExecutorHistory;
import com.zyw.recommend_system.logic.paging_service.ExecutorLike;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

public class LikesViewModel extends ViewModel {
    PagingConfig pagingConfig=new PagingConfig(15,5,false,15);//初始化配置,可以定义最大加载的数据量

    public ListeningExecutorService executorService= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public LiveData<PagingData<Article>> getLikesPaging(){
        CoroutineScope viewModelScope= ViewModelKt.getViewModelScope(this);
        Pager<Integer, Article> pager = new Pager<Integer, Article>(pagingConfig,
                ()->new ArticleDataSource(new ExecutorLike(executorService)));    //构造函数
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewModelScope);
    }
}
