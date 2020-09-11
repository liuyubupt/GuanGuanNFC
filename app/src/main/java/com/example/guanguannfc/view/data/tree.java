package com.example.guanguannfc.view.data;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.dataVisualization.Allactivity;

import com.example.guanguannfc.model.DataBaseTest.FakeData;

import com.example.guanguannfc.controller.timeManagement.GetTime;
import com.example.guanguannfc.model.Dao.DaoActSta;
import com.example.guanguannfc.model.DataBaseTest.FakeData;
import com.example.guanguannfc.model.Helper.HelperActivity;

import com.example.guanguannfc.model.Initialization;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class tree extends AppCompatActivity {

    private View view;

    private List<TreeView> tl = new ArrayList<>();
    private Calendar c=Calendar.getInstance();
    String [][] Data;
    private Allactivity allactivity = new Allactivity(this);

    protected void onCreate(@Nullable Bundle savedInstanceState){
        view = this.getWindow().getDecorView();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree);
        TreeAdapter adapter = new TreeAdapter(tree.this,R.layout.tree_view,tl);

        ListView listView = findViewById(R.id.treeview);
        listView.setAdapter(adapter);
        allactivity = new Allactivity(this);

        int s=c.get(Calendar.HOUR_OF_DAY);
//提取他的时钟值，int型
        if(s>= 12 && s<=18){
            view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.night));
        }
        else {
            view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.day));
        }
        String str1="早上好";

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TreeView treeView1 = tl.get(position);
                //Toast.makeText(Notebook.this, group.getGroup_name(), Toast.LENGTH_SHORT).show();
                int positon1 = position+1;
                String pid = String.valueOf(positon1);
                //Toast.makeText(tree.this, String.valueOf(s), Toast.LENGTH_SHORT).show();

            }
            });
        initView();

    }
    public void initView(){
        Initialization.initialization(this);
        FakeData fakeData = new FakeData(this);
        fakeData.insert();
        Data = allactivity.sortedactivity1("aaa","全部","最新活动在前");
        for (int a= 0; a<10; a++){
            //double f = Math.ceil(Integer.parseInt(Data[a][4])/50000);
            if(Data[a][0] .equals("学习")|| Data[a][0].equals("工作")){
                if(Data[a][7].equals("0")){
                    TreeView t = new TreeView(Data[a][0],Data[a][1],Data[a][2],String.valueOf(2*Integer.parseInt(Data[a][4])/(30*60*10)),"未被收取");
                    tl.add(t);
                }
                else {
                    TreeView t2 = new TreeView(Data[a][0],Data[a][1],Data[a][2],String.valueOf(2*Integer.parseInt(Data[a][4])/(30*60*10)),"已被收取");
                    tl.add(t2);
                }
            }

        }
    }



    }
