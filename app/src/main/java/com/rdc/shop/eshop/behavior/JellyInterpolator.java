package com.rdc.shop.eshop.behavior;

import android.view.animation.LinearInterpolator;

public class JellyInterpolator extends LinearInterpolator {

    private float mFactor;

    public JellyInterpolator(float factor) {
        mFactor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input)
                * Math.sin((input - mFactor / 4) * (2 * Math.PI) / mFactor) + 1);
    }
}
