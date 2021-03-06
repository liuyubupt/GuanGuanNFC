package com.example.guanguannfc.view.management;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.dataManagement.ThingManage;

public class MsimpleAdapter extends BaseAdapter {
    private PopupWindow mPopWindow;
    private PopupWindow lpopwindow;
    private Context context;
    private String [] goodsname;
    private String [] goodsnum;
    private String thingName;
    private String boxName;
    private ThingManage boxget;
    private int num;

    public MsimpleAdapter(Context context,String[] goodsname,String[] goodsnum ,String boxName,ThingManage boxget,PopupWindow mPopWindow){
        this.context=context;
        this.goodsname=goodsname;
        this.goodsnum=goodsnum;
        this.boxName=boxName;
        this.boxget=boxget;
        this.mPopWindow=mPopWindow;
    }
    @Override
    public int getCount() {
        return goodsname.length;
    }

    @Override
    public Object getItem(int i) {
        return goodsname[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder ViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listview3,parent,false);
            ViewHolder = new ViewHolder();
            ViewHolder.tv_goods_name = (TextView)convertView.findViewById(R.id.tv_goods_name);
            ViewHolder.btn_change_num=convertView.findViewById(R.id.btn_change_num);
            ViewHolder.tv_goods_shuliang =convertView.findViewById(R.id.tv_goods_shuliang);
            ViewHolder.btn_delet=convertView.findViewById(R.id.btn_delet);
            convertView.setTag(ViewHolder);

        }else {
            ViewHolder = (ViewHolder) convertView.getTag();
        }
        ViewHolder.tv_goods_name.setText(goodsname[position]);
        ViewHolder.tv_goods_shuliang.setText(goodsnum[position]);
        ViewHolder.btn_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thingName=ViewHolder.tv_goods_name.getText().toString();
                boxget.deleteThings(boxName,thingName);
                mPopWindow.dismiss();
            }
        });
        ViewHolder.btn_change_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thingName=ViewHolder.tv_goods_name.getText().toString();
                showPopupWindow14(boxName,thingName);
            }
            private void showPopupWindow14(final String boxName, final String thingname) {
                final View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listview4,null);
                lpopwindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
                //设置各个控件的点击响应
                Button btn_change_goods=contentView.findViewById(R.id.btn_change_goods);
                btn_change_goods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ed_num=contentView.findViewById(R.id.ed_num);
                        String nums=ed_num.getText().toString();
                        num=Integer.valueOf(nums).intValue();
                        boxget.updataThings(boxName,thingname,num);
                        lpopwindow.dismiss();
                        mPopWindow.dismiss();
                    }
                });
                //显示PopupWindow
                View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_boxmanagement,null);
                lpopwindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
            }
        });
        return convertView;
    }
    static class ViewHolder {
        TextView tv_goods_name;
        TextView tv_goods_shuliang;
        ImageView btn_change_num;
        ImageView btn_delet;
        Button btn_change_goods;
    }

}
