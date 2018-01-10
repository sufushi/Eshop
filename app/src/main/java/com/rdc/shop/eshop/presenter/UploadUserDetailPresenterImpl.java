package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IUploadUserDetailContract;
import com.rdc.shop.eshop.model.UploadUserDetailModelImpl;

public class UploadUserDetailPresenterImpl implements IUploadUserDetailContract.Presenter {

    private IUploadUserDetailContract.Model mModel;
    private IUploadUserDetailContract.View mView;

    public UploadUserDetailPresenterImpl(IUploadUserDetailContract.View view) {
        mView = view;
        mModel = new UploadUserDetailModelImpl();
    }

    @Override
    public void uploadUserDetail(User user) {
        mModel.uploadUserDetail(this, user);
    }

    @Override
    public void uploadUserIcon(String path) {
        mModel.uploadUserIcon(this, path);
    }

    @Override
    public void onUploadUserDetailSuccess(String response) {
        mView.onUploadUserDetailSuccess(response);
    }

    @Override
    public void onUploadUserDetailFailed(String response) {
        mView.onUploadUserDetailFailed(response);
    }

    @Override
    public void onUploadUserIconSuccess(String url) {
        mView.onUploadUserIconSuccess(url);
    }

    @Override
    public void onUploadUserIconFailed(String response) {
        mView.onUploadUserIconFailed(response);
    }
}
