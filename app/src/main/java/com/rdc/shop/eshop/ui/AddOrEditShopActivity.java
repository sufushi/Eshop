package com.rdc.shop.eshop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.adapter.DetailImgUriAdapter;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.ICreateOrEditShopContract;
import com.rdc.shop.eshop.presenter.CreateOrEditShopPresenterImpl;
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
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class AddOrEditShopActivity extends BaseActivity implements ICreateOrEditShopContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.et_shop_name)
    EditText mEtShopName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_shop_description)
    EditText mEtShopDescription;
    @BindView(R.id.riv_add_shop_logo)
    RoundedImageView mRivAddShopLogo;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.ngiv_shop_images)
    NineGridImageView mNgivShopImages;

    private static final int CODE_CHOOSE_PHOTO = 1;
    private static final int CODE_ADD_LOGO = 2;
    private static final int CODE_ADD_IMAGES = 3;

    private DetailImgUriAdapter mDetailImgUriAdapter;
    private CapturePhotoUtil mCapturePhotoUtil;
    private List<Uri> mSelectedList;
    private List<String> mImageList;
    private Uri mLogo;
    private String mLogoUrl;
    private int mMode;
    private boolean isAdd;
    private Shop mShop;
    private Boolean isLogoChanged;
    private Boolean isImagesChanged;

    private CreateOrEditShopPresenterImpl mCreateOrEditShopPresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_add_or_edit_shop;
    }

    @Override
    protected void initData() {
        isAdd = getIntent().getBooleanExtra("isAdd", false);
        mShop = (Shop) getIntent().getSerializableExtra("shop");
        mImageList = new ArrayList<>();
        if (mShop != null) {
            mEtShopName.setText(mShop.getShopName());
            mEtPhone.setText(mShop.getContractNumber());
            mEtShopDescription.setText(mShop.getDescription());
            mImageList = mShop.getShopImageList();
            mLogoUrl = mShop.getShopLogo();
            Glide.with(this).load(mLogoUrl).into(mRivAddShopLogo);
            mSelectedList = new ArrayList<>();
            for (int i = 0; i < mImageList.size(); i++) {
                Uri uri = Uri.parse(mImageList.get(i));
                mSelectedList.add(uri);
            }
            updateNineGridView();
        }
        isLogoChanged = false;
        isImagesChanged = false;
        mCreateOrEditShopPresenter = new CreateOrEditShopPresenterImpl(this);
    }

    @Override
    protected void initView() {
        if (isAdd) {
            mTbTitle.setTitle("创建店铺");
            mBtnDelete.setVisibility(View.GONE);
        } else {
            mTbTitle.setTitle("编辑店铺");
            mBtnDelete.setVisibility(View.VISIBLE);
        }
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.ll_shop_category, R.id.riv_add_shop_logo, R.id.tv_add_shop_images, R.id.btn_confirm, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop_category:
                break;
            case R.id.riv_add_shop_logo:
                mMode = CODE_ADD_LOGO;
                importPicture(mMode);
                break;
            case R.id.tv_add_shop_images:
                mMode = CODE_ADD_IMAGES;
                importPicture(mMode);
                break;
            case R.id.btn_confirm:
                if (checkData()) {
                    if (isLogoChanged) {
                        uploadLogo();
                    } else if (isImagesChanged) {
                        uploadCompressImages();
                    } else {
                        if (isAdd) {
                            uploadData();
                        } else {
                            updateData();
                        }
                    }
                } else {
                    return;
                }
                break;
            case R.id.btn_delete:
                onBackPressed();
                break;
        }
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(mEtShopName.getText().toString())) {
            showToast("店铺名称不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showToast("联系方式不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtShopDescription.getText().toString())) {
            showToast("店铺详情不能为空");
            return false;
        }
        if (isAdd) {
            if (mLogo == null) {
                showToast("还没有添加店铺logo");
                return false;
            }
            if (mSelectedList == null) {
                showToast("请选择店铺相关图片");
                return false;
            }
        }
        return true;
    }

    private void uploadLogo() {
        String path;
        path = PictureUtil.handleImageOnKitKat(this, mLogo);
        PictureUtil.compressImage(path, 600, 800, 800);
        ProgressDialogUtil.showProgressDialog(this, "上传logo");
        mCreateOrEditShopPresenter.uploadShopIcon(path);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadCompressImages() {
        int size = mSelectedList.size() - mImageList.size();
        final String[] paths = new String[size];
        for (int i = 0; i < size; i++) {
            paths[i] = PictureUtil.handleImageOnKitKat(this, mSelectedList.get(i + mImageList.size()));
            PictureUtil.compressImage(paths[i], 600, 800, 800);
        }
        ProgressDialogUtil.setMsg("上传图片");
        mCreateOrEditShopPresenter.uploadShopImages(paths);
    }

    private void uploadData() {
        if (mShop == null) {
            mShop = new Shop();
        }
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser().getObjectId());
        mShop.setOwner(user);
        mShop.setShopName(mEtShopName.getText().toString());
        mShop.setContractNumber(mEtPhone.getText().toString());
        mShop.setDescription(mEtShopDescription.getText().toString());
        mCreateOrEditShopPresenter.uploadShopData(mShop);
    }

    private void updateData() {
        mShop.setShopName(mEtShopName.getText().toString());
        mShop.setContractNumber(mEtPhone.getText().toString());
        mShop.setDescription(mEtShopDescription.getText().toString());
        mCreateOrEditShopPresenter.updateShopData(mShop);
    }

    private void importPicture(final int mode) {
        new AlertDialog.Builder(this)
                .setItems(new String[]{"拍摄", "从相册中选择"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto();
                                break;
                            case 1:
                                if (mode == CODE_ADD_LOGO) {
                                    choosePhoto(1);
                                } else if (mode == CODE_ADD_IMAGES) {
                                    choosePhoto(9);
                                }
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

    private void choosePhoto(int photoNumber) {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(photoNumber)
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
                if (mMode == CODE_ADD_LOGO) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    mLogo = uris.get(0);
                    Glide.with(this).load(mLogo).into(mRivAddShopLogo);
                    if (!isLogoChanged) {
                        isLogoChanged = true;
                    }
                } else if (mMode == CODE_ADD_IMAGES){
                    List<Uri> list = new ArrayList<>();
                    if (mSelectedList != null) {
                        list.addAll(mSelectedList);
                    }
                    List<Uri> uriList = Matisse.obtainResult(data);
                    for (Uri uri : uriList) {
                        if (!list.contains(uri)) {
                            list.add(uri);
                        }
                    }
                    mSelectedList = list;
                    updateNineGridView();
                    if (!isImagesChanged) {
                        isImagesChanged = true;
                    }
                }
                break;
        }
    }

    private void handleCaptureResult() {
        switch (mMode) {
            case CODE_ADD_LOGO:
                Uri uri = mCapturePhotoUtil.getPhotoUri();
                if (uri != null) {
                    mLogo = uri;
                    Glide.with(this).load(mLogo).into(mRivAddShopLogo);
                } else {
                    showToast("返回数据有误");
                }
                break;
            case CODE_ADD_IMAGES:
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
                break;
            default:
        }
    }

    private void updateNineGridView() {
        if (mDetailImgUriAdapter == null) {
            mDetailImgUriAdapter = new DetailImgUriAdapter();
            mNgivShopImages.setAdapter(mDetailImgUriAdapter);
        }
        while (mSelectedList.size() > 9) {
            mSelectedList.remove(0);
            if (!isAdd) {
                mImageList.remove(0);
            }
        }
        mNgivShopImages.setImagesData(mSelectedList);
    }

    @Override
    public void onUploadShopIconSuccess(String iconUrl) {
        if (mShop == null) {
            mShop = new Shop();
        }
        mShop.setShopLogo(iconUrl);
        if (isImagesChanged) {
            uploadCompressImages();
        } else {
            if (isAdd) {
                uploadData();
            } else {
                updateData();
            }
        }
    }

    @Override
    public void onUploadShopIconFailed(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onUploadShopImagesSuccess(List<String> urls) {
        if (mShop == null) {
            mShop = new Shop();
        }
        mImageList.addAll(urls);
        mShop.setShopImageList(mImageList);
        if (isAdd) {
            uploadData();
        } else {
            updateData();
        }
    }

    @Override
    public void onUploadShopImagesFailed(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onUploadShopDataSuccess(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onUploadShopDataFailed(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onUpdateShopDataSuccess(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onUpdateShopDataFailed(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onDeleteShopSuccess(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onDeleteShopFailed(String response) {
        showToast(response);
        ProgressDialogUtil.dismiss();
    }
}
