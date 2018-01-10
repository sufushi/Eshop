package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.contract.IDeleteBatchContract;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

public class DeleteBatchModelImpl<T extends BmobObject> implements IDeleteBatchContract.Model<T> {

    @Override
    public void deleteBatch(final IDeleteBatchContract.Presenter presenter, List list) {
        new BmobBatch().deleteBatch((List<BmobObject>) list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        BatchResult result = list.get(i);
                        BmobException bmobException = result.getError();
                        if (bmobException == null) {
                            presenter.onDeleteBatchSuccess(String.valueOf(i));
                        } else {
                            presenter.onDeleteBatchFailed(bmobException.getMessage());
                        }
                    }
                } else {
                    presenter.onDeleteBatchFailed(e.getMessage());
                }
            }
        });
    }

}
