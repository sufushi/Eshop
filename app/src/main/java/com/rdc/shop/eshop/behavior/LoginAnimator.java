package com.rdc.shop.eshop.behavior;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.rdc.shop.eshop.contract.ILoginContract;


public class LoginAnimator {

    private View mInputView;
    private View mProgressView;

    public LoginAnimator(View inputView, View progressView) {
        mInputView = inputView;
        mProgressView = progressView;
    }

    public void loginAnimator(final View view, float oldWidth, float newWidth, final ILoginContract.Presenter presenter,
                              final String username, final String password) {

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(oldWidth, newWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.5f);

        animatorSet.setDuration(600);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("error", "mProgressView show");
                progressAnimator(mProgressView);
                mInputView.setVisibility(View.INVISIBLE);
                mProgressView.setVisibility(View.VISIBLE);
                presenter.login(username, password);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void progressAnimator(View progressView) {
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1.0f);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1.0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(progressView, propertyValuesHolder1, propertyValuesHolder2);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new JellyInterpolator(0.15f));
        objectAnimator.start();
    }

    public void resetInputView(final View view, float oldWidth, float newWidth) {
        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(oldWidth, newWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1.0f);

        animatorSet.setDuration(600);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(View.INVISIBLE);
                mInputView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

}
