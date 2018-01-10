package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Address;
import com.rdc.shop.eshop.contract.IGetAddressContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetAddressModelImpl implements IGetAddressContract.Model {

    @Override
    public void getAddress(final IGetAddressContract.Presenter presenter, String userId) {
        BmobQuery<Address> addressBmobObject = new BmobQuery<>();
        addressBmobObject.addWhereEqualTo("userId", userId);
        addressBmobObject.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> list, BmobException e) {
                if (e == null) {
                    presenter.onGetAddressSuccess(list);
                } else {
                    presenter.onGetAddressFailed(e.getMessage());
                }
            }
        });
    }
}
