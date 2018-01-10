package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.entity.Store;
import com.rdc.shop.eshop.listener.OnGroupEditInterface;
import com.rdc.shop.eshop.listener.OnModifyCountInterface;
import com.rdc.shop.eshop.listener.OnSelectInterface;


import java.util.List;
import java.util.Map;

public class TrolleyELAdapter extends BaseExpandableListAdapter {

    private List<Store> mStoreList;
    private Map<Integer, List<Good>> mGoodListMap;
    private Context mContext;
    private int mFlag;

    private OnSelectInterface mOnSelectInterface;
    private OnModifyCountInterface mOnModifyCountInterface;
    private OnGroupEditInterface mOnGroupEditInterface;

    public TrolleyELAdapter(List<Store> storeList, Map<Integer, List<Good>> goodListMap, Context context) {
        mStoreList = storeList;
        mGoodListMap = goodListMap;
        mContext = context;
    }

    public void setOnSelectInterface(OnSelectInterface onSelectInterface) {
        mOnSelectInterface = onSelectInterface;
    }

    public void setOnModifyCountInterface(OnModifyCountInterface onModifyCountInterface) {
        mOnModifyCountInterface = onModifyCountInterface;
    }

    public void setOnGroupEditInterface(OnGroupEditInterface onGroupEditInterface) {
        mOnGroupEditInterface = onGroupEditInterface;
    }

