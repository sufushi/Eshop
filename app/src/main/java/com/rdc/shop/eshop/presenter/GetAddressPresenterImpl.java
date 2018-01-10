package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Address;
import com.rdc.shop.eshop.contract.IGetAddressContract;
import com.rdc.shop.eshop.model.GetAddressModelImpl;

import java.util.List;

public class GetAddressPresenterImpl implements IGetAddressContract.Presenter {

    private IGetAddressContract.Model mModel;
    private IGetAddressContract.View mView;

    public GetAddressPresenterImpl(IGetAddressContract.View view) {
        mView = view;
        mModel = new GetAddressModelImpl();
    }

    @Override
    public void getAddress(String userId) {
        mModel.getAddress(this, userId);
    }

    @Override
    public void onGetAddressSuccess(List<Address> addressList) {
        mView.onGetAddressSuccess(addressList);
    }

    @Override
    public void onGetAddressFailed(String response) {
        mView.onGetAddressFailed(response);
    }
}
