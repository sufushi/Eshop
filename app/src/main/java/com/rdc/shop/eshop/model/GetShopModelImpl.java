package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IGetShopContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetShopModelImpl implements IGetShopContract.Model {

    @Override
    public void getShop(final IGetShopContract.Presenter presenter) {
        BmobQuery<Shop> shopBmobQuery = new BmobQuery<>();
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser().getObjectId());
        shopBmobQuery.addWhereEqualTo("owner", new BmobPointer(user));
        shopBmobQuery.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> list, BmobException e) {
                if (e == null ) {
                    presenter.onGetShopSuccess(list);
                } else {
                    presenter.onGetShopFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getShopById(final IGetShopContract.Presenter presenter, int shopId) {
        BmobQuery<Shop> shopBmobQuery = new BmobQuery<>();
        shopBmobQuery.addWhereEqualTo("objectId", shopId);
        shopBmobQuery.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> list, BmobException e) {
                if (e == null) {
                    presenter.onGetShopSuccess(list);
                } else {
                    presenter.onGetShopFailed(e.getMessage());
                }
            }
        });
    }
}
