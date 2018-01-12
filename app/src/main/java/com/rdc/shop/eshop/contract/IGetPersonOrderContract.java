package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Order;

import java.util.List;

public interface IGetPersonOrderContract {

    interface Model {
        void getPersonOrder(Presenter presenter, int state);
    }

    interface View {
        void onGetPersonOrderSuccess(List<Order> orderList);
        void onGetPersonOrderFailed(String response);
    }

    interface Presenter {
        void getPersonOrder(int state);
        void onGetPersonOrderSuccess(List<Order> orderList);
        void onGetPersonOrderFailed(String response);
    }

}
