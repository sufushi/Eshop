package com.rdc.shop.eshop.listener;


import android.view.View;

public interface OnClickRecyclerViewListener {
    void onItemClick(int position, View view);
    boolean onItemLongClick(int position);
}
