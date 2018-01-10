package com.rdc.shop.eshop.listener;

import android.view.View;

public interface OnModifyCountInterface {

    void onIncrease(int groupPosition, int childPosition, View countView, boolean isSelected);
    void onDecrease(int groupPosition, int childPosition, View countView, boolean isSelected);
    void onChildDelete(int groupPosition, int childPosition);

}
