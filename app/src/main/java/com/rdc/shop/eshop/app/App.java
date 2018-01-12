package com.rdc.shop.eshop.app;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

import static com.rdc.shop.eshop.constant.Constant.APPID;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
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
