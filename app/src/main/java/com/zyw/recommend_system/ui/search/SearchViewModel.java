package com.zyw.recommend_system.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.zyw.recommend_system.logic.ArticleDataSource;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.paging_service.ExecutorSearch;

import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

public class SearchViewModel extends ViewModel {
    PagingConfig pagingConfig=new PagingConfig(15,5,false,15);//初始化配置,可以定义最大加载的数据量

    public ListeningExecutorService executorService= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public LiveData<PagingData<Article>> getArticleSearchPaging(String keyWord){
        CoroutineScope viewModelScope= ViewModelKt.getViewModelScope(this);
        Pager<Integer, Article> pager = new Pager<Integer, Article>(pagingConfig,
                ()->new ArticleDataSource(new ExecutorSearch(executorService,keyWord)));    //构造函数
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewModelScope);
    }
}
