package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Collection;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetPersonInterestContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetPersonInterestModelImpl implements IGetPersonInterestContract.Model {

    @Override
    public void getPersonInterest(final IGetPersonInterestContract.Presenter presenter, String userId) {
        BmobQuery<Collection> collectionBmobQuery = new BmobQuery<>();
        collectionBmobQuery.addWhereEqualTo("userId", userId);
        collectionBmobQuery.include("good,good.shop");
        collectionBmobQuery.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null) {
                    Map<String, Good> map = new HashMap<>();
                    List<Good> goodList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Good good = list.get(i).getGood();
                        if (!map.containsKey(good.getObjectId())) {
                            map.put(good.getObjectId(), good);
                            goodList.add(good);
                        }
                    }
                    presenter.onGetPersonInterestSuccess(goodList);
                } else {
                    presenter.onGetPersonInterestFailed(e.getMessage());
                }
            }
        });
    }
}
