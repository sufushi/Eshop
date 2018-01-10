package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.contract.IDeleteBatchContract;
import com.rdc.shop.eshop.model.DeleteBatchModelImpl;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class DeleteBatchPresenterImpl<T extends BmobObject> implements IDeleteBatchContract.Presenter<T> {

    private IDeleteBatchContract.Model<T> mModel;
    private IDeleteBatchContract.View mView;

    public DeleteBatchPresenterImpl(IDeleteBatchContract.View view) {
        mView = view;
        mModel = new DeleteBatchModelImpl<>();
    }

    @Override
    public void deleteBatch(List list) {
        mModel.deleteBatch(this, list);
    }

    @Override
    public void onDeleteBatchSuccess(String response) {
        mView.onDeleteBatchSuccess(response);
    }

    @Override
    public void onDeleteBatchFailed(String response) {
        mView.onDeleteBatchFailed(response);
    }
}
