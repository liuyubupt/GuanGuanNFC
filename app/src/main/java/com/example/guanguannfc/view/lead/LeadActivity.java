package com.example.guanguannfc.view.lead;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guanguannfc.R;
import com.example.guanguannfc.view.homepage.HomePageActivity;

public class LeadActivity extends AppCompatActivity {
    private  int[]  arrayPicture=new int[]{
            R.drawable.img_lead1,R.drawable.img_lead2,R.drawable.img_lead3,R.drawable.img_lead4,R.drawable.img_lead5,R.drawable.img_lead6};
    private ImageSwitcher imageSwitcher;
    private int  index;
    private  float touchDownX;
    private  float touchUpX;
    private Button btn_ok;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        //设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageSwitcher=findViewById(R.id.imageswitch);
        btn_ok=findViewById(R.id.btn_ok);
        //设置视图工厂
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView  imageView=new ImageView(LeadActivity.this);
                imageView.setImageResource(arrayPicture[index]);//设置显示图片（利用下标）
                if(index == 5){
                    btn_ok.setVisibility(View.VISIBLE);
                }
                else {
                    btn_ok.setVisibility(View.INVISIBLE);
                }
                return imageView;//返回图像视图
            }
        });
        //设置触摸监听器
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeadActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });
        imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                //判断动作是不是按下  获得按下时的X坐标
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    touchDownX=event.getX();
                    return true;
                } else if(event.getAction()==MotionEvent.ACTION_UP) {
                    touchUpX=event.getX();
                    //判断是左滑动还是右滑动
                    if(touchUpX-touchDownX>100){
                        //判断是不是第一张图片 是就将索引变成最后一张图片索引，
                        // 不是则当前索引减一
                        index=index==0?arrayPicture.length-1:index-1;
                        //使用自带的淡入淡出
                        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(LeadActivity.this,android.R.anim.fade_in));
                        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(LeadActivity.this,android.R.anim.fade_out));
                        imageSwitcher.setImageResource(arrayPicture[index]);
                    }else if(touchDownX-touchUpX>100){
                        index=index==arrayPicture.length-1?0:index+1;//注意这里下标是从0开始的，所以应该是长度减1
                        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(LeadActivity.this,android.R.anim.fade_in));
                        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(LeadActivity.this,android.R.anim.fade_out));
                        imageSwitcher.setImageResource(arrayPicture[index]);
                    }
                    if(index == 5){
                        btn_ok.setVisibility(View.VISIBLE);
                    }
                    else {
                        btn_ok.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
