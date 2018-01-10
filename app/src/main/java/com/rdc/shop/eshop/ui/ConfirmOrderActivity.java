package com.rdc.shop.eshop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.ConfirmOrderRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.base.BasePopupWindow;
import com.rdc.shop.eshop.bean.Address;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Order;
import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IGetAddressContract;
import com.rdc.shop.eshop.contract.IUploadBatchContract;
import com.rdc.shop.eshop.presenter.GetAddressPresenterImpl;
import com.rdc.shop.eshop.presenter.UploadBatchPresenterImpl;
import com.rdc.shop.eshop.utils.PopupWindowUtil;
import com.rdc.shop.eshop.view.PayFragment;
import com.rdc.shop.eshop.view.PayPwdView;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class ConfirmOrderActivity extends BaseActivity implements IGetAddressContract.View,
        BasePopupWindow.ViewInterface, IUploadBatchContract.View, PayPwdView.InputCallBack {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.tv_recipient_name)
    TextView mTvRecipientName;
    @BindView(R.id.tv_recipient_contract)
    TextView mTvRecipientContract;
    @BindView(R.id.tv_recipient_address)
    TextView mTvRecipientAddress;
    @BindView(R.id.rv_good_info)
    RecyclerView mRvGoodInfo;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_commit_order)
    TextView mTvCommitOrder;

    private float mTotalPrice;
    private String mOrderNumber;
    private List<Good> mGoodList;
    private ConfirmOrderRvAdapter mConfirmOrderRvAdapter;

    private List<String> mPlaceList;
    private int mSelectPlaceIndex = 0;
    private GetAddressPresenterImpl mGetAddressPresenter;
    private UploadBatchPresenterImpl<Order> mUploadBatchPresenter;

    private PayFragment mPayFragment;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initData() {
        mTotalPrice = getIntent().getFloatExtra("total_price", 0.00f);
        mGoodList = (List<Good>) getIntent().getSerializableExtra("goods");
        mTbTitle.setTitle(R.string.string_confirm_order);
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mConfirmOrderRvAdapter = new ConfirmOrderRvAdapter(this);
        mConfirmOrderRvAdapter.appendData(mGoodList);
        mGetAddressPresenter = new GetAddressPresenterImpl(this);
        mUploadBatchPresenter = new UploadBatchPresenterImpl(this);
    }

    @Override
    protected void initView() {
        mTvRecipientName.setText(BmobUser.getCurrentUser().getUsername());
        mTvRecipientContract.setText(BmobUser.getCurrentUser().getMobilePhoneNumber());
        String totalPrice = "￥" + mTotalPrice;
        mTvTotalPrice.setText(totalPrice);
        mRvGoodInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvGoodInfo.setItemAnimator(new DefaultItemAnimator());
        mRvGoodInfo.setAdapter(mConfirmOrderRvAdapter);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.ll_recipient_address, R.id.tv_commit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_recipient_address:
                selectPlace();
                break;
            case R.id.tv_commit_order:
                PopupWindowUtil.getInstance().showFullScreen(findViewById(R.id.rl_confirm_oder),
                        this, R.layout.layout_popup_window_pay_way, this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGetAddressPresenter != null) {
            mGetAddressPresenter.getAddress(BmobUser.getCurrentUser().getObjectId());
        }
    }

    private void selectPlace() {
        if (mPlaceList == null || mPlaceList.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("您还没有创建任何收货地址，是否现在创建？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(ConfirmOrderActivity.this, AddOrEditPlaceActivity.class);
                            intent.putExtra("isAdd", true);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_place, null);
            LoopView loopView = (LoopView) dialogView.findViewById(R.id.lv_select_place);
            loopView.setNotLoop();
            loopView.setItems(mPlaceList);
            loopView.setInitPosition(mSelectPlaceIndex);
            loopView.setTextSize(18);
            loopView.setLineSpacingMultiplier(1.0f);
            loopView.setItemsVisibleCount(5);
            loopView.setCenterTextColor(Color.parseColor("#ef1b1b"));
            loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mSelectPlaceIndex = index;
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("选择收货地址")
                    .setView(dialogView)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mSelectPlaceIndex >= 0 && mSelectPlaceIndex <= mPlaceList.size()) {
                                mTvRecipientAddress.setText(mPlaceList.get(mSelectPlaceIndex));
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }
    }

    @Override
    public void onGetAddressSuccess(List<Address> addressList) {
        mPlaceList = new ArrayList<>();
        for (Address address :
                addressList) {
            mPlaceList.add(address.getAddress());
            Log.e("error", "address=" + address.getAddress());
        }
    }

    @Override
    public void onGetAddressFailed(String response) {
        showToast(response);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        TextView tvOrderNumber = (TextView) view.findViewById(R.id.tv_order_number);
        TextView tvPayMoney = (TextView) view.findViewById(R.id.tv_pay_money);
        RelativeLayout rlWalletPay = (RelativeLayout) view.findViewById(R.id.rl_wallet_pay);
        RelativeLayout rlAliPay = (RelativeLayout) view.findViewById(R.id.rl_ali_pay);
        RelativeLayout rlWechatPay = (RelativeLayout) view.findViewById(R.id.rl_wechat_pay);
        final ImageView ivWalletCheck = (ImageView) view.findViewById(R.id.iv_wallet_check);
        final ImageView ivAliCheck = (ImageView) view.findViewById(R.id.iv_ali_check);
        final ImageView ivWechatCheck = (ImageView) view.findViewById(R.id.iv_wechat_check);
        Button btnConfrimPay = (Button) view.findViewById(R.id.btn_confirm_pay);
        mOrderNumber = BmobUser.getCurrentUser().getObjectId() + System.currentTimeMillis();
        String orderNumber = "订单编号(" + mOrderNumber + ")";
        tvOrderNumber.setText(orderNumber);
        tvPayMoney.setText(String.valueOf(mTotalPrice));
        rlWalletPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivWalletCheck.setVisibility(View.VISIBLE);
                ivAliCheck.setVisibility(View.GONE);
                ivWechatCheck.setVisibility(View.GONE);
            }
        });
        rlAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivWalletCheck.setVisibility(View.GONE);
                ivAliCheck.setVisibility(View.VISIBLE);
                ivWechatCheck.setVisibility(View.GONE);
            }
        });
        rlWechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivWalletCheck.setVisibility(View.GONE);
                ivAliCheck.setVisibility(View.GONE);
                ivWechatCheck.setVisibility(View.VISIBLE);
            }
        });
        btnConfrimPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowUtil.getInstance().dismiss();
                Bundle bundle = new Bundle();
                bundle.putString(PayFragment.EXTRA_CONTENT, "支付：¥ " + mTotalPrice);

                mPayFragment = new PayFragment();
                mPayFragment.setArguments(bundle);
                mPayFragment.setPaySuccessCallBack(ConfirmOrderActivity.this);
                mPayFragment.show(getSupportFragmentManager(), "Pay");
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PopupWindowUtil.getInstance().dismiss();
                return true;
            }
        });
    }

    private void commitOrder() {
        List<Order> orderList = new ArrayList<>();
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser().getObjectId());
        for (int i = 0; i < mGoodList.size(); i++) {
            Order order = new Order();
            order.setUser(user);
            order.setGood(mGoodList.get(i));
            order.setOrderNumber(mOrderNumber + i);
            order.setState(0);
            orderList.add(order);
        }
        mUploadBatchPresenter.uploadBatch(orderList);
    }

    @Override
    public void onUploadBatchSuccess(String response) {
        if (mPayFragment != null) {
            mPayFragment.dismiss();
        }
        showToast(response);
    }

    @Override
    public void onUploadBatchFailed(String response) {
        if (mPayFragment != null) {
            mPayFragment.dismiss();
        }
        showToast(response);
    }

    @Override
    public void onInputFinish(String result) {
        commitOrder();
    }
}
