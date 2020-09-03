package com.example.guanguannfc.view.management;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.dataManagement.ActivityManage;
import com.example.guanguannfc.controller.dataManagement.ThingManage;
import com.example.guanguannfc.controller.nfcManagement.NFCManage;
import com.example.guanguannfc.view.homepage.HomePageActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageFragment extends Fragment {
    public static boolean actisnfc=false;
    public static boolean boxisnfc=false;
    public PopupWindow mPopWindow;
    private String username;
    private ThingManage boxget;
    private String[][] box;
    private ConstraintLayout lay_box,lay_time,lay_search;

    private ExpandableListView expand_list_id;
    private Context context;
    private ActivityManage getact;
    private String[] groups;
    private ListView lv_search;
    private String name;
    private int num;
    private GridView gridView1;
    TextView tv_box,tv_time;
    // 获取颜色资源文件
    int colorgray,colorRedDark;
    //Model：定义的数据


    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    private String[][] childs;
    private String[] child;
    private String[] goodsname;
    private String[] goodsnum;
    private String boxName;
    private String [] boxnames;
    List<Act> childsq = new ArrayList<Act>();
    private String boxname1;
    private String name2;
    private String num2;
    private String boxlocation;
    private String [] searchthings;

//    NFC获取的值
    private String getBoxName="" ;
    private int boxIndex;
    private int z;

    private View view;
    Context ctx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_boxmanagement, container, false);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            username = bundle.getString("username");
            getBoxName = bundle.getString("getboxname");
        }
        tv_box=view.findViewById(R.id.tv_boxmanage);
        tv_time=view.findViewById(R.id.tv_timemanagement);
//        Toast.makeText(getActivity(),getBoxName,Toast.LENGTH_SHORT).show();
        ctx = getActivity();
        checkClick();
//        Toast.makeText(getActivity(),"用户名"+username,Toast.LENGTH_LONG).show();
        boxget =new ThingManage(username,ctx);
        box=boxget.boxAndPosition();
        if(!getBoxName.equals("")){
            boxnames=box[0];
            for (int i = 0;i<boxnames.length;i++){
                if (boxnames[i].equals(getBoxName)){
                    boxIndex = i;
                    break;
                }
            }
            showbox(boxIndex);

        }

        getact=new ActivityManage(username,ctx);
        initView();


        gridView1= view.findViewById(R.id.box);
        if (box != null){
            boxnames= box[0];
            GridviewAdapter gridviewAdapter=new GridviewAdapter(getActivity(),boxnames);
            gridView1.setAdapter(gridviewAdapter);
            gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showbox(position);
                }
            });
        }
        ImageView s=view.findViewById(R.id.s);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box=null;
                box=boxget.boxAndPosition();
                if (box != null){
                    boxnames= box[0];
                    GridviewAdapter gridviewAdapter2=new GridviewAdapter(getActivity(),boxnames);
                    gridView1.setAdapter(gridviewAdapter2);
                    gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            showbox(position);
                        }
                    });
                }
            }
        });
        lay_box=view.findViewById(R.id.layout_boxmanagement);
