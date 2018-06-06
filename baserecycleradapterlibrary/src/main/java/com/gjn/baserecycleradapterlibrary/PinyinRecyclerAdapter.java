package com.gjn.baserecycleradapterlibrary;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * PinyinRecyclerAdapter
 * Author: gjn.
 * Time: 2018/3/1.
 */

public abstract class PinyinRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {


    public PinyinRecyclerAdapter(Context ctx, int layoutId, List<T> list) {
        super(ctx, layoutId, list);
    }

    @Override
    public void bindData(RecyclerViewHolder holder, T item, int position) {
        String section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.getView(getTag()).setVisibility(View.VISIBLE);
        } else {
            holder.getView(getTag()).setVisibility(View.GONE);
        }
    }

    //根据分类的字符串获取其第一次出现的位置
    public int getPositionForSection(String section) {
        for (int i = 0; i < getItemCount(); i++) {
            String firstChar = getFrist(getData().get(i));
            if (firstChar.equals(section)) {
                return i;
            }
        }
        return -1;
    }

    //根据ListView的当前位置获取分类的字符串
    public String getSectionForPosition(int position) {
        return getFrist(getData().get(position));
    }

    public abstract String getFrist(T t);

    public abstract int getTag();
}
