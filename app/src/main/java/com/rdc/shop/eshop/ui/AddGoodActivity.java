package com.rdc.shop.eshop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.DetailImgUriAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.contract.IUploadGoodContract;
import com.rdc.shop.eshop.presenter.UploadGoodPresenterImpl;
import com.rdc.shop.eshop.utils.CapturePhotoUtil;
import com.rdc.shop.eshop.utils.FolderManager;
import com.rdc.shop.eshop.utils.PictureUtil;
import com.rdc.shop.eshop.utils.ProgressDialogUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class AddGoodActivity extends BaseActivity implements IUploadGoodContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.sp_category)
    Spinner mSpCategory;
    @BindView(R.id.et_good_name)
    EditText mEtGoodName;
    @BindView(R.id.et_original_price)
    EditText mEtOriginalPrice;
    @BindView(R.id.et_discount_price)
    EditText mEtDiscountPrice;
    @BindView(R.id.et_good_size)
    EditText mEtGoodSize;
    @BindView(R.id.et_good_color)
    EditText mEtGoodColor;
    @BindView(R.id.et_good_description)
    EditText mEtGoodDescription;
    @BindView(R.id.nine_grid_img_view)
    NineGridImageView mNineGridImgView;

    private static final int CODE_CHOOSE_PHOTO = 1;

    private DetailImgUriAdapter mDetailImgUriAdapter;
    private CapturePhotoUtil mCapturePhotoUtil;
    private List<Uri> mSelectedList;

    private IUploadGoodContract.Presenter mPresenter;

    private Shop mShop;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_add_good;
    }

    @Override
    protected void initData() {
        mShop = (Shop) getIntent().getSerializableExtra("shop");
        mPresenter = new UploadGoodPresenterImpl(this);
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle(R.string.string_add_good);
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.good_categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpCategory.setAdapter(adapter);
        mSpCategory.setSelection(0);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.iv_import_photo, R.id.btn_complete, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_import_photo:
                importPicture();
                break;
            case R.id.btn_complete:
                uploadGood();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
        }
    }

    private void uploadGood() {
        if (!checkData()) {
            return;
        }
        if (mSelectedList == null || mSelectedList.isEmpty()) {
            Good good = saveGoodData(null);
            mPresenter.uploadGoodData(good);
            return;
        }
        uploadImg();
    }

    private void importPicture() {
        new AlertDialog.Builder(this)
                .setItems(new String[]{"拍摄", "从相册中选择"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto();
                                break;
                            case 1:
                                choosePhoto();
                                break;
                        }
                    }
                }).show();
    }

    private void takePhoto() {
        if (mCapturePhotoUtil == null) {
            mCapturePhotoUtil = new CapturePhotoUtil(this, FolderManager.getPhotoFolder());
        }
        mCapturePhotoUtil.capture();
    }

    private void choosePhoto() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .theme(R.style.CustomMatisseTheme)
                .forResult(CODE_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CapturePhotoUtil.CAPTURE_PHOTO_REQUEST_CODE:
                handleCaptureResult();
                break;
            case CODE_CHOOSE_PHOTO:
                List<Uri> list = new ArrayList<>();
                if (mSelectedList != null) {
                    list.addAll(mSelectedList);
                }
                List<Uri> uris = Matisse.obtainResult(data);
                for (Uri uri : uris) {
                    if (!list.contains(uri)) {
                        list.add(uri);
                    }
                }
                mSelectedList = list;
                updateNineGridView();
                break;
        }
    }

    private void handleCaptureResult() {
        Uri photoFileUri = mCapturePhotoUtil.getPhotoUri();
        if (photoFileUri != null) {
            File file = mCapturePhotoUtil.getPhotoFile();
            PictureUtil.compressImage(file.getPath(), 600, 800, 700);
            List<Uri> tmpList = new ArrayList<>();
            if (mSelectedList != null) {
                tmpList.addAll(mSelectedList);
            }
            mSelectedList = tmpList;
            mSelectedList.add(photoFileUri);
            updateNineGridView();
        } else {
            showToast("返回数据有误");
        }
    }

    private void updateNineGridView() {
        if (mDetailImgUriAdapter == null) {
            mDetailImgUriAdapter = new DetailImgUriAdapter();
            mNineGridImgView.setAdapter(mDetailImgUriAdapter);
        }
        while (mSelectedList.size() > 9) {
            mSelectedList.remove(0);
        }
        mNineGridImgView.setImagesData(mSelectedList);
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(mEtGoodName.getText())) {
            showToast("商品名称不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtOriginalPrice.getText())) {
            showToast("原价不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtGoodSize.getText())) {
            showToast("商品尺码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtGoodColor.getText())) {
            showToast("商品颜色不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtGoodDescription.getText())) {
            showToast("商品详情不能为空");
            return false;
        }
        if (!TextUtils.isEmpty(mEtDiscountPrice.getText())) {
            if (Float.valueOf(mEtDiscountPrice.getText().toString()) > Float.valueOf(mEtOriginalPrice.getText().toString())) {
                showToast("折扣价不能高于原价");
                return false;
            }
        }
        return true;
    }

    private Good saveGoodData(List<String> urls) {
        Good good = new Good();
        if (urls != null && urls.size() > 0) {
            good.setGoodIcon(urls.get(0));
        }
        good.setCategory((long) mSpCategory.getSelectedItemPosition());
        good.setGoodImageList(urls);
        good.setSellerId(BmobUser.getCurrentUser().getObjectId());
        good.setShop(mShop);
        good.setGoodName(mEtGoodName.getText().toString());
        good.setSize(mEtGoodSize.getText().toString());
        good.setColor(mEtGoodColor.getText().toString());
        good.setPrice(Float.parseFloat(mEtOriginalPrice.getText().toString()));
        good.setChoosed(false);
        if (TextUtils.isEmpty(mEtDiscountPrice.getText())) {
            good.setDiscountPrice(Float.parseFloat(mEtOriginalPrice.getText().toString()));
        } else {
            good.setDiscountPrice(Float.parseFloat(mEtDiscountPrice.getText().toString()));
        }
        good.setDescription(mEtGoodDescription.getText().toString());
        return good;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadImg() {
        final String[] paths = new String[mSelectedList.size()];
        for (int i = 0; i < mSelectedList.size(); i++) {
            paths[i] = PictureUtil.handleImageOnKitKat(this, mSelectedList.get(i));
            PictureUtil.compressImage(paths[i], 600, 800, 800);
        }
        doUpLoad(paths);
    }

    private void doUpLoad(final String[] paths) {
        ProgressDialogUtil.showProgressDialog(this, "上传图片");
        mPresenter.uploadImageList(paths);
    }

    @Override
    public void onUploadImageListSuccess(List<String> urls) {
        mPresenter.uploadGoodData(saveGoodData(urls));
        ProgressDialogUtil.setMsg("正在上传数据...");
    }

    @Override
    public void onUploadImageListFailed(String response) {
        ProgressDialogUtil.dismiss();
        showToast(response);
    }

    @Override
    public void onUploadGoodDataSuccess(String response) {
        ProgressDialogUtil.dismiss();
        showToast(response);
    }

    @Override
    public void onUploadGoodDataFailed(String response) {
        ProgressDialogUtil.dismiss();
        showToast(response);
    }

}
