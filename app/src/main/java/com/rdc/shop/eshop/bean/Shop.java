package com.rdc.shop.eshop.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class Shop extends BmobObject implements Serializable {

    private Integer id;
    private Long fansNumber;
    private Long goodsNumber;
    private Long newGoodsNumber;
    private Long discountGoodsNumber;
    private Long commentsNumber;
    private String shopName;
    private String contractNumber;
    private String rate;
    private String shopLogo;
    private String shopImage;
    private String description;
    private List<String> shopImageList;
    private List<User> fansList;
    private List<Good> goodsList;
    private List<Good> newGoodsList;
    private List<Good> discountGoodsList;
    private List<Comment> commentList;
    private User owner;

    public Shop() {
    }

    public Shop(Integer id, Long fansNumber, Long goodsNumber, Long newGoodsNumber,
                Long discountGoodsNumber, Long commentsNumber, String shopName,
                String rate, String shopLogo, String shopImage, String description) {
        this.id = id;
        this.fansNumber = fansNumber;
        this.goodsNumber = goodsNumber;
        this.newGoodsNumber = newGoodsNumber;
        this.discountGoodsNumber = discountGoodsNumber;
        this.commentsNumber = commentsNumber;
        this.shopName = shopName;
        this.rate = rate;
        this.shopLogo = shopLogo;
        this.shopImage = shopImage;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(Long fansNumber) {
        this.fansNumber = fansNumber;
    }

    public Long getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Long goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Long getNewGoodsNumber() {
        return newGoodsNumber;
    }

    public void setNewGoodsNumber(Long newGoodsNumber) {
        this.newGoodsNumber = newGoodsNumber;
    }

    public Long getDiscountGoodsNumber() {
        return discountGoodsNumber;
    }

    public void setDiscountGoodsNumber(Long discountGoodsNumber) {
        this.discountGoodsNumber = discountGoodsNumber;
    }

    public Long getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(Long commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getShopImageList() {
        return shopImageList;
    }

    public void setShopImageList(List<String> shopImageList) {
        this.shopImageList = shopImageList;
    }

    public List<User> getFansList() {
        return fansList;
    }

    public void setFansList(List<User> fansList) {
        this.fansList = fansList;
    }

    public List<Good> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Good> goodsList) {
        this.goodsList = goodsList;
    }

    public List<Good> getNewGoodsList() {
        return newGoodsList;
    }

    public void setNewGoodsList(List<Good> newGoodsList) {
        this.newGoodsList = newGoodsList;
    }

    public List<Good> getDiscountGoodsList() {
        return discountGoodsList;
    }

    public void setDiscountGoodsList(List<Good> discountGoodsList) {
        this.discountGoodsList = discountGoodsList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", fansNumber=" + fansNumber +
                ", goodsNumber=" + goodsNumber +
                ", newGoodsNumber=" + newGoodsNumber +
                ", discountGoodsNumber=" + discountGoodsNumber +
                ", commentsNumber=" + commentsNumber +
                ", shopName='" + shopName + '\'' +
                ", rate='" + rate + '\'' +
                ", shopLogo='" + shopLogo + '\'' +
                ", shopImage='" + shopImage + '\'' +
                ", description='" + description + '\'' +
                ", fansList=" + fansList +
                ", goodsList=" + goodsList +
                ", newGoodsList=" + newGoodsList +
                ", discountGoodsList=" + discountGoodsList +
                ", commentList=" + commentList +
                '}';
    }
}
