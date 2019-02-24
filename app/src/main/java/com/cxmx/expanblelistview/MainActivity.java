package com.cxmx.expanblelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.elv_first_order)
    ExpandableListView elvFirstOrder;
    private List<String> FirstList = new ArrayList<>();
    private List<List<String>> SecondList = new ArrayList<>();
    private List<String> GroupList;
    private FirstAdapter firstAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDate();
    }

    private void initDate() {
        //最外层模拟数据载入
        for (int i = 0; i < 4; i++) {
            FirstList.add("项目" + (i + 1));
        }
        initView();
    }

    private void initView() {
        firstAdapter = new FirstAdapter(this, FirstList, SecondList);
        elvFirstOrder.setAdapter(firstAdapter);
        elvFirstOrder.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (elvFirstOrder.isGroupExpanded(groupPosition)) {
                    elvFirstOrder.collapseGroup(groupPosition);
                } else {
                    for (int i = 0; i < 4; i++) {
                        SecondList.add(new ArrayList<String>());
                    }
                    GroupList = new ArrayList<>();
                    GroupList.clear();
//                    GroupList = SecondList.get(groupPosition);
                    //模拟第二层数据加载
                    for (int i = 0; i < 4; i++) {
                        GroupList.add("项目" + (groupPosition + 1) + "的小组" + (i + 1));
//                        Log.e("m_tag", "onGroupClick: " + (i + 1) + "小组，GroupList" + GroupList.size());
                    }
                    Log.e("m_tag", "onGroupClick: 点击了项目" + (groupPosition + 1));
                    SecondList.set(groupPosition, GroupList);
                    elvFirstOrder.expandGroup(groupPosition, true);
                }
                firstAdapter.notifyDataSetChanged();
                return true;
            }
        });
        elvFirstOrder.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < firstAdapter.getGroupCount(); i++) {
                    if (i != groupPosition) {
                        elvFirstOrder.collapseGroup(i);
                    }
                }
            }
        });
    }
}
