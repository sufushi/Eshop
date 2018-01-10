package com.rdc.shop.eshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.RegionAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.entity.Region;
import com.rdc.shop.eshop.db.RegionDao;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectRegionActivity extends BaseActivity implements OnClickRecyclerViewListener {


    public static final String REGION_PROVINCE = "region_province";
    public static final String REGION_CITY = "region_city";
    public static final String REGION_AREA = "region_area";

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_region)
    RecyclerView mRvRegion;

    private List<Region> mList;
    private List<Region> mProvinceList;
    private List<Region> mCityList;
    private List<Region> mAreaList;

    private RegionDao mRegionDao;
    private RegionAdapter mRegionAdapter;

    private int mState = 0;

    private String mProvince;
    private String mCity;
    private String mArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_select_region;
    }

    @Override
    protected void initData() {
        mRegionDao = new RegionDao(this);
        mList = new ArrayList<>();
        mRegionAdapter = new RegionAdapter(mList, this);
        mRegionAdapter.setOnRecyclerViewListener(this);
        mProvinceList = mRegionDao.loadProvinceList();
        mRegionAdapter.appendData(mProvinceList);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("选择省份");
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRvRegion.setLayoutManager(new LinearLayoutManager(this));
        mRvRegion.setAdapter(mRegionAdapter);
    }

    @Override
    protected void initListener() {

    }

    private void finishSelect() {
        Intent intent = new Intent();
        intent.putExtra(REGION_PROVINCE, mProvince);
        intent.putExtra(REGION_CITY, mCity);
        intent.putExtra(REGION_AREA, mArea);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(int position, View view) {
        Region region = mRegionAdapter.getItem(position);
        if (mState == 0) {
            mCityList = mRegionDao.loadCityList(region.getId());
            mList.clear();
            mRegionAdapter.appendData(mCityList);
            mProvince = region.getName();
            mTbTitle.setTitle("选择城市");
            mState++;
        } else if (mState == 1) {
            mAreaList = mRegionDao.loadCountryList(region.getId());
            mCity = region.getName();
            if (mAreaList.size() == 0) {
                finishSelect();
            } else {
                mList.clear();
                mRegionAdapter.appendData(mAreaList);
                mTbTitle.setTitle("选择地区");
                mState++;
            }
        } else if (mState == 2) {
            mArea = region.getName();
            mState++;
            finishSelect();
        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mState == 0) {
            super.onBackPressed();
        }
        if (mState == 1) {
            mList.clear();
            mRegionAdapter.appendData(mProvinceList);
            mTbTitle.setTitle("选择省份");
            mState--;
        } else if (mState == 2) {
            mList.clear();
            mRegionAdapter.appendData(mCityList);
            mTbTitle.setTitle("选择城市");
            mState--;
        }
    }

}
