package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IGetUserDetailContract;
import com.rdc.shop.eshop.model.GetUserDetailModelImpl;

public class GetUserDetailPresenterImpl implements IGetUserDetailContract.Presenter {

    private IGetUserDetailContract.Model mModel;
    private IGetUserDetailContract.View mView;

    public GetUserDetailPresenterImpl(IGetUserDetailContract.View view) {
        mView = view;
        mModel = new GetUserDetailModelImpl();
    }

    @Override
    public void getUserDetail(String userId) {
        mModel.getUserDetail(this, userId);
    }

    @Override
    public void onGetUserDetailSuccess(User user) {
        mView.onGetUserDetailSuccess(user);
    }

    @Override
    public void onGetUserDeatilFailed(String response) {
        mView.onGetUserDetailFailed(response);
    }
}
