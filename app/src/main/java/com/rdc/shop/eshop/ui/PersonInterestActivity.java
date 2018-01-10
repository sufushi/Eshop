package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.InterestCardRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.decorator.SpaceItemDecoration;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;
import com.view.jameson.library.CardScaleHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonInterestActivity extends BaseActivity implements OnClickRecyclerViewListener {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_interest)
    RecyclerView mRvInterest;

    private List<Good> mGoodList;
    private List<String> mImageUrls;
    private InterestCardRvAdapter mInterestCardRvAdapter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_interest;
    }

    @Override
    protected void initData() {
        String[] urls = getResources().getStringArray(R.array.visit_record);
        mImageUrls = Arrays.asList(urls);
        mGoodList = new ArrayList<>();
        for (int i = 0; i < mImageUrls.size(); i++) {
            float price = new Random().nextInt(400) + 2.0f;
            Good good = new Good();
            good.setGoodName("商品" + i);
            good.setPrice(price);
            good.setGoodIcon(mImageUrls.get(i));
            mGoodList.add(good);
        }
        mInterestCardRvAdapter = new InterestCardRvAdapter(this);
        mInterestCardRvAdapter.appendData(mGoodList);
        mInterestCardRvAdapter.setOnRecyclerViewListener(this);
        mRvInterest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvInterest.addItemDecoration(new SpaceItemDecoration(10));
        mRvInterest.setItemAnimator(new DefaultItemAnimator());
        mRvInterest.setAdapter(mInterestCardRvAdapter);
        new CardScaleHelper().attachToRecyclerView(mRvInterest);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("我的关注");
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
