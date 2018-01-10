package com.rdc.shop.eshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rdc.shop.eshop.MainActivity;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.contract.IRegisterCobtract;
import com.rdc.shop.eshop.presenter.RegisterPresenterImpl;
import com.rdc.shop.eshop.utils.ProgressDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterCobtract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.et_register_username)
    EditText mEtRegisterUsername;
    @BindView(R.id.et_register_password)
    EditText mEtRegisterPassword;
    @BindView(R.id.et_register_password_again)
    EditText mEtRegisterPasswordAgain;
    @BindView(R.id.btn_register_register)
    Button mBtnRegisterRegister;
    @BindView(R.id.ll_register)
    LinearLayout mLlRegister;
    @BindView(R.id.sv_register)
    ScrollView mSvRegister;

    private IRegisterCobtract.Presenter mPresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        mPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle(R.string.string_register);
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hideInputSoft(mEtRegisterUsername);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register_register)
    public void onViewClicked() {
        ProgressDialogUtil.showProgressDialog(this, "注册中...");
        String phoneNumber = mEtRegisterUsername.getText().toString().trim();
        String password = mEtRegisterPassword.getText().toString();
        String confirmPassword = mEtRegisterPasswordAgain.getText().toString();
        mPresenter.register(phoneNumber, password, confirmPassword);
    }

    @Override
    public void onRegisterSuccess(String response) {
        ProgressDialogUtil.dismiss();
        Snackbar.make(mSvRegister, response, Snackbar.LENGTH_LONG)
                .show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        LoginActivity.mInstance.finish();
        finish();
    }

    @Override
    public void onRegisterFailed(String response) {
        ProgressDialogUtil.dismiss();
        Snackbar.make(mSvRegister, response, Snackbar.LENGTH_LONG)
                .show();
    }
}
