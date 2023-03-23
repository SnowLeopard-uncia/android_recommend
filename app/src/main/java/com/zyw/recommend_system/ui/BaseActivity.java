package com.zyw.recommend_system.ui;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyw.recommend_system.R;



public class BaseActivity extends AppCompatActivity {

    private ImageView ivBack;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);
        getWindow().setStatusBarColor(getColor(R.color.bg_color_main));
    }

    public void initNavBar(){
        ivBack = findViewById(R.id.iv_back);
         ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}