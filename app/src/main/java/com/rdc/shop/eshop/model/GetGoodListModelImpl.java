package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetGoodListContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetGoodListModelImpl implements IGetGoodListContract.Model {

    @Override
    public void getGoodList(final IGetGoodListContract.Presenter presenter, boolean limit) {
        BmobQuery<Good> goodBmobQuery = new BmobQuery<>();
        if (limit) {
            goodBmobQuery.setLimit(5);
        }
        goodBmobQuery.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> list, BmobException e) {
                if (e == null) {
                    presenter.onGetGoodListSuccess(list);
                } else {
                    presenter.onGetGoodListFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getGoodList(final IGetGoodListContract.Presenter presenter, int skip) {
        BmobQuery<Good> goodBmobQuery = new BmobQuery<>();
        goodBmobQuery.setLimit(5);
        goodBmobQuery.setSkip(skip);
        goodBmobQuery.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> list, BmobException e) {
                if (e == null) {
                    presenter.onGetGoodListSuccess(list);
                } else {
                    presenter.onGetGoodListFailed(e.getMessage());
                }
            }
        });
    }
}
