package com.example.guanguannfc.view.loginAndLogon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guanguannfc.R;
import com.example.guanguannfc.controller.userManagement.Login;
import com.example.guanguannfc.model.DataBaseTest.FakeData;
import com.example.guanguannfc.model.Initialization;
import com.example.guanguannfc.view.homepage.HomePageActivity;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    private SigninFragment frag_signin = new SigninFragment();
    private LogonFragment frag_logon = new LogonFragment();
    Login login = new Login(this);
    public static String username,psw;
    public TextView tv_sign,tv_log;

    private EditText edit_username,edit_psw;

    // 获取颜色资源文件
    int colorgray,colorRedDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
//        登陆过了
        if(sprfMain.getBoolean("main",false)){
            Initialization.initialization(this);
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            LoginActivity.this.finish();
        }
//        未登录
        else {
            setContentView(R.layout.activity_las);
            initView();
//        frag_logon.getContex(this);
            getFragmentManager().beginTransaction().replace(R.id.logandsign,frag_signin).commit();

            Initialization.initialization(this);
            if (!login.LIsExistUserName("aaa")){
                FakeData fakeData = new FakeData(this);
                fakeData.insert();
            }
        }



    }

    private void initView(){
        tv_log=findViewById(R.id.button_logon);
        tv_sign=findViewById(R.id.button_signin);
        colorgray = getResources().getColor(R.color.colorgray);
        colorRedDark = getResources().getColor(R.color.colorRedDark);
    }

    @SuppressLint("WrongConstant")
    public void click(View v){
        int id=v.getId();

        switch(id){
            case R.id.button_signin:
                tv_sign.setVisibility(4);
                tv_log.setVisibility(0);
                getFragmentManager().beginTransaction().replace(R.id.logandsign,frag_signin).commit();
                break;
            case R.id.button_logon:
                tv_sign.setVisibility(0);
                tv_log.setVisibility(4);
                getFragmentManager().beginTransaction().replace(R.id.logandsign,frag_logon).commit();
                break;

            case R.id.text_agree:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);

                builder.setTitle("协议");
                builder.setMessage(R.string.no_mean);
                builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final AlertDialog b=builder.create();
                b.show();
                break;




        }

    }

    public void changeColor(){
        tv_sign.setTextColor(colorRedDark);
        tv_log.setTextColor(colorgray);
        getFragmentManager().beginTransaction().replace(R.id.logandsign,frag_signin).commit();
    }

//    public void init(){
//        button_signin=findViewById(R.id.button_signin_confirm);
//        button_signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,Homepage.class);
//                startActivity(intent);
//            }
//        });
//    }


}
