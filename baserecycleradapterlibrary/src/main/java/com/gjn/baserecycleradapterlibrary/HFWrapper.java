package com.gjn.baserecycleradapterlibrary;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * HFWrapper
 * Author: gjn.
 * Time: 2017/9/4.
 */

public class HFWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0x1000;
    private static final int TYPE_FOOTER = 0x2000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HFWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }

    private boolean isHeader(int pos){
        return pos < getHeaderCount();
    }

    private boolean isFooter(int pos){
        return pos >= (getHeaderCount() + getRealItemCount());
    }

    private int getHeaderCount() {
        return mHeaderViews.size();
    }

    private int getFooterCount() {
        return mFootViews.size();
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    public void addHeadView(View v){
        mHeaderViews.put(mHeaderViews.size() + TYPE_HEADER, v);
    }

    public void addFootView(View v){
        mFootViews.put(mFootViews.size() + TYPE_FOOTER, v);
    }

    public void clearFootView(){
        mFootViews.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)){
            return mHeaderViews.keyAt(position);
        }else if (isFooter(position)){
            return mFootViews.keyAt(position - getHeaderCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null){
            return RecyclerViewHolder.getHolder(mHeaderViews.get(viewType));
        }else if(mFootViews.get(viewType) != null){
            return RecyclerViewHolder.getHolder(mFootViews.get(viewType));
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)){
            return;
        }else if (isFooter(position)){
            return;
        }else {
            mInnerAdapter.onBindViewHolder(holder, position - getHeaderCount());
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + getRealItemCount();
    }
}
