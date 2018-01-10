package com.rdc.shop.eshop.model;

import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.contract.IUploadGoodContract;
import com.rdc.shop.eshop.utils.ProgressDialogUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class UploadGoodModelImpl implements IUploadGoodContract.Model {

    @Override
    public void uploadImageList(final IUploadGoodContract.Presenter presenter, final String[] paths) {
        BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                if (urls.size() == paths.length) {
                    presenter.onUploadImageListSuccess(urls);
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
                presenter.onUploadImageListFailed(s);
            }
        });
    }

    @Override
    public void uploadGoodData(final IUploadGoodContract.Presenter presenter, Good good) {
        good.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    presenter.onUploadGoodDataSuccess(s);
                } else {
                    presenter.onUploadGoodDataFailed(e.getMessage());
                }
            }
        });
    }
}
