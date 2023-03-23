package com.zyw.recommend_system.ui.collection;

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
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.paging_service.ExecutorCollection;
import com.zyw.recommend_system.logic.paging_service.ExecutorHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

public class CollectionViewModel extends ViewModel {

    PagingConfig pagingConfig=new PagingConfig(15,5,false,15);//初始化配置,可以定义最大加载的数据量

    public ListeningExecutorService executorService= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public LiveData<PagingData<Article>> getCollectionPaging(){
        CoroutineScope viewModelScope= ViewModelKt.getViewModelScope(this);
        Pager<Integer, Article> pager = new Pager<Integer, Article>(pagingConfig,
                ()->new ArticleDataSource(new ExecutorCollection(executorService)));    //构造函数
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewModelScope);
    }

//    private List<Poem> poemList = new ArrayList<>();
//
//    public List<Poem> getPoemList() {
//        return poemList;
//    }
//
//    public void setPoemList(List<Poem> poemList) {
//        this.poemList = poemList;
//    }
//
//    public LiveData<List<Poem>> getCollectionList(String userName){
//        return Repository.getInstance().getCollection(userName);
//    }
//
//    public UserInfo getUser() {
//        return Repository.getInstance().getUserName();
//    }
}
