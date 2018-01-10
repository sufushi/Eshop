package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;
import com.rdc.shop.eshop.bean.Coupon;

import java.text.SimpleDateFormat;

import butterknife.BindView;

public class BenefitRvAdapter extends BaseRecyclerViewAdapter<Coupon> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SimpleDateFormat mSimpleDateFormat;

    public BenefitRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_coupon, null);
        return new BenefitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BenefitViewHolder) holder).bindView(mDataList.get(position));
    }

    class BenefitViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_coupon_value)
        TextView mTvCouponValue;
        @BindView(R.id.tv_provider)
        TextView mTvProvider;
        @BindView(R.id.tv_details)
        TextView mTvDetails;
        @BindView(R.id.tv_expiry_date)
        TextView mTvExpiryDate;

        BenefitViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Coupon coupon) {
            String value = "￥" + coupon.getValue();
            mTvCouponValue.setText(value);
            mTvProvider.setText(coupon.getProvider());
            mTvDetails.setText(coupon.getDetails());
            mTvExpiryDate.setText(mSimpleDateFormat.format(coupon.getExpiryDate()));
        }
    }
}
