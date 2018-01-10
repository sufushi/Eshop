package com.rdc.shop.eshop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.GoodLabelRvAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Address;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Shoppingcart;
import com.rdc.shop.eshop.contract.IGetAddressContract;
import com.rdc.shop.eshop.contract.IUploadContract;
import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;
import com.rdc.shop.eshop.presenter.GetAddressPresenterImpl;
import com.rdc.shop.eshop.presenter.UploadPresenterImpl;
import com.rdc.shop.eshop.utils.ImageLoaderUtil;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class GoodDetailActivity extends BaseActivity implements OnBannerListener, OnClickRecyclerViewListener, IGetAddressContract.View, IUploadContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_simple_detail)
    TextView mTvSimpleDetail;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_reduce_price_awake)
    TextView mTvReducePriceAwake;
    @BindView(R.id.tv_select_category)
    TextView mTvSelectCategory;
    @BindView(R.id.tv_select_place)
    TextView mTvSelectPlace;
    @BindView(R.id.tv_brand)
    TextView mTvBrand;
    @BindView(R.id.tv_size)
    TextView mTvSize;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_concrete_detail)
    TextView mTvConcreteDetail;
    @BindView(R.id.rv_goods_label)
    RecyclerView mRvGoodsLabel;
    @BindView(R.id.round_rect_iv)
    RoundedImageView mRoundRectIv;
    @BindView(R.id.tv_select_category_price)
    TextView mTvSelectCategoryPrice;
    @BindView(R.id.tv_select_category_number)
    TextView mTvSelectCategoryNumber;
    @BindView(R.id.ib_select_category_close)
    ImageButton mIbSelectCategoryClose;
    @BindView(R.id.tv_select_category_confirm)
    TextView mTvSelectCategoryConfirm;
    @BindView(R.id.btn_reduce)
    Button mBtnReduce;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.rv_select_category)
    RecyclerView mRvSelectCategory;
    @BindView(R.id.nsv_select_category)
    NestedScrollView mNsvSelectCategory;

    private List<String> mImgUrlList;
    private List<String> mGoodLabelList;
    private GoodLabelRvAdapter mGoodLabelRvAdapter;
    private int mSelectPlaceIndex = 0;
    String[] schools;
    private List<Address> mAddressList;
    private List<String> mPlaceList;
    private GetAddressPresenterImpl mGetAddressPresenter;
    private UploadPresenterImpl<Shoppingcart> mShoppingcartUploadPresenter;
    //    private List<GoodCategory> mCategoryList;
