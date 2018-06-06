package com.gjn.baserecycleradapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * PinyinSort
 * Author: gjn.
 * Time: 2018/3/1.
 */

public class PinyinSort {

    public static List<String> sort(List<String> list){
        return sort(list,PinyinUtils.defualtListener());
    }

    public static List<String> sort(List<String> list, PinyinUtils.TongYinListener listener){

        List<String> pyLists = new ArrayList<>();

        for (String s : list) {
            pyLists.add(PinyinUtils.getPinYin(s,listener));
        }

        List<String> sortList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            sortList.add(pyLists.get(i)+"_"+list.get(i));
        }

        Collections.sort(sortList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                o1 = o1.split("_")[0].toLowerCase();
                o2 = o2.split("_")[0].toLowerCase();
                return o1.compareTo(o2);
            }
        });

        List<String> newList = new ArrayList<>();

        for (String s : sortList) {
            newList.add(s.split("_")[1]);
        }

        return newList;
    }
}
