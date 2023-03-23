package com.zyw.recommend_system.logic.model;

import java.util.List;

public class ArticleList {
    private List<Article> articleList;
    private int currPage;
    private int totalPage;

    public ArticleList(List<Article> articleList, int currPage, int totalPage) {
        this.articleList = articleList;
        this.currPage = currPage;
        this.totalPage = totalPage;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
