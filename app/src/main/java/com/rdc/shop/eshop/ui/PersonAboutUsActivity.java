package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAboutUsActivity extends BaseActivity {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_about_us;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("关于我们");
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
