package com.zyw.recommend_system.ui.article;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.logic.model.RealName;

public class ArticleDetailViewModel extends ViewModel {

    public LiveData<Boolean> like(String articleId){
        return Repository.getInstance().like(articleId);
    }

    public LiveData<Boolean> collect(String articleId){
        return Repository.getInstance().collect(articleId);
    }

    public LiveData<Boolean> deleteLike(String articleId){
        return Repository.getInstance().deleteLike(articleId);
    }

    public LiveData<Boolean> collectDelete(String articleId){
        return Repository.getInstance().collectDelete(articleId);
    }
    public LiveData<Article> getArticleDetail(String articleId){
        return Repository.getInstance().getArticleDetail(articleId);
    }
}
