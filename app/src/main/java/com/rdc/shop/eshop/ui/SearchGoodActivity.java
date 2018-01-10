package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchGoodActivity extends BaseActivity {
    
    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_search_suggestion)
    RecyclerView mRvSearchSuggestion;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_search_good;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