//      lay_box.setVisibility(View.VISIBLE);
        lay_time=view.findViewById(R.id.layout_timemanagement);
        lay_time.setVisibility(View.INVISIBLE);
        lay_search=view.findViewById(R.id.layout_search);
        lay_search.setVisibility(View.INVISIBLE);
        final ListView lv_search = (ListView) view.findViewById(R.id.lv_search);

        SearchView sv=view.findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
            public boolean onQueryTextSubmit(final String query) {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(getActivity(), "请输入查找内容！", Toast.LENGTH_SHORT).show();
                } else {
                    searchthings=boxget.searchThing(query);
                    SearchAdapter SearchAdapter=new SearchAdapter(getActivity(),searchthings,boxnames);
                    lv_search.setAdapter(SearchAdapter);
                    lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int ss=1;
                            for(int i=0;i<boxnames.length;i++){
                                if(boxnames[i].equals(searchthings[position])){
                                    ss=i;
                                }else {
                                }
                            }
                            showbox(ss);

                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        ImageView addbox = view.findViewById(R.id.iv_addbox);
        addbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showaddbox();
            }
        });
        return view;




    }
    private void showchangebox() {
        //设置contentView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_changebox, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
    private void showaddgood_box(){
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_goods_box, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        final EditText ed_name=contentView.findViewById(R.id.ed_name);
        final EditText ed_num=contentView.findViewById(R.id.ed_num);
        Button btn_add_goods=contentView.findViewById(R.id.btn_add_goods);
        btn_add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name2= ed_name.getText().toString();
                num2 = ed_num.getText().toString();
                mPopWindow.dismiss();
                na.add(name2);
                nu.add(num2);
                showaddbox();
            }
        });

        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
    private void showaddbox() {
        //设置contentView
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_addbox, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        final EditText ed_box_name=contentView.findViewById(R.id.ed_box_name);
        final EditText ed_box_position=contentView.findViewById(R.id.ed_box_position);
        Button btn2 =contentView.findViewById(R.id.btn_addgoods2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                showaddgood_box();
                boxname1=ed_box_name.getText().toString();
                boxlocation=ed_box_position.getText().toString();
            }
        });
        ed_box_name.setText(boxname1);
        ed_box_position.setText(boxlocation);
        ListView lv_goods=contentView.findViewById(R.id.lv_goods);
        SimpleAdapter ms=new SimpleAdapter(getActivity(),getData2(),
                R.layout.activity_lv_goods,
                new String[]{"tv_goods_name","tv_goods_shuliang"},
                new int[]{R.id.tv_goods_name,R.id.tv_goods_shuliang});
        lv_goods.setAdapter(ms);
        Button addbox=contentView.findViewById(R.id.add_box);
        addbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String box1="1";
                boxisnfc=true;
                mPopWindow.dismiss();
                box(box1,boxname1,na,nu,boxlocation);
                boxnfc();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
    List<String> na=new ArrayList<>();
    List<String> nu=new ArrayList<>();
    private void boxnfc(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_nfcbox, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        // TODO: 2016/5/17 设置背景颜色
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        mPopWindow.setOutsideTouchable(false);
        // TODO: 2016/5/17 设置可以获取焦点
        mPopWindow.setFocusable(false);
        Button nfcbox=contentView.findViewById(R.id.btn_box_nfc);
        nfcbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                String box1="0";
                String boxname=null;
                boxisnfc=false;
                box(box1,boxname,na,nu,boxlocation);
            }
        });
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
    private void showbox(final int num) {
        //设置contentView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_displaygoods, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        ListView listView =contentView.findViewById(R.id.listview);
        final TextView tv_boxname=contentView.findViewById(R.id.tv_boxname);
        tv_boxname.setText(box[0][num]);
        final TextView tv_position=contentView.findViewById(R.id.tv_position);
        tv_position.setText(box[1][num]);
        final String boxname=tv_boxname.getText().toString();
        String [][] thing=boxget.thingAndNumberInBox(box[0][num]);
        if(thing==null){
            goodsname=new String[0];
            goodsnum=new String[0];
        }
        else {goodsname=thing[0];
              goodsnum=thing[1];}
        MsimpleAdapter mSimpleAdapter = new MsimpleAdapter(getActivity(),goodsname,goodsnum, boxname,boxget,mPopWindow);
        listView.setAdapter(mSimpleAdapter);
        ImageView btn2 =contentView.findViewById(R.id.btb_addgoods);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("boxname",boxname);
                showaddgoods(boxname,num,mPopWindow);
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }


    private void showaddgoods(final String boxname, final int num2, final PopupWindow sPopWindow){
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_addgoods, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        Button btn_add_goods=contentView.findViewById(R.id.btn_add_goods);
        btn_add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_name=contentView.findViewById(R.id.ed_name);
                EditText ed_num=contentView.findViewById(R.id.ed_num);
                name=ed_name.getText().toString();
                String i=ed_num.getText().toString();
                if(name.equals("")){
                    Toast.makeText(getActivity(), "物品名称不能为空", Toast.LENGTH_SHORT ).show();
                }
                else {
                    if(i.equals("")){
                        Toast.makeText(getActivity(), "物品数量不能为空", Toast.LENGTH_SHORT ).show();
                    }
                    else {
                        boxName=boxname;
                        num=Integer.valueOf(i).intValue();
                        String [][] thing=boxget.thingAndNumberInBox(box[0][num2]);
                        if(thing==null){
                            boxget.addThings(boxName,name,num);
                            mPopWindow.dismiss();
                            sPopWindow.dismiss();
                        }
                        else {
                            String [] goodsname=thing[0];
                            boolean isContains = Arrays.asList(goodsname).contains(name);
                            if(isContains){
                                shownoname();
                            }
                            else {
                                boxget.addThings(boxName,name,num);
                                mPopWindow.dismiss();
                                sPopWindow.dismiss();
                            }
                        }
                    }
                }
            }
        });
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
    private void shownoname(){
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_noname, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_boxmanagement, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    private void showaddact() {
        //设置contentView
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_addact, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置各个控件的点击响应
        Button btn_addact=contentView.findViewById(R.id.btn_addact);
        btn_addact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText acttype=contentView.findViewById(R.id.ed_acttype);
                EditText actname=contentView.findViewById(R.id.ed_actname);
                String actttype =acttype.getText().toString();
                String acttname=actname.getText().toString();
                String ms1= "1";
                actisnfc=true;
                act(ms1,actttype,acttname);
                shownfcact(actttype,acttname);
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_changeact, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
    private void shownfcact(String acttype,String actname){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_nfcact, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        Button btn_act_nfc=contentView.findViewById(R.id.btn_act_nfc);
        btn_act_nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                String ms1="0";
                String acttype=null;
                String actname=null;
                actisnfc=false;
                act(ms1,acttype,actname);
            }
        });
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_changeact, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    private void initView() {
        colorgray = getResources().getColor(R.color.colorgray);
        colorRedDark = getResources().getColor(R.color.colorRedDark);
        groups=getact.getBigActivity(getActivity());
        for (int i =0;i<groups.length;i++){
            if (groups.length>1){
                child = getact.getSmallActivity(groups[i]);
                childsq.add(new Act(groups[i], child));
            }
        }
        expand_list_id=view.findViewById(R.id.expand_list_id);
        ExpandableListviewAdapter adapter=new ExpandableListviewAdapter(getActivity(),groups,childsq,getact);
        expand_list_id.setAdapter(adapter);
        ImageView bt_shauxin=view.findViewById(R.id.btn_shuaxin);
        bt_shauxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childsq.clear();
                groups=getact.getBigActivity(getActivity());
                for (int i =0;i<groups.length;i++){
                    if (groups.length>1){
                        child = getact.getSmallActivity(groups[i]);
                        childsq.add(new Act(groups[i], child));
                    }
                }
                ExpandableListviewAdapter adapter2=new ExpandableListviewAdapter(getActivity(),groups,childsq,getact);
                expand_list_id.setAdapter(adapter2);
            }
        });
        expand_list_id.expandGroup(0);
        //关闭数组某个数组，可以通过该属性来实现全部展开和只展开一个列表功能
        //expand_list_id.collapseGroup(0);
        expand_list_id.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                showToastShort(groups[groupPosition]);
                return false;
            }
        });
        //子视图的点击事件
        expand_list_id.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                return true;
            }
        });


        //用于当组项折叠时的通知。
        expand_list_id.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                showToastShort("折叠了数据___"+groups[groupPosition]);
            }
        });
        //
        //用于当组项折叠时的通知。
        expand_list_id.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                showToastShort("展开了数据___"+groups[groupPosition]);
            }
        });








    }

    private void showToastShort(String s) {

    }


    public void checkClick(){
        view.findViewById(R.id.tv_boxmanage).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                lay_box.setVisibility(View.VISIBLE);
                lay_time.setVisibility(View.INVISIBLE);
                lay_search.setVisibility(View.INVISIBLE);
                tv_box.setTextColor(colorRedDark);
                tv_time.setTextColor(colorgray);
            }
        });
        view.findViewById(R.id.tv_timemanagement).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                lay_box.setVisibility(View.INVISIBLE);
                lay_time.setVisibility(View.VISIBLE);
                lay_search.setVisibility(View.INVISIBLE);
                tv_box.setTextColor(colorgray);
                tv_time.setTextColor(colorRedDark);
            }
        });
        view.findViewById(R.id.sv_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_box.setVisibility(View.INVISIBLE);
                lay_time.setVisibility(View.INVISIBLE);
                lay_search.setVisibility(View.VISIBLE);
            }
        });

    }

    private List<Map<String,Object>> getData20() {
        List<Map<String, Object>> list = new ArrayList<Map<String ,Object>>();
        String [] name=new String[]{"化妆品","球类","笔","书"};
        String [] num = new String[]{"2","5","4","7"};
        for (int i=0;i<name.length;i++)
        {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("tvName",name[i]);
            map.put("tv_shuliang",num[i]);
            list.add(map);
        }
        return list;
    }

    private List<Map<String,Object>> getData2() {
        List<Map<String, Object>> list = new ArrayList<Map<String ,Object>>();
        for (int i=0;i<na.size();i++)
        {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("tv_goods_name",na.get(i));
            map.put("tv_goods_shuliang",nu.get(i));
            list.add(map);
        }
        return list;
    }

    public static boolean act(String act1,String acttype,String actname){
        if(act1 == "1"){
            return true;
        }
        else if(act1=="0"){
            return false;
        }
        return false;
    }
    public static boolean box(String box1,String boxname,List<String> na,List<String>nu,String boxlocation){
        if(box1 == "1"){
            return true;
        }
        else if(box1=="0"){
            return false;
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        onCreate(null);
    }
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
    }

    public void showScanBox(String boxname){
        int boxnum=0;
        for (int i=0;i<box[0].length;i++){
            if (boxname.equals(box[0][i])){
                boxnum=i;
                break;
            }
        }
        showbox(boxnum);


    }

}
