package com.rdc.shop.eshop.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class Good extends BmobObject implements Serializable{

    private Long id;
    private Long category;
    private Long salesNumber;
    private Long reserve;
    private Long shopId;
    private Long count;
    private String sellerId;
    private String goodName;
    private String goodIcon;
    private String description;
    private String size;
    private String color;
    private Float price;
    private Float discountPrice;
    private List<String> goodImageList;
    private Boolean isChoosed;
    private Shop shop;

    public Good() {
    }


    public Good(Long id, String goodIcon, String goodName,Float price,
                Float discountPrice, Long count, String color, String size) {
        this.id = id;
        this.goodIcon = goodIcon;
        this.goodName = goodName;
        this.price = price;
        this.discountPrice = discountPrice;
        this.count = count;
        this.color = color;
        this.size = size;
        isChoosed = false;
    }

    public Good(Long id, Long category, Long salesNumber, Long reserve, Long shopId, Long count,
                String goodName, String goodIcon, String description, String size, String color,
                Float price, Float discountPrice, List<String> goodImageList, Boolean isChoosed) {
        this.id = id;
        this.category = category;
        this.salesNumber = salesNumber;
        this.reserve = reserve;
        this.shopId = shopId;
        this.count = count;
        this.goodName = goodName;
        this.goodIcon = goodIcon;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.discountPrice = discountPrice;
        this.goodImageList = goodImageList;
        this.isChoosed = isChoosed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(Long salesNumber) {
        this.salesNumber = salesNumber;
    }

    public Long getReserve() {
        return reserve;
    }

    public void setReserve(Long reserve) {
        this.reserve = reserve;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodIcon() {
        return goodIcon;
    }

    public void setGoodIcon(String goodIcon) {
        this.goodIcon = goodIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<String> getGoodImageList() {
        return goodImageList;
    }

    public void setGoodImageList(List<String> goodImageList) {
        this.goodImageList = goodImageList;
    }

    public Boolean getChoosed() {
        return isChoosed;
    }

    public void setChoosed(Boolean choosed) {
        isChoosed = choosed;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", category=" + category +
                ", salesNumber=" + salesNumber +
                ", reserve=" + reserve +
                ", shopId=" + shopId +
                ", goodName='" + goodName + '\'' +
                ", goodIcon='" + goodIcon + '\'' +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
