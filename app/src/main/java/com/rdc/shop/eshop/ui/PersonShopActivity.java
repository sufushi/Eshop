package com.rdc.shop.eshop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.PersonShopRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.contract.IGetShopContract;
import com.rdc.shop.eshop.decorator.SpaceItemDecoration;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;
import com.rdc.shop.eshop.presenter.GetShopPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonShopActivity extends BaseActivity implements OnClickRecyclerViewListener, IGetShopContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_person_shop)
    RecyclerView mRvPersonShop;
    @BindView(R.id.ll_add_shop)
    LinearLayout mLlAddShop;

    private List<Shop> mShopList;
    private PersonShopRvAdapter mPersonShopRvAdapter;
    private GetShopPresenterImpl mGetShopPresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_shop;
    }

    @Override
    protected void initData() {
        mPersonShopRvAdapter = new PersonShopRvAdapter(this);
        mPersonShopRvAdapter.setOnRecyclerViewListener(this);
        mRvPersonShop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvPersonShop.setItemAnimator(new DefaultItemAnimator());
        mRvPersonShop.addItemDecoration(new SpaceItemDecoration(6));
        mRvPersonShop.setAdapter(mPersonShopRvAdapter);
        mGetShopPresenter = new GetShopPresenterImpl(this);
        mGetShopPresenter.getShop();
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle(R.string.string_person_shop);
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

    @OnClick(R.id.ll_add_shop)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddOrEditShopActivity.class);
        intent.putExtra("isAdd", true);
        startActivity(intent);
    }

    @Override
    public void onItemClick(final int position, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setItems(new String[]{"编辑店铺", "查看商品"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                editShop(position);
                                break;
                            case 1:
                                Intent intent = new Intent(PersonShopActivity.this, PersonGoodActivity.class);
                                intent.putExtra("shop", mShopList.get(position));
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void editShop(int position) {
        Intent intent = new Intent(PersonShopActivity.this, AddOrEditShopActivity.class);
        intent.putExtra("isAdd", false);
        intent.putExtra("shop", mShopList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onGetShopSuccess(List<Shop> shopList) {
        mShopList = shopList;
        mPersonShopRvAdapter.updataData(mShopList);
    }

    @Override
    public void onGetShopFailed(String response) {
        showToast(response);
    }
}
