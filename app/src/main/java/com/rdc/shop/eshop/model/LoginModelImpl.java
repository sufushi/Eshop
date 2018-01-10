package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.ILoginContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginModelImpl implements ILoginContract.Model {

    @Override
    public void login(final ILoginContract.Presenter presenter, String username, String password) {
        if ("".equals(username) || "".equals(password)) {
            presenter.onLoginFailed("用户名或密码不能为空");
        } else {
            BmobUser.loginByAccount(username, password, new LogInListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        presenter.onLoginSuccess("登陆成功");
                    } else {
                        presenter.onLoginFailed(e.getMessage());
                    }
                }
            });
        }
    }
}
