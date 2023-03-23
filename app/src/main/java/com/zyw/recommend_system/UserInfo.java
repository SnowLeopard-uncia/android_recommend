package com.zyw.recommend_system;

import com.google.gson.annotations.SerializedName;

/**
 * 此类用于查看用户信息
 */
public class UserInfo {

    private String GUID;
    private String token;
    private String userId;
    private boolean first;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //    @SerializedName("pro_path") //这是一个bug的来源 忘记加序列化的名字了
//    private String proPath;
//
//    private String username;
//
//    public UserInfo(String proPath, String username) {
//        this.proPath = proPath;
//        this.username = username;
//    }
//
//    public String getProPath() {
//        return proPath;
//    }
//
//    public void setProPath(String proPath) {
//        this.proPath = proPath;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
}
