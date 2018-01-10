package com.rdc.shop.eshop.contract;

import cn.bmob.v3.BmobObject;

public interface IDeleteContract {

    interface Model<T extends BmobObject> {
        void delete(Presenter presenter, T t);
    }

    interface View {
        void onDeleteSuccess(String response);
        void onDeleteFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void delete(T t);
        void onDeleteSuccess(String response);
        void onDeleteFailed(String response);
    }

}
