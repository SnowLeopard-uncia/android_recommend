package com.zyw.recommend_system.ui.main.recommend;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyw.recommend_system.databinding.FragmentRecommendBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;
import com.zyw.recommend_system.ui.adapter.UserComparator;
import com.zyw.recommend_system.ui.main.MainViewModel;
import com.zyw.recommend_system.ui.search.SearchActivity;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class RecommendFragment extends Fragment {

    private MainViewModel mViewModel;
    private FragmentRecommendBinding recommendBinding;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recommendBinding =   FragmentRecommendBinding.inflate(inflater);
        recommendBinding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        boolean isFirst = mViewModel.getFirst();
        String tag = "";
        if (isFirst){
            if (mViewModel.getTag()==0){
                tag="科学";
            }else {
                tag="技术";
            }
            mViewModel.setFirst(false);
        }
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        recommendBinding.rvUserRecommend.setLayoutManager(linearLayoutManager);

        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        recommendBinding.rvUserRecommend.setAdapter(adapter);

//        mViewModel.getRecommendArticlePaging(isFirst,tag).observe(this.getActivity(), new Observer<PagingData<Article>>() {
//            @Override
//            public void onChanged(PagingData<Article> articlePagingData) {
//                adapter.submitData(getViewLifecycleOwner().getLifecycle(),articlePagingData);
//            }
//        });
        /*

        adapter.addLoadStateListener(new Function1<CombinedLoadStates, Unit>() {
            @Override
            public Unit invoke(CombinedLoadStates combinedLoadStates) {
                switch (combinedLoadStates.getRefresh()){
                    case LoadState.Loading:
                        break;
                    case LoadState.NotLoading:
                        break;
                    case LoadState.Error:
                        break;
                }
                return null;
            }
        });

         */
        return recommendBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}