package com.rdc.shop.eshop.model;

import android.util.Log;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.bean.Shoppingcart;
import com.rdc.shop.eshop.bean.entity.Store;
import com.rdc.shop.eshop.contract.IGetShopingcartContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetShoppingcartModelImpl implements IGetShopingcartContract.Model {

    @Override
    public void getShoppingcart(final IGetShopingcartContract.Presenter presenter, String userId) {
        BmobQuery<Shoppingcart> shoppingcartBmobQuery = new BmobQuery<>();
        shoppingcartBmobQuery.addWhereEqualTo("userId", userId);
        shoppingcartBmobQuery.include("good,good.shop");
        shoppingcartBmobQuery.findObjects(new FindListener<Shoppingcart>() {
            @Override
            public void done(List<Shoppingcart> list, BmobException e) {
                if (e == null) {
                    Map<Integer, List<Good>> goodListMap = new HashMap<>();
                    List<Store> storeList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Good good = list.get(i).getGood();
                        good.setCount((long) list.get(i).getCount());
                        Shop shop = good.getShop();
                        if (goodListMap.containsKey(shop.getId())) {
                            int flag = 0;
                            for (int j = 0; j < goodListMap.get(shop.getId()).size(); j++) {
                                Log.e("error", "goodId=" + good.getId());
                                if (good.getId() == goodListMap.get(shop.getId()).get(j).getId()) {
                                    long count = goodListMap.get(shop.getId()).get(j).getCount() + 1;
                                    goodListMap.get(shop.getId()).get(j).setCount(count);
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 0) {
                                goodListMap.get(shop.getId()).add(good);
                            }
                        } else {
                            List<Good> goodList = new ArrayList<>();
                            goodList.add(good);
                            goodListMap.put(shop.getId(), goodList);
                            storeList.add(new Store(shop.getId(), shop.getShopName()));
                        }
                    }
                    presenter.onGetShopingcartSuccess(storeList, goodListMap);
                } else {
                    presenter.onGetShoppingcartFailed(e.getMessage());
                }
            }
        });
    }
}
