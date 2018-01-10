package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.contract.IUploadBatchContract;
import com.rdc.shop.eshop.model.UploadBatchModelImpl;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class UploadBatchPresenterImpl<T extends BmobObject> implements IUploadBatchContract.Presenter<T> {

    private IUploadBatchContract.Model<T> mModel;
    private IUploadBatchContract.View mView;

    public UploadBatchPresenterImpl(IUploadBatchContract.View view) {
        mView = view;
        mModel = new UploadBatchModelImpl<>();
    }

    @Override
    public void uploadBatch(List<T> list) {
        mModel.uploadBatch(this, list);
    }

    @Override
    public void onUploadBatchSuccess(String response) {
        mView.onUploadBatchSuccess(response);
    }

    @Override
    public void onUploadBatchFailed(String response) {
        mView.onUploadBatchFailed(response);
    }
}
