package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.contract.IUploadContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UploadModelImpl implements IUploadContract.Model{

    @Override
    public void upload(final IUploadContract.Presenter presenter, BmobObject bmobObject) {
        bmobObject.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    presenter.onUploadSuccess(s);
                } else {
                    presenter.onUploadFailed(e.getMessage());
                }
            }
        });
    }
}
