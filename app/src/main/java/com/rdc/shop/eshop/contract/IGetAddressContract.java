package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Address;

import java.util.List;

public interface IGetAddressContract {

    interface Model {
        void getAddress(Presenter presenter, String userId);
    }

    interface View {
        void onGetAddressSuccess(List<Address> addressList);
        void onGetAddressFailed(String response);
    }

    interface Presenter {
        void getAddress(String userId);
        void onGetAddressSuccess(List<Address> addressList);
        void onGetAddressFailed(String response);
    }

}
