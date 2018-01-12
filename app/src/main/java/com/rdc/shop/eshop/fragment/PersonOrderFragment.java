package com.rdc.shop.eshop.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.PersonOrderRvAdapter;
import com.rdc.shop.eshop.base.BaseFragment;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Order;
import com.rdc.shop.eshop.contract.IGetPersonOrderContract;
import com.rdc.shop.eshop.presenter.GetPersonOrderPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PersonOrderFragment extends BaseFragment implements IGetPersonOrderContract.View {

    @BindView(R.id.rv_order)
    RecyclerView mRvOrder;
    @BindView(R.id.pb_load)
    ProgressBar mPbLoad;
    Unbinder unbinder;
    private int mCategory;

    private List<Good> mGoodList;
    private PersonOrderRvAdapter mPersonOrderRvAdapter;

    private IGetPersonOrderContract.Presenter mPresenter;

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
        mGoodList = new ArrayList<>();
        mPersonOrderRvAdapter = new PersonOrderRvAdapter(mBaseActivity);
        mRvOrder.setLayoutManager(new LinearLayoutManager(mBaseActivity, LinearLayoutManager.VERTICAL, false));
        mRvOrder.setItemAnimator(new DefaultItemAnimator());
        mRvOrder.setAdapter(mPersonOrderRvAdapter);
        setParams(bundle);
    }

    private void setParams(Bundle bundle) {
        mCategory = bundle.getInt("category");
        mPresenter = new GetPersonOrderPresenterImpl(this);
        mPresenter.getPersonOrder(mCategory);
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

    @Override
    public void onGetPersonOrderSuccess(List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            Good good = orderList.get(i).getGood();
            good.setCount(orderList.get(i).getCount());
            mGoodList.add(good);
        }
        mPersonOrderRvAdapter.appendData(mGoodList);
    }

    @Override
    public void onGetPersonOrderFailed(String response) {
        Log.e("error", response);
    }
}
