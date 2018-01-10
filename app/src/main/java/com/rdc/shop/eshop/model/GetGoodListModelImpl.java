package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetGoodListContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetGoodListModelImpl implements IGetGoodListContract.Model {

    @Override
    public void getGoodList(final IGetGoodListContract.Presenter presenter) {
        BmobQuery<Good> goodBmobQuery = new BmobQuery<>();
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
