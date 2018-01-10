package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.User;

public interface IUploadUserDetailContract {

    interface Model {
        void uploadUserDetail(Presenter presenter, User user);
        void uploadUserIcon(Presenter presenter, String path);
    }

    interface View {
        void onUploadUserDetailSuccess(String response);
        void onUploadUserDetailFailed(String response);
        void onUploadUserIconSuccess(String url);
        void onUploadUserIconFailed(String response);
    }

    interface Presenter {
        void uploadUserDetail(User user);
        void uploadUserIcon(String path);
        void onUploadUserDetailSuccess(String response);
        void onUploadUserDetailFailed(String response);
        void onUploadUserIconSuccess(String url);
        void onUploadUserIconFailed(String response);
    }

}
