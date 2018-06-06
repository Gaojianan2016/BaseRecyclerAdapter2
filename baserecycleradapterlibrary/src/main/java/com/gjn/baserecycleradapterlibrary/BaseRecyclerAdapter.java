package com.gjn.baserecycleradapterlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRecyclerAdapter
 * Author: gjn.
 * Time: 2017/6/9.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(View view, int pos);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int pos);
    }

    protected List<T> mData;
    protected Context mContext;
    protected int mLayoutId;

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerAdapter(Context ctx, int layoutId, List<T> list){
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mLayoutId = layoutId;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = RecyclerViewHolder.getHolder(mContext,parent,mLayoutId);
        addItemClick(holder);
        return holder;
    }

    protected void addItemClick(final RecyclerViewHolder holder) {
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            });
        }
        if (mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(v,holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder,mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int pos){
        return mData.get(pos);
    }

    public void add(int pos,T item){
        if (pos > getItemCount()){
            pos = getItemCount();
        }
        if (pos < 0){
            pos = 0;
        }
        mData.add(pos,item);
        notifyItemInserted(pos);
    }

    public void add(T item){
        add(getItemCount(),item);
    }

    public void add(List<T> items){
        if (items != null){
            for (T item : items) {
                add(item);
            }
        }
    }

    public void addstart(T item){
        add(0,item);
    }

    public void delete(int pos){
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void delete(){
        int pos = getItemCount()-1;
        delete(pos);
    }

    public List<T> getData(){
        return mData;
    }

    public void change(int pos,T item){
        mData.set(pos,item);
        notifyItemChanged(pos,item);
    }

    public void clear(){
        mData = new ArrayList<T>();
        notifyDataSetChanged();
    }

    public void setData(List<T> list){
        clear();
        add(list);
    }

    public void move(int from, int to){
        T temp = mData.get(from);

        mData.remove(from);
        mData.add(to,temp);

        notifyItemMoved(from,to);
    }

    public abstract void bindData(RecyclerViewHolder holder, T item, int position);

}
