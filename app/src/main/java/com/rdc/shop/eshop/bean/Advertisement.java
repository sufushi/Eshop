package com.rdc.shop.eshop.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class Advertisement extends BmobObject implements Serializable{

    private Long shopId;
    private List<String> imageList;
    private List<String> titleList;

    public Advertisement() {
    }

    public Advertisement(Long shopId, List<String> imageList, List<String> titleList) {
        this.shopId = shopId;
        this.imageList = imageList;
        this.titleList = titleList;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "shopId=" + shopId +
                ", imageList=" + imageList +
                ", titleList=" + titleList +
                '}';
    }
}
