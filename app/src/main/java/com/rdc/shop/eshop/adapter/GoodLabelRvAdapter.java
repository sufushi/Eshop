package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;

import butterknife.BindView;

public class GoodLabelRvAdapter extends BaseRecyclerViewAdapter<String> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GoodLabelRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_good_label, null);
        return new GoodLabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GoodLabelViewHolder)holder).bindView(mDataList.get(position));
    }

    class GoodLabelViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_good_label)
        TextView mTvGoodLabel;

        GoodLabelViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(String s) {
            mTvGoodLabel.setText(s);
        }
    }
}
