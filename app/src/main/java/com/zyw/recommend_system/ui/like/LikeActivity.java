package com.zyw.recommend_system.ui.like;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivityLikeBinding;
;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.BaseActivity;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;
import com.zyw.recommend_system.ui.adapter.LikesAdapter;
import com.zyw.recommend_system.ui.adapter.UserComparator;

import java.util.List;

public class LikeActivity extends BaseActivity {

   ActivityLikeBinding activityLikeBinding;
   LikesViewModel likesViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLikeBinding = DataBindingUtil.setContentView(this, R.layout.activity_like);
        likesViewModel = new ViewModelProvider(this).get(LikesViewModel.class);

        initNavBar();

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        activityLikeBinding.rvLikes.setLayoutManager(linearLayoutManager);

        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        activityLikeBinding.rvLikes.setAdapter(adapter);

        likesViewModel.getLikesPaging().observe(this, new Observer<PagingData<Article>>() {
            @Override
            public void onChanged(PagingData<Article> articlePagingData) {
                adapter.submitData(getLifecycle(),articlePagingData);
            }
        });
    }
}