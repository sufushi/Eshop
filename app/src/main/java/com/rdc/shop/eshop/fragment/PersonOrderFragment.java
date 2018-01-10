package com.rdc.shop.eshop.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PersonOrderFragment extends BaseFragment {

    @BindView(R.id.rv_order)
    RecyclerView mRvOrder;
    @BindView(R.id.pb_load)
    ProgressBar mPbLoad;
    Unbinder unbinder;
    private int mCategory;

    public static PersonOrderFragment newInstance(int category) {
        PersonOrderFragment personOrderFragment = new PersonOrderFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("category", category);
        personOrderFragment.setArguments(bundle);
        return personOrderFragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_person_order;
    }

    @Override
    protected void initData(Bundle bundle) {
        setParams(bundle);

    }

    private void setParams(Bundle bundle) {
        mCategory = bundle.getInt("category");
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setListener() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
