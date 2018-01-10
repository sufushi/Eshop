package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.contract.IUpdateContract;
import com.rdc.shop.eshop.model.UpdateModelImpl;

import cn.bmob.v3.BmobObject;

public class UpdatePresenterImpl<T extends BmobObject> implements IUpdateContract.Presenter {

    private IUpdateContract.Model<T> mModel;
    private IUpdateContract.View mView;

    public UpdatePresenterImpl(IUpdateContract.View view) {
        mView = view;
        mModel = new UpdateModelImpl();
    }

    @Override
    public void update(BmobObject bmobObject) {
        mModel.update(this, (T) bmobObject);
    }

    @Override
    public void onUpdateSuccess(String response) {
        mView.onUpdateSuccess(response);
    }

    @Override
    public void onUpdateFailed(String response) {
        mView.onUpdateFailed(response);
    }
}
