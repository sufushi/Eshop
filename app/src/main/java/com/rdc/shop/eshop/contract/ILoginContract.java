package com.rdc.shop.eshop.contract;



public interface ILoginContract {

    interface Model {
        void login(Presenter presenter, String username, String password);
    }

    interface View {
        void onLoginSuccess(String msg);
        void onLoginFailed(String msg);
    }

    interface Presenter {
        void login(String username, String password);
        void onLoginSuccess(String msg);
        void onLoginFailed(String msg);
    }
}
