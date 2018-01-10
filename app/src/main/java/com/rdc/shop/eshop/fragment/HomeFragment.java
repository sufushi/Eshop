package com.rdc.shop.eshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.HomeRvAdapter;
import com.rdc.shop.eshop.base.BaseFragment;
import com.rdc.shop.eshop.bean.Advertisement;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.GoodxShop;
import com.rdc.shop.eshop.contract.IGetAdvertisementContract;
import com.rdc.shop.eshop.contract.IGetGoodListContract;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;
import com.rdc.shop.eshop.presenter.GetAdvertisementPresenterImpl;
import com.rdc.shop.eshop.presenter.GetGoodListPresenterImpl;
import com.rdc.shop.eshop.ui.GoodDetailActivity;
import com.rdc.shop.eshop.ui.SearchGoodActivity;
import com.rdc.shop.eshop.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class HomeFragment extends BaseFragment implements OnClickRecyclerViewListener, IGetAdvertisementContract.View, IGetGoodListContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.ptr_classic_frame_layout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    Unbinder unbinder;

    private List<GoodxShop> mGoodxShopList;
    private HomeRvAdapter mHomeRvAdapter;

    private int mScrollDistance;
    private int mRecyclerY;

    private Advertisement mAdvertisement;
    private List<Good> mGoodList;

    private GetAdvertisementPresenterImpl mGetAdvertisementPresenter;
    private GetGoodListPresenterImpl mGetGoodListPresenter;

    public static HomeFragment newInstance(String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("title", title);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle bundle) {
        mScrollDistance = 0;
        mGoodxShopList = new ArrayList<>();
        mHomeRvAdapter = new HomeRvAdapter(mBaseActivity);
        mHomeRvAdapter.setOnRecyclerViewListener(this);
        mGetAdvertisementPresenter = new GetAdvertisementPresenterImpl(this);
        mGetGoodListPresenter = new GetGoodListPresenterImpl(this);
        mGetAdvertisementPresenter.getAdvertisement();
    }

    private void insertData() {
        Good good = new Good();
        good.setGoodName("联想（ThinkPad）");
        good.setPrice((float) 5287);
        good.setGoodIcon("https://img14.360buyimg.com/n4/s130x130_jfs/t7552/242/3636271892/270172/192bae2b/59f7d75fN558c192d.jpg");
        good.setDescription("E470c（20H3A001CD）14英寸笔记本电");
        good.setCategory(1L);
        good.setSalesNumber(2340L);
        good.setReserve(300L);
        good.setCount(0L);
        good.setSize("14寸");
        good.setColor("黑色");
        good.setDiscountPrice((float) 4999);
        good.setGoodImageList(null);
        good.setSellerId("853603ea11");
        good.setChoosed(false);
        good.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    showToast(s);
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    @Override
    protected void initView() {
        mTbTitle.getBackground().mutate().setAlpha(0);

        mRvHome.setLayoutManager(new LinearLayoutManager(mBaseActivity, LinearLayoutManager.VERTICAL, false));
        mRvHome.setItemAnimator(new DefaultItemAnimator());
        mRvHome.setAdapter(mHomeRvAdapter);

        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(mBaseActivity);
        mPtrClassicFrameLayout.setResistanceHeader(1.7f);
        mPtrClassicFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrClassicFrameLayout.setDurationToClose(1000);
        mPtrClassicFrameLayout.setPullToRefresh(false);
        mPtrClassicFrameLayout.setKeepHeaderWhenRefresh(true);
        StoreHouseHeader header = new StoreHouseHeader(mBaseActivity);
        header.setPadding(0, DensityUtil.dp2px(20f, mBaseActivity), 0, DensityUtil.dp2px(20f, mBaseActivity));
        header.setTextColor(mBaseActivity.getResources().getColor(R.color.whitesmoke));
        header.initWithString("Eshop");
        mPtrClassicFrameLayout.setDurationToCloseHeader(1500);
        mPtrClassicFrameLayout.setHeaderView(header);
        mPtrClassicFrameLayout.addPtrUIHandler(header);
        mPtrClassicFrameLayout.setBackgroundColor(mBaseActivity.getResources().getColor(R.color.gray));
        mPtrClassicFrameLayout.disableWhenHorizontalMove(true);
    }

    @Override
    protected void setListener() {
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mTbTitle.setVisibility(View.GONE);
                mPtrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrClassicFrameLayout.refreshComplete();
                        mTbTitle.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPtrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mBaseActivity, "不要拉了，我没东西啦", Toast.LENGTH_SHORT).show();
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                }, 1000);
            }


        });
        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollDistance += dy;
                int toolbarHeight = mTbTitle.getBottom() * 2;
                if (mScrollDistance <= toolbarHeight) {
                    float scale = (float) mScrollDistance / toolbarHeight;
                    float alpha = scale * 255;
                    mTbTitle.getBackground().mutate().setAlpha((int) alpha);
                } else {
                    mTbTitle.getBackground().mutate().setAlpha(255);
                }

            }

        });
    }

    @Override
    public void onItemClick(int position, View view) {
        GoodxShop goodxShop = mGoodxShopList.get(position);
        Intent intent = new Intent(mBaseActivity, GoodDetailActivity.class);
        intent.putExtra("good", goodxShop.getGood());
//        intent.putStringArrayListExtra("imageList", (ArrayList<String>) goodxShop.getGood().getGoodImageList());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onGetAdvertisementSuccess(Advertisement advertisement) {
        mAdvertisement = advertisement;
        GoodxShop goodxShop = new GoodxShop(null, mAdvertisement, 0);
        mGoodxShopList.add(goodxShop);
        mHomeRvAdapter.updataData(mGoodxShopList);
        mGetGoodListPresenter.getGoodList();
    }

    @Override
    public void onGetAdvertisementFailed(String response) {
        showToast(response);
    }

    @Override
    public void onGetGoodListSuccess(List<Good> goodList) {
        mGoodList = goodList;
        for (Good good :
                goodList) {
            GoodxShop goodxShop = new GoodxShop(good, null, 1);
            mGoodxShopList.add(goodxShop);
        }
        mHomeRvAdapter.updataData(mGoodxShopList);
    }

    @Override
    public void onGetGoodListFailed(String response) {
        showToast(response);
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        startActivity(SearchGoodActivity.class);
    }
}
