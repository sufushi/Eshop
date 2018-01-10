package com.rdc.shop.eshop.app;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

import static com.rdc.shop.eshop.constant.Constant.APPID;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
    }

    private void initBmob() {
        Bmob.initialize(this, APPID, "Bmob");
//        //设置BmobConfig
//        BmobConfig config =new BmobConfig.Builder(getApplicationContext())
//                .setConnectTimeout(30) //请求超时时间（单位为秒）：默认15s
//                .setUploadBlockSize(500 * 1024) //文件分片上传时每片的大小（单位字节），默认512*1024
//                .build();
//        Bmob.initialize(config);
    }
}
