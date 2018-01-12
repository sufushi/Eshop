package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;
import com.rdc.shop.eshop.bean.Good;

import butterknife.BindView;

public class GoodSearchRvAdapter extends BaseRecyclerViewAdapter<Good> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GoodSearchRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_good_search, null);
        return new GoodSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GoodSearchViewHolder) holder).bindView(mDataList.get(position));
    }

    class GoodSearchViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_good_name)
        TextView mTvGoodName;

        public GoodSearchViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Good good) {
            mTvGoodName.setText(good.getGoodName());
        }
    }
}
