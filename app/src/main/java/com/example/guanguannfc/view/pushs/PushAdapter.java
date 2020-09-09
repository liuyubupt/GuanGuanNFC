package com.example.guanguannfc.view.pushs;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.guanguannfc.R;

import java.util.List;
import java.util.Objects;

public class PushAdapter extends ArrayAdapter {
    private final int resourceId;
    private PopupWindow mPopWindow;
    private Objects img;
    private List<Object> list;

    public PushAdapter(Context context, int textViewResourceId, List<PushItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final PushItem pushItem =(PushItem)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView imageView = view.findViewById(R.id.img_back);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_author = view.findViewById(R.id.tv_author);
        TextView tv_content = view.findViewById(R.id.tv_content);
        imageView.setImageResource(pushItem.getImageId());
        tv_title.setText(pushItem.getTitle());
        tv_author.setText(pushItem.getAuthor());
        tv_content.setText(pushItem.getContent());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpush(pushItem.getTitle(),pushItem.getAuthor(),pushItem.getContent(),pushItem.getPinlun(), pushItem.getImageId());
            }
        });


        return view;
    }
    private void showpush(String title,String Author,String content,String pinlun,int id){
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_show_push, null);
        mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        TextView tv_push_title=contentView.findViewById(R.id.tv_push_title);
        TextView tv_push_author=contentView.findViewById(R.id.tv_push_author);
        TextView tv_push_content=contentView.findViewById(R.id.tv_push_content);
        TextView tv_push_pinlun=contentView.findViewById(R.id.tv_push_pinlun);
        LinearLayout lay_showpush2=contentView.findViewById(R.id.lay_showpush2);
        lay_showpush2.setBackground(getContext().getResources().getDrawable(id));
        tv_push_title.setText(title);
        tv_push_author.setText("作者："+Author);
        tv_push_content.setText(content);
        tv_push_pinlun.setText("评论:"+pinlun);
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.fragment_push,null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }
}
