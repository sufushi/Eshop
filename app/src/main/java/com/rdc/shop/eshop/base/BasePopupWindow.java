package com.rdc.shop.eshop.base;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.rdc.shop.eshop.utils.MeasureUtil;

public class BasePopupWindow extends PopupWindow {

    private final PopupController mPopupController;

    private BasePopupWindow(Context context) {
        mPopupController = new PopupController(context, this);
    }

    @Override
    public int getWidth() {
        return mPopupController.getPopupView().getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return mPopupController.getPopupView().getMeasuredHeight();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mPopupController.setBackgroundAlpha(1.0f);
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    public static class Builder {
        private final PopupController.PopupParams mPopupParams;
        private ViewInterface listener;

        public Builder(Context context) {
            mPopupParams = new PopupController.PopupParams(context);
        }

        public Builder setView(int layoutResId) {
            mPopupParams.view = null;
            mPopupParams.layoutResId = layoutResId;
            return this;
        }

        public Builder setView(View view) {
            mPopupParams.view = view;
            mPopupParams.layoutResId = 0;
            return this;
        }

        public Builder setViewOnClickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        public Builder setSize(int width, int height) {
            mPopupParams.width = width;
            mPopupParams.height = height;
            return this;
        }

        public Builder setBackgroundAlpha(float alpha) {
            mPopupParams.isShowBg = true;
            mPopupParams.bg_alpha = alpha;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            mPopupParams.isTouchable = touchable;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            mPopupParams.isShowAnim = true;
            mPopupParams.animationStyle = animationStyle;
            return this;
        }

        public BasePopupWindow create() {
            final BasePopupWindow popupWindow = new BasePopupWindow(mPopupParams.context);
            mPopupParams.apply(popupWindow.mPopupController);
            if (listener != null && mPopupParams.layoutResId != 0) {
                listener.getChildView(popupWindow.mPopupController.getPopupView(), mPopupParams.layoutResId);
            }
            MeasureUtil.measureSize(popupWindow.mPopupController.getPopupView());
            return popupWindow;
        }

    }
}
