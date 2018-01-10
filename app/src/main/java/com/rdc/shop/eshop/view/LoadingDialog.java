package com.rdc.shop.eshop.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
//import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdc.shop.eshop.R;

public class LoadingDialog extends Dialog {

    private TextView mMessageTextView;
//    private ProgressBar mProgressBar;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.layout_dialog_loading);
        init();
    }

    private void init() {
        mMessageTextView = (TextView) findViewById(R.id.tv_loading);
        setCanceledOnTouchOutside(false);
    }

    public LoadingDialog setMessage(String message) {
        mMessageTextView.setText(message);
        return this;
    }

}
