package com.example.guanguannfc.view.friends;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.userManagement.Friend;

import java.util.ArrayList;
import java.util.List;

public class FrendFragment extends Fragment {

    private View view;
    private String userName;
    private ConstraintLayout cl_friend,cl_friendAct;
    private ImageView tv_friend,tv_friendAct;
    private List<FriendItem> friendItemsList=new ArrayList<FriendItem>();
    private List<FriendActItem> friendActItemList = new ArrayList<FriendActItem>();
    private ListView lv_friends,lv_friendAct;
    private FriendAdapter friendAdapter;
    private FriendActAdapter friendActAdapter;
    private String[][] act2;
    private String[][] friendList;
    private String[][] friendActList;

    private Friend friend;
    private String del_name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_frend, container, false);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            userName = bundle.getString("username");
//            isCount = bundle.getBoolean("isCount");
        }


//        Toast.makeText(getActivity(),"用户名"+userName,Toast.LENGTH_LONG).show();
        initView();
        checkClick();
        getFriends();
        if (friendList!=null){
            initFriends(friendList);
        }
        getFriendAct();
        if (friendActList != null) {
            initFriendAct(friendActList);
        }

        friendAdapter = new FriendAdapter(getActivity(),R.layout.item_friend,friendItemsList);
        lv_friends.setAdapter(friendAdapter);
        friendActAdapter = new FriendActAdapter(getActivity(),R.layout.item_friendact,friendActItemList);
        lv_friendAct.setAdapter(friendActAdapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        getFriends();
        if (friendList!=null){
            initFriends(friendList);
        }
        lv_friends.setAdapter(friendAdapter);

        getFriendAct();
        if (friendActList != null) {
            initFriendAct(friendActList);
        }
        lv_friendAct.setAdapter(friendActAdapter);

    }

    private void initView(){
        cl_friend=view.findViewById(R.id.cl_frinds);
        cl_friendAct=view.findViewById(R.id.cl_frindAct);
        tv_friend=view.findViewById(R.id.text_frinds);
        tv_friendAct=view.findViewById(R.id.text_frindAct);

        lv_friends = view.findViewById(R.id.lv_friendList);
        lv_friendAct=view.findViewById(R.id.lv_friendActList);

        friend = new Friend(getActivity());

    }

    private void initFriends(String[][] array){
        friendItemsList.clear();
//        for (int i = 0;i<20;i++){
//            FriendItem friendItem=new FriendItem("好朋友"+(i+1),R.drawable.img_head);
//            friendItemsList.add(friendItem);
//        }
        for (int i = 0;i<array.length;i++){
            FriendItem friendItem=new FriendItem(array[i],R.drawable.img_head);
            friendItemsList.add(friendItem);
        }
    }

    private void getFriends(){
       friendList = friend.friendlist(userName);
    }

    private void initFriendAct(String[][] array){
        friendActItemList.clear();
        for(int i=0;i<array.length;i++){
            FriendActItem friendActItem = new FriendActItem(array[i],R.drawable.img_head);
            friendActItemList.add(friendActItem);
        }

//        for (int i = 0;i<10;i++){
//            String[][] act1={{"好朋友"+(i+1),5+"","记录生活的点点滴滴","工作","2020-5-11","15时47分3秒","16时47分3秒","1时0分0秒","2020-05-21",Integer.toString(R.drawable.img_head)}};
//            FriendActItem friendActItem = new FriendActItem(act1[0]);
//            friendActItemList.add(friendActItem);
//        }


    }
    private void getFriendAct(){
        friendActList = friend.friendact(userName);
    }

    private void checkClick(){
        view.findViewById(R.id.text_frinds).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                cl_friend.setVisibility(View.VISIBLE);
                cl_friendAct.setVisibility(View.INVISIBLE);
                tv_friend.setImageDrawable(getResources().getDrawable((R.drawable.img_friend2)));
                tv_friendAct.setImageDrawable(getResources().getDrawable((R.drawable.img_nofriend1)));
            }
        });
        view.findViewById(R.id.text_frindAct).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                cl_friend.setVisibility(View.INVISIBLE);
                cl_friendAct.setVisibility(View.VISIBLE);
                tv_friendAct.setImageDrawable(getResources().getDrawable((R.drawable.img_nofriend1)));
                tv_friend.setImageDrawable(getResources().getDrawable((R.drawable.img_friend2)));
                getFriendAct();
                if (friendActList != null) {
                    initFriendAct(friendActList);
                }
                lv_friendAct.setAdapter(friendActAdapter);

            }
        });

        lv_friends.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                del_name = friendList[i][0];
                //获取AlertDialog对象
                dialog.setTitle("提示");//设置标题
                dialog.setMessage("是否删除好友？");//设置信息具体内容
                dialog.setCancelable(false);//设置是否可取消
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override//设置ok的事件
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在此处写入ok的逻辑
                        friend.delete(userName,del_name);
                        getFriends();
                        if (friendList!=null){
                            initFriends(friendList);
                        }
                        lv_friends.setAdapter(friendAdapter);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override//设置取消事件
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在此写入取消的事件
                    }
                });
                dialog.show();
                return false;
            }
        });
    }


}
