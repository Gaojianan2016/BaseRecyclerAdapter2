package com.gjn.baserecycleradapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gjn.baserecycleradapterlibrary.BaseRecyclerAdapter;
import com.gjn.baserecycleradapterlibrary.HFWrapper;
import com.gjn.baserecycleradapterlibrary.PinyinRecyclerAdapter;
import com.gjn.baserecycleradapterlibrary.RecyclerViewHolder;
import com.gjn.baserecycleradapterlibrary.TypeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private int type = 0;
    private SideBar sideBar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);

        sideBar = findViewById(R.id.sidebar);

        tv = findViewById(R.id.tv_py);

        final RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);

        final BaseRecyclerAdapter<String> adapter1 = new BaseRecyclerAdapter<String>(this, R.layout.item1 , null) {
            @Override
            public void bindData(RecyclerViewHolder recyclerViewHolder, String s, int i) {
                ((Button) recyclerViewHolder.getView(R.id.btn_item1)).setText(s);
            }
        };

        TypeRecyclerAdapter.TypeSupport<String> support = new TypeRecyclerAdapter.TypeSupport<String>() {
            @Override
            public int getLayoutId(int i) {
                if (i == 0) {
                    return R.layout.item2;
                }else {
                    return R.layout.item1;
                }
            }

            @Override
            public int getType(int i, String s) {
                if (i % 2 == 0){
                    return 1;
                }else {
                    return 0;
                }
            }
        };

        final TypeRecyclerAdapter<String> adapter2 = new TypeRecyclerAdapter<String>(this, null, support) {
            @Override
            public void bindData(RecyclerViewHolder recyclerViewHolder, String s, int i) {
                if (getItemViewType(i) == 0) {
                    recyclerViewHolder.setTextViewText(R.id.tv_item2, s);
                }else {
                    ((Button) recyclerViewHolder.getView(R.id.btn_item1)).setText(s);
                }
            }
        };

        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("张三1");
        list.add("张三2");
        list.add("李四");
        list.add("李四21");
        list.add("李四");
        list.add("王五123");
        list.add("王五");
        list.add("王五12111");
        list.add("王五");
        list.add("赵柳");
        list.add("阿什顿");
        list.add("阿什顿123123");
        list.add("后天呢我");
        list.add("问");
        list.add("问123");
        list.add("请问男人");
        list.add("请问男人123");
        list.add("请问男人");
        list.add("请问男人");
        list.add("知道");
        list.add("知道123");
        list.add("知道");
        list.add("知道123");
        list.add("氪金648");
        list.add("氪金518");
        list.add("氪金328");
        list.add("氪金128");
        list = PinyinSort.sort(list);

        final PinyinRecyclerAdapter<String> adapter3 = new PinyinRecyclerAdapter<String>(this, R.layout.item3, list) {
            @Override
            public String getFrist(String s) {
                s = PinyinUtils.getPinYinFrist(s);
                if (!s.matches("[A-Z]")){
                    s = "#";
                }
                Log.e("-s-", "s = " + s);
                return s.substring(0, 1);
            }

            @Override
            public int getTag() {
                return R.id.tv_header;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, String item, int position) {
                super.bindData(holder, item, position);
                holder.setTextViewText(R.id.tv_header, PinyinUtils.getPinYinFrist(item));
                holder.setTextViewText(R.id.tv_item3, item);
            }
        };

        sideBar.setShowView(tv).setOnChooseLetterListener(new SideBar.ChooseLetterListener() {
            @Override
            public void OnChooseLetterListener(String py) {
                manager.scrollToPosition(adapter3.getPositionForSection(py));
            }
        });

        rv.setAdapter(adapter1);

        for (int i = 0; i < 10; i++) {
            adapter1.add("test"+i);
            adapter2.add("test"+i);
        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type++;
                if (type > 2){
                    type = 0;
                }
                sideBar.setVisibility(View.GONE);
                switch (type) {
                    case 1:
                        rv.setAdapter(adapter2);
                        break;
                    case 2:
                        rv.setAdapter(adapter3);
                        sideBar.setVisibility(View.VISIBLE);
                        break;
                    default:
                        rv.setAdapter(adapter1);
                }
            }
        });


        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HFWrapper<String> hfWrapper;
                switch (type) {
                    case 1:
                        hfWrapper = new HFWrapper<>(adapter2);
                        break;
                    case 2:
                        hfWrapper = new HFWrapper<>(adapter3);
                        break;
                    default:
                        hfWrapper = new HFWrapper<>(adapter1);
                }

                TextView head = new TextView(MainActivity.this);
                head.setText("header");
                head.setTextSize(20);
                TextView footer = new TextView(MainActivity.this);
                footer.setText("footer");
                footer.setTextSize(20);
                hfWrapper.addHeadView(head);
                hfWrapper.addFootView(footer);
                rv.setAdapter(hfWrapper);
            }
        });
    }
}
