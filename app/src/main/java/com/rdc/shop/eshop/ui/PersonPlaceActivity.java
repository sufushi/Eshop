package com.rdc.shop.eshop.ui;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.PersonPlaceRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Address;
import com.rdc.shop.eshop.contract.IGetAddressContract;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;
import com.rdc.shop.eshop.presenter.GetAddressPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class PersonPlaceActivity extends BaseActivity implements OnClickRecyclerViewListener, IGetAddressContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_person_place)
    RecyclerView mRvPersonPlace;
    @BindView(R.id.ll_add_place)
    LinearLayout mLlAddPlace;

    private List<Address> mAddressList;
    private PersonPlaceRvAdapter mPersonPlaceRvAdapter;

    private GetAddressPresenterImpl mGetAddressPresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_place;
    }

    @Override
    protected void initData() {
        mPersonPlaceRvAdapter = new PersonPlaceRvAdapter(this);
        mPersonPlaceRvAdapter.setOnRecyclerViewListener(this);
        mRvPersonPlace.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvPersonPlace.setItemAnimator(new DefaultItemAnimator());
        mRvPersonPlace.setAdapter(mPersonPlaceRvAdapter);
        mGetAddressPresenter = new GetAddressPresenterImpl(this);
//        mRvPersonPlace.addItemDecoration(new LineDecoration(this, LineDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("收货地址");
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
    protected void onResume() {
        super.onResume();
        if (mGetAddressPresenter != null) {
            mGetAddressPresenter.getAddress(BmobUser.getCurrentUser().getObjectId());
        }
    }

    @OnClick(R.id.ll_add_place)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddOrEditPlaceActivity.class);
        intent.putExtra("isAdd", true);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, View view) {
        Intent intent = new Intent(this, AddOrEditPlaceActivity.class);
        intent.putExtra("isAdd", false);
        intent.putExtra("address", mAddressList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onGetAddressSuccess(List<Address> addressList) {
        mAddressList = addressList;
        mPersonPlaceRvAdapter.updataData(mAddressList);
    }

    @Override
    public void onGetAddressFailed(String response) {
        showToast(response);
    }
}
