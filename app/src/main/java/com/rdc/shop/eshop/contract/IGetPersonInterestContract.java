package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Good;

import java.util.List;

public interface IGetPersonInterestContract {

    interface Model {
        void getPersonInterest(Presenter presenter, String userId);
    }

    interface View {
        void onGetPersonInterestSuccess(List<Good> goodList);
        void onGetPersonInterestFailed(String response);
    }

    interface Presenter {
        void getPersonInterest(String userId);
        void onGetPersonInterestSuccess(List<Good> goodList);
        void onGetPersonInterestFailed(String response);
    }

}
