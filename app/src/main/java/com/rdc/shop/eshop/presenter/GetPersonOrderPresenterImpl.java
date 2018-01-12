package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Order;
import com.rdc.shop.eshop.contract.IGetPersonGoodsContract;
import com.rdc.shop.eshop.contract.IGetPersonOrderContract;
import com.rdc.shop.eshop.model.GetPersonOrderModelImpl;

import java.util.List;

public class GetPersonOrderPresenterImpl implements IGetPersonOrderContract.Presenter {

    private IGetPersonOrderContract.Model mModel;
    private IGetPersonOrderContract.View mView;

    public GetPersonOrderPresenterImpl(IGetPersonOrderContract.View view) {
        mView = view;
        mModel = new GetPersonOrderModelImpl();
    }

    @Override
    public void getPersonOrder(int state) {
        mModel.getPersonOrder(this, state);
    }

    @Override
    public void onGetPersonOrderSuccess(List<Order> orderList) {
        mView.onGetPersonOrderSuccess(orderList);
    }

    @Override
    public void onGetPersonOrderFailed(String response) {
        mView.onGetPersonOrderFailed(response);
    }
}
