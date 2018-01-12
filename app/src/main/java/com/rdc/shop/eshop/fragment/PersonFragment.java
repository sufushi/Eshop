package com.rdc.shop.eshop.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseFragment;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IGetPersonInterestContract;
import com.rdc.shop.eshop.contract.IGetUserDetailContract;
import com.rdc.shop.eshop.presenter.GetPersonInterestPresenterImpl;
import com.rdc.shop.eshop.presenter.GetUserDetailPresenterImpl;
import com.rdc.shop.eshop.ui.LoginActivity;
import com.rdc.shop.eshop.ui.PersonAboutUsActivity;
import com.rdc.shop.eshop.ui.PersonBenefitActivity;
import com.rdc.shop.eshop.ui.PersonDetailsActivity;
import com.rdc.shop.eshop.ui.PersonInterestActivity;
import com.rdc.shop.eshop.ui.PersonOrderActivity;
import com.rdc.shop.eshop.ui.PersonPlaceActivity;
import com.rdc.shop.eshop.ui.PersonServiceCenterActivity;
import com.rdc.shop.eshop.ui.PersonSettingActivity;
import com.rdc.shop.eshop.ui.PersonShopActivity;
import com.rdc.shop.eshop.ui.PersonVisitRecordActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment implements IGetUserDetailContract.View, IGetPersonInterestContract.View {

    Unbinder unbinder;
    @BindView(R.id.civ_user_icon)
    CircleImageView mCivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_action_next)
    ImageView mIvActionNext;
    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.tv_interest_num)
    TextView mTvInterestNum;
    @BindView(R.id.tv_interest)
    TextView mTvInterest;
    @BindView(R.id.rl_interest)
    RelativeLayout mRlInterest;
    @BindView(R.id.tv_benefit_num)
    TextView mTvBenefitNum;
    @BindView(R.id.rl_benefit)
    RelativeLayout mRlBenefit;
    @BindView(R.id.tv_points_num)
    TextView mTvPointsNum;
    @BindView(R.id.rl_points)
    RelativeLayout mRlPoints;
    @BindView(R.id.tv_person_order)
    TextView mTvPersonOrder;
    @BindView(R.id.tv_visit_record)
    TextView mTvVisitRecord;
    @BindView(R.id.tv_person_place)
    TextView mTvPersonPlace;
    @BindView(R.id.tv_service_center)
    TextView mTvServiceCenter;
    @BindView(R.id.tv_about_us)
    TextView mTvAboutUs;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.iv_settings)
    ImageView mIvSettings;

    private String mTitle;
    private User mUser;

    private GetUserDetailPresenterImpl mGetUserDetailPresenter;
    private IGetPersonInterestContract.Presenter mGetPersonInterestPresenter;

    public static PersonFragment newInstance(String title) {
        PersonFragment homeFragment = new PersonFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("title", title);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initData(Bundle bundle) {
        mGetUserDetailPresenter = new GetUserDetailPresenterImpl(this);
        mGetPersonInterestPresenter = new GetPersonInterestPresenterImpl(this);
        mGetPersonInterestPresenter.getPersonInterest(BmobUser.getCurrentUser().getObjectId());
        setParams(bundle);
    }

    private void setParams(Bundle bundle) {
        mTitle = bundle.getString("title");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGetUserDetailPresenter != null) {
            mGetUserDetailPresenter.getUserDetail(BmobUser.getCurrentUser().getObjectId());
        }
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

    @OnClick({R.id.iv_action_next, R.id.iv_settings, R.id.rl_interest, R.id.rl_benefit, R.id.rl_points,
            R.id.tv_person_shop, R.id.tv_person_order, R.id.tv_visit_record, R.id.tv_person_place,
            R.id.tv_service_center, R.id.tv_about_us, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_action_next:
                Intent intent = new Intent(mBaseActivity, PersonDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", mUser);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.iv_settings:
                startActivity(PersonSettingActivity.class);
                break;
            case R.id.rl_interest:
                startActivity(PersonInterestActivity.class);
                break;
            case R.id.rl_benefit:
                startActivity(PersonBenefitActivity.class);
                break;
            case R.id.rl_points:
                showToast("尚未开发！");
                // TODO: 2017/10/5
                break;
            case R.id.tv_person_shop:
                startActivity(PersonShopActivity.class);
                break;
            case R.id.tv_person_order:
                startActivity(PersonOrderActivity.class);
                break;
            case R.id.tv_visit_record:
                startActivity(PersonVisitRecordActivity.class);
                break;
            case R.id.tv_person_place:
                startActivity(PersonPlaceActivity.class);
                break;
            case R.id.tv_service_center:
                startActivity(PersonServiceCenterActivity.class);
                break;
            case R.id.tv_about_us:
                startActivity(PersonAboutUsActivity.class);
                break;
            case R.id.tv_logout:
                logout();
                break;
        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity)
                .setTitle("温馨提示")
                .setMessage("您确定要退出登录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        BmobUser.logOut();
                        Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                        startActivity(loginIntent);
                        mBaseActivity.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onGetUserDetailSuccess(User user) {
        mUser = user;
        if (user != null) {
            if (user.getUserIcon() != null) {
                Glide.with(mBaseActivity).load(user.getUserIcon()).into(mCivUserIcon);
            }
            mTvUserName.setText(user.getUsername());
        }
    }

    @Override
    public void onGetUserDetailFailed(String response) {
        showToast(response);
    }

    @Override
    public void onGetPersonInterestSuccess(List<Good> goodList) {
        String number = goodList.size() + "个";
        mTvInterestNum.setText(number);
    }

    @Override
    public void onGetPersonInterestFailed(String response) {

    }
}
