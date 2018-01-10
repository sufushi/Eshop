package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Advertisement;

public interface IGetAdvertisementContract {

    interface Model {
        void getAdvertisement(Presenter presenter);
    }

    interface View {
        void onGetAdvertisementSuccess(Advertisement advertisement);
        void onGetAdvertisementFailed(String response);
    }

    interface Presenter {
        void getAdvertisement();
        void onGetAdvertisementSuccess(Advertisement advertisement);
        void onGetAdvertisementFailed(String response);
    }
}
