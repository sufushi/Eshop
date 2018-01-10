package com.rdc.shop.eshop.bean;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject{

    private Long id;
    private String orderNumber;
    private User user;
    private Good good;
    private String address;
    private String remark;
    private int state;

    public Order() {

    }

    public Order(Long id, String orderNumber, User user, Good good, String address) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.user = user;
        this.good = good;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", user=" + user +
                ", good=" + good +
                ", address='" + address + '\'' +
                ", remark='" + remark + '\'' +
                ", state=" + state +
                '}';
    }
}
