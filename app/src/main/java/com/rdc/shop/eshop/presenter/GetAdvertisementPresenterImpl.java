package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Advertisement;
import com.rdc.shop.eshop.contract.IGetAdvertisementContract;
import com.rdc.shop.eshop.model.GetAdvertisementModelImpl;

public class GetAdvertisementPresenterImpl implements IGetAdvertisementContract.Presenter {

    private IGetAdvertisementContract.Model mModel;
    private IGetAdvertisementContract.View mView;

    public GetAdvertisementPresenterImpl(IGetAdvertisementContract.View view) {
        mView = view;
        mModel = new GetAdvertisementModelImpl();
    }

    @Override
    public void getAdvertisement() {
        mModel.getAdvertisement(this);
    }

    @Override
    public void onGetAdvertisementSuccess(Advertisement advertisement) {
        mView.onGetAdvertisementSuccess(advertisement);
    }

    @Override
    public void onGetAdvertisementFailed(String response) {
        mView.onGetAdvertisementFailed(response);
    }
}
