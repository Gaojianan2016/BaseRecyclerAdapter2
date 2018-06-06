package com.gjn.baserecycleradapterlibrary;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * TypeRecyclerAdapter
 * Author: gjn.
 * Time: 2017/9/25.
 */

public abstract class TypeRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    protected TypeSupport<T> mTypeSupport;

    public TypeRecyclerAdapter(Context ctx, List<T> list, TypeSupport<T> typeSupport) {
        super(ctx, -1, list);
        mTypeSupport = typeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mTypeSupport.getType(position,mData.get(position));
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mTypeSupport.getLayoutId(viewType);
        RecyclerViewHolder holder = RecyclerViewHolder.getHolder(mContext,parent,layoutId);
        addItemClick(holder);
        return holder;
    }

    public interface TypeSupport<T>{
        int getLayoutId(int type);
        int getType(int position, T t);
    }
}
