package com.zyw.recommend_system.ui.history;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivityHistoryBinding;

import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.BaseActivity;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;
import com.zyw.recommend_system.ui.adapter.UserComparator;
import com.zyw.recommend_system.ui.like.LikesViewModel;

public class HistoryActivity extends BaseActivity {

    ActivityHistoryBinding historyBinding;
    HistoryViewModel historyViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        initNavBar();

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        historyBinding.rvHistory.setLayoutManager(linearLayoutManager);


        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        historyBinding.rvHistory.setAdapter(adapter);

        historyViewModel.getHistoryPaging().observe(this, new Observer<PagingData<Article>>() {
            @Override
            public void onChanged(PagingData<Article> articlePagingData) {
                adapter.submitData(getLifecycle(),articlePagingData);
            }
        });
    }
}