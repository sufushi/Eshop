package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseRecyclerViewAdapter;
import com.rdc.shop.eshop.bean.Address;

import butterknife.BindView;

public class PersonPlaceRvAdapter extends BaseRecyclerViewAdapter<Address> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PersonPlaceRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_person_place, null);
        return new PersonPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PersonPlaceViewHolder) holder).bindView(mDataList.get(position));
    }

    class PersonPlaceViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_user_place)
        TextView mTvUserPlace;
        @BindView(R.id.tv_user_name)
        TextView mTvUserName;
        @BindView(R.id.tv_user_sex)
        TextView mTvUserSex;
        @BindView(R.id.tv_user_contract)
        TextView mTvUserContract;

        PersonPlaceViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Address address) {
            mTvUserPlace.setText(address.getAddress());
            mTvUserName.setText(address.getContractName());
            mTvUserSex.setText(address.getSex() ? "先生" : "女士");
            mTvUserContract.setText(address.getPhoneNumber());
        }
    }
}
