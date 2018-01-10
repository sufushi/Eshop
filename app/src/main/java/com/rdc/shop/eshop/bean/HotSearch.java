package com.rdc.shop.eshop.bean;

public class HotSearch {

    private Long count;
    private String keyname;

    public HotSearch() {
    }

    public HotSearch(Long count, String keyName) {
        this.count = count;
        this.keyname = keyName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    @Override
    public String toString() {
        return "HotSearch{" +
                "count=" + count +
                ", keyname='" + keyname + '\'' +
                '}';
    }
}
