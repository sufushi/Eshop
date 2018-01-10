package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class GoodRvAdapter extends BaseRecyclerViewAdapter<Good> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GoodRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_visit_record_good, null);
        return new GoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GoodViewHolder) holder).bindView(mDataList.get(position));
    }

    class GoodViewHolder extends BaseRvHolder {

        @BindView(R.id.iv_good)
        ImageView mIvGood;
        @BindView(R.id.tv_good_name)
        TextView mTvGoodName;
        @BindView(R.id.tv_good_price)
        TextView mTvGoodPrice;

        GoodViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Good good) {
            Glide.with(mContext)
                    .load(good.getGoodIcon())
                    .into(mIvGood);
            mTvGoodName.setText(good.getGoodName());
            String price = "￥" + String.valueOf(good.getPrice());
            mTvGoodPrice.setText(price);
            Log.e("error", price);
        }
    }
}
