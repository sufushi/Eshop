package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetPersonInterestContract;
import com.rdc.shop.eshop.model.GetPersonInterestModelImpl;

import java.util.List;

public class GetPersonInterestPresenterImpl implements IGetPersonInterestContract.Presenter {

    private IGetPersonInterestContract.Model mModel;
    private IGetPersonInterestContract.View mView;

    public GetPersonInterestPresenterImpl(IGetPersonInterestContract.View view) {
        mView = view;
        mModel = new GetPersonInterestModelImpl();
    }

    @Override
    public void getPersonInterest(String userId) {
        mModel.getPersonInterest(this, userId);
    }

    @Override
    public void onGetPersonInterestSuccess(List<Good> goodList) {
        mView.onGetPersonInterestSuccess(goodList);
    }

    @Override
    public void onGetPersonInterestFailed(String response) {
        mView.onGetPersonInterestFailed(response);
    }
}
