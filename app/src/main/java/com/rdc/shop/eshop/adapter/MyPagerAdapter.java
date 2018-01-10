package com.rdc.shop.eshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.rdc.shop.eshop.base.BaseFragment;

import java.util.List;

public class MyPagerAdapter<T extends BaseFragment> extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<T> mBaseFragmentList;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyPagerAdapter(FragmentManager fm, String[] titles, List<T> baseFragmentList) {
        super(fm);
        mTitles = titles;
        mBaseFragmentList = baseFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mBaseFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
