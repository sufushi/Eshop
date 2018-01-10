package com.rdc.shop.eshop.contract;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Shoppingcart;
import com.rdc.shop.eshop.bean.entity.Store;

import java.util.List;
import java.util.Map;

public interface IGetShopingcartContract {

    interface Model {
        void getShoppingcart(Presenter presenter, String userId);
    }

    interface View {
        void onGetShopingcartSuccess(List<Shoppingcart> shoppingcartList, List<Store> storeList, Map<Integer, List<Good>> goodListMap);
        void onGetShoppingcartFailed(String response);
    }

    interface Presenter {
        void getShoppingcart(String userId);
        void onGetShopingcartSuccess(List<Shoppingcart> shoppingcartList, List<Store> storeList, Map<Integer, List<Good>> goodListMap);
        void onGetShoppingcartFailed(String response);
    }

}
