package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;
import com.rdc.shop.eshop.bean.Good;

import butterknife.BindView;

public class ConfirmOrderRvAdapter extends BaseRecyclerViewAdapter<Good> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ConfirmOrderRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_commit_order, null);
        return new ConfirmOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConfirmOrderViewHolder) holder).bindView(mDataList.get(position));
    }

    class ConfirmOrderViewHolder extends BaseRvHolder {

        @BindView(R.id.riv_shop_logo)
        RoundedImageView mRivShopLogo;
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;
        @BindView(R.id.iv_good_image)
        ImageView mIvGoodImage;
        @BindView(R.id.tv_good_name)
        TextView mTvGoodName;
        @BindView(R.id.tv_good_size_color)
        TextView mTvGoodSizeColor;
        @BindView(R.id.tv_good_price)
        TextView mTvGoodPrice;
        @BindView(R.id.tv_good_count)
        TextView mTvGoodCount;
        @BindView(R.id.tv_delivery_way)
        TextView mTvDeliveryWay;
        @BindView(R.id.tv_delivery_time)
        TextView mTvDeliveryTime;
        @BindView(R.id.et_remarks)
        EditText mEtRemarks;
        @BindView(R.id.cb_buy_anonymous)
        CheckBox mCbBuyAnonymous;

        public ConfirmOrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Good good) {
            Glide.with(mContext).load(good.getShop().getShopLogo()).into(mRivShopLogo);
            mTvShopName.setText(good.getShop().getShopName());
            Glide.with(mContext).load(good.getGoodIcon()).into(mIvGoodImage);
            mTvGoodName.setText(good.getGoodName());
            String sizeColor = "颜色:" + good.getColor() + "尺寸:" + good.getSize();
            mTvGoodSizeColor.setText(sizeColor);
            String price = "￥" + good.getPrice();
            mTvGoodPrice.setText(price);
            String count = "x " + good.getCount();
            mTvGoodCount.setText(count);
            mTvDeliveryWay.setText("包邮");

        }
    }

}
