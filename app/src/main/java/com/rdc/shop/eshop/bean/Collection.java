package com.rdc.shop.eshop.bean;

import cn.bmob.v3.BmobObject;

public class Collection extends BmobObject {

    private String userId;
    private Good good;

    public Collection() {

    }

    public Collection(String userId, Good good) {
        this.userId = userId;
        this.good = good;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "userId='" + userId + '\'' +
                ", good=" + good +
                '}';
    }
}
