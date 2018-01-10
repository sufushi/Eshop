package com.rdc.shop.eshop.contract;

import java.util.List;

import cn.bmob.v3.BmobObject;

public interface IUploadBatchContract {

    interface Model<T extends BmobObject> {
        void uploadBatch(Presenter presenter, List<T> tList);
    }

    interface View {
        void onUploadBatchSuccess(String response);
        void onUploadBatchFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void uploadBatch(List<T> tList);
        void onUploadBatchSuccess(String response);
        void onUploadBatchFailed(String response);
    }

}
