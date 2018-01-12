package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Good;

import java.util.List;

public interface IGetGoodListContract {

    interface Model {
        void getGoodList(Presenter presenter, boolean limit);
        void getGoodList(Presenter presenter, int skip);
    }

    interface View {
        void onGetGoodListSuccess(List<Good> goodList);
        void onGetGoodListFailed(String response);
    }

    interface Presenter {
        void getGoodList(boolean limit);
        void getGoodList(int skip);
        void onGetGoodListSuccess(List<Good> goodList);
        void onGetGoodListFailed(String response);
    }
}
