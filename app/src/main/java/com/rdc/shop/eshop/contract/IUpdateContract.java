package com.rdc.shop.eshop.contract;

import cn.bmob.v3.BmobObject;

public interface IUpdateContract {

    interface Model<T extends BmobObject> {
        void update(IUpdateContract.Presenter presenter, T t);
    }

    interface View {
        void onUpdateSuccess(String response);
        void onUpdateFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void update(T t);
        void onUpdateSuccess(String response);
        void onUpdateFailed(String response);
    }
}
