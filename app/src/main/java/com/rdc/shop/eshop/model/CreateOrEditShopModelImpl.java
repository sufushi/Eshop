package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.bean.User;
import com.rdc.shop.eshop.contract.ICreateOrEditShopContract;
import com.rdc.shop.eshop.utils.ProgressDialogUtil;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

public class CreateOrEditShopModelImpl implements ICreateOrEditShopContract.Model {

    @Override
    public void uploadShopIcon(final ICreateOrEditShopContract.Presenter presenter, String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onUploadShopIconSuccess(bmobFile.getFileUrl());
                } else {
                    presenter.onUploadShopIconFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void uploadShopImages(final ICreateOrEditShopContract.Presenter presenter, final String[] paths) {
        BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                if (urls.size() == paths.length) {
                    presenter.onUploadShopImagesSuccess(urls);
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                String str = "总上传图片数:" + total + "\n第 " + curIndex + " 张图片正在上传";
                if (ProgressDialogUtil.isShow()) {
                    ProgressDialogUtil.setMsg(str);
                }
            }

            @Override
            public void onError(int i, String s) {
                presenter.onUploadShopImagesFailed(s);
            }
        });
    }

    @Override
    public void uploadShopData(final ICreateOrEditShopContract.Presenter presenter, final Shop shop) {
        shop.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
//                    User user = new User();
//                    user.setShop(shop);
//                    user.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                presenter.onUploadShopDataSuccess("创建店铺成功");
//                            } else {
//                                presenter.onUploadShopDataFailed(e.getMessage());
//                            }
//                        }
//                    });
                    presenter.onUploadShopDataSuccess("创建店铺成功");
                } else {
                    presenter.onUploadShopDataFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void updateShopData(final ICreateOrEditShopContract.Presenter presenter, Shop shop) {
        shop.update(shop.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onUpdateShopDataSuccess("修改成功");
                } else {
                    presenter.onUpdateShopDataFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteShop(final ICreateOrEditShopContract.Presenter presenter, Shop shop) {
        shop.delete(shop.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    presenter.onDeleteShopSuccess("删除成功");
                } else {
                    presenter.onDeleteShopFailed(e.getMessage());
                }
            }
        });
    }
}
