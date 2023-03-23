package com.zyw.recommend_system.ui.collection;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivityCollectionBinding;

import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.BaseActivity;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;
import com.zyw.recommend_system.ui.adapter.UserComparator;

import java.util.List;

public class CollectionActivity extends BaseActivity {

   ActivityCollectionBinding activityCollectionBinding;

    CollectionViewModel collectionViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCollectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_collection);
        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        initNavBar();

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        activityCollectionBinding.rvCollection.setLayoutManager(linearLayoutManager);

        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        activityCollectionBinding.rvCollection.setAdapter(adapter);

        collectionViewModel.getCollectionPaging().observe(this, new Observer<PagingData<Article>>() {
            @Override
            public void onChanged(PagingData<Article> articlePagingData) {
                adapter.submitData(getLifecycle(),articlePagingData);
            }
        });

    }
}