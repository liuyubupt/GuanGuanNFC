package com.example.guanguannfc.controller.userManagement;

import android.content.Context;
import android.icu.util.Measure;
import android.os.Message;

import com.example.guanguannfc.controller.timeManagement.GetTime;
import com.example.guanguannfc.model.Dao.DaoFriend;
import com.example.guanguannfc.model.Dao.DaoMoment;
import com.example.guanguannfc.model.Helper.HelperApplication;
import com.example.guanguannfc.model.Helper.HelperFriend;
import com.example.guanguannfc.model.Helper.HelperFriendAct;

import java.util.ArrayList;
import java.util.List;

public class Friend {
    DaoFriend DF;
    DaoMoment DM;
    Message message;
    GetTime gt = new GetTime();
    public Friend(Context context, Message message){
        this.DF = new DaoFriend(context);
        this.DM = new DaoMoment(context);
        this.message = message;
    }
    public void friendlist(final String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HelperFriend> list = new ArrayList<>();
                list = DF.query(username);
                if (list!=null){
                    int i = list.size();
                    String [][] arr = new String[i][2];
                    for (int a=0;a<i;a++){
                        arr [a][0]= list.get(a).getUser_name();
                        arr [a][1]= String.valueOf(list.get(a).getLevel());
                    }
                    message.getLoadMessage1(arr);
                }
                else {
                    String[][] arr1 = new String [0][0];
                    message.getLoadMessage1(arr1);
                }

            }
        }).start();

    }
    public void friendact(final String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HelperFriendAct> list =new ArrayList<>();
                list = DF.queryFriendAct(username);
                if (list!=null){
                    int n=list.size();
                    String arr1[][] = new String[n][9];
                    for (int i=0;i<n;i++){
                        arr1[i][0]=list.get(i).getFriend_name();
                        arr1[i][1]=String.valueOf(list.get(i).getLevel());
                        arr1[i][2]=gt.transString(list.get(i).getBegin_time())[0][0];
                        arr1[i][3]=gt.transString(list.get(i).getEnd_time())[0][0];
                        arr1[i][4]=gt.transString1(list.get(i).getLen_time());
                        arr1[i][5]=list.get(i).getActivity_type();
                        arr1[i][6]=list.get(i).getMoment_text();
                        arr1[i][7]=gt.transString(list.get(i).getShared_time())[0][0];
                        arr1[i][8]=gt.transString(list.get(i).getBegin_time())[0][1];
                    }
                    message.getLoadMessage2(arr1);
                }
                else {
                    String[][] arr = new String[0][0];
                    message.getLoadMessage2(arr);
                }
            }
        }).start();

    }

    public void insert(final String username ,final String friendname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean is = DF.insert(username,friendname);

                    message.getLoadMessage(is);

            }
        }).start();

    }

    public void delete(final String username ,final String friendname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean is = DF.delete(username,friendname);

                message.getLoadMessage0(is);

            }
        }).start();
    }

    public void updateapply(final String username ,final String friendname){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean is = DM.update(username,friendname);

                message.getLoadMessage5(is);

            }
        }).start();
    }
    public void friendapply(final String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HelperApplication> list = new ArrayList<>();
                list = DM.query(username);
                if (list!= null){
                    int n = list.size();
                    String[][] arr = new String[n][3];
                    for (int i=0;i<n;i++){
                        arr[i][0] = list.get(i).getUser_name();
                        arr[i][1] = list.get(i).getContent();
                        arr[i][2] = gt.timeStampToDate(list.get(i).getTime());
                    }
                    message.getLoadMessage3(arr);
                }
                else {
                    String[][] arr1 = new String[0][0];
                    message.getLoadMessage3(arr1);
                }
            }
        }).start();

    }

    public void apply(final String username, final String friendname,final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean is = DM.insert(username,friendname,content);

                message.getLoadMessage6(is);

            }
        }).start();
    }

    public interface Message{
        void getLoadMessage(boolean bl);
        void getLoadMessage0(boolean bl);
        void getLoadMessage1(String[][] arr);
        void getLoadMessage2(String[][] arr);
        void getLoadMessage3(String[][] arr);
        void getLoadMessage5(boolean bl);
        void getLoadMessage6(boolean bl);
    }

}
