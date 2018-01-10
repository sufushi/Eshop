package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.contract.IUploadContract;
import com.rdc.shop.eshop.model.UploadModelImpl;

import cn.bmob.v3.BmobObject;


public class UploadPresenterImpl<T extends BmobObject> implements IUploadContract.Presenter {

    private IUploadContract.Model<T> mModel;
    private IUploadContract.View mView;

    public UploadPresenterImpl(IUploadContract.View view) {
        mView = view;
        mModel = new UploadModelImpl();
    }

    @Override
    public void upload(BmobObject bmobObject) {
        mModel.upload(this, (T) bmobObject);
    }

    @Override
    public void onUploadSuccess(String response) {
        mView.onUploadSuccess(response);
    }

    @Override
    public void onUploadFailed(String response) {
        mView.onUploadFailed(response);
    }
}
