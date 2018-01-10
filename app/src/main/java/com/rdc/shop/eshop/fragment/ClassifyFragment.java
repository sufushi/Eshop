package com.rdc.shop.eshop.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kevin.www.categorydemo.CategoryBean;
import com.kevin.www.categorydemo.HomeAdapter;
import com.kevin.www.categorydemo.MenuAdapter;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClassifyFragment extends BaseFragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.lv_menu)
    ListView mLvMenu;
    @BindView(R.id.lv_home)
    ListView mLvHome;
    @BindView(R.id.tv_home_title)
    TextView mTvHomeTitle;

    private List<String> mMenuList = new ArrayList<>();
    private List<CategoryBean.DataBean> mHomeList = new ArrayList<>();
    private List<Integer> mHomeTitleList;

    private MenuAdapter mMenuAdapter;
    private HomeAdapter mHomeAdapter;

    private int mCurrentItem;
    private int mScrollState;

    public static ClassifyFragment newInstance(String title) {
        ClassifyFragment classifyFragment = new ClassifyFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("title", title);
        classifyFragment.setArguments(bundle);
        return classifyFragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initData(Bundle bundle) {
        Fresco.initialize(mBaseActivity);
        String json = getJson(mBaseActivity, "category.json");
        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
        mHomeTitleList = new ArrayList<>();
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            CategoryBean.DataBean dataBean = categoryBean.getData().get(i);
            mMenuList.add(dataBean.getModuleTitle());
            mHomeTitleList.add(i);
            mHomeList.add(dataBean);
        }
        mTvHomeTitle.setText(categoryBean.getData().get(0).getModuleTitle());

        mMenuAdapter = new MenuAdapter(mBaseActivity, mMenuList);
        mHomeAdapter = new HomeAdapter(mBaseActivity, mHomeList);

    }

    @Override
    protected void initView() {
        mLvMenu.setAdapter(mMenuAdapter);
        mLvHome.setAdapter(mHomeAdapter);
    }

    @Override
    protected void setListener() {
        mLvMenu.setOnItemClickListener(this);
        mLvHome.setOnScrollListener(this);
    }

    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMenuAdapter.setSelectItem(position);
        mMenuAdapter.notifyDataSetInvalidated();
        mTvHomeTitle.setText(mMenuList.get(position));
        mLvHome.smoothScrollToPosition(mHomeTitleList.get(position));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            return;
        }
        int current = mHomeTitleList.indexOf(firstVisibleItem);
        if (mCurrentItem != current && current >= 0) {
            mCurrentItem = current;
            mTvHomeTitle.setText(mMenuList.get(mCurrentItem));
            mMenuAdapter.setSelectItem(mCurrentItem);
            mMenuAdapter.notifyDataSetInvalidated();
        }
    }
}
