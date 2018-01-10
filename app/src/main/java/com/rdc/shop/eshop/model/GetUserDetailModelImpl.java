package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IGetUserDetailContract;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class GetUserDetailModelImpl implements IGetUserDetailContract.Model {

    @Override
    public void getUserDetail(final IGetUserDetailContract.Presenter presenter, String userId) {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.getObject(userId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    presenter.onGetUserDetailSuccess(user);
                } else {
                    presenter.onGetUserDeatilFailed(e.getMessage());
                }
            }
        });
    }
}
