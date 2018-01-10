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

import butterknife.BindView;

public class PersonGoodsRvAdapter extends BaseRecyclerViewAdapter<Good> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PersonGoodsRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_home_goods, null);
        return new PersonGoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PersonGoodViewHolder) holder).bindView(mDataList.get(position));
    }

    class PersonGoodViewHolder extends BaseRvHolder {

        @BindView(R.id.iv_good_image)
        ImageView mIvGoodImage;
        @BindView(R.id.tv_good_price)
        TextView mTvGoodPrice;
        @BindView(R.id.tv_good_name)
        TextView mTvGoodName;
        @BindView(R.id.tv_good_description)
        TextView mTvGoodDescription;

        public PersonGoodViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Good good) {
            Glide.with(mContext).load(good.getGoodIcon()).into(mIvGoodImage);
            String price = "￥" + good.getPrice();
            mTvGoodPrice.setText(price);
            mTvGoodName.setText(good.getGoodName());
            mTvGoodDescription.setText(good.getDescription());
        }
    }
}
