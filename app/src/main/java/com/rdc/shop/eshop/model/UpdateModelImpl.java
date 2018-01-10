package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.contract.IUpdateContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateModelImpl implements IUpdateContract.Model {


    @Override
    public void update(final IUpdateContract.Presenter presenter, BmobObject bmobObject) {
        bmobObject.update(bmobObject.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onUpdateSuccess("success");
                } else {
                    presenter.onUpdateFailed(e.getMessage());
                }
            }
        });
    }
}
