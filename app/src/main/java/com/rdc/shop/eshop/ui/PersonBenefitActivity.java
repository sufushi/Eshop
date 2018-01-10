package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.BenefitRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Coupon;
import com.rdc.shop.eshop.decorator.SpaceItemDecoration;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonBenefitActivity extends BaseActivity implements OnClickRecyclerViewListener {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_benefit)
    RecyclerView mRvBenefit;

    private List<Coupon> mCouponList;
    private BenefitRvAdapter mBenefitRvAdapter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_benefit;
    }

    @Override
    protected void initData() {
        mCouponList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Long value = new Random().nextInt(20) + (long) i;
            int fullPrice = new Random().nextInt(100) + 2 * i;
            Coupon coupon = new Coupon(value, "商家" + i, "满" + fullPrice + "元可用,APP下单使用,在线支付专享", new Date());
            mCouponList.add(coupon);
        }
        mBenefitRvAdapter = new BenefitRvAdapter(this);
        mBenefitRvAdapter.setOnRecyclerViewListener(this);
        mBenefitRvAdapter.appendData(mCouponList);
        mRvBenefit.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvBenefit.setItemAnimator(new DefaultItemAnimator());
        mRvBenefit.addItemDecoration(new SpaceItemDecoration(6));
        mRvBenefit.setAdapter(mBenefitRvAdapter);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("设置");
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onItemClick(int position, View view) {
        Log.e("error", "onItemClick" + position);
    }

    @Override
    public boolean onItemLongClick(int position) {
        Log.e("error", "onItemLongClick" + position);
        return false;
    }
}
