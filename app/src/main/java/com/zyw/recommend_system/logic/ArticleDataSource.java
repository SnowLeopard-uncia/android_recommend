package com.zyw.recommend_system.logic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagingState;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.ArticleList;
import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.network.ServiceCreator;
import com.zyw.recommend_system.logic.network.service.ArticleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.HttpException;

/**
 * 第一步是定义用于标识数据源的 PagingSource 实现。PagingSource API 类包含 load() 方法，
 * 换该方法，以指明如何从相应数据源检索分页数据。
 * Key 和 Value。键定义了用于加载数据的标识符，值是数据本身的类型。例如，
 * 如果您通过将 Int 页码传递给 Retrofit 来从网络加载各页 User 对象，则应选择 Int 作为 Key 类型，选择 User 作为 Value 类型。
 */
public class ArticleDataSource extends ListenableFuturePagingSource<Integer, Article> {
//    PagingSource 实现会将其构造函数中提供的参数传递给 load() 方法，以便为查询加载适当的数据。
//    @NonNull
//   private ArticleService mArticleService;  //Retrofit的实例
   @NonNull
   private String mQuery;  //查询需要的内容

   private ExecutorService pool= Executors.newCachedThreadPool();//使用线程池

    LoadDataInterface<Article> loadDataInterface; //加载数据的接口

    public ArticleDataSource(@NonNull LoadDataInterface<Article> loadDataInterface) {
        this.loadDataInterface = loadDataInterface;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, Article>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
        // 从第一页开始加载
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }
//     loadParams   加载的键和要加载的项数
        /*
        try {
            mArticleService = ServiceCreator.create(ArticleService.class);
            Call<BaseResponse<ArticleList>> responseCall = mArticleService.getSearchArticleList("",
                    "","","","1");
            //同步方法，反正load里面是挂起的函数
            BaseResponse<ArticleList> articleListBaseResponse = responseCall.execute().body();
            if (articleListBaseResponse!=null){
                List<Article> list = articleListBaseResponse.getData().getArticleList();
               int currPage = articleListBaseResponse.getData().getCurrPage();
                return new LoadResult.Page<>(
                                list,
                        null,
                        currPage+1,
                        LoadResult.Page.COUNT_UNDEFINED,
                        LoadResult.Page.COUNT_UNDEFINED
                        );
            }
        }catch (Exception e){
           e.printStackTrace();
        }
            */
        Integer finalNextPageNumber = nextPageNumber;
        //处理错误
        ListenableFuture<LoadResult<Integer, Article>> pageFuture =
                Futures.transform(loadDataInterface.load(finalNextPageNumber),  //mArticleService的接口
                        (Function<List<Article>, LoadResult.Page<Integer, Article>>) input -> {
//					这里传入的三个参数中,刚才请求的数据,第二个参数为请求的上一页的页数,当为null时不再加载上一页,第三个参数则是下一页
                            if (input==null){
                                input=new ArrayList<>();
                            }
//					load()经load得到数据后,将数据传回,返回加载结果
                            return new LoadResult.Page<>(input,
                                    finalNextPageNumber==1?null:finalNextPageNumber-1,
                                    input.isEmpty()?null:finalNextPageNumber+1);
                        }, pool);
        ListenableFuture<LoadResult<Integer, Article>> partialLoadResultFuture =
                Futures.catching(pageFuture, HttpException.class,
                        LoadResult.Error::new, pool);

        return Futures.catching(partialLoadResultFuture,
                IOException.class, LoadResult.Error::new, pool);


    }

//    private LoadResult<Integer, Article> toLoadResult(@NonNull SearchUserResponse response) {
//        return new LoadResult.Page<>(response.getUsers(),
//                null, // Only paging forward.
//                response.getNextPageNumber(),
//                LoadResult.Page.COUNT_UNDEFINED,
//                LoadResult.Page.COUNT_UNDEFINED);
//    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Article> pagingState) {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> 当前页面是第一页
        //  * nextKey == null -> 当前页面是最后一页
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Article> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }


}
