package com.zyw.recommend_system.ui.real_name;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivityRealNameBinding;
import com.zyw.recommend_system.ui.BaseActivity;

public class RealNameActivity extends BaseActivity {

    ActivityRealNameBinding activityRealNameBinding;

    RealNameViewModel realNameViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);
        activityRealNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_real_name);
        realNameViewModel = new ViewModelProvider(this).get(RealNameViewModel.class);



        activityRealNameBinding.btnRealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = activityRealNameBinding.etRealName.getText().toString();
                String number = activityRealNameBinding.etRealNumber.getText().toString();
                if (name.equals("")){
                    Toast.makeText(RealNameActivity.this,"姓名不能为空！",Toast.LENGTH_SHORT).show();
                }else if (number.equals("")){
                    Toast.makeText(RealNameActivity.this,"身份证不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    realNameViewModel.postRealNameState(name,number).observe(RealNameActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean){
                                activityRealNameBinding.realNameArea.setVisibility(View.GONE);
                                activityRealNameBinding.realNameSuccess.setVisibility(View.VISIBLE);
                                realNameViewModel.setRealNameState(aBoolean);
                            }

                        }
                    });
                }


            }
        });


    }
}