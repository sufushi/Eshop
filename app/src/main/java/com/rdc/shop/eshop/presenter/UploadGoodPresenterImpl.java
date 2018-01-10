package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IUploadGoodContract;
import com.rdc.shop.eshop.model.UploadGoodModelImpl;

import java.util.List;

public class UploadGoodPresenterImpl implements IUploadGoodContract.Presenter {

    private IUploadGoodContract.Model mModel;
    private IUploadGoodContract.View mView;

    public UploadGoodPresenterImpl(IUploadGoodContract.View view) {
        mView = view;
        mModel = new UploadGoodModelImpl();
    }

    @Override
    public void uploadImageList(String[] paths) {
        mModel.uploadImageList(this, paths);
    }

    @Override
    public void uploadGoodData(Good good) {
        mModel.uploadGoodData(this, good);
    }

    @Override
    public void onUploadImageListSuccess(List<String> urls) {
        mView.onUploadImageListSuccess(urls);
    }

    @Override
    public void onUploadImageListFailed(String response) {
        mView.onUploadImageListFailed(response);
    }

    @Override
    public void onUploadGoodDataSuccess(String response) {
        mView.onUploadGoodDataSuccess(response);
    }

    @Override
    public void onUploadGoodDataFailed(String response) {
        mView.onUploadGoodDataFailed(response);
    }
}
