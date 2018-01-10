package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.contract.IUploadBatchContract;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

public class UploadBatchModelImpl<T extends BmobObject> implements IUploadBatchContract.Model<T> {

    @Override
    public void uploadBatch(final IUploadBatchContract.Presenter presenter, List<T> list) {
        new BmobBatch().insertBatch((List<BmobObject>) list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        BatchResult result = list.get(i);
                        BmobException bmobException = result.getError();
                        if (bmobException == null) {
                            presenter.onUploadBatchSuccess(String.valueOf(i));
                        } else {
                            presenter.onUploadBatchFailed(bmobException.getMessage());
                        }
                    }
                } else {
                    presenter.onUploadBatchFailed(e.getMessage());
                }
            }
        });
    }
}
