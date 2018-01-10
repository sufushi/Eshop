package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetPersonGoodsContract;
import com.rdc.shop.eshop.model.GetPersonGoodsModelImpl;

import java.util.List;

public class GetPersonGoodsPresenterImpl implements IGetPersonGoodsContract.Presenter {

    private IGetPersonGoodsContract.Model mModel;
    private IGetPersonGoodsContract.View mView;

    public GetPersonGoodsPresenterImpl(IGetPersonGoodsContract.View view) {
        mView = view;
        mModel = new GetPersonGoodsModelImpl();
    }

    @Override
    public void getPersonGoods(String sellerId) {
        mModel.getPersonGoods(this, sellerId);
    }

    @Override
    public void onGetPersonGoodsSuccess(List<Good> goodList) {
        mView.onGetPersonGoodsSuccess(goodList);
    }

    @Override
    public void onGetPersonGoodsFailed(String response) {
        mView.onGetPersonGoodsFailed(response);
    }
}
