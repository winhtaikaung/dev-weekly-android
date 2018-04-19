package com.winhtaikaung.devweekly.base;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by winhtaikaung on 8/11/17.
 */

public abstract class BaseAdapter<VH extends BaseAdapter.BaseViewHolder> extends Adapter<VH> {

    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    protected void onItemHolderClickListener(VH itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView, itemHolder.getPosition(),
                    itemHolder.getItemId());
        }
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected BaseAdapter mAdapter;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClickListener(this);
        }
    }
}

