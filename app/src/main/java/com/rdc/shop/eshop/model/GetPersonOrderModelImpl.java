package com.rdc.shop.eshop.model;

import android.util.Log;

import com.rdc.shop.eshop.bean.Order;
import com.rdc.shop.eshop.contract.IGetPersonOrderContract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetPersonOrderModelImpl implements IGetPersonOrderContract.Model {

    @Override
    public void getPersonOrder(final IGetPersonOrderContract.Presenter presenter, int state) {
        BmobQuery<Order> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("user", BmobUser.getCurrentUser());
        BmobQuery<Order> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("state", state);
        List<BmobQuery<Order>> andQueries = new ArrayList<>();
        andQueries.add(query1);
        andQueries.add(query2);
        BmobQuery<Order> orderBmobQuery = new BmobQuery<>();
        orderBmobQuery.and(andQueries);
        orderBmobQuery.include("good,good.shop");
        orderBmobQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {
                    presenter.onGetPersonOrderSuccess(list);
                } else {
                    presenter.onGetPersonOrderFailed(e.getMessage());
                }
            }
        });
    }
}
