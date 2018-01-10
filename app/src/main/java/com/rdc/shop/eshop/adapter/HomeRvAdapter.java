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
import com.rdc.shop.eshop.bean.Good;
import com.rdc.shop.eshop.bean.GoodxShop;
import com.rdc.shop.eshop.utils.ImageLoaderUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

public class HomeRvAdapter extends BaseRecyclerViewAdapter<GoodxShop> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private static final int BANNER_TYPE = 0;
    private static final int LIST_TYPE = 1;

    public HomeRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == BANNER_TYPE) {
            view = mLayoutInflater.inflate(R.layout.item_banner, null);
            return new BannerViewHolder(view);
        } else if (viewType == LIST_TYPE){
            view = mLayoutInflater.inflate(R.layout.item_home_goods, null);
            return new ListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER_TYPE) {
            ((BannerViewHolder) holder).bindView(mDataList.get(position));
        } else {
            ((ListViewHolder) holder).bindView(mDataList.get(position));
        }
    }

    class BannerViewHolder extends BaseRvHolder {

        private Banner mBanner;

        BannerViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.banner);
        }

        @Override
        protected void bindView(GoodxShop goodxShop) {
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            mBanner.setImageLoader(new ImageLoaderUtil());
            mBanner.setImages(goodxShop.getAdvertisement().getImageList());
            mBanner.setBannerAnimation(Transformer.BackgroundToForeground);
            mBanner.setBannerTitles(goodxShop.getAdvertisement().getTitleList());
            mBanner.isAutoPlay(true);
            mBanner.setDelayTime(3000);
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            mBanner.start();
        }
    }

    class ListViewHolder extends BaseRvHolder {

        private ImageView mIvGoodImage;
        private TextView mTvGoodPrice;
        private TextView mTvGoodName;
        private TextView mTvGoodDescription;

        ListViewHolder(View itemView) {
            super(itemView);
            mIvGoodImage = (ImageView) itemView.findViewById(R.id.iv_good_image);
            mTvGoodPrice = (TextView) itemView.findViewById(R.id.tv_good_price);
            mTvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            mTvGoodDescription = (TextView) itemView.findViewById(R.id.tv_good_description);
        }

        @Override
        protected void bindView(GoodxShop goodxShop) {
            Good good = goodxShop.getGood();
            Glide.with(mContext).load(good.getGoodIcon()).into(mIvGoodImage);
            String price = "￥" + String.valueOf(good.getPrice());
            mTvGoodPrice.setText(price);
            mTvGoodName.setText(good.getGoodName());
            mTvGoodDescription.setText(good.getDescription());
        }
    }
}
