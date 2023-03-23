package com.zyw.recommend_system.logic.model.params;

public class ArticlePost {
    String title;
    String author;
    String summary;
    String keyWord;
    String link;
    String classify;
    String pubtime;

    public ArticlePost(String title, String author, String summary, String keyWord, String link, String classify, String pubtime) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.keyWord = keyWord;
        this.link = link;
        this.classify = classify;
        this.pubtime = pubtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }
}
