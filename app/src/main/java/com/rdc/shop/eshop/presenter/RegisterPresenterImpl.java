package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.contract.IRegisterCobtract;
import com.rdc.shop.eshop.model.RegisterModelImpl;

public class RegisterPresenterImpl implements IRegisterCobtract.Presenter {

    private IRegisterCobtract.Model mModel;
    private IRegisterCobtract.View mView;

    public RegisterPresenterImpl(IRegisterCobtract.View view) {
        mView = view;
        mModel = new RegisterModelImpl();
    }

    @Override
    public void register(String phoneNumber, String password, String confirmPassword) {
        mModel.register(this, phoneNumber, password, confirmPassword);
    }

    @Override
    public void onRegisterSuccess(String response) {
        mView.onRegisterSuccess(response);
    }

    @Override
    public void onRegisterFailed(String response) {
        mView.onRegisterFailed(response);
    }
}
