package com.example.guanguannfc.view.management;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.dataManagement.ActivityManage;

import java.util.List;

public class ExpandableListviewAdapter extends BaseExpandableListAdapter {
    private PopupWindow mPopWindow;
    //Model：定义的数据
    private String[] groups;
    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    private List<Act> childsq;
    private Context context;
    private ActivityManage getact;
    private String [] child;

    public ExpandableListviewAdapter(Context context, String[] groups, List<Act> childsq,ActivityManage getact){
        this.context=context;
        this.groups=groups;
        this.childsq=childsq;
        this.getact=getact;
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int i) {
        if (childsq.get(i).getAct() != null) return childsq.get(i).getAct().length;
        return 0;

    }

    @Override
    public Object getGroup(int i) {
        return groups[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return childsq.get(i).getAct()[i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    public boolean hasStableIds() {
        return true;
    }

/**
 *
 * 获取显示指定组的视图对象
 *
 * @param groupPosition 组位置
 * @param isExpanded 该组是展开状态还是伸缩状态，true=展开
 * @param convertView 重用已有的视图对象
 * @param parent 返回的视图对象始终依附于的视图组
 */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_parent_item,parent,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.parent_textview_id = convertView.findViewById(R.id.parent_textview_id);
            groupViewHolder.parent_image = convertView.findViewById(R.id.parent_image);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.parent_textview_id.setText(groups[groupPosition]);
        //如果是展开状态，
        if (isExpanded){
            groupViewHolder.parent_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.img_down));
        }else{
            groupViewHolder.parent_image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.img_right));
        }
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_chidren_item,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.chidren_item = (TextView)convertView.findViewById(R.id.chidren_item);
            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.chidren_item.setText(childsq.get(groupPosition).getAct()[childPosition]);
        final String old=childViewHolder.chidren_item.getText().toString();
        final ImageView btn1 =convertView.findViewById(R.id.btn_change_actname);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(old,getact);
            }
            private void showPopupWindow(final String old, final ActivityManage getact) {
                final View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_changeactname,null);
                mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
                //设置各个控件的点击响应
                Button btn_change_act=contentView.findViewById(R.id.btn_change_act);
                btn_change_act.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ed_newactname=contentView.findViewById(R.id.ed_newactname);
                        String newactname=ed_newactname.getText().toString();
                        getact.updataSmallActivity(old,newactname);
                        mPopWindow.dismiss();
                        groups=getact.getBigActivity(context);
                        for (int i =0;i<groups.length;i++){
                            if (groups.length>1){
                                child = getact.getSmallActivity(groups[i]);
                                childsq.add(new Act(groups[i], child));
                            }
                        }
                    }
                });
                //显示PopupWindow
                View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_boxmanagement,null);
                mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
            }
        });
        return convertView;

    }



    //指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    static class GroupViewHolder {
        TextView parent_textview_id;
        ImageView parent_image;
    }

    static class ChildViewHolder {
        TextView chidren_item;

    }


}
