package com.rdc.shop.eshop.bean.entity;

public class Store {

    private Integer storeId;
    private String storeName;
    private Boolean isChoosed;
    private Boolean isEdit;

    public Store() {
    }

    public Store(Integer storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
        isChoosed = false;
        isEdit = false;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Boolean getChoosed() {
        return isChoosed;
    }

    public void setChoosed(Boolean choosed) {
        isChoosed = choosed;
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }
}
