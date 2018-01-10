package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.MainActivity;

import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.behavior.LoginAnimator;
import com.rdc.shop.eshop.contract.ILoginContract;
import com.rdc.shop.eshop.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends BaseActivity implements ILoginContract.View {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_sign_up)
    TextView mTvSignUp;
    @BindView(R.id.civ_user_icon)
    CircleImageView mCivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.ll_login_progress)
    LinearLayout mLlLoginProgress;
    @BindView(R.id.et_input_user_name)
    EditText mEtInputUserName;
    @BindView(R.id.ll_input_user_name)
    LinearLayout mLlInputUserName;
    @BindView(R.id.et_input_user_password)
    EditText mEtInputUserPassword;
    @BindView(R.id.ll_input_user_password)
    LinearLayout mLlInputUserPassword;
    @BindView(R.id.ll_login_input)
    LinearLayout mLlLoginInput;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.ll_other_login)
    LinearLayout mLlOtherLogin;
    @BindView(R.id.nsv_other_login)
    NestedScrollView mNsvOtherLogin;
    @BindView(R.id.tv_third_party_login)
    TextView mTvThirdPartyLogin;

    private float mTvLoginWidth = 0;
    private float mLlLoginInputWidth = 0;

    private LoginPresenterImpl mLoginPresenter;
    private LoginAnimator mLoginAnimator;
    private BottomSheetBehavior mBottomSheetBehavior;

    public static LoginActivity mInstance = null;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
//        mTvUserName.setText("");
        mInstance = this;
        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginAnimator = new LoginAnimator(mLlLoginInput, mLlLoginProgress);
        mBottomSheetBehavior = BottomSheetBehavior.from(mNsvOtherLogin);
    }

    @Override
    protected void initView() {
        hideInputSoft(mEtInputUserName);
    }

    @Override
    protected void initListener() {
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                boolean operate = newState == BottomSheetBehavior.STATE_EXPANDED;
                mTvThirdPartyLogin.setSelected(operate);
                mNsvOtherLogin.setSelected(operate);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.tv_sign_up, R.id.tv_login, R.id.tv_forget_password,
            R.id.tv_third_party_login, R.id.tv_qq_login, R.id.tv_wechat_login, R.id.tv_wei_blog_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_sign_up:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.tv_third_party_login:
                dealBottomSheetBehavior();
                break;
            case R.id.tv_qq_login:
                showToast("QQ登录");
                break;
            case R.id.tv_wechat_login:
                showToast("微信登录");
                break;
            case R.id.tv_wei_blog_login:
                showToast("微博登录");
                break;
            default:
                break;
        }
    }

    private void dealBottomSheetBehavior() {
        boolean operate = !mNsvOtherLogin.isSelected();
        if (operate) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        mNsvOtherLogin.setSelected(operate);
        mTvThirdPartyLogin.setSelected(operate);
    }

    private void login() {
        if (mTvLoginWidth == 0) {
            mTvLoginWidth = mTvLogin.getX();
        }
        if (mLlLoginInputWidth == 0) {
            mLlLoginInputWidth = mLlLoginInput.getX();
        }
        mLlInputUserName.setVisibility(View.INVISIBLE);
        mLlInputUserPassword.setVisibility(View.INVISIBLE);
        String username = mEtInputUserName.getText().toString();
        String password = mEtInputUserPassword.getText().toString();
        mLoginAnimator.loginAnimator(mLlLoginInput, mLlLoginInputWidth, mTvLoginWidth, mLoginPresenter, username, password);
    }

    @Override
    public void onLoginSuccess(String msg) {
        showToast(msg);
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onLoginFailed(String msg) {
        showToast(msg);
        mLoginAnimator.resetInputView(mLlLoginInput, mTvLoginWidth, mLlLoginInputWidth);
        mLlInputUserName.setVisibility(View.VISIBLE);
        mLlInputUserPassword.setVisibility(View.VISIBLE);
    }

}
