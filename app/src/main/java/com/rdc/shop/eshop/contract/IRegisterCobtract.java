package com.rdc.shop.eshop.contract;

public interface IRegisterCobtract {

    interface Model {
        void register(Presenter presenter, String phoneNumber, String password, String confirmPassword);
    }

    interface View {
        void onRegisterSuccess(String response);
        void onRegisterFailed(String response);
    }

    interface Presenter {
        void register(String phoneNumber, String password, String confirmPassword);
        void onRegisterSuccess(String response);
        void onRegisterFailed(String response);
    }

}
