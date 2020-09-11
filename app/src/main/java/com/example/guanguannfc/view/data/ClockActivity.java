package com.example.guanguannfc.view.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.Image;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.dataVisualization.Allactivity;
import com.example.guanguannfc.controller.nfcManagement.BaseNfcActivity;
import com.example.guanguannfc.controller.nfcManagement.NFCManage;
import com.example.guanguannfc.controller.timeManagement.GetTime;
import com.example.guanguannfc.controller.userManagement.UserInfo;
import com.example.guanguannfc.view.homepage.HomePageActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClockActivity  extends BaseNfcActivity {
    private ImageView btn_share,btn_stop;
    private TextView tv_start_time,tv_now_time,tv_distance,tv_duration,tv_event_type,tv_event_name;
    private String startTime,nowTime,duration,isFirst,userName,actName,actType;
    private Long lstartTime,date,endTime;
    private Boolean iscount;
    private GetTime getTime;
    private String TimeInfo;
    private String[] infos;
    private Allactivity allactivity;
    private ImageView img_back;

//    分享
    private ShareDialog shareDialog;
    private String text_content;
    //    计时
    LocalBroadcastManager lbm;
    ClockService.MyBinder binder;
    IntentFilter intentFilter;
    private int actId;
    private String countState;

//NFC
    private NFCManage nfcManage;
    private String mTagText;
    private String[] allActs;

//    更新个人信息
    private UserInfo userInfo;





    ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);


//      获取传入数值
        Intent mainIntent=getIntent();
        userName=mainIntent.getStringExtra("username");
        countState=mainIntent.getStringExtra("countState");
        initDialog();