    @Override
    public int getGroupCount() {
        return mStoreList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Integer groupId = mStoreList.get(groupPosition).getStoreId();
        return mGoodListMap.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mStoreList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Good> goodList = mGoodListMap.get(mStoreList.get(groupPosition).getStoreId());
        return goodList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final TrolleyGroupViewHolder trolleyGroupViewHolder;
        if (convertView == null) {
            trolleyGroupViewHolder = new TrolleyGroupViewHolder();
            convertView = View.inflate(mContext, R.layout.item_group_good, null);
            trolleyGroupViewHolder.mCbSelectGroup = (CheckBox) convertView.findViewById(R.id.cb_select_group);
            trolleyGroupViewHolder.mTvStoreName = (TextView) convertView.findViewById(R.id.tv_store_name);
            trolleyGroupViewHolder.mBtnEdit = (Button) convertView.findViewById(R.id.btn_store_edit);
            convertView.setTag(trolleyGroupViewHolder);
        } else {
            trolleyGroupViewHolder = (TrolleyGroupViewHolder) convertView.getTag();
        }
        final Store store = (Store) getGroup(groupPosition);
        if (store != null) {
            trolleyGroupViewHolder.mTvStoreName.setText(store.getStoreName());
            trolleyGroupViewHolder.mCbSelectGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    store.setChoosed(((CheckBox) v).isChecked());
                    mOnSelectInterface.onSelectGroup(groupPosition, ((CheckBox) v).isChecked());
                }
            });
            trolleyGroupViewHolder.mCbSelectGroup.setChecked(store.getChoosed());
            if (store.getEdit()) {
                trolleyGroupViewHolder.mBtnEdit.setText(R.string.string_complete);
            } else {
                trolleyGroupViewHolder.mBtnEdit.setText(R.string.string_edit);
            }
            trolleyGroupViewHolder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (store.getEdit()) {
                        store.setEdit(false);
                    } else {
                        store.setEdit(true);
                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            mStoreList.remove(groupPosition);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final TrolleyChildViewHolder trolleyChildViewHolder;
        final Good good = (Good) getChild(groupPosition, childPosition);
        if (convertView == null) {
            trolleyChildViewHolder = new TrolleyChildViewHolder();
            convertView = View.inflate(mContext, R.layout.item_child_good, null);
            trolleyChildViewHolder.mCbSelectChild = (CheckBox) convertView.findViewById(R.id.cb_select_child);
            trolleyChildViewHolder.mIvGood = (ImageView) convertView.findViewById(R.id.iv_good);
            trolleyChildViewHolder.mTvGoodName = (TextView) convertView.findViewById(R.id.tv_good_name);
            trolleyChildViewHolder.mTvColorSize = (TextView) convertView.findViewById(R.id.tv_color_size);
            trolleyChildViewHolder.mTvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            trolleyChildViewHolder.mTvDiscountPrice = (TextView) convertView.findViewById(R.id.tv_discount_price);
            trolleyChildViewHolder.mTvBuyNum = (TextView) convertView.findViewById(R.id.tv_buy_num);
            trolleyChildViewHolder.mRlNormal = (RelativeLayout) convertView.findViewById(R.id.rl_normal);
            trolleyChildViewHolder.mBtnReduce = (Button) convertView.findViewById(R.id.btn_reduce);
            trolleyChildViewHolder.mEtNum = (EditText) convertView.findViewById(R.id.et_num);
            trolleyChildViewHolder.mBtnAdd = (Button) convertView.findViewById(R.id.btn_add);
            trolleyChildViewHolder.mTvGoodSize = (TextView) convertView.findViewById(R.id.tv_good_size);
            trolleyChildViewHolder.mTvGoodsDelete = (TextView) convertView.findViewById(R.id.tv_goods_delete);
            trolleyChildViewHolder.mLlEdit = (LinearLayout) convertView.findViewById(R.id.ll_edit);
            trolleyChildViewHolder.groupPosition = groupPosition;
            trolleyChildViewHolder.childPosition = childPosition;
            convertView.setTag(trolleyChildViewHolder);
        } else {
            trolleyChildViewHolder = (TrolleyChildViewHolder) convertView.getTag();
        }
        trolleyChildViewHolder.setListener();
        if (mStoreList.get(groupPosition).getEdit()) {
            trolleyChildViewHolder.mLlEdit.setVisibility(View.VISIBLE);
            trolleyChildViewHolder.mRlNormal.setVisibility(View.GONE);
        } else {
            trolleyChildViewHolder.mLlEdit.setVisibility(View.GONE);
            trolleyChildViewHolder.mRlNormal.setVisibility(View.VISIBLE);
        }
        if (good != null) {
            trolleyChildViewHolder.mTvGoodName.setText(good.getGoodName());
            Glide.with(mContext).load(good.getGoodIcon()).into(trolleyChildViewHolder.mIvGood);
            String color_size = "颜色:" + good.getColor() + ",尺码:" + good.getSize();
            trolleyChildViewHolder.mTvColorSize.setText(color_size);
            String price = "￥" + good.getPrice();
            trolleyChildViewHolder.mTvPrice.setText(price);
            String discountPrice = "￥" + good.getDiscountPrice();
            SpannableString spannableString = new SpannableString(discountPrice);
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, 0, String.valueOf(good.getDiscountPrice()).length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (trolleyChildViewHolder.mTvDiscountPrice.getText().toString().length() > 0) {
                trolleyChildViewHolder.mTvDiscountPrice.setText("");
            }
            trolleyChildViewHolder.mTvDiscountPrice.append(spannableString);
            String buyNum = "X" + good.getCount();
            trolleyChildViewHolder.mTvBuyNum.setText(buyNum);

            trolleyChildViewHolder.mEtNum.setText(String.valueOf(good.getCount()));
            trolleyChildViewHolder.mTvGoodSize.setText(color_size);
            if (good.getChoosed() != null) {
                trolleyChildViewHolder.mCbSelectChild.setChecked(good.getChoosed());
            }
            trolleyChildViewHolder.mCbSelectChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    good.setChoosed(((CheckBox) v).isChecked());
                    trolleyChildViewHolder.mCbSelectChild.setChecked(((CheckBox) v).isChecked());
                    mOnSelectInterface.onSelectChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class TrolleyGroupViewHolder {
        CheckBox mCbSelectGroup;
        TextView mTvStoreName;
        Button mBtnEdit;
    }

    private class TrolleyChildViewHolder implements View.OnClickListener {
        CheckBox mCbSelectChild;
        ImageView mIvGood;
        TextView mTvGoodName;
        TextView mTvColorSize;
        TextView mTvPrice;
        TextView mTvDiscountPrice;
        TextView mTvBuyNum;
        RelativeLayout mRlNormal;
        Button mBtnReduce;
        EditText mEtNum;
        Button mBtnAdd;
        TextView mTvGoodSize;
        TextView mTvGoodsDelete;
        LinearLayout mLlEdit;
        int groupPosition;
        int childPosition;

        TrolleyChildViewHolder() {
        }

        public void setListener() {
            mCbSelectChild.setOnClickListener(this);
            mBtnAdd.setOnClickListener(this);
            mBtnReduce.setOnClickListener(this);
            mTvGoodsDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cb_select_child:
                    mCbSelectChild.setChecked(((CheckBox) v).isChecked());
                    mOnSelectInterface.onSelectChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                    break;
                case R.id.btn_add:
                    mOnModifyCountInterface.onIncrease(groupPosition, childPosition, mEtNum, mCbSelectChild.isChecked());
                    break;
                case R.id.btn_reduce:
                    mOnModifyCountInterface.onDecrease(groupPosition, childPosition, mEtNum, mCbSelectChild.isChecked());
                    break;
                case R.id.tv_goods_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                            .setTitle("温馨提示")
                            .setMessage("您确定要将这些商品从购物车中移除吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOnModifyCountInterface.onChildDelete(groupPosition, childPosition);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                    break;
                default:
                    break;
            }
        }
    }

}
