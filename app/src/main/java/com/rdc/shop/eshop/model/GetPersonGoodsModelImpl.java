package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetPersonGoodsContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetPersonGoodsModelImpl implements IGetPersonGoodsContract.Model {

    @Override
    public void getPersonGoods(final IGetPersonGoodsContract.Presenter presenter, String sellerId) {

        BmobQuery<Good> goodBmobQuery = new BmobQuery<>();
        goodBmobQuery.addWhereEqualTo("sellerId", sellerId);
        goodBmobQuery.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> list, BmobException e) {
                if (e == null) {
                    presenter.onGetPersonGoodsSuccess(list);
                } else {
                    presenter.onGetPersonGoodsFailed(e.getMessage());
                }
            }
        });
    }
}
