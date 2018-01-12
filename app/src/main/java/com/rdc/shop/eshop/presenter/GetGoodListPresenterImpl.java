package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetGoodListContract;
import com.rdc.shop.eshop.model.GetGoodListModelImpl;

import java.util.List;

public class GetGoodListPresenterImpl implements IGetGoodListContract.Presenter {

    private IGetGoodListContract.Model mModel;
    private IGetGoodListContract.View mView;

    public GetGoodListPresenterImpl(IGetGoodListContract.View view) {
        mView = view;
        mModel = new GetGoodListModelImpl();
    }

    @Override
    public void getGoodList(boolean limit) {
        mModel.getGoodList(this, limit);
    }

    @Override
    public void getGoodList(int skip) {
        mModel.getGoodList(this, skip);
    }

    @Override
    public void onGetGoodListSuccess(List<Good> goodList) {
        mView.onGetGoodListSuccess(goodList);
    }

    @Override
    public void onGetGoodListFailed(String response) {
        mView.onGetGoodListFailed(response);
    }
}
