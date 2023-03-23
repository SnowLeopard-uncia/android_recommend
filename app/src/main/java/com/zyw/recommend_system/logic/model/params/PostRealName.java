package com.zyw.recommend_system.logic.model.params;

public class PostRealName {
    private String name;
    private String  idNo;

    public PostRealName(String name, String idNo) {
        this.name = name;
        this.idNo = idNo;
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
