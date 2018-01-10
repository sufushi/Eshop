package com.rdc.shop.eshop.presenter;


import com.rdc.shop.eshop.contract.ILoginContract;
import com.rdc.shop.eshop.model.LoginModelImpl;

public class LoginPresenterImpl implements ILoginContract.Presenter {

    private ILoginContract.Model mModel;
    private ILoginContract.View mView;

    public LoginPresenterImpl(ILoginContract.View view) {
        mView = view;
        mModel = new LoginModelImpl();
    }

    @Override
    public void login(String username, String password) {
        mModel.login(this, username, password);
    }

    @Override
    public void onLoginSuccess(String msg) {
        mView.onLoginSuccess(msg);
    }

    @Override
    public void onLoginFailed(String msg) {
        mView.onLoginFailed(msg);
    }
}
