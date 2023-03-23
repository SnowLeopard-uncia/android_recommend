package com.zyw.recommend_system.ui.upload;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.logic.model.params.ArticlePost;

public class UploadLoadViewModel extends ViewModel {

    LiveData<Boolean> state;

    public LiveData<Boolean> postArticleState(String title, String author, String summary, String keyWord, String link, String classify, String pubtime){
        state= Repository.getInstance().postArticle(title,author,summary,keyWord,link,classify,pubtime);
        return state;
    }

}
