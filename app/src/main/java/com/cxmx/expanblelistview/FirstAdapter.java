package com.cxmx.expanblelistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:TQX
 * @Date : 2019/2/23 0023
 * @description:
 */
public class FirstAdapter extends BaseExpandableListAdapter {
    private List<String> list = new ArrayList<>();
    private List<List<String>> groupList = new ArrayList<>();
    private List<String> childList = new ArrayList<>();
    private List<String> ThridList = new ArrayList<>();
    private List<List<String>> PeopleList = new ArrayList<>();

    private Context context;
    private MainActivity mainActivity = new MainActivity();

    private SecondAdapter secondAdapter;

    private ProjectViewHolder projectViewHolder;
    private GroupViewHolder groupViewHolder;

    public FirstAdapter(Context context, List<String> list, List<List<String>> mgroupList) {
        this.context = context;
        this.list = list;
        this.groupList = mgroupList;
    }

    @Override
    public int getGroupCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //null == groupList ? 0 : groupList.get(groupPosition).size()
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.e("m_tag", "点击了====:项目" + groupPosition + "小组" + childPosition);
        return groupList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.elv_project_layout, null);
            projectViewHolder = new ProjectViewHolder(convertView);
            convertView.setTag(projectViewHolder);
        } else {
            projectViewHolder = (ProjectViewHolder) convertView.getTag();
        }
        projectViewHolder.projectName.setText(list.get(groupPosition));
        if (isExpanded) {
            projectViewHolder.ivOpenStyle.setImageResource(R.drawable.single_btn_pre);
        } else {
            projectViewHolder.ivOpenStyle.setImageResource(R.drawable.single_btn_nor);
        }
        Log.e("m_tag", "isExpanded: " + isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_group, null);
        groupViewHolder = new GroupViewHolder(convertView);
        childList = groupList.get(groupPosition);
        secondAdapter = new SecondAdapter(context, childList, PeopleList);
        groupViewHolder.elvSecondOrder.setAdapter(secondAdapter);
        convertView.setTag(groupViewHolder);
//        } else {
//            groupViewHolder = (GroupViewHolder) convertView.getTag();
//        }
        groupViewHolder.elvSecondOrder.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int mGroupPosition, long id) {
                if (groupViewHolder.elvSecondOrder.isGroupExpanded(mGroupPosition)) {
                    groupViewHolder.elvSecondOrder.collapseGroup(mGroupPosition);
                    Log.e("m_tag", "close--->");
                } else {
//                    PeopleList.clear();
                    for (int i = 0; i < childList.size(); i++) {
                        PeopleList.add(new ArrayList<String>());
                    }
                    ThridList.clear();
                    //模拟数据请求配置
                    for (int i = 0; i < 4; i++) {
                        ThridList.add("组员" + i);
                        Log.e("m_tag", "组员: " + i);
                    }
                    PeopleList.set(mGroupPosition, ThridList);
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            groupViewHolder.elvSecondOrder.expandGroup(mGroupPosition, true);
                            Log.e("m_tag", "open--->");
                            secondAdapter.notifyDataSetChanged();
                        }
                    });

                }
                return true;
            }
        });
        groupViewHolder.elvSecondOrder.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(final ExpandableListView parent, final View view, int groupPosition, final int childPosition, long id) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "点击了" + ThridList.get(childPosition), Toast.LENGTH_SHORT).show();
                        secondAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
        groupViewHolder.elvSecondOrder.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < secondAdapter.getGroupCount(); i++) {
                    if (i != groupPosition) {
                        groupViewHolder.elvSecondOrder.collapseGroup(i);
                    }
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ProjectViewHolder {
        @BindView(R.id.iv_open_style)
        ImageView ivOpenStyle;
        @BindView(R.id.project_name)
        TextView projectName;
        @BindView(R.id.tv_onlineNumber)
        TextView tvOnlineNumber;

        ProjectViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class GroupViewHolder {
        @BindView(R.id.elv_second_order)
        CustomExpandableListView elvSecondOrder;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
