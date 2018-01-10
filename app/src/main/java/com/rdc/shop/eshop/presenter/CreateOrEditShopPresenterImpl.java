package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.contract.ICreateOrEditShopContract;
import com.rdc.shop.eshop.model.CreateOrEditShopModelImpl;

import java.util.List;

public class CreateOrEditShopPresenterImpl implements ICreateOrEditShopContract.Presenter {

    private ICreateOrEditShopContract.Model mModel;
    private ICreateOrEditShopContract.View mView;

    public CreateOrEditShopPresenterImpl(ICreateOrEditShopContract.View view) {
        mView = view;
        mModel = new CreateOrEditShopModelImpl();
    }

    @Override
    public void uploadShopIcon(String path) {
        mModel.uploadShopIcon(this, path);
    }

    @Override
    public void uploadShopImages(String[] paths) {
        mModel.uploadShopImages(this, paths);
    }

    @Override
    public void uploadShopData(Shop shop) {
        mModel.uploadShopData(this, shop);
    }

    @Override
    public void updateShopData(Shop shop) {
        mModel.updateShopData(this, shop);
    }

    @Override
    public void deleteShop(Shop shop) {
        mModel.deleteShop(this, shop);
    }

    @Override
    public void onUploadShopIconSuccess(String iconUrl) {
        mView.onUploadShopIconSuccess(iconUrl);
    }

    @Override
    public void onUploadShopIconFailed(String response) {
        mView.onUploadShopIconFailed(response);
    }

    @Override
    public void onUploadShopImagesSuccess(List<String> urls) {
        mView.onUploadShopImagesSuccess(urls);
    }

    @Override
    public void onUploadShopImagesFailed(String response) {
        mView.onUploadShopImagesFailed(response);
    }

    @Override
    public void onUploadShopDataSuccess(String response) {
        mView.onUploadShopDataSuccess(response);
    }

    @Override
    public void onUploadShopDataFailed(String response) {
        mView.onUploadShopDataFailed(response);
    }

    @Override
    public void onUpdateShopDataSuccess(String response) {
        mView.onUpdateShopDataSuccess(response);
    }

    @Override
    public void onUpdateShopDataFailed(String response) {
        mView.onUpdateShopDataFailed(response);
    }

    @Override
    public void onDeleteShopSuccess(String response) {
        mView.onDeleteShopSuccess(response);
    }

    @Override
    public void onDeleteShopFailed(String response) {
        mView.onDeleteShopFailed(response);
    }
}
