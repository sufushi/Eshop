package com.rdc.shop.eshop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.IUploadUserDetailContract;
import com.rdc.shop.eshop.presenter.UploadUserDetailPresenterImpl;
import com.rdc.shop.eshop.utils.CapturePhotoUtil;
import com.rdc.shop.eshop.utils.DBCopyUtil;
import com.rdc.shop.eshop.utils.FolderManager;
import com.rdc.shop.eshop.utils.PictureUtil;
import com.rdc.shop.eshop.utils.ProgressDialogUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class PersonDetailsActivity extends BaseActivity implements IUploadUserDetailContract.View {

    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.iv_user_icon)
    ImageView mIvUserIcon;
    @BindView(R.id.rl_user_icon)
    RelativeLayout mRlUserIcon;
    @BindView(R.id.tv_user_phone_number)
    TextView mTvUserPhoneNumber;
    @BindView(R.id.tv_user_nik_name)
    TextView mTvUserNikName;
    @BindView(R.id.tv_user_password)
    TextView mTvUserPassword;
    @BindView(R.id.rl_user_nik_name)
    RelativeLayout mRlUserNikName;
    @BindView(R.id.tv_user_sex)
    TextView mTvUserSex;
    @BindView(R.id.rl_user_sex)
    RelativeLayout mRlUserSex;
    @BindView(R.id.tv_user_address)
    TextView mTvUserAddress;
    @BindView(R.id.rl_user_address)
    RelativeLayout mRlUserAddress;

    private static final int REQUEST_SELECT_REGION = 0;
    private static final int CODE_CHOOSE_PHOTO = 1;

    private CapturePhotoUtil mCapturePhotoUtil;
    private Uri mUserIconUri;

    private Boolean isMale;

    private IUploadUserDetailContract.Presenter mPresenter;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_person_details;
    }

    @Override
    protected void initData() {
        mPresenter = new UploadUserDetailPresenterImpl(this);
        isMale = false;
    }

    @Override
    protected void initView() {
        mTbTitle.setTitle("个人详情");
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            if (user.getUserIcon() != null) {
                Glide.with(this).load(user.getUserIcon()).into(mIvUserIcon);
            }
            mTvUserPhoneNumber.setText(user.getPhoneNumber());
            mTvUserNikName.setText(user.getUsername());
            mTvUserPassword.setText(user.getUserPassword());
            mTvUserSex.setText((isMale = user.getSex()) ? "男" : "女");
            mTvUserAddress.setText(user.getLocation());
        }
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

    @OnClick({R.id.rl_user_icon, R.id.rl_user_phone_number, R.id.rl_user_nik_name, R.id.rl_user_password,
            R.id.rl_user_sex, R.id.rl_user_address, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user_icon:
                selectPicture();
                break;
            case R.id.rl_user_nik_name:
                showChangeUsernameDialog();
                break;
            case R.id.rl_user_phone_number:
                showChangePhoneNumberDialog();
                break;
            case R.id.rl_user_sex:
                selectSex();
                break;
            case R.id.rl_user_password:
                showChangePasswordDialog();
                break;
            case R.id.rl_user_address:
                DBCopyUtil.copyDataBaseFromAssets(this, "region.db");
                Intent selectRegionIntent = new Intent(this, SelectRegionActivity.class);
                startActivityForResult(selectRegionIntent, REQUEST_SELECT_REGION);
                break;
            case R.id.btn_complete:
                if (mUserIconUri == null) {
                    User user = saveUser(null);
                    mPresenter.uploadUserDetail(user);
                    return;
                }
                uploadUserIcon();
                break;
            default:
                break;
        }
    }

    private User saveUser(String url) {
        User user = new User();
        if (url != null) {
            user.setUserIcon(url);
            Log.e("error", url);
        }
        user.setPhoneNumber(mTvUserPhoneNumber.getText().toString());
        user.setUsername(mTvUserNikName.getText().toString());
        user.setPassword(mTvUserPassword.getText().toString());
        user.setUserPassword(mTvUserPassword.getText().toString());
        user.setSex(isMale);
        user.setLocation(mTvUserAddress.getText().toString());
        return user;
    }

    private void uploadUserIcon() {
        String path;
        path = PictureUtil.handleImageOnKitKat(this, mUserIconUri);
        PictureUtil.compressImage(path, 600, 800, 800);
        ProgressDialogUtil.showProgressDialog(this, "上传图片");
        mPresenter.uploadUserIcon(path);
    }

    private void selectSex() {
        final Boolean temp = isMale;
        isMale = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("选择性别")
                .setIcon(R.drawable.ic_sex)
                .setSingleChoiceItems(new String[]{"男", "女"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            isMale = true;
                        } else {
                            isMale = false;
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isMale) {
                            mTvUserSex.setText("男");
                        } else {
                            mTvUserSex.setText("女");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isMale = temp;
                        if (isMale) {
                            mTvUserSex.setText("男");
                        } else {
                            mTvUserSex.setText("女");
                        }
                    }
                });
        builder.create().show();
    }

    private void showChangePhoneNumberDialog() {
        final View view = View.inflate(this, R.layout.layout_dialog_change_phone_number, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_phone_number);
        editText.setText(mTvUserPhoneNumber.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("修改手机号码")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = editText.getText().toString();
                        mTvUserPhoneNumber.setText(phoneNumber);
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

    private void showChangeUsernameDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_change_nik_name, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_nik_name);
        editText.setText(mTvUserNikName.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("修改姓名")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvUserNikName.setText(editText.getText().toString());
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

    private void showChangePasswordDialog() {
        final View view = View.inflate(this, R.layout.layout_dialog_change_password, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_password);
        editText.setText(mTvUserPassword.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("修改手机号码")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = editText.getText().toString();
                        mTvUserPassword.setText(phoneNumber);
                    }
                })
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void selectPicture() {
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
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .theme(R.style.CustomMatisseTheme)
                .forResult(CODE_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CapturePhotoUtil.CAPTURE_PHOTO_REQUEST_CODE:
                    handleCaptureResult();
                    break;
                case CODE_CHOOSE_PHOTO:
                    List<Uri> uris = Matisse.obtainResult(data);
                    mUserIconUri = uris.get(0);
                    Glide.with(this).load(mUserIconUri).into(mIvUserIcon);
                    break;
                case REQUEST_SELECT_REGION:
                    String province = data.getStringExtra(SelectRegionActivity.REGION_PROVINCE);
                    String city = data.getStringExtra(SelectRegionActivity.REGION_CITY);
                    String area = data.getStringExtra(SelectRegionActivity.REGION_AREA);
                    mTvUserAddress.setText(province + " " + city + " " + area);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleCaptureResult() {
        Uri photoFileUri = mCapturePhotoUtil.getPhotoUri();
        if (photoFileUri != null) {
            mUserIconUri = photoFileUri;
            Glide.with(this).load(mUserIconUri).into(mIvUserIcon);
        } else {
            showToast("返回数据有误");
        }
    }

    @Override
    public void onUploadUserDetailSuccess(String response) {
        ProgressDialogUtil.dismiss();
        showToast(response);
    }

    @Override
    public void onUploadUserDetailFailed(String response) {
        ProgressDialogUtil.dismiss();
        showToast(response);
    }

    @Override
    public void onUploadUserIconSuccess(String url) {
        mPresenter.uploadUserDetail(saveUser(url));
        ProgressDialogUtil.setMsg("正在上传数据...");
    }

    @Override
    public void onUploadUserIconFailed(String response) {
        ProgressDialogUtil.dismiss();
        showToast(response);
    }
}
