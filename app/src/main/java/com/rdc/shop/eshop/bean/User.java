package com.rdc.shop.eshop.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser implements Serializable {

    private Long id;
//    private String username;
    private String UserPassword;
    private String phoneNumber;
    private String userIcon;
    private String location;
    private Boolean sex;
    private BmobFile iconFile;  //头像文件

    public User() {
    }

    public User(Long id, String UserPassword, String phoneNumber, String userIcon, String location, Boolean sex) {
        this.id = id;
//        this.username = username;
        this.UserPassword = UserPassword;
        this.phoneNumber = phoneNumber;
        this.userIcon = userIcon;
        this.location = location;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String password) {
        this.UserPassword = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public BmobFile getIconFile() {
        return iconFile;
    }

    public void setIconFile(BmobFile iconFile) {
        this.iconFile = iconFile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
//                ", username='" + username + '\'' +
                ", UserPassword='" + UserPassword + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", location='" + location + '\'' +
                ", sex=" + sex +
                '}';
    }
}
