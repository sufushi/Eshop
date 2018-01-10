package com.rdc.shop.eshop.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BasePopupWindow;

public class PopupWindowUtil {

    private static PopupWindow mPopupWindow;

    private PopupWindowUtil() {
    }

    public static PopupWindowUtil getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final PopupWindowUtil sInstance = new PopupWindowUtil();
    }

    public void showUpPop(View view, Context context, int layoutResId, BasePopupWindow.ViewInterface listener) {
        if (mPopupWindow.isShowing()) {
            return;
        }
        mPopupWindow = new BasePopupWindow.Builder(context)
                .setView(layoutResId)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewOnClickListener(listener)
                .create();
        mPopupWindow.showAsDropDown(view, 0, -(mPopupWindow.getHeight() + view.getMeasuredHeight()));
    }

    public void showDownPop(View view, Context context, int layoutResId, BasePopupWindow.ViewInterface listener) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) return;
        mPopupWindow = new BasePopupWindow.Builder(context)
                .setView(layoutResId)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnClickListener(listener)
                .setOutsideTouchable(true)
                .create();
        mPopupWindow.showAsDropDown(view);
    }

    public void showRightPop(View view, Context context, int layoutResId, BasePopupWindow.ViewInterface listener) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) return;
        mPopupWindow = new BasePopupWindow.Builder(context)
                .setView(layoutResId)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimHorizontal)
                .setViewOnClickListener(listener)
                .create();
        mPopupWindow.showAsDropDown(view, view.getWidth(), -view.getHeight());
    }

    public void showLeftPop(View view, Context context, int layoutResId, BasePopupWindow.ViewInterface listener) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) return;
        mPopupWindow = new BasePopupWindow.Builder(context)
                .setView(layoutResId)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
                .setViewOnClickListener(listener)
                .create();
        mPopupWindow.showAsDropDown(view, -mPopupWindow.getWidth(), -view.getHeight());
    }

    public void showFullScreen(View view, Context context, int layoutResId, BasePopupWindow.ViewInterface listener) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            return;
        }
        View upView = LayoutInflater.from(context).inflate(layoutResId, null);
        MeasureUtil.measureSize(upView);
        mPopupWindow = new BasePopupWindow.Builder(context)
                .setView(layoutResId)
                .setSize(ViewGroup.LayoutParams.WRAP_CONTENT, upView.getMeasuredHeight())
                .setBackgroundAlpha(0.3f)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnClickListener(listener)
                .create();
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }
}
