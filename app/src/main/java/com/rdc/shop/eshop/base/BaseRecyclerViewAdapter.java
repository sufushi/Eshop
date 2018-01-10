package com.rdc.shop.eshop.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdc.shop.eshop.listener.OnClickRecyclerViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDataList = new ArrayList<>();
    protected OnClickRecyclerViewListener mOnRecyclerViewListener;

    //更新数据
    public void updataData(List dataList) {
        mDataList.clear();
        appendData(dataList);

    }

    //分页加载，追加数据
    public void appendData(List dataList) {
        if (null != dataList && !dataList.isEmpty()) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        } else if (dataList != null && dataList.isEmpty()) {
            notifyDataSetChanged();
            //空数据更新
        }
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * RecyclerView不提供点击事件，自定义点击事件
     */
    public void setOnRecyclerViewListener(OnClickRecyclerViewListener onRecyclerViewListener) {
        mOnRecyclerViewListener = onRecyclerViewListener;
    }

    public abstract class BaseRvHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public BaseRvHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        protected abstract void bindView(T t);

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewListener != null) {
                mOnRecyclerViewListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }

}

