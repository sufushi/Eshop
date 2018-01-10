package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.contract.IDeleteContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class DeleteModelImpl implements IDeleteContract.Model {

    @Override
    public void delete(final IDeleteContract.Presenter presenter, BmobObject bmobObject) {
        bmobObject.delete(bmobObject.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onDeleteSuccess("删除成功");
                } else {
                    presenter.onDeleteFailed(e.getMessage());
                }
            }
        });
    }
}
