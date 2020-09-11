package com.example.guanguannfc.view.loginAndLogon;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.userManagement.UserInfo;
import com.example.guanguannfc.model.Helper.HelperUserInfo;
import com.example.guanguannfc.view.TestActivity;
import com.example.guanguannfc.view.homepage.HomePageActivity;
import com.example.guanguannfc.controller.userManagement.Login;
import com.example.guanguannfc.view.lead.LeadActivity;


public class SigninFragment extends Fragment implements Login.Message {

    private EditText edit_username,edit_psw;
    private String username,psw;
    private Login login ;
    private UserInfo leadupdate;
    private Boolean lead;
    private HelperUserInfo leads;
    private int id;
    Button button_signin;
    private ImageView loadImg;
    private RelativeLayout mRelativeLayout;
    private Animation animation;
    Context ctx;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    private void initSoundPool() throws Exception{//初始化声音池
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setMaxStreams(10);
        //spb.setAudioAttributes(null);    //转换音频格式
        mSoundPool = spb.build();
        //加载声音文件，并且设置为1号声音放入hm中
        hm.put(1, mSoundPool.load(getActivity(), R.raw.button_guan, 1));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        sprfMain= PreferenceManager.getDefaultSharedPreferences(getActivity());
        editorMain=sprfMain.edit();
        ctx=getActivity();
        edit_username=view.findViewById(R.id.edit_username);
        edit_psw=view.findViewById(R.id.edit_psw);
        button_signin = (Button) view.findViewById(R.id.button_signin_confirm);
        loadImg = view.findViewById(R.id.load_img);
        mRelativeLayout = view.findViewById(R.id.rela_img);

        username = sprfMain.getString("userName","");
        psw = sprfMain.getString("psw","");
        edit_username.setText(username);
        edit_psw.setText(psw);
        login=new Login(ctx,this);
        leadupdate = new UserInfo(ctx);

        leads =leadupdate.getlead(username);
        id=leads.getIs_studied();
        try {
            initSoundPool();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        View rootView = inflater.inflate(R.layout.signin, null); // 先解析file.xml布局，得到一个view
        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"登录",Toast.LENGTH_LONG).show();
                username = edit_username.getText().toString();
                psw = edit_psw.getText().toString();
//判断是否第一次登陆
                leads =leadupdate.getlead(username);
                id=leads.getIs_studied();
//动画效果
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
                animation.setDuration(500);
                animation.setRepeatCount(8);//动画的反复次数
                animation.setFillAfter(true);//设置为true，动画转化结束后被应用
                loadImg.startAnimation(animation);//開始动画
                mRelativeLayout.setVisibility(View.VISIBLE);

                if (username.equals("")){
                    Toast.makeText(ctx,"请输入账号",Toast.LENGTH_LONG).show();
                }
                else if(psw.equals("")){
                    Toast.makeText(ctx,"请输入密码",Toast.LENGTH_LONG).show();
                }
                else {
                    if(psw.length()<6 | psw.length()>20){
                    Toast.makeText(ctx,"密码长度为6-20位",Toast.LENGTH_LONG).show();
                }
                    else {
                        login.login1(username,psw);

//                        if (){
//
//                        }

//                        else{

//                            if(id == 0) {
//                                Intent intent = new Intent(getActivity(), LeadActivity.class);
//                                editorMain.putBoolean("main", true);
//                                editorMain.putString("userName", username);
//                                editorMain.putString("psw", psw);
//                                editorMain.commit();
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("userName", username);
//                                intent.putExtras(bundle);
//                                lead=leadupdate.leadupdate(username,1);
//                                startActivity(intent);
//                            }
//                            else {
//                                Intent intent = new Intent(getActivity(), HomePageActivity.class);
//                                editorMain.putBoolean("main", true);
//                                editorMain.putString("userName", username);
//                                editorMain.putString("psw", psw);
//                                editorMain.commit();
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("userName", username);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                            }
//                        }
                    }
//                    else if(!login.LIsExistUserName(username)){
//                        Toast.makeText(ctx,"账号不存在",Toast.LENGTH_LONG).show();
//                    }
//
//                    else if (login.isloginSuccess(username,psw)==false){
//                        Toast.makeText(ctx,"密码错误",Toast.LENGTH_LONG).show();

//                }



//                    }




                }

            }
        });


        TextView forgetkey=view.findViewById(R.id.text_forgetkey);
        forgetkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Forgetkey.class);
                startActivity(intent);
            }
        });



        return view;


    }
    //Controller中load方法的回调，即保证网络请求完成以后执行后续方法，保证顺序执行
    //一般来说一个controller请求会对应一个回调方法
    @Override
    public void getLoadMessage(final String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadImg.clearAnimation();
                mRelativeLayout.setVisibility(View.GONE);

                if ("登陆成功".equals(str)) {
                    Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                    if(id == 0) {
                        mSoundPool.play(hm.get(1), 1, 1, 0, 0, 1);
                        Intent intent = new Intent(getActivity(), LeadActivity.class);
                        editorMain.putBoolean("main", true);
                        editorMain.putString("userName", username);
                        editorMain.putString("psw", psw);
                        editorMain.commit();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putString("userName", username);
                        intent.putExtras(bundle);
                        lead=leadupdate.leadupdate(username,1);
                        startActivity(intent);
                    }
                    else {
                        mSoundPool.play(hm.get(1), 1, 1, 0, 0, 1);
                        Intent intent = new Intent(getActivity(), HomePageActivity.class);
                        editorMain.putBoolean("main", true);
                        editorMain.putString("userName", username);
                        editorMain.putString("psw", psw);
                        editorMain.commit();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putString("userName", username);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }else {
                    Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



}