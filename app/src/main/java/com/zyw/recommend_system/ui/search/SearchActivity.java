package com.zyw.recommend_system.ui.search;

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
import android.widget.Toast;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivitySearchBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.BaseActivity;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;
import com.zyw.recommend_system.ui.adapter.UserComparator;
import com.zyw.recommend_system.ui.collection.CollectionViewModel;

public class SearchActivity extends BaseActivity {

    ActivitySearchBinding searchBinding;
    SearchViewModel searchViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);


        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        searchBinding.rvSearchResult.setLayoutManager(linearLayoutManager);

        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        searchBinding.rvSearchResult.setAdapter(adapter);

        searchBinding.ivSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        searchBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = searchBinding.etSearchText.getText().toString();
                if (keyWord.equals("")){
                    Toast.makeText(SearchActivity.this,"请输入关键词",Toast.LENGTH_SHORT).show();
                }else {
                    searchViewModel.getArticleSearchPaging(keyWord).observe(SearchActivity.this, new Observer<PagingData<Article>>() {
                        @Override
                        public void onChanged(PagingData<Article> articlePagingData) {
                            adapter.submitData(getLifecycle(),articlePagingData);
                        }
                    });
                }
            }
        });
    }
}