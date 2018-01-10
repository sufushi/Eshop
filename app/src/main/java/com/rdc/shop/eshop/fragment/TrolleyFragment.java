package com.rdc.shop.eshop.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.TrolleyELAdapter;
import com.rdc.shop.eshop.base.BaseFragment;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Shoppingcart;
import com.rdc.shop.eshop.bean.entity.Store;
import com.rdc.shop.eshop.contract.IDeleteBatchContract;
import com.rdc.shop.eshop.contract.IGetShopingcartContract;
import com.rdc.shop.eshop.listener.OnGroupEditInterface;
import com.rdc.shop.eshop.listener.OnModifyCountInterface;
import com.rdc.shop.eshop.listener.OnSelectInterface;
import com.rdc.shop.eshop.presenter.DeleteBatchPresenterImpl;
import com.rdc.shop.eshop.presenter.GetShoppingcartPresenterImpl;
import com.rdc.shop.eshop.ui.ConfirmOrderActivity;
import com.rdc.shop.eshop.utils.ProgressDialogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

import static android.app.Activity.RESULT_OK;

public class TrolleyFragment extends BaseFragment implements OnSelectInterface,
        OnModifyCountInterface, OnGroupEditInterface, IGetShopingcartContract.View,
        IDeleteBatchContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.elv_goods)
    ExpandableListView mElvGoods;
    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_go_to_pay)
    TextView mTvGoToPay;
    @BindView(R.id.ll_info)
    LinearLayout mLlInfo;
    @BindView(R.id.tv_share)
    TextView mTvShare;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.ll_share)
    LinearLayout mLlShare;

    private static final int REQUEST_CODE_PAY = 0;

    private String mTitle;
    private float mTotalPrice;
    private int mTotalCount;
    private int mFlag;
    private Set<String> mPayedGoodSet;
    private List<Shoppingcart> mShoppingcartList;
    private List<Store> mStoreList;
    private Map<Integer, List<Good>> mGoodListMap;
    private TrolleyELAdapter mTrolleyELAdapter;

    private IGetShopingcartContract.Presenter mGetShoppingcartPresenter;
    private IDeleteBatchContract.Presenter mDeleteBatchPresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                setTitle();
            }
        }
    };

    public static TrolleyFragment newInstance(String title) {
        TrolleyFragment homeFragment = new TrolleyFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("title", title);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_trolley;
    }

    @Override
    protected void initData(Bundle bundle) {
        mGetShoppingcartPresenter = new GetShoppingcartPresenterImpl(this);
        mGetShoppingcartPresenter.getShoppingcart(BmobUser.getCurrentUser().getObjectId());
        setParams(bundle);
        mPayedGoodSet = new HashSet<>();
        mDeleteBatchPresenter = new DeleteBatchPresenterImpl(this);
    }

    private void setParams(Bundle bundle) {
        mTitle = bundle.getString("title");
        mStoreList = new ArrayList<>();
        mGoodListMap = new HashMap<>();
//        init();
//        mTrolleyELAdapter = new TrolleyELAdapter(mStoreList, mGoodListMap, mBaseActivity);
//        mTrolleyELAdapter.setOnSelectInterface(this);
//        mTrolleyELAdapter.setOnModifyCountInterface(this);
//        mTrolleyELAdapter.setOnGroupEditInterface(this);
    }

    private void init() {
        String[] imgUrls = mBaseActivity.getResources().getStringArray(R.array.trolley_good_image_urls);
        for (int i = 0; i < 3; i++) {
            mStoreList.add(new Store(i, "淘宝" + i + "号店"));
            List<Good> goodList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                float price = 12.0f + new Random().nextInt(20);
                float discountPrice = 6.0f + new Random().nextInt(5);
                int count = new Random().nextInt(3) + 1;
                String color;
                switch (new Random().nextInt(3)) {
                    case 0:
                        color = "黑色";
                        break;
                    case 1:
                        color = "白色";
                        break;
                    case 2:
                        color = "红色";
                        break;
                    default:
                        color = "蓝色";
                        break;
                }
                String size = String.valueOf(25 + new Random().nextInt(20));
                goodList.add(new Good((long) i, imgUrls[i * j], mStoreList.get(i).getStoreName() + "第" + (j + 1) + "个商品",
                        price, discountPrice, (long) count, color, size));
            }
            mGoodListMap.put(mStoreList.get(i).getStoreId(), goodList);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_edit, R.id.cb_select_all, R.id.tv_go_to_pay, R.id.tv_share, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                edit();
                break;
            case R.id.cb_select_all:
                selectAll();
                break;
            case R.id.tv_go_to_pay:
                goToPay();
                break;
            case R.id.tv_share:
                showToast("分享");
                break;
            case R.id.tv_delete:
                delete();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGetShoppingcartPresenter != null) {
            mGetShoppingcartPresenter.getShoppingcart(BmobUser.getCurrentUser().getObjectId());
        }
        setTitle();
    }

    private void setTitle() {
        int count = 0;
        for (int i = 0; i < mStoreList.size(); i++) {
            mStoreList.get(i).setChoosed(mCbSelectAll.isChecked());
            Store store = mStoreList.get(i);
            List<Good> goodList = mGoodListMap.get(store.getStoreId());
            for (Good good :
                    goodList) {
                count += 1;
            }
        }
        mTvTitle.setText("购物车" + "(" + count + ")");
        if (count == 0) {
            mTvTips.setVisibility(View.VISIBLE);
        } else {
            mTvTips.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSelectGroup(int groupPosition, boolean isSelected) {
        Store store = mStoreList.get(groupPosition);
        List<Good> goodList = mGoodListMap.get(store.getStoreId());
        for (int i = 0; i < goodList.size(); i++) {
            goodList.get(i).setChoosed(isSelected);
        }
        if (isAllSelect()) {
            mCbSelectAll.setChecked(true);
        } else {
            mCbSelectAll.setSelected(false);
        }
        mTrolleyELAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onSelectChild(int groupPosition, int childPosition, boolean isSelected) {
        boolean isChildrenSameState = true;
        Store store = mStoreList.get(groupPosition);
        List<Good> goodList = mGoodListMap.get(store.getStoreId());
        for (int i = 0; i < goodList.size(); i++) {
            if (goodList.get(i).getChoosed() != isSelected) {
                isChildrenSameState = false;
                break;
            }
        }
        if (isChildrenSameState) {
            store.setChoosed(isSelected);
        } else {
            store.setChoosed(false);
        }
        if (isAllSelect()) {
            mCbSelectAll.setChecked(true);
        } else {
            mCbSelectAll.setChecked(false);
        }
        mTrolleyELAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onIncrease(int groupPosition, int childPosition, View countView, boolean isSelected) {
        Good good = (Good) mTrolleyELAdapter.getChild(groupPosition, childPosition);
        int currentCount = good.getCount().intValue();
        currentCount++;
        good.setCount((long) currentCount);
        ((EditText) countView).setText(String.valueOf(currentCount));
        mTrolleyELAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onDecrease(int groupPosition, int childPosition, View countView, boolean isSelected) {
        Good good = (Good) mTrolleyELAdapter.getChild(groupPosition, childPosition);
        int currentCount = good.getCount().intValue();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        good.setCount((long) currentCount);
        ((EditText) countView).setText(String.valueOf(currentCount));
        mTrolleyELAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onChildDelete(int groupPosition, int childPosition) {
        mGoodListMap.get(mStoreList.get(groupPosition).getStoreId()).remove(childPosition);
        Store store = mStoreList.get(groupPosition);
        List<Good> goodList = mGoodListMap.get(store.getStoreId());
        if (goodList.size() == 0) {
            mStoreList.remove(groupPosition);
        }
        mTrolleyELAdapter.notifyDataSetChanged();
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void onGroupEdit(int groupPosition) {
        mStoreList.get(groupPosition).setEdit(true);
        mTrolleyELAdapter.notifyDataSetChanged();
    }

    private boolean isAllSelect() {
        for (Store store :
                mStoreList) {
            if (!store.getChoosed()) {
                return false;
            }
        }
        return true;
    }

    private void calculate() {
        mTotalCount = 0;
        mTotalPrice = 0.00f;
        for (int i = 0; i < mStoreList.size(); i++) {
            Store store = mStoreList.get(i);
            List<Good> goodList = mGoodListMap.get(store.getStoreId());
            for (int j = 0; j < goodList.size(); j++) {
                Good good = goodList.get(j);
                if (good.getChoosed()) {
                    mTotalCount++;
                    mTotalPrice += good.getPrice() * good.getCount();
                }
            }
        }
        String totalPrice = "￥" + mTotalPrice;
        mTvTotalPrice.setText(totalPrice);
        mTvGoToPay.setText("去支付(" + mTotalCount + ")");
    }

    private void edit() {
        if (mFlag == 0) {
            mLlInfo.setVisibility(View.GONE);
            mTvGoToPay.setVisibility(View.GONE);
            mLlShare.setVisibility(View.VISIBLE);
            mTvEdit.setText(R.string.string_complete);
        } else if (mFlag == 1) {
            mLlInfo.setVisibility(View.VISIBLE);
            mTvGoToPay.setVisibility(View.VISIBLE);
            mLlShare.setVisibility(View.GONE);
            mTvEdit.setText(R.string.string_edit);
        }
        mFlag = (mFlag + 1) % 2;
    }

    private void selectAll() {
        for (int i = 0; i < mStoreList.size(); i++) {
            mStoreList.get(i).setChoosed(mCbSelectAll.isChecked());
            Store store = mStoreList.get(i);
            List<Good> goodList = mGoodListMap.get(store.getStoreId());
            for (int j = 0; j < goodList.size(); j++) {
                goodList.get(j).setChoosed(mCbSelectAll.isChecked());
            }
        }
        mTrolleyELAdapter.notifyDataSetChanged();
        calculate();
    }

    private void delete() {
        if (mTotalCount == 0) {
            showToast("请选择要移除的商品");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity)
                .setTitle("温馨提示")
                .setMessage("您确定要将这些商品从购物车中移除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDelete();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    private void onDelete() {
        List<Store> stores = new ArrayList<>();
        for (int i = 0; i < mStoreList.size(); i++) {
            Store store = mStoreList.get(i);
            if (store.getChoosed()) {
                stores.add(store);
            }
            List<Good> goods = new ArrayList<>();
            List<Good> goodList = mGoodListMap.get(store.getStoreId());
            for (int j = 0; j < goodList.size(); j++) {
                if (goodList.get(j).getChoosed()) {
                    goods.add(goodList.get(j));
                }
            }
            goodList.removeAll(goods);
        }
        mStoreList.removeAll(stores);
        mTrolleyELAdapter.notifyDataSetChanged();
        calculate();
        setTitle();
    }

    private void goToPay() {
        if (mTotalCount == 0) {
            showToast("请选择要支付的商品");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity)
                .setTitle("温馨提示")
                .setMessage("总计:\n" + mTotalCount + "个商品\n" + mTotalPrice + "RMB")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Good> goodList = new ArrayList<>();
                        for (Map.Entry<Integer, List<Good>> entry :
                                mGoodListMap.entrySet()) {
                            List<Good> goods = entry.getValue();
                            for (int i = 0; i < goods.size(); i++) {
                                if (goods.get(i).getChoosed()) {
                                    goodList.add(goods.get(i));
                                    mPayedGoodSet.add(goods.get(i).getObjectId());
                                }
                            }
                        }
                        Intent intent = new Intent(mBaseActivity, ConfirmOrderActivity.class);
                        intent.putExtra("total_price", mTotalPrice);
                        intent.putExtra("goods", (Serializable) goodList);
                        startActivityForResult(intent, REQUEST_CODE_PAY);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    public void onGetShopingcartSuccess(List<Shoppingcart> shoppingcartList, List<Store> storeList,
                                        Map<Integer, List<Good>> goodListMap) {
        mShoppingcartList = shoppingcartList;
        mStoreList = storeList;
        mGoodListMap = goodListMap;
        if (goodListMap.size() > 0) {
            mTvTips.setVisibility(View.GONE);
        }
        mTrolleyELAdapter = new TrolleyELAdapter(mStoreList, mGoodListMap, mBaseActivity);
        mTrolleyELAdapter.setOnSelectInterface(this);
        mTrolleyELAdapter.setOnModifyCountInterface(this);
        mTrolleyELAdapter.setOnGroupEditInterface(this);
        mElvGoods.setAdapter(mTrolleyELAdapter);
        for (int i = 0; i < mTrolleyELAdapter.getGroupCount(); i++) {
            mElvGoods.expandGroup(i);
        }
//        if (goodListMap.size() > 0) {
//            View view = mBaseActivity.getLayoutInflater().inflate(R.layout.layout_elv_footer, null);
//            mElvGoods.addFooterView(view);
//        }
        setTitle();
    }

    @Override
    public void onGetShoppingcartFailed(String response) {
        showToast(response);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PAY) {
                List<Shoppingcart> shoppingcartList = new ArrayList<>();
                for (Shoppingcart shoppingcart :
                        mShoppingcartList) {
                    if (mPayedGoodSet.contains(shoppingcart.getGood().getObjectId())) {
                        shoppingcartList.add(shoppingcart);
                    }
                }
                mDeleteBatchPresenter.deleteBatch(shoppingcartList);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDeleteBatchSuccess(String response) {
        Log.e("error", response);
        mPayedGoodSet = new HashSet<>();
//        mGetShoppingcartPresenter.getShoppingcart(BmobUser.getCurrentUser().getObjectId());
    }

    @Override
    public void onDeleteBatchFailed(String response) {
        Log.e("error", response);
    }
}
