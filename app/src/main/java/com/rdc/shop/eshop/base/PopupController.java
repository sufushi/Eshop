package com.rdc.shop.eshop.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

class PopupController {

    private Context mContext;
    private int mLayoutResId;
    private View mView;
    private View mPopupView;
    private PopupWindow mPopupWindow;
    private Window mWindow;

    PopupController(Context context, PopupWindow popupWindow) {
        mContext = context;
        mPopupWindow = popupWindow;
    }

    View getPopupView() {
        return mPopupView;
    }

    public void setView(int layoutResId) {
        mView = null;
        this.mLayoutResId = layoutResId;
        installContent();
    }

    public void setView(View view) {
        mView = view;
        this.mLayoutResId = 0;
        installContent();
    }

    private void installContent() {
        if (mLayoutResId != 0) {
            mPopupView = LayoutInflater.from(mContext).inflate(mLayoutResId, null);
        } else if (mView != null) {
            mPopupView = mView;
        }
        mPopupWindow.setContentView(mPopupView);
    }

    private void setSize(int width, int height) {
        if (width == 0 || height == 0) {
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
        }
    }

    void setBackgroundAlpha(float alpha) {
        mWindow = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = alpha;
        mWindow.setAttributes(params);
    }

    private void setAnimationStyle(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
    }

    private void setOutsideTouchable(boolean touchable) {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(touchable);
        mPopupWindow.setFocusable(touchable);
    }

    static class PopupParams {
        public Context context;
        public int layoutResId;
        public View view;
        public int width;
        public int height;
        public int animationStyle;
        public float bg_alpha;
        public boolean isTouchable;
        public boolean isShowBg;
        public boolean isShowAnim;

        public PopupParams(Context context) {
            this.context = context;
        }

        public void apply(PopupController popupController) {
            if (view != null) {
                popupController.setView(view);
            } else if (layoutResId != 0) {
                popupController.setView(layoutResId);
            } else {
                throw new IllegalArgumentException("popipView's contentView is null");
            }
            popupController.setSize(width, height);
            popupController.setOutsideTouchable(isTouchable);
            if (isShowBg) {
                popupController.setBackgroundAlpha(bg_alpha);
            }
            if (isShowAnim) {
                popupController.setAnimationStyle(animationStyle);
            }
        }
    }
}
