package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.GoodRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.decorator.SpaceItemDecoration;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonVisitRecordActivity extends BaseActivity implements OnClickRecyclerViewListener {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_visit)
    RecyclerView mRvVisit;

    private List<String> mImageUrls;
    private List<Good> mGoodList;
    private GoodRvAdapter mGoodRvAdapter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_visit_record;
    }

    @Override
    protected void initData() {
        String[] urls = getResources().getStringArray(R.array.visit_record);
        mImageUrls = Arrays.asList(urls);
        mGoodList = new ArrayList<>();
        for (int i = 0; i < mImageUrls.size(); i++) {
            float price = new Random().nextInt(400) + 2.0f;
            Good good = new Good();
            good.setPrice(price);
            good.setGoodName("商品" + i);
            good.setGoodIcon(mImageUrls.get(i));
            mGoodList.add(good);
        }
        mGoodRvAdapter = new GoodRvAdapter(this);
        mGoodRvAdapter.appendData(mGoodList);
        mRvVisit.setLayoutManager(new GridLayoutManager(this, 2));
        mRvVisit.addItemDecoration(new SpaceItemDecoration(10));
        mRvVisit.setItemAnimator(new DefaultItemAnimator());
        mRvVisit.setAdapter(mGoodRvAdapter);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("浏览记录");
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

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }


}
