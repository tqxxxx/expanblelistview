package com.cxmx.expanblelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:TQX
 * @Date : 2019/2/23 0023
 * @description:
 */
public class SecondAdapter extends BaseExpandableListAdapter {
    private List<String> groupList = new ArrayList<>();
    private List<String> ThridList = new ArrayList<>();
    private List<List<String>> GroupList = new ArrayList<>();
    private Context context;

    private GroupViewHolder groupViewHolder;
    private PeoPleViewHolder peoPleViewHolder;

    public SecondAdapter(Context context, List<String> groupList, List<List<String>> GroupList) {
        this.groupList = groupList;
        this.GroupList = GroupList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return null == groupList ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return null == GroupList ? 0 : GroupList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return GroupList.get(groupPosition).get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.elv_group_layout, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.groupName.setText(groupList.get(groupPosition));
        if (isExpanded) {
            groupViewHolder.ivOpenStyle.setImageResource(R.drawable.single_btn_pre);
        } else {
            groupViewHolder.ivOpenStyle.setImageResource(R.drawable.single_btn_nor);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.elv_people_layout, null);
            peoPleViewHolder = new PeoPleViewHolder(convertView);
            peoPleViewHolder.tvPeopleName.setText(GroupList.get(groupPosition).get(childPosition));
        } else {
            peoPleViewHolder = (PeoPleViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        @BindView(R.id.iv_open_style)
        ImageView ivOpenStyle;
        @BindView(R.id.group_name)
        TextView groupName;
        @BindView(R.id.tv_onlineNumber)
        TextView tvOnlineNumber;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class PeoPleViewHolder {
        @BindView(R.id.tv_people_name)
        TextView tvPeopleName;

        PeoPleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
