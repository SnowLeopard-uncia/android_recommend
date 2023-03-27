package com.zyw.recommend_system.ui.main;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zyw.recommend_system.R;
import com.zyw.recommend_system.logic.Repository;
import com.zyw.recommend_system.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    BottomNavigationView bottomNavigationView;
    MainViewModel mainViewModel;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.nav_view);
        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
//        //获取NavController
//        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
//        //通过setupWithNavController将底部导航和导航控制器进行绑定
//        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        //1、先拿NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //2、再拿NavController
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        if (Repository.getInstance().getFirstIn()){
           showChooseDialog();
        }
    }



    private void showChooseDialog() {
        //1使用dialog 设置style
        final Dialog dialog = new Dialog(this,R.style.Pop_Style_Theme);
        //设置布局
        View view = View.inflate(this,R.layout.dialog_pop_layout,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置 这里为底部
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.pop_menu_anim_style);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.findViewById(R.id.btn_choose_science).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.setTag(0);
//                mainViewModel.setFirst(false);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_choose_technology).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.setTag(1);
//                mainViewModel.setFirst(false);
                dialog.dismiss();
            }
        });
    }
}