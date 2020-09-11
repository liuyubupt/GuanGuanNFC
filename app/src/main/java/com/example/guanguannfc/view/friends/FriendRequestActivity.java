package com.example.guanguannfc.view.friends;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.userManagement.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends AppCompatActivity implements Friend.Message{
    private String userName;

    private List<FriendRequestItem> friendRequestItemsList = new ArrayList<FriendRequestItem>();
    private FriendRequestAdapter friendRequestAdapter ;
    private ListView lv_friendRequest;

    private Friend friend;
    private String[][] arr_request;
    private String friendName;
    private ImageView img_back;

    private boolean isRefuse,isAgree;
    private int mark=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        //      获取传入数值
        Intent mainIntent=getIntent();
        userName=mainIntent.getStringExtra("username");

        lv_friendRequest = findViewById(R.id.lv_friendRequest);
        img_back=findViewById(R.id.btn_back);


        getRequestList();
        initList();

        checkClick();
    }


    private void initList(){
        friendRequestItemsList.clear();
//        for (int i = 0;i<6;i++){
//            String[] info = {"用户"+i,"你好，我是用户"+i};
//            FriendRequestItem friendRequestItem = new FriendRequestItem(info);
//            friendRequestItemsList.add(friendRequestItem);
//        }

//        if (arr_request!=null){
//            for (int i=0;i<arr_request.length;i++){
//                FriendRequestItem friendRequestItem = new FriendRequestItem(arr_request[i]);
//                friendRequestItemsList.add(friendRequestItem);
//            }
            friendRequestAdapter = new FriendRequestAdapter(FriendRequestActivity.this,R.layout.item_friendrequest,friendRequestItemsList,this);
//            lv_friendRequest.setAdapter(friendRequestAdapter);
        }

//        else {
//            Toast.makeText(FriendRequestActivity.this,"无好友请求",Toast.LENGTH_LONG).show();
//        }

//    }

    private void getRequestList() {
        friend = new Friend(this,this);

        friend.friendapply(userName);
//        if (arr_request!=null){
//            Toast.makeText(FriendRequestActivity.this,arr_request[0][0],Toast.LENGTH_LONG).show();
//        }
    }

    private void checkClick(){
        friendRequestAdapter.setOnRefuseListener(new FriendRequestAdapter.onRefuseListener() {
            @Override
            public void onRefuseClick(int i) {
                final String friendName = arr_request[i][0];

               friend.updateapply(userName,friendName);

            }
        });

        friendRequestAdapter.setOnAgreeListener(new FriendRequestAdapter.onAgreeListener() {
            @Override
            public void onAgreeClick(int i) {
                friendName = arr_request[i][0];

                friend.insert(userName,friendName);
//                if (isAgree) {
//                    Toast.makeText(FriendRequestActivity.this,"成功添加"+friendName+"为好友",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(FriendRequestActivity.this,"出错了，请重试",Toast.LENGTH_LONG).show();
//                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public void getLoadMessage(final boolean bl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isAgree =bl;

                if (isAgree) {
                    Toast.makeText(FriendRequestActivity.this,"成功添加"+friendName+"为好友",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(FriendRequestActivity.this,"出错了，请重试",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void getLoadMessage0(boolean bl) {

    }

    @Override
    public void getLoadMessage1(final String[][] arr) {



    }

    @Override
    public void getLoadMessage2(String[][] arr) {

    }

    @Override
    public void getLoadMessage3(final String[][] arr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arr_request =arr;
                if (arr_request!=null){
                    for (int i=0;i<arr_request.length;i++){
                        FriendRequestItem friendRequestItem = new FriendRequestItem(arr_request[i]);
                        friendRequestItemsList.add(friendRequestItem);
                    }
                    lv_friendRequest.setAdapter(friendRequestAdapter);
                }
            }
        });
    }

    @Override
    public void getLoadMessage5(final boolean bl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    isRefuse =bl;
                    if (isRefuse) {
                        Toast.makeText(FriendRequestActivity.this,"已拒绝"+friendName+"的好友请求",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(FriendRequestActivity.this,"出错了，请重试",Toast.LENGTH_LONG).show();
                    }


            }
        });
    }

    @Override
    public void getLoadMessage6(boolean bl) {

    }
}
