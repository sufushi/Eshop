package com.rdc.shop.eshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.GoodSearchRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IGetGoodListContract;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;
import com.rdc.shop.eshop.presenter.GetGoodListPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchGoodActivity extends BaseActivity implements IGetGoodListContract.View, OnClickRecyclerViewListener {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_search_suggestion)
    RecyclerView mRvSearchSuggestion;
    @BindView(R.id.et_search_good)
    EditText mEtSearchGood;

    private static final int REQUEST_CODE_GOOD_DETAIL = 1;

    private String mKeyWord;

    private List<Good> mGoodList;
    private GoodSearchRvAdapter mGoodSearchRvAdapter;

    private IGetGoodListContract.Presenter mGetGoodListPresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_search_good;
    }

    @Override
    protected void initData() {
        mKeyWord = "";
        mGoodList = new ArrayList<>();
        mGoodSearchRvAdapter = new GoodSearchRvAdapter(this);
        mGetGoodListPresenter = new GetGoodListPresenterImpl(this);
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
        mRvSearchSuggestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvSearchSuggestion.setItemAnimator(new DefaultItemAnimator());
        mRvSearchSuggestion.setAdapter(mGoodSearchRvAdapter);
    }

    @Override
    protected void initListener() {
        mGoodSearchRvAdapter.setOnRecyclerViewListener(this);
        mEtSearchGood.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    mKeyWord = mEtSearchGood.getText().toString();
                    mGetGoodListPresenter.getGoodList();
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onGetGoodListSuccess(List<Good> goodList) {
        mGoodList.clear();
        for (int i = 0; i < goodList.size(); i++) {
            if (goodList.get(i).getGoodName().contains(mKeyWord)) {
                mGoodList.add(goodList.get(i));
            }
        }
        mGoodSearchRvAdapter.updataData(mGoodList);
    }

    @Override
    public void onGetGoodListFailed(String response) {
        Log.e("error", response);
    }

    @Override
    public void onItemClick(int position, View view) {
        Intent intent = new Intent(this, GoodDetailActivity.class);
        intent.putExtra("good", mGoodList.get(position));
        startActivityForResult(intent, REQUEST_CODE_GOOD_DETAIL);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GOOD_DETAIL) {
                Intent intent = new Intent();
                intent.putExtra("tab", "trolley");
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
