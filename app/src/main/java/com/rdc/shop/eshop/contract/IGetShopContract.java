package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Shop;

import java.util.List;

public interface IGetShopContract {

    interface Model {
        void getShop(Presenter presenter);
        void getShopById(Presenter presenter, int shopId);
    }

    interface View {
        void onGetShopSuccess(List<Shop> shopList);
        void onGetShopFailed(String response);
    }

    interface Presenter {
        void getShop();
        void getShopById(int shopId);
        void onGetShopSuccess(List<Shop> shopList);
        void onGetShopFailed(String response);
    }

}
