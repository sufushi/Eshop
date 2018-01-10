package com.rdc.shop.eshop.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.PersonGoodsRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.contract.IGetPersonGoodsContract;
import com.rdc.shop.eshop.presenter.GetPersonGoodsPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class PersonGoodActivity extends BaseActivity implements IGetPersonGoodsContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.rv_person_goods)
    RecyclerView mRvPersonGoods;

    private List<Good> mGoodList;
    private PersonGoodsRvAdapter mPersonGoodsRvAdapter;
    private GetPersonGoodsPresenterImpl mGetPersonGoodsPresenter;

    private Shop mShop;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_good;
    }

    @Override
    protected void initData() {
        mShop = (Shop) getIntent().getSerializableExtra("shop");
        mTbTitle.setTitle("我的商品");
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mPersonGoodsRvAdapter = new PersonGoodsRvAdapter(this);
        mGetPersonGoodsPresenter = new GetPersonGoodsPresenterImpl(this);
        mGetPersonGoodsPresenter.getPersonGoods(BmobUser.getCurrentUser().getObjectId());
    }

    @Override
    protected void initView() {
        mRvPersonGoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvPersonGoods.setItemAnimator(new DefaultItemAnimator());
        mRvPersonGoods.setAdapter(mPersonGoodsRvAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onGetPersonGoodsSuccess(List<Good> goodList) {
        mGoodList = goodList;
        mPersonGoodsRvAdapter.updataData(mGoodList);
    }

    @Override
    public void onGetPersonGoodsFailed(String response) {
        showToast(response);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddGoodActivity.class);
        intent.putExtra("shop", mShop);
        startActivity(intent);
    }
}
