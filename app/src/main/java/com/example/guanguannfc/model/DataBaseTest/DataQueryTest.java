package com.example.guanguannfc.model.DataBaseTest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guanguannfc.R;
import com.example.guanguannfc.model.Dao.DaoActSta;
import com.example.guanguannfc.model.Dao.DaoActivity;
import com.example.guanguannfc.model.Dao.DaoActivityType;
import com.example.guanguannfc.model.Dao.DaoBox;
import com.example.guanguannfc.model.Dao.DaoFriend;
import com.example.guanguannfc.model.Dao.DaoMoment;
import com.example.guanguannfc.model.GuanContract;
import com.example.guanguannfc.model.Helper.HelperApplication;
import com.example.guanguannfc.model.Helper.HelperBox;
import com.example.guanguannfc.model.Helper.HelperBoxContent;
import com.example.guanguannfc.model.Helper.HelperFriend;
import com.example.guanguannfc.model.Helper.HelperFriendAct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataQueryTest extends AppCompatActivity implements View.OnClickListener {
    Button mButton2,mButton3,mButton4,mButton5,mButton6,mButton7,mButton8,mButton9,mButton10,mButton11;
    EditText from,to;
    DaoActSta mActSta = new DaoActSta(this);
    DaoActivityType mDaoActivityType = new DaoActivityType(this);
    DaoBox mDaoBox = new DaoBox(this);
    DaoMoment mDaoMoment = new DaoMoment(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liuyu_dataquerytest);
        initView();
        initEvent();
    }

    private void initView() {
        mButton2 = findViewById(R.id.all_activity);
        mButton3 = findViewById(R.id.all_type);
        mButton4 = findViewById(R.id.all_typeAndActivity);
        mButton5 = findViewById(R.id.all_box);
        mButton6 = findViewById(R.id.all_boxAndContent);
        mButton7 = findViewById(R.id.insertTest);
        mButton8 = findViewById(R.id.friend_act);
        mButton9 = findViewById(R.id.application);
        mButton10 = findViewById(R.id.allFriends);
        mButton11 = findViewById(R.id.switchMoment);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        FakeData fakeData = new FakeData(this);
        fakeData.insert();

    }

    private void initEvent() {
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton10.setOnClickListener(this);
        mButton11.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_activity:
                List list = mActSta.queryByLengthDesc("aaa","工作");
                for(int i=0;i<list.size();i++){

                    Log.v(i+"",list.get(i).toString());
                }
                break;
            case R.id.all_type:
                List list1 = mActSta.queryActType("aaa",0l,1596686207110l);
                for(int i=0;i<list1.size();i++){

                    Log.v(i+"",list1.get(i).toString());
                }
                break;
            case R.id.all_typeAndActivity:
                HashMap<String, ArrayList<String>> hashMap = mDaoActivityType.queryTypeAndActivity("aaa");
                Iterator iterator =hashMap.entrySet().iterator();
               while(iterator.hasNext()){
                   Log.v("TAG",iterator.next().toString());
               }
                break;
            case R.id.all_box:
                ArrayList<HelperBox> arrayList = mDaoBox.queryAllBox("aaa");
                for(HelperBox helperBox:arrayList){
                    Log.v("TAG",helperBox.toString());
                }
                break;
            case R.id.all_boxAndContent:
                HashMap<String,ArrayList<HelperBoxContent>> hashMap1 = mDaoBox.queryBoxAndContext("aaa");
                Set<Map.Entry<String,ArrayList<HelperBoxContent>>> set = hashMap1.entrySet();
                for(Map.Entry<String,ArrayList<HelperBoxContent>> entry:set){
                    Log.v("TAG",entry.toString());
                }
                break;
            case R.id.insertTest:
                DaoActSta daoActSta = new DaoActSta(this);
                try {
                    daoActSta.insert("aaa","做作业",100000000000l,100000000000000000l);
                    Log.v("tag","插入成功");
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.friend_act:
                DaoFriend daoFriend = new DaoFriend(this);
                List<HelperFriendAct> helperFriendActs = daoFriend.queryFriendAct("ddd");
                for (HelperFriendAct helperFriendAct:helperFriendActs){
                    Log.v("",helperFriendAct.toString());
                }
                break;
            case R.id.application:
                DaoMoment daoMoment = new DaoMoment(this);
                List<HelperApplication> helperApplications = daoMoment.query("bbb");
                for(HelperApplication helperApplication:helperApplications){
                    Log.v("",helperApplication.toString());
                }
                break;
            case R.id.allFriends:
                DaoFriend daoFriend1 = new DaoFriend(this);
                List<HelperFriend> helperFriends = daoFriend1.query("aaa");
                for (HelperFriend helperFriend:helperFriends){
                    Log.v("",helperFriend.toString());
                }
            case R.id.switchMoment:
                String from1 = from.getText().toString();
                String to1 = to.getText().toString();
                mDaoMoment.update(from1,to1);
                break;

        }
    }
}
