package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.contract.IGetShopContract;
import com.rdc.shop.eshop.model.GetShopModelImpl;

import java.util.List;

public class GetShopPresenterImpl implements IGetShopContract.Presenter {

    private IGetShopContract.Model mModel;
    private IGetShopContract.View mView;

    public GetShopPresenterImpl(IGetShopContract.View view) {
        mView = view;
        mModel = new GetShopModelImpl();
    }

    @Override
    public void getShop() {
        mModel.getShop(this);
    }

    @Override
    public void getShopById(int shopId) {
        mModel.getShopById(this, shopId);
    }

    @Override
    public void onGetShopSuccess(List<Shop> shopList) {
        mView.onGetShopSuccess(shopList);
    }

    @Override
    public void onGetShopFailed(String response) {
        mView.onGetShopFailed(response);
    }
}
