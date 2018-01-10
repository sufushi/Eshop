package com.rdc.shop.eshop.bean;

import java.util.List;

public class Collection {

    private Long userId;
    private List<Good> goodsList;

    public Collection() {
    }

    public Collection(Long userId, List<Good> goodsList) {
        this.userId = userId;
        this.goodsList = goodsList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Good> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Good> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "userId=" + userId +
                ", goodsList=" + goodsList +
                '}';
    }
}
