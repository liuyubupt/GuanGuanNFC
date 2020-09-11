package com.example.guanguannfc.view.loginAndLogon;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.guanguannfc.controller.userManagement.Register;

import com.example.guanguannfc.R;

public class LogonFragment extends Fragment implements Register.Message {

    private EditText edit_username,edit_psw,edit_psw_confirm;
    private String username,pasword,pasword_confirm;
    private CheckBox checkBox;
    private Context ctx;
    private Button btn_logon;
    private Register register;

    private ImageView loadImg;
    private RelativeLayout mRelativeLayout;
    private Animation animation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_logon, container, false);
        ctx=getActivity();
        register=new Register(ctx,this);
        btn_logon=view.findViewById(R.id.button_logon_confirm);
        edit_username=view.findViewById(R.id.edit_username);
        edit_psw=view.findViewById(R.id.edit_psw);
        edit_psw_confirm=view.findViewById(R.id.edit_psw_confirm);
        checkBox=view.findViewById(R.id.checkBox);

        loadImg = view.findViewById(R.id.load_img);
        mRelativeLayout = view.findViewById(R.id.rela_img);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btn_logon.setClickable(true);
                    btn_logon.setEnabled(true);
                }
                else {
                    btn_logon.setClickable(false);
                    btn_logon.setEnabled(false);
                }
            }
        });


        btn_logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=edit_username.getText().toString();
                pasword=edit_psw.getText().toString();
                pasword_confirm=edit_psw_confirm.getText().toString();

                //动画效果
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
                animation.setDuration(500);
                animation.setRepeatCount(8);//动画的反复次数
                animation.setFillAfter(true);//设置为true，动画转化结束后被应用
                loadImg.startAnimation(animation);//開始动画
                mRelativeLayout.setVisibility(View.VISIBLE);


                if(username.equals("")){
                    Toast.makeText(ctx,"请设置账号",Toast.LENGTH_LONG).show();
                }
                else if(pasword.equals("")){
                    Toast.makeText(ctx,"请设置密码",Toast.LENGTH_LONG).show();
                }
                else if(pasword_confirm.equals("")){
                    Toast.makeText(ctx,"请确认密码",Toast.LENGTH_LONG).show();
                }

                else {

                    if(pasword_confirm.equals(pasword) == false){
                        Toast.makeText(ctx,"两次输入的密码不同",Toast.LENGTH_LONG).show();

                    }
                    else if(pasword.length()<6 | pasword.length()>20){
                        Toast.makeText(ctx,"密码长度为6-20位",Toast.LENGTH_LONG).show();
                    }
                    else {
//                        if(register.ISRegisterSuccess(username,pasword)){
//                            Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_LONG).show();
//                            edit_username.setText("");
//                            edit_psw.setText("");
//                            edit_psw_confirm.setText("");
//                            checkBox.setChecked(false);
//                            getFragmentManager().beginTransaction()
//                                    .addToBackStack(null)
//                                    .replace(R.id.logandsign,new SigninFragment())
//                                    .commit();
//                            LoginActivity loginActivity = (LoginActivity) getActivity();
//                            loginActivity.changeColor();
//                        }
//                        else{
//                            Toast.makeText(ctx,"账号已存在",Toast.LENGTH_LONG).show();
//                        }
                        register.register1(username,pasword);

                    }
                }



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
                if ("注册成功".equals(str)) {

                    Toast.makeText(ctx,str,Toast.LENGTH_LONG).show();
                    edit_username.setText("");
                    edit_psw.setText("");
                    edit_psw_confirm.setText("");
                    checkBox.setChecked(false);
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.logandsign,new SigninFragment())
                            .commit();
                    LoginActivity loginActivity = (LoginActivity) getActivity();
                    loginActivity.changeColor();

                }else {
                    Toast.makeText(ctx,str,Toast.LENGTH_LONG).show();
                }


            }
        });
    }

}
