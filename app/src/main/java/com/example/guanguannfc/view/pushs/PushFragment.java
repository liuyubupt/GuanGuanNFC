package com.example.guanguannfc.view.pushs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.userManagement.UserInfo;

import java.util.ArrayList;
import java.util.List;


public class PushFragment extends Fragment {

    private View view;
    private String userName;
    private TextView tv_show;
    private List<PushItem> pushItemList = new ArrayList<PushItem>();
    private ListView lv_pushs;
    private String[][] pushs1;
    private PushAdapter pushAdapter;
    private UserInfo push;
    private Context context;
    private String [][] pushlist;
    private String [] pushl;
    private String [] pl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_push, container, false);

//        Toast.makeText(getActivity(),"用户名"+userName,Toast.LENGTH_LONG).show();
        lv_pushs= view.findViewById(R.id.lv_pushs);
        push=new UserInfo(getActivity());
        pushlist=push.Push();
        initPushs(pushs1);
        pushAdapter = new PushAdapter(getActivity(),R.layout.item_push,pushItemList);
        lv_pushs.setAdapter(pushAdapter);
        return view;
    }
    private void initPushs(String[][] array){
        pushItemList.clear();
        for(int i=0;i<pushlist.length;i++){
            pushl=pushlist[i];
            int[] intArray = new int[] {Integer.parseInt(Integer.toString(R.drawable.img_push1)),Integer.parseInt(Integer.toString(R.drawable.img_push2)),Integer.parseInt(Integer.toString(R.drawable.img_push3)),Integer.parseInt(Integer.toString(R.drawable.img_push4))};
            PushItem pushItem2 = new PushItem(pushlist[i],intArray[i]);
            pushItemList.add(pushItem2);
        }
    }
}
