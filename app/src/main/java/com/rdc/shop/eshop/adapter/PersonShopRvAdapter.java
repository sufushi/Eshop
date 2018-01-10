package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;
import com.rdc.shop.eshop.bean.Shop;
import com.rdc.shop.eshop.utils.MeasureUtil;

import butterknife.BindView;

public class PersonShopRvAdapter extends BaseRecyclerViewAdapter<Shop> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PersonShopRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_shop, null);
        return new PersonShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PersonShopViewHolder) holder).bindView(mDataList.get(position));
    }

    class PersonShopViewHolder extends BaseRvHolder {

        @BindView(R.id.iv_shop_logo)
        ImageView mIvShopLogo;
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;
        @BindView(R.id.tv_shop_description)
        TextView mTvShopDescription;

        public PersonShopViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Shop shop) {
            Glide.with(mContext).load(shop.getShopLogo()).into(mIvShopLogo);
            mTvShopName.setText(shop.getShopName());
            mTvShopDescription.setText(shop.getDescription());
            mTvShopDescription.getLayoutParams().width = MeasureUtil.getScreenWidth(mContext);
        }
    }
}
