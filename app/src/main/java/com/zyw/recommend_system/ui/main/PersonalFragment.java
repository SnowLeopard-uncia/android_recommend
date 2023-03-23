package com.zyw.recommend_system.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zyw.recommend_system.MyApplication;
import com.zyw.recommend_system.databinding.FragmentPersonalBinding;
import com.zyw.recommend_system.ui.history.HistoryActivity;
import com.zyw.recommend_system.ui.collection.CollectionActivity;
import com.zyw.recommend_system.ui.like.LikeActivity;
import com.zyw.recommend_system.ui.real_name.RealNameActivity;
import com.zyw.recommend_system.ui.upload.UploadActivity;


public class PersonalFragment extends Fragment {

    FragmentPersonalBinding personalBinding;
    MainViewModel mainViewModel;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView: " );
        personalBinding=FragmentPersonalBinding.inflate(inflater);
        mainViewModel=new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        String GUID = mainViewModel.getUserGUID();
        String name = "用户"+GUID.substring(0,8);
//        personalBinding.tvName.setText(userName);  //用这个是显示不出来的，经典DataBinding覆盖问题
        personalBinding.setTvUsernames(name);  //嗯就是被这个覆盖

        //实名状态  逻辑：实名认证入口的出现情况、实名认证状态
        if (mainViewModel.getUserRealName()){
            personalBinding.llRealName.setVisibility(View.GONE);
            personalBinding.setTvRealNameState("已实名");
        }else {
            personalBinding.setTvRealNameState("未实名");
        }


        personalBinding.llCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });

        personalBinding.llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LikeActivity.class);
                startActivity(intent);
            }
        });

        personalBinding.llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        personalBinding.llRealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RealNameActivity.class);
                startActivity(intent);
            }
        });

        personalBinding.llToRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainViewModel.getUserRealName()){
                    Intent intent = new Intent(getActivity(), UploadActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MyApplication.getContext(),"您还未实名，请实名后再进行推荐",Toast.LENGTH_LONG).show();
                }
            }
        });

        return personalBinding.getRoot();
    }

//    下面是关于Fragment生命周期的实验
    @Override
    public void onResume() {
        super.onResume();
        //实名状态  逻辑：实名认证入口的出现情况、实名认证状态
        if (mainViewModel.getUserRealName()){
            personalBinding.llRealName.setVisibility(View.GONE);
            personalBinding.setTvRealNameState("已实名");
        }else {
            personalBinding.setTvRealNameState("未实名");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TAG", "onActivityCreated: ");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: " );

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: " );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: " );
    }

}