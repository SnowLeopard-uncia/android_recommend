package com.zyw.recommend_system.logic.network;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.zyw.recommend_system.MyApplication;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.BaseResponse;
import com.zyw.recommend_system.logic.model.RealName;
import com.zyw.recommend_system.logic.model.UserLogin;
import com.zyw.recommend_system.logic.model.params.ArticleId;
import com.zyw.recommend_system.logic.model.params.ArticlePost;
import com.zyw.recommend_system.logic.model.params.PostRealName;
import com.zyw.recommend_system.logic.network.service.ArticleService;
import com.zyw.recommend_system.logic.network.service.CollectionService;
import com.zyw.recommend_system.logic.network.service.LikeService;
import com.zyw.recommend_system.logic.network.service.UserService;
import com.zyw.recommend_system.utils.ApiException;
import com.zyw.recommend_system.utils.RetrofitCallback;
import com.zyw.recommend_system.utils.StatusCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleNetWork {
    /*service*/
    private static UserService userService = ServiceCreator.create(UserService.class);
    private static ArticleService articleService = ServiceCreator.create(ArticleService.class);
    private static LikeService likeService = ServiceCreator.create(LikeService.class);
    private static CollectionService collectionService = ServiceCreator.create(CollectionService.class);

   static  MutableLiveData<UserLogin> loginData = new MutableLiveData<>();

    static MutableLiveData<Boolean> isCollect = new MutableLiveData<>();

    static MutableLiveData<Boolean> isCollectDelete = new MutableLiveData<>();

    static MutableLiveData<Boolean> isLike = new MutableLiveData<>();

    static MutableLiveData<Boolean> isLikeDelete = new MutableLiveData<>();

    static MutableLiveData<RealName> postRealNameLiveData = new MutableLiveData<>();

    static MutableLiveData<Boolean> realNameState = new MutableLiveData<>();

    static MutableLiveData<Boolean> postArticleState = new MutableLiveData<>();

    static MutableLiveData<Article> articleDetail = new MutableLiveData<>();


    public static LiveData<UserLogin> userLogin(String GUID){
        ArticleId articleId = new ArticleId(GUID);
        userService.userLogin(articleId).enqueue(new RetrofitCallback<BaseResponse<UserLogin>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserLogin>> call, Response<BaseResponse<UserLogin>> response) {
                UserLogin userLogin = response.body() != null ? response.body().getData() : null;
                loginData.postValue(userLogin);
            }
        });
        return loginData;
    }

    public static LiveData<RealName> postRealName(String name,String number){
        PostRealName postRealName = new PostRealName(name,number);
        userService.userRealName(postRealName).enqueue(new Callback<BaseResponse<RealName>>() {
            @Override
            public void onResponse(Call<BaseResponse<RealName>> call, Response<BaseResponse<RealName>> response) {
                // TODO: 2023/3/22 请求成功的
                assert response.body() != null;
                int state = response.body().getCode();
                String message = response.body().getMsg();
                if (state == StatusCode.SUCCESS.code){
                    postRealNameLiveData.postValue(response.body().getData());
                }else  {
                    Toast.makeText(MyApplication.getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RealName>> call, Throwable t) {
                // TODO: 2023/3/22 请求失败的接口状态
                if (t instanceof ApiException){
                    ApiException apiException = (ApiException) t;
                    if (apiException.isFail()){
                        t.printStackTrace();//相应处
                        Log.e("ArticleWord", "onFailure: "+apiException.getMsg());
                        Toast.makeText(MyApplication.getContext(),apiException.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return postRealNameLiveData;
    }

    public static LiveData<Boolean> getUserRealName(){
        userService.getUserRealName().enqueue(new RetrofitCallback<BaseResponse<RealName>>() {
            @Override
            public void onResponse(Call<BaseResponse<RealName>> call, Response<BaseResponse<RealName>> response) {
                RealName realName = response.body() != null ? response.body().getData() : null;
                boolean state ;
                if (realName.getRole()==0){
                    state = false;
                }else {
                    state=true;
                }
                realNameState.postValue(state);
            }
        });
        return realNameState;
    }

    public static LiveData<Boolean> postArticle(String title, String author, String summary, String keyWord, String link, String classify, String pubtime){
        ArticlePost articlePost = new ArticlePost(title,author,summary,keyWord,link,classify,pubtime);
        articleService.postArticle(articlePost).enqueue(new RetrofitCallback<BaseResponse<UserLogin>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserLogin>> call, Response<BaseResponse<UserLogin>> response) {
                Log.e("ArticleWord", "onResponse: "+response.toString());
                assert response.body() != null;
                int state = response.body().getCode();
                String message = response.body().getMsg();
                if (state == StatusCode.SUCCESS.code){
                    postArticleState.postValue(true);
                }else if (state == StatusCode.ERROR.code){
                    Toast.makeText(MyApplication.getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return postArticleState;
    }



    public static LiveData<Boolean> collect(String articleId){
        collectionService.addCollection(articleId).enqueue(new RetrofitCallback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                Integer code = response.body()!=null?response.body().getCode():null;
                if (code==StatusCode.SUCCESS.code){
                    Toast.makeText(MyApplication.getContext(),"收藏成功!",Toast.LENGTH_SHORT).show();
                    isCollect.postValue(true);
                }else {
                    isCollect.postValue(false);
                }

            }
        });
        return isCollect;
    }


    public static LiveData<Boolean> deleteCollection(String articleId){
        collectionService.deleteCollection(articleId).enqueue(new RetrofitCallback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                Integer code = response.body()!=null?response.body().getCode():null;
                if (code==StatusCode.SUCCESS.code){
                    isCollectDelete.postValue(true);
                    Toast.makeText(MyApplication.getContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                }else {
                    isCollectDelete.postValue(false);
                }
            }
        });
        return isCollectDelete;
    }
    public static LiveData<Boolean> like(String articleId){
        likeService.addLike(articleId).enqueue(new RetrofitCallback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                Integer code = response.body()!=null?response.body().getCode():null;
                if (code==StatusCode.SUCCESS.code){
                    Toast.makeText(MyApplication.getContext(),"点赞成功!",Toast.LENGTH_SHORT).show();
                    isLike.postValue(true);
                }else {
                    isLike.postValue(false);
                }

            }
        });
        return isLike;
    }

    public static LiveData<Boolean> deleteLike(String articleId){
        likeService.deleteLike(articleId).enqueue(new RetrofitCallback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                Integer code = response.body()!=null?response.body().getCode():null;
                if (code==StatusCode.SUCCESS.code){
                    isLikeDelete.postValue(true);
                    Toast.makeText(MyApplication.getContext(),"取消喜欢",Toast.LENGTH_SHORT).show();
                }else {
                    isLikeDelete.postValue(false);
                }

            }
        });
        return isLikeDelete;
    }

    //阅读文章详情

    public static LiveData<Article> getArticleDetail(String articleId){
        articleService.getArticleDetail(articleId).enqueue(new RetrofitCallback<BaseResponse<Article>>() {
            @Override
            public void onResponse(Call<BaseResponse<Article>> call, Response<BaseResponse<Article>> response) {
                Article article = response.body() != null ? response.body().getData() : null;
                articleDetail.postValue(article);
            }
        });
        return articleDetail;
    }


}
