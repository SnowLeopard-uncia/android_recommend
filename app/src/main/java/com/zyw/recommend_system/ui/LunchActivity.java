package com.zyw.recommend_system.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.logic.dao.UserDao;
import com.zyw.recommend_system.logic.model.UserLogin;
import com.zyw.recommend_system.ui.main.MainActivity;
import com.zyw.recommend_system.utils.UUIDUtil;


public class LunchActivity extends AppCompatActivity {

    private CountDownTimer timer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        // 设置全屏一定要在setContentView()之前,否则有可能不起作用
        setContentView(R.layout.activity_lunch);
        TextView tvSkip = findViewById(R.id.tv_skip);
        initCountDown(tvSkip);
    }

    private void setFullScreen() {
        // 用于extends AppCompatActivity，
        if (getSupportActionBar() != null){ getSupportActionBar().hide(); }
        // 去除状态栏，如 电量、Wifi信号等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initCountDown(final TextView tvSkip) {
        // 判断当前Activity是否isFinishing()，
        // 避免在finish，所有对象都为null的状态下执行CountDown造成内存泄漏
        if (!isFinishing()) {
            timer = new CountDownTimer(1000 * 4, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    //耗时操作
                    if (Repository.getInstance().getFirstIn()){
                        Log.e("LunchActivity", "onTick: 首次进入");
                        //  TODO：首次安装判断
                        String GUID = UUIDUtil.generateGUID();
                        UserDao.saveGUID(GUID);
                        //使用GUID去登录取得token存入
                        Repository.getInstance().userLogin(GUID).observe(LunchActivity.this, new Observer<UserLogin>() {
                            @Override
                            public void onChanged(UserLogin userLogin) {
                                UserDao.saveToken(userLogin.getToken());
                                UserDao.saveUserId(userLogin.getUserId());
                                Log.e("LunchActivity", "onTick: GUID"+UserDao.getGUID());
                                Log.e("LunchActivity", "onTick: 首次存入token"+userLogin.getToken());
                            }
                        });

                    }else {
                        // TODO: 2023/3/21 使用GUID去登录 取得token存入
                        Repository.getInstance().userLogin(UserDao.getGUID()).observe(LunchActivity.this, new Observer<UserLogin>() {
                            @Override
                            public void onChanged(UserLogin userLogin) {
                                UserDao.saveToken(userLogin.getToken());
                                Log.e("LunchActivity", "onTick: 二次存入token");
                            }
                        });
                    }
                    int time = (int) millisUntilFinished;
                    tvSkip.setText(time / 1000 + " 跳过");
                    tvSkip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkToJump();
                        }
                    });
                }

                @Override
                public void onFinish() {
                    checkToJump();
                }
            }.start();
        }
    }


    private void checkToJump() {

        Intent intent=new Intent(LunchActivity.this,MainActivity.class);
        startActivity(intent);
        /*
        // 如果是首次安装打开，则跳至选择页面；否则跳至主界面
        if (Repository.getInstance().getFirstIn()){
            String GUID = UUIDUtil.generateGUID();
            UserDao.saveGUID(GUID);
            Intent intent=new Intent(LunchActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
        Intent intent=new Intent(LunchActivity.this,MainActivity.class);
        startActivity(intent);
        }
               */
        // 回收内存
        destoryTimer();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    public void destoryTimer() {
        // 避免内存泄漏
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}