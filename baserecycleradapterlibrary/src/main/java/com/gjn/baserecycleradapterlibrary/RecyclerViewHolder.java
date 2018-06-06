package com.gjn.baserecycleradapterlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerViewHolder
 * Author: gjn.
 * Time: 2017/6/9.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }

    public static RecyclerViewHolder getHolder(View view){
        return new RecyclerViewHolder(view);
    }

    public static RecyclerViewHolder getHolder(Context context, ViewGroup parent, int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        return new RecyclerViewHolder(view);
    }

    private <T extends View>T findViewById(int id){
        View view = mView.get(id);
        if (view == null){
            view = itemView.findViewById(id);
            mView.put(id,view);
        }
        return (T) view;
    }

    public View getView(int id){
        return findViewById(id);
    }

    public RecyclerViewHolder setTextViewText(int id,String txt){
        TextView textView = (TextView) getView(id);
        textView.setText(txt);
        return this;
    }

    public ImageView getImageView(int id){
        return (ImageView) getView(id);
    }

    public RecyclerViewHolder setVisibility(int id, int visibility){
        getView(id).setVisibility(visibility);
        return this;
    }

    public RecyclerViewHolder setClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setLongClickListener(View.OnLongClickListener listener){
        itemView.setOnLongClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setClickListener(int id,View.OnClickListener listener){
        findViewById(id).setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setLongClickListener(int id, View.OnLongClickListener listener){
        findViewById(id).setOnLongClickListener(listener);
        return this;
    }
}
