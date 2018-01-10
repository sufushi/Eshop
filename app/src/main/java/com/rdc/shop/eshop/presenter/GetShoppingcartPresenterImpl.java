package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.entity.Store;
import com.rdc.shop.eshop.contract.IGetShopingcartContract;
import com.rdc.shop.eshop.model.GetShoppingcartModelImpl;

import java.util.List;
import java.util.Map;

public class GetShoppingcartPresenterImpl implements IGetShopingcartContract.Presenter {

    private IGetShopingcartContract.Model mModel;
    private IGetShopingcartContract.View mView;

    public GetShoppingcartPresenterImpl(IGetShopingcartContract.View view) {
        mView = view;
        mModel = new GetShoppingcartModelImpl();
    }

    @Override
    public void getShoppingcart(String userId) {
        mModel.getShoppingcart(this, userId);
    }

    @Override
    public void onGetShopingcartSuccess(List<Store> storeList, Map<Integer, List<Good>> goodListMap) {
        mView.onGetShopingcartSuccess(storeList, goodListMap);
    }

    @Override
    public void onGetShoppingcartFailed(String response) {
        mView.onGetShoppingcartFailed(response);
    }
}
