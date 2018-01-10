package com.rdc.shop.eshop.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Address;
import com.rdc.shop.eshop.contract.IDeleteContract;
import com.rdc.shop.eshop.contract.IUpdateContract;
import com.rdc.shop.eshop.contract.IUploadContract;
import com.rdc.shop.eshop.presenter.DeletePresenterImpl;
import com.rdc.shop.eshop.presenter.UpdatePresenterImpl;
import com.rdc.shop.eshop.presenter.UploadPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class AddOrEditPlaceActivity extends BaseActivity implements IUploadContract.View, IUpdateContract.View, IDeleteContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_male)
    TextView mTvMale;
    @BindView(R.id.tv_female)
    TextView mTvFemale;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_place)
    EditText mEtPlace;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;

    private boolean isAdd;
    private Address mAddress;

    private UploadPresenterImpl<BmobObject> mAddressPresenter;
    private UpdatePresenterImpl<BmobObject> mUpdatePresenter;
    private DeletePresenterImpl<BmobObject> mDeletePresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_add_or_edit_place;
    }

    @Override
    protected void initData() {
        isAdd = getIntent().getBooleanExtra("isAdd", false);
        mAddressPresenter = new UploadPresenterImpl<>(this);
        mUpdatePresenter = new UpdatePresenterImpl<>(this);
        mDeletePresenter = new DeletePresenterImpl<>(this);
    }

    @Override
    protected void initView() {
        if (isAdd) {
            mTbTitle.setTitle("新增地址");
            mBtnDelete.setVisibility(View.GONE);
            mTvMale.setSelected(true);
        } else {
            mTbTitle.setTitle("编辑地址");
            mBtnDelete.setVisibility(View.VISIBLE);
            mAddress = (Address) getIntent().getSerializableExtra("address");
            mEtName.setText(mAddress.getContractName());
            mEtPhone.setText(mAddress.getPhoneNumber());
            mEtPlace.setText(mAddress.getAddress());
            boolean sex = mAddress.getSex();
            mTvMale.setSelected(sex);
            mTvFemale.setSelected(!sex);
        }
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @OnClick({R.id.btn_confirm, R.id.btn_delete, R.id.tv_male, R.id.tv_female})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                confirm();
                break;
            case R.id.btn_delete:
                mDeletePresenter.delete(mAddress);
                break;
            case R.id.tv_male:
                mTvMale.setSelected(true);
                mTvFemale.setSelected(false);
                break;
            case R.id.tv_female:
                mTvMale.setSelected(false);
                mTvFemale.setSelected(true);
                break;
            default:
                break;
        }
    }

    private void confirm() {
        if (isAdd) {
            Address address = new Address(BmobUser.getCurrentUser().getObjectId(),
                    mEtName.getText().toString(), mEtPhone.getText().toString(),
                    mEtPlace.getText().toString(), mTvMale.isSelected());
            mAddressPresenter.upload(address);
        } else {
            mAddress.setContractName(mEtName.getText().toString());
            mAddress.setPhoneNumber(mEtPhone.getText().toString());
            mAddress.setAddress(mEtPlace.getText().toString());
            mAddress.setSex(mTvMale.isSelected());
            mUpdatePresenter.update(mAddress);
        }
    }

    @Override
    public void onUploadSuccess(String response) {
        showToast(response);
        finish();
    }

    @Override
    public void onUploadFailed(String response) {
        showToast(response);
    }

    @Override
    public void onUpdateSuccess(String response) {
        showToast(response);
        finish();
    }

    @Override
    public void onUpdateFailed(String response) {
        showToast(response);
    }

    @Override
    public void onDeleteSuccess(String response) {
        showToast(response);
        finish();
    }

    @Override
    public void onDeleteFailed(String response) {
        showToast(response);
    }
}
