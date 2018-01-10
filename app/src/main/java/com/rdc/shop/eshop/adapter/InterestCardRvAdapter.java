package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;
import com.rdc.shop.eshop.bean.Good;
import com.view.jameson.library.CardAdapterHelper;

import butterknife.BindView;

public class InterestCardRvAdapter extends BaseRecyclerViewAdapter<Good> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CardAdapterHelper mCardAdapterHelper;

    public InterestCardRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCardAdapterHelper = new CardAdapterHelper();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_interest_card, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, view);
        return new InterestCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        ((InterestCardViewHolder) holder).bindView(mDataList.get(position));
    }

    class InterestCardViewHolder extends BaseRvHolder {

        @BindView(R.id.riv_interest_good_name)
        RoundedImageView mRivInterestGoodName;
        @BindView(R.id.tv_interest_good_name)
        TextView mTvInterestGoodName;
        @BindView(R.id.tv_interest_good_price)
        TextView mTvInterestGoodPrice;

        InterestCardViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Good good) {
            Glide.with(mContext).load(good.getGoodIcon()).into(mRivInterestGoodName);
            mTvInterestGoodName.setText(good.getGoodName());
            String price = "￥" + String.valueOf(good.getPrice());
            mTvInterestGoodPrice.setText(price);
        }
    }
}
