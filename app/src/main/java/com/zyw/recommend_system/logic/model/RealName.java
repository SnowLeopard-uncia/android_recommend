package com.zyw.recommend_system.logic.model;

public class RealName {
    private int userId=1;
    private int role =0; //0表示没有实名，1表示实名
    private String name ="";
    private String  idNo="";
    private String token="";

    public RealName(int userId, int role, String name, String idNo, String token) {
        this.userId = userId;
        this.role = role;
        this.name = name;
        this.idNo = idNo;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
