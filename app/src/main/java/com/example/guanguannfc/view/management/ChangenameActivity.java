package com.example.guanguannfc.view.management;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guanguannfc.R;

public class ChangenameActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeactname);
    }

    public void Addbox(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddboxActivity.class);
        startActivity(intent);
    }
}
