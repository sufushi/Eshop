package com.rdc.shop.eshop.contract;

import java.util.List;

import cn.bmob.v3.BmobObject;

public interface IDeleteBatchContract {

    interface Model<T extends BmobObject> {
        void deleteBatch(Presenter presenter, List<T> tList);
    }

    interface View {
        void onDeleteBatchSuccess(String response);
        void onDeleteBatchFailed(String response);
    }

    interface Presenter<T extends BmobObject> {
        void deleteBatch(List<T> tList);
        void onDeleteBatchSuccess(String response);
        void onDeleteBatchFailed(String response);
    }

}
