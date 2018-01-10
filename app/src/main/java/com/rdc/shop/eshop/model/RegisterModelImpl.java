package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IRegisterCobtract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.rdc.shop.eshop.constant.Constant.DEFAULT_USER_ICON_URL;

public class RegisterModelImpl implements IRegisterCobtract.Model {

    @Override
    public void register(final IRegisterCobtract.Presenter presenter, String phoneNumber, String password, String confirmPassword) {
        if ("".equals(phoneNumber)) {
            presenter.onRegisterFailed("手机号不能为空");
            return;
        }
        if ("".equals(password)) {
            presenter.onRegisterFailed("密码不能为空");
            return;
        }
        if ("".equals(confirmPassword)) {
            presenter.onRegisterFailed("确认密码不能为空");
            return;
        }
        if (!password.equals(confirmPassword)) {
            presenter.onRegisterFailed("密码不一致");
            return;
        }
        User user = new User();
        user.setUsername(phoneNumber);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setUserPassword(password);
        user.setSex(true);
        user.setUserIcon(DEFAULT_USER_ICON_URL);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    presenter.onRegisterSuccess("注册成功");
                } else {
                    presenter.onRegisterFailed(e.getMessage());
                }
            }
        });
    }
}
