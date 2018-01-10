package com.rdc.shop.eshop.bean;

public class GoodxShop {

    private Good good;
    private Advertisement advertisement;
    private int type;

    public GoodxShop() {
    }

    public GoodxShop(Good good, Advertisement advertisement, int type) {
        this.good = good;
        this.advertisement = advertisement;
        this.type = type;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GoodxShop{" +
                "good=" + good +
                ", advertisement=" + advertisement +
                ", type=" + type +
                '}';
    }
}
