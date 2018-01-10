package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IUploadUserDetailContract;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UploadUserDetailModelImpl implements IUploadUserDetailContract.Model{

    @Override
    public void uploadUserDetail(final IUploadUserDetailContract.Presenter presenter, User user) {
        user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onUploadUserDetailSuccess("修改成功");
                } else {
                    presenter.onUploadUserDetailFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void uploadUserIcon(final IUploadUserDetailContract.Presenter presenter, String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onUploadUserIconSuccess(bmobFile.getFileUrl());
                } else {
                    presenter.onUploadUserIconFailed(e.getMessage());
                }
            }

        });
    }
}