//    private GoodCategoryRvAdapter mGoodCategoryRvAdapter;
    private Map<String, Integer> mCategoryMap;
    private String mCurCategory;

    private Good mGood;

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void initData() {
        mGood = (Good) getIntent().getSerializableExtra("good");
        mImgUrlList = mGood.getGoodImageList();
//        schools = getResources().getStringArray(R.array.school);
        initBanners();
        String price = "￥" + mGood.getPrice();
        mTvPrice.setText(price);
        mTvSimpleDetail.setText(mGood.getGoodName());
        mTvConcreteDetail.setText(mGood.getDescription());
        mTvSize.setText(mGood.getSize());
        mTvSelectPlace.setText("选择收货地址");
        mTvSelectCategory.setText("选择商品种类");
        mGetAddressPresenter = new GetAddressPresenterImpl(this);
        mShoppingcartUploadPresenter = new UploadPresenterImpl<>(this);
//        mTvSimpleDetail.setText("美的(Media) MB55V30 洗衣机全自动 小型家用波轮 5.5公斤");
//        mTvSelectCategory.setText("美的(Media) MB55V30 洗衣机全自动小型家用波轮 1件");
//        SharedPreferences place = getSharedPreferences("sp_place", MODE_PRIVATE);
//        String school = place.getString("place", "中山大学南校区");
//        mTvSelectPlace.setText(school);
//        mTvBrand.setText("美的(Midea)");
//        mTvSize.setText("5.5公斤");
//        mTvVersion.setText("MB55V30");
//        mTvConcreteDetail.setText("商品名称：美的（Midea）MB55V30 洗衣机全自动 小型家用波轮 5.5公斤\n" +
//                "商品毛重：33.0kg\n商品产地：中国大陆\n货号：新MB55V30\n产品类型：波轮\n洗涤容量：4.6~5.9kg\n能效等级：三级\n" +
//                "深度：51~55cm\n宽度：51~55cm\n电机类型：定频\n高度：91cm以上\n排水类型：下排水\n包装清单：洗衣机 x1、说明书（含保修卡） x1、进水管 x1、箱底底板 x1");
//        mTvSelectCategoryPrice.setText("￥848");
//        mTvSelectCategoryNumber.setText("商品编号123456");
//        mCategoryList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            mCategoryList.add(new GoodCategory("全自动波轮洗衣机种类" + (i + 1), 0));
//        }
//        mGoodCategoryRvAdapter = new GoodCategoryRvAdapter(this);
//        mGoodCategoryRvAdapter.appendData(mCategoryList);
//        mGoodCategoryRvAdapter.initBooleanList(mCategoryList.size());
//        mGoodCategoryRvAdapter.setOnRecyclerViewListener(this);
//        mRvSelectCategory.setItemAnimator(new DefaultItemAnimator());
//        mRvSelectCategory.setLayoutManager(new LinearLayoutManager(this));
//        mRvSelectCategory.setAdapter(mGoodCategoryRvAdapter);
//        mCategoryMap = new HashMap<>();
//        mCurCategory = "";
    }

    private void initBanners() {
//        List<String> list_path = new ArrayList<>();
        List<String> list_title = new ArrayList<>();
        for (int i = 0; i < mImgUrlList.size(); i++) {
            list_title.add("图片" + i);
        }
//        list_path.add("http://img3.imgtn.bdimg.com/it/u=987011704,1105122635&fm=27&gp=0.jpg");
//        list_path.add("http://img1.imgtn.bdimg.com/it/u=1919310923,1970925386&fm=27&gp=0.jpg");
//        list_path.add("http://img0.imgtn.bdimg.com/it/u=2677594999,635370845&fm=27&gp=0.jpg");
//        list_title.add("美的洗衣机");
//        list_title.add("全自动波轮,包邮加保修");
//        list_title.add("不要998,只要848!!!!");
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        mBanner.setImageLoader(new ImageLoaderUtil());
        mBanner.setImages(mImgUrlList);
        mBanner.setBannerAnimation(Transformer.BackgroundToForeground);
        mBanner.setBannerTitles(list_title);
        mBanner.isAutoPlay(false);
//        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
//        mImgUrlList = new ArrayList<>();
//        mImgUrlList = list_path;
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle(R.string.string_description);
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGoodLabelList = new ArrayList<>();
        mGoodLabelList.add("包邮");
        mGoodLabelList.add("学生价");
        mGoodLabelList.add("四年保修");
        mGoodLabelList.add("质量承诺");
        mGoodLabelRvAdapter = new GoodLabelRvAdapter(this);
        mRvGoodsLabel.setLayoutManager(new GridLayoutManager(this, 3));
        mRvGoodsLabel.setAdapter(mGoodLabelRvAdapter);
        mGoodLabelRvAdapter.appendData(mGoodLabelList);
        mBottomSheetBehavior = BottomSheetBehavior.from(mNsvSelectCategory);
        mBottomSheetBehavior.setHideable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGetAddressPresenter != null) {
            mGetAddressPresenter.getAddress(BmobUser.getCurrentUser().getObjectId());
        }
    }

    @Override
    protected void initListener() {
        mBanner.setOnBannerListener(this);
    }

    @OnClick({R.id.tv_reduce_price_awake, R.id.ll_select_category, R.id.ll_select_place,
            R.id.ib_select_category_close, R.id.tv_select_category_confirm, R.id.btn_reduce, R.id.btn_add,
            R.id.tv_buy, R.id.tv_add_to_trolley})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reduce_price_awake:
                break;
            case R.id.ll_select_category:
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.ll_select_place:
                selectPlace();
                break;
            case R.id.ib_select_category_close:
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.tv_select_category_confirm:
                onSelectCategory();
                break;
            case R.id.btn_reduce:
                reduce();
                break;
            case R.id.btn_add:
                add();
                break;
            case R.id.tv_buy:

                break;
            case R.id.tv_add_to_trolley:
                Shoppingcart shoppingcart = new Shoppingcart();
                shoppingcart.setUserId(BmobUser.getCurrentUser().getObjectId());
                shoppingcart.setGood(mGood);
                shoppingcart.setCount(1);
                mShoppingcartUploadPresenter.upload(shoppingcart);
                break;
            default:
                break;
        }
    }

    private void add() {
//        if (mCurCategory.equals("")) {
//            showToast("请选择一个种类");
//        } else {
//            if (mCategoryMap.containsKey(mCurCategory)) {
//                int num = mCategoryMap.get(mCurCategory);
//                num++;
//                mCategoryMap.put(mCurCategory, num);
//                mTvNum.setText(String.valueOf(num));
//                for (int i = 0; i < mCategoryList.size(); i++) {
//                    if (mCurCategory.equals(mCategoryList.get(i).getDescription())) {
//                        mCategoryList.get(i).setCount(num);
//                        break;
//                    }
//                }
//            } else {
//                int num = Integer.parseInt(mTvNum.getText().toString());
//                num++;
//                mCategoryMap.put(mCurCategory, num);
//                mTvNum.setText(String.valueOf(num));
//                for (int i = 0; i < mCategoryList.size(); i++) {
//                    if (mCurCategory.equals(mCategoryList.get(i).getDescription())) {
//                        mCategoryList.get(i).setCount(num);
//                        break;
//                    }
//                }
//            }
//            mGoodCategoryRvAdapter.notifyDataSetChanged();
//        }
    }

    private void reduce() {
//        if (Integer.parseInt(mTvNum.getText().toString()) == 0) {
//            return;
//        } else if (mCurCategory.equals("")) {
//            return;
//        } else {
//            if (mCategoryMap.containsKey(mCurCategory)) {
//                int num = mCategoryMap.get(mCurCategory);
//                num--;
//                mCategoryMap.put(mCurCategory, num);
//                mTvNum.setText(String.valueOf(num));
//                for (int i = 0; i < mCategoryList.size(); i++) {
//                    if (mCurCategory.equals(mCategoryList.get(i).getDescription())) {
//                        mCategoryList.get(i).setCount(num);
//                        break;
//                    }
//                }
//                mGoodCategoryRvAdapter.notifyDataSetChanged();
//            }
//        }
    }

    private void onSelectCategory() {
//        String string = "";
//        for (int i = 0; i < mCategoryList.size(); i++) {
//            if (mCategoryList.get(i).getCount() > 0) {
//                if (i != mCategoryList.size()) {
//                    string += mCategoryList.get(i).getDescription() + "  x  " + mCategoryList.get(i).getCount() + "\n";
//                } else {
//                    string += mCategoryList.get(i).getDescription() + "  x  " + mCategoryList.get(i).getCount();
//                }
//            }
//        }
//        mTvSelectCategory.setText(string);
//        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void OnBannerClick(int position) {
        startActivity(DetailsPhotoActivity.newIntent(this, position, mImgUrlList));
    }

    private void selectPlace() {
        if (mPlaceList == null || mPlaceList.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("您还没有创建任何收货地址，是否现在创建？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(GoodDetailActivity.this, AddOrEditPlaceActivity.class);
                            intent.putExtra("isAdd", true);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_place, null);
            LoopView loopView = (LoopView) dialogView.findViewById(R.id.lv_select_place);
            loopView.setNotLoop();
            loopView.setItems(mPlaceList);
            loopView.setInitPosition(mSelectPlaceIndex);
            loopView.setTextSize(18);
            loopView.setLineSpacingMultiplier(1.0f);
            loopView.setItemsVisibleCount(5);
            loopView.setCenterTextColor(Color.parseColor("#ef1b1b"));
            loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mSelectPlaceIndex = index;
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("选择收货地址")
                    .setView(dialogView)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mSelectPlaceIndex >= 0 && mSelectPlaceIndex <= mPlaceList.size()) {
                                mTvSelectPlace.setText(mPlaceList.get(mSelectPlaceIndex));
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }
    }

    @Override
    public void onItemClick(int position, View view) {
//        mCurCategory = mCategoryList.get(position).getDescription();
//        if (mCategoryMap.containsKey(mCurCategory)) {
//            mTvNum.setText(String.valueOf(mCategoryMap.get(mCurCategory)));
//        } else {
//            mCategoryMap.put(mCurCategory, 0);
//            mTvNum.setText("0");
//        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onGetAddressSuccess(List<Address> addressList) {
        mAddressList = addressList;
        mPlaceList = new ArrayList<>();
        for (Address address :
                mAddressList) {
            mPlaceList.add(address.getAddress());
            Log.e("error", "address=" + address.getAddress());
        }
    }

    @Override
    public void onGetAddressFailed(String response) {
        showToast(response);
    }

    @Override
    public void onUploadSuccess(String response) {
        showToast(response);
    }

    @Override
    public void onUploadFailed(String response) {
        showToast(response);
    }
}
