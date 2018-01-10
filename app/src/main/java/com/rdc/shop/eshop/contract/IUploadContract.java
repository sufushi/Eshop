package com.rdc.shop.eshop.contract;

import cn.bmob.v3.BmobObject;

public interface IUploadContract {

    interface Model<T extends BmobObject> {
        void upload(Presenter presenter, T t);
    }

    interface View {
        void onUploadSuccess(String response);
        void onUploadFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void upload(T t);
        void onUploadSuccess(String response);
        void onUploadFailed(String response);
    }

}
