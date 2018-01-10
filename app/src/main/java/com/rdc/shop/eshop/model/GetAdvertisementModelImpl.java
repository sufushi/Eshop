package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Advertisement;
import com.rdc.shop.eshop.contract.IGetAdvertisementContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetAdvertisementModelImpl implements IGetAdvertisementContract.Model {

    @Override
    public void getAdvertisement(final IGetAdvertisementContract.Presenter presenter) {
        BmobQuery<Advertisement> advertisementBmobQuery = new BmobQuery<>();
        advertisementBmobQuery.findObjects(new FindListener<Advertisement>() {
            @Override
            public void done(List<Advertisement> list, BmobException e) {
                if (e == null) {
                    int index = list.size();
                    presenter.onGetAdvertisementSuccess(list.get(index - 1));
                } else {
                    presenter.onGetAdvertisementFailed(e.getMessage());
                }
            }
        });
    }
}
