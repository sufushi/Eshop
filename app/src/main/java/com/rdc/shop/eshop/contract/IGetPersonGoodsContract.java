package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Good;

import java.util.List;

public interface IGetPersonGoodsContract {

    interface Model {
        void getPersonGoods(Presenter presenter, String sellerId);
    }

    interface View {
        void onGetPersonGoodsSuccess(List<Good> goodList);
        void onGetPersonGoodsFailed(String response);
    }

    interface Presenter {
        void getPersonGoods(String sellerId);
        void onGetPersonGoodsSuccess(List<Good> goodList);
        void onGetPersonGoodsFailed(String response);
    }

}
