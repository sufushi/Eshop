package com.rdc.shop.eshop.presenter;

import com.rdc.shop.eshop.contract.IDeleteContract;
import com.rdc.shop.eshop.model.DeleteModelImpl;

import cn.bmob.v3.BmobObject;

public class DeletePresenterImpl<T extends BmobObject> implements IDeleteContract.Presenter {

    private IDeleteContract.Model<T> mModel;
    private IDeleteContract.View mView;

    public DeletePresenterImpl(IDeleteContract.View view) {
        mView = view;
        mModel = new DeleteModelImpl();
    }

    @Override
    public void delete(BmobObject bmobObject) {
        mModel.delete(this, (T) bmobObject);
    }

    @Override
    public void onDeleteSuccess(String response) {
        mView.onDeleteSuccess(response);
    }

    @Override
    public void onDeleteFailed(String response) {
        mView.onDeleteFailed(response);
    }
}
