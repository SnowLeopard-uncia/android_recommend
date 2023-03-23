package com.zyw.recommend_system.logic.model;

import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class Article implements Serializable, Parcelable {
    @SerializedName("articleId")
    private Integer articleId;
    private String title;
    private String author;
    private String summary;
    @SerializedName("keyword")
    private String keyWord;
    private String link;
    private int views;
    private int like;
    private int stars;
    @SerializedName("pubTime")
    private String createTime;
    private boolean liked;
    private boolean stared;

    public Article(Integer articleId, String title, String author, String summary, String keyWord, String link, int views, int like, int stars, String createTime, boolean liked, boolean stared) {
        this.articleId = articleId;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.keyWord = keyWord;
        this.link = link;
        this.views = views;
        this.like = like;
        this.stars = stars;
        this.createTime = createTime;
        this.liked = liked;
        this.stared = stared;
    }

    protected Article(Parcel in) {
        if (in.readByte() == 0) {
            articleId = null;
        } else {
            articleId = in.readInt();
        }
        title = in.readString();
        author = in.readString();
        summary = in.readString();
        keyWord = in.readString();
        link = in.readString();
        views = in.readInt();
        like = in.readInt();
        stars = in.readInt();
        createTime = in.readString();
        liked = in.readByte() != 0;
        stared = in.readByte() != 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Integer getId() {
        return articleId;
    }

    public void setId(Integer articleId) {
        this.articleId = articleId;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (articleId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(articleId);
        }
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(summary);
        dest.writeString(keyWord);
        dest.writeString(link);
        dest.writeInt(views);
        dest.writeInt(like);
        dest.writeInt(stars);
        dest.writeString(createTime);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeByte((byte) (stared ? 1 : 0));
    }


    @NonNull
    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", link='" + link + '\'' +
                ", views=" + views +
                ", like=" + like +
                ", stars=" + stars +
                ", createTime=" + createTime +
                ", liked=" + liked +
                ", stared=" + stared +
                '}';
    }
}
