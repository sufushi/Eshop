package com.rdc.shop.eshop.bean.entity;

public class PersonPlace {

    private Long id;
    private String place;
    private String userName;
    private Boolean sex;
    private String contract;

    public PersonPlace(String place, String userName, Boolean sex, String contract) {
        this.place = place;
        this.userName = userName;
        this.sex = sex;
        this.contract = contract;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "PersonPlace{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", contract='" + contract + '\'' +
                '}';
    }
}