//      获取service数值
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                binder = (ClockService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        initView();

        Intent intent = new Intent(this,ClockService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        lbm = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("clock");
        lbm.registerReceiver(receiver,intentFilter);


//        Toast.makeText(this,userName,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (countState != null) {
            if (countState.equals("sameID")) {
//                Toast.makeText(ClockActivity.this, "刷的是同一张贴纸", Toast.LENGTH_SHORT).show();
//                stopCount();
            } else if (countState.equals("difID")) {
//                Toast.makeText(ClockActivity.this, "刷了不同贴纸", Toast.LENGTH_SHORT).show();
//                startNewCount(actId, actName);
//                stopCount();
            }
        }
    }

    private void initView(){
        btn_share=findViewById(R.id.btn_share);
        btn_stop=findViewById(R.id.btn_stop);
        tv_start_time=findViewById(R.id.tv_start_time);
        tv_now_time=findViewById(R.id.tv_now_time);
        tv_duration=findViewById(R.id.tv_duration);
        tv_event_type=findViewById(R.id.tv_event_type);
        tv_event_name=findViewById(R.id.tv_event_name);
        img_back=findViewById(R.id.btn_back);

        allactivity = new Allactivity(this);

        actId = HomePageActivity.actId;
        actName = HomePageActivity.actName;
        actType=HomePageActivity.actType;
        tv_event_type.setText(actType);
        tv_event_name.setText(actName);
        date=1111111l;

        iscount=true;
        nfcManage=new NFCManage(userName,ClockActivity.this);
        String[] arry =  allactivity.allacttype(userName);
        allActs = new String[arry.length-1];
        for (int t=1;t<arry.length;t++){
            allActs[t-1]=arry[t];
        }

        userInfo=new UserInfo(this);
    }

    private void initDialog(){
        shareDialog=new ShareDialog(ClockActivity.this);
        shareDialog.setCancel(new ShareDialog.IOnCancelListener() {
            @Override
            public void onCancel(ShareDialog dialog) {

            }

        });
        shareDialog.setConfirm(new ShareDialog.IOnConfirmListener() {
            @Override
            public void onConfirm(ShareDialog dialog) {
                text_content=shareDialog.getEditText().getText().toString();
                boolean isShared = userInfo.updateact(userName,lstartTime,text_content);
                if (isShared){
                    Toast.makeText(ClockActivity.this,"分享成功",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ClockActivity.this,"分享失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void click(View v){
        int id=v.getId();
        switch (id){
            case R.id.btn_stop:
                stopCount();
                break;
            case R.id.btn_share:
                if (!iscount){
                    shareDialog.show();
                }
                else {
                    Toast.makeText(ClockActivity.this,"请结束计时后再分享",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_back:
                onBackPressed();
                break;

        }
    }

    public void onBackPressed() {
        if(iscount){
            Intent intent = new Intent();
            intent.putExtra("result","计时继续");
            this.setResult(1,intent);
            this.finish();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("result","计时结束");
            this.setResult(1,intent);
            this.finish();
        }
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            lstartTime = intent.getLongExtra("lstarttime",0);
            startTime = intent.getStringExtra("starttime");
            nowTime = intent.getStringExtra("nowtime");
            duration = getTime.countTime(lstartTime);
            tv_start_time.setText(startTime);
            tv_now_time.setText(nowTime);
            tv_duration.setText(duration);
        }
    };

    public String[] read(){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                content.append(line);
                content.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        String str = content.toString();
        String[] arr = str.split("\n");
        return arr;
    }



    //    检测NFC
    @SuppressLint("MissingSuperCall")
    public void onNewIntent(Intent intent){
        Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        mTagText = NFCManage.readNfcTag(intent);
        String isNFCExist = NFCManage.isNFCExist(mTagText);
        if (isNFCExist==null){
                Toast.makeText(ClockActivity.this,"空标签",Toast.LENGTH_SHORT).show();
        }
        else if (isNFCExist.equals("Act")){
            String[] actInfo = nfcManage.nfcForActivity(mTagText);
            if(actInfo[0]!=null){
                int newActID=Integer.parseInt(actInfo[1]);
                String newActType=actInfo[2];
                String newActName=actInfo[0];
//            如果正在计时
                if (iscount){
//                如果刷的是同一张贴纸
                    if (actId==newActID){
                        stopCount();
                    }
//                如果刷了不同贴纸
                    else {
                        stopCount();
                        startNewCount(newActID,newActName,newActType);
                    }
                }

                else {
                    startNewCount(newActID,newActName,newActType);
                }
            }
            else {
                Toast.makeText(this,"活动不存在",Toast.LENGTH_SHORT).show();
            }
        }
        else if (isNFCExist.equals("Box")){
            Toast.makeText(ClockActivity.this,"请退回主页扫描盒子贴纸",Toast.LENGTH_SHORT).show();
        }
        else if (isNFCExist.equals("Something is exist!")){
            nfcManage.setNFCNll(detectedTag);
            Toast.makeText(ClockActivity.this,"标签不为空，请擦除内容后再写入",Toast.LENGTH_SHORT).show();
        }
    }

    private void stopCount(){
        iscount = false;
        HomePageActivity.isCount=false;
        binder.stopTimer();
        endTime=getTime.getStartTime();
        Boolean isSuccess= allactivity.insertdata(userName,actName, lstartTime,endTime);

        if (isSuccess) {
//            Toast.makeText(this,"计时结束",Toast.LENGTH_SHORT).show();
            String[][] today_date = getTime.transString(endTime);
            Boolean isActive = userInfo.updateActDay(userName,today_date[0][0]);
            if (isActive){
                Toast.makeText(this,"打卡成功",Toast.LENGTH_SHORT).show();
            }
            else {
//                Toast.makeText(this,"今日已打卡",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"数据记录失败",Toast.LENGTH_SHORT).show();
        }

    }

    private void startNewCount(int newID,String newname,String newtype){

        iscount = true;
        HomePageActivity.isCount=true;
        actId = newID;
        actName = newname;
        actType=newtype;
        HomePageActivity.actId = newID;
        HomePageActivity.actName = newname;

        tv_event_type.setText(actType);
        tv_event_name.setText(actName);
        binder.starTimer();
        Toast.makeText(this,"开始计时",Toast.LENGTH_SHORT).show();
//      获取service数值
        lbm.registerReceiver(receiver,intentFilter);
    }
}
