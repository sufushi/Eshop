package com.rdc.shop.eshop.bean;


import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Shoppingcart extends BmobObject implements Serializable {

    private String userId;
    private Good good;
    private Integer count;

    public Shoppingcart() {
    }

    public Shoppingcart(String userId, Good good, Integer count) {
        this.userId = userId;
        this.good = good;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Shoppingcart{" +
                "userId='" + userId + '\'' +
                ", good=" + good +
                ", count=" + count +
                '}';
    }
}
