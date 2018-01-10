package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.User;

public interface IGetUserDetailContract {

    interface Model {
        void getUserDetail(Presenter presenter, String userId);
    }

    interface View {
        void onGetUserDetailSuccess(User user);
        void onGetUserDetailFailed(String response);
    }

    interface Presenter {
        void getUserDetail(String userId);
        void onGetUserDetailSuccess(User user);
        void onGetUserDeatilFailed(String response);
    }
}
