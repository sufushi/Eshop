package com.rdc.shop.eshop.bean;

import java.util.Date;

public class Coupon {

    private Long userId;
    private Long value;
    private String provider;
    private String details;
    private Date expiryDate;

    public Coupon() {
    }

    public Coupon(Long value, String provider, String details, Date expiryDate) {
        this.value = value;
        this.provider = provider;
        this.details = details;
        this.expiryDate = expiryDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "userId=" + userId +
                ", value=" + value +
                ", provider='" + provider + '\'' +
                ", details='" + details + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
