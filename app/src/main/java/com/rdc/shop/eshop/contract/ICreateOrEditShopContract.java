package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Shop;

import java.util.List;

public interface ICreateOrEditShopContract {

    interface Model {
        void uploadShopIcon(Presenter presenter, String path);
        void uploadShopImages(Presenter presenter, String[] paths);
        void uploadShopData(Presenter presenter, Shop shop);
        void updateShopData(Presenter presenter, Shop shop);
        void deleteShop(Presenter presenter, Shop shop);
    }

    interface View {
        void onUploadShopIconSuccess(String iconUrl);
        void onUploadShopIconFailed(String response);
        void onUploadShopImagesSuccess(List<String> urls);
        void onUploadShopImagesFailed(String response);
        void onUploadShopDataSuccess(String response);
        void onUploadShopDataFailed(String response);
        void onUpdateShopDataSuccess(String response);
        void onUpdateShopDataFailed(String response);
        void onDeleteShopSuccess(String response);
        void onDeleteShopFailed(String response);
    }

    interface Presenter {
        void uploadShopIcon(String path);
        void uploadShopImages(String[] paths);
        void uploadShopData(Shop shop);
        void updateShopData(Shop shop);
        void deleteShop(Shop shop);
        void onUploadShopIconSuccess(String iconUrl);
        void onUploadShopIconFailed(String response);
        void onUploadShopImagesSuccess(List<String> urls);
        void onUploadShopImagesFailed(String response);
        void onUploadShopDataSuccess(String response);
        void onUploadShopDataFailed(String response);
        void onUpdateShopDataSuccess(String response);
        void onUpdateShopDataFailed(String response);
        void onDeleteShopSuccess(String response);
        void onDeleteShopFailed(String response);
    }

}
