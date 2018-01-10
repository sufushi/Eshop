package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Good;

import java.util.List;

public interface IUploadGoodContract {

    interface Model {
        void uploadImageList(Presenter presenter, String[] paths);
        void uploadGoodData(Presenter presenter, Good good);
    }

    interface View {
        void onUploadImageListSuccess(List<String> urls);
        void onUploadImageListFailed(String response);
        void onUploadGoodDataSuccess(String response);
        void onUploadGoodDataFailed(String response);
    }

    interface Presenter {
        void uploadImageList(String[] paths);
        void uploadGoodData(Good good);
        void onUploadImageListSuccess(List<String> urls);
        void onUploadImageListFailed(String response);
        void onUploadGoodDataSuccess(String response);
        void onUploadGoodDataFailed(String response);
    }

}
