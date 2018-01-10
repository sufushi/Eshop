package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.MyPagerAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.fragment.PersonOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonOrderActivity extends BaseActivity {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.tl_category)
    TabLayout mTlCategory;
    @BindView(R.id.vp_order_record)
    ViewPager mVpOrderRecord;

    private MyPagerAdapter mMyPagerAdapter;
    private List<PersonOrderFragment> mPersonOrderFragmentList;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_order;
    }

    @Override
    protected void initData() {
        mPersonOrderFragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PersonOrderFragment personOrderFragment = PersonOrderFragment.newInstance(i);
            mPersonOrderFragmentList.add(personOrderFragment);
        }
        String[] titles = getResources().getStringArray(R.array.order_status);
        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), titles, mPersonOrderFragmentList);
        mVpOrderRecord.setAdapter(mMyPagerAdapter);
        mVpOrderRecord.setOffscreenPageLimit(4);
        mTlCategory.setupWithViewPager(mVpOrderRecord);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("我的订单");
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
}
