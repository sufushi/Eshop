package com.rdc.shop.eshop.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Address extends BmobObject implements Serializable{

    private String userId;
    private String contractName;
    private String phoneNumber;
    private String address;
    private Boolean sex;

    public Address() {
    }

    public Address(String userId, String contractName, String phoneNumber, String address, Boolean sex) {
        this.userId = userId;
        this.contractName = contractName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Address{" +
                "contractName='" + contractName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                '}';
    }
}
