package com.zyw.recommend_system.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zyw.recommend_system.logic.dao.UserDao;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.UserInfo;
import com.zyw.recommend_system.logic.model.RealName;
import com.zyw.recommend_system.logic.model.UserLogin;
import com.zyw.recommend_system.logic.model.params.ArticlePost;
import com.zyw.recommend_system.logic.network.ArticleNetWork;


import java.util.List;

/**
 * 一般在仓库层中定义的方法，为了能将异步获取的数据以响应式编程的方式通知给上一层，通常会返回一个LiveData对象。
 */
public class Repository {

    private static Repository repository;
    public static Repository getInstance(){
        if (repository==null){
            repository=new Repository();
        }
        return repository;
    }

    public Repository() {

    }

    public LiveData<UserLogin> userLogin(String GUID){
       return ArticleNetWork.userLogin(GUID);
    }

    public LiveData<RealName> postRealName(String name, String number){
        return ArticleNetWork.postRealName(name,number);
    }

    public LiveData<Boolean> postArticle(String title, String author, String summary, String keyWord, String link, String classify, String pubtime){
        return ArticleNetWork.postArticle(title,author,summary,keyWord,link,classify,pubtime);
    }

    public LiveData<Boolean> getUserRealName(){
        return ArticleNetWork.getUserRealName();
    }

    public LiveData<Boolean> collect(String articleId){
            return ArticleNetWork.collect(articleId);
    }

    public LiveData<Boolean> collectDelete(String articleId){
        return ArticleNetWork.deleteCollection(articleId);
    }
    public LiveData<Boolean> like(String articleId){
        return ArticleNetWork.like(articleId);
    }

    public LiveData<Boolean> deleteLike(String articleId){
        return ArticleNetWork.deleteLike(articleId);
    }

    public LiveData<Article> getArticleDetail(String articleId){
        return ArticleNetWork.getArticleDetail(articleId);
    }

    //sharepreference
    public boolean getFirstIn(){
        return UserDao.getFirst();
    }

    public void setFirstIn(Boolean isFirst){
        UserDao.saveFirst(isFirst);
    }

    public String getGUID(){
        return UserDao.getGUID();
    }


    public String getUserId(){
        return UserDao.getUserId();
    }

    public String getToken(){
        return UserDao.getToken();
    }

    public void setToken(String token){
         UserDao.saveToken(token);
    }

    public Boolean getRealName(){
        return UserDao.getRealName();
    }

    public void setRealName(Boolean realName){
        UserDao.setRealName(realName);
    }

    public int getTag(){
        return UserDao.getTag();
    }

    public void setTag(int tag){
        UserDao.setTag(tag);
    }
}
