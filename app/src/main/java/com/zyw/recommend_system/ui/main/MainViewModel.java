package com.zyw.recommend_system.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.zyw.recommend_system.logic.ArticleDataSource;
import com.zyw.recommend_system.logic.paging_service.ExecutorSearch;
import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.logic.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

public class MainViewModel extends ViewModel {
    public MutableLiveData<PagingData<Article>> articleData = new MutableLiveData<>();

    PagingConfig pagingConfig=new PagingConfig(15,5,false,15);//初始化配置,可以定义最大加载的数据量
    // pageSize  每页显示的数据的大小。对应 PagingSource 里 LoadParams.loadSize
    //prefetchDistance  预刷新的距离，距离最后一个 item 多远时加载数据，默认为 pageSize
    //initialLoadSize  初始化加载数量，默认为 pageSize * 3
    public ListeningExecutorService executorService= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public LiveData<PagingData<Article>> getArticlePaging(String keyWord){
        CoroutineScope viewModelScope= ViewModelKt.getViewModelScope(this);
        Pager<Integer, Article> pager = new Pager<Integer, Article>(pagingConfig,
                ()->new ArticleDataSource(new ExecutorSearch(executorService,keyWord)));    //构造函数
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewModelScope);
    }


    //ViewModel设置共享值
    //可能因为共享了这个list，所以导致Recycler View的错误
    //解决方案：三个不同的list变量  事实证明,三个Fragment不能公用一个list变量
    // 为什么呢，因为三个RecyclerView是不同的，，三个Recycler View使用的List不一样，
    // Inconsistency detected. Invalid view holder adapter positionViewHolder，数据移除和数据增加时，RecyclerView的Adapter中的数据集和移除／添加等操作后的数据集没有保持一致
    //就是索引溢出 跟Adapter没关系，三个RecyclerView可以公用一个Adapter，只要数据和布局一样就行。注意传进去的List要一致
    private List<Article> scienceList = new ArrayList<>();
    private List<Article> technologyList = new ArrayList<>();

    //实名认证查看
    public LiveData<Boolean> realNameState(){
        return Repository.getInstance().getUserRealName();
    }

    public String getUserGUID() {
      return Repository.getInstance().getGUID();
    }

    public Boolean getUserRealName() {
        return Repository.getInstance().getRealName();
    }

    public void setFirst(Boolean first) {
         Repository.getInstance().setFirstIn(first);
    }

    public boolean getRealName(){
        return Repository.getInstance().getRealName();
    }


}
