package com.rdc.shop.eshop.listener;

public interface OnSelectInterface {

    void onSelectGroup(int groupPosition, boolean isSelected);
    void onSelectChild(int groupPosition, int childPosition, boolean isSelected);

}
