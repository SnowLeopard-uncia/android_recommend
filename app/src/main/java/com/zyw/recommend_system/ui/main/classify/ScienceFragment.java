package com.zyw.recommend_system.ui.main.classify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.zyw.recommend_system.databinding.FragmentScienceBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.adapter.UserComparator;
import com.zyw.recommend_system.ui.main.MainViewModel;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;

public class ScienceFragment extends Fragment {
    FragmentScienceBinding fragmentScienceBinding;
    MainViewModel mainViewModel;
    public ScienceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentScienceBinding=FragmentScienceBinding.inflate(inflater);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this.getActivity());
        fragmentScienceBinding.rvClassifyScience.setLayoutManager(linearLayoutManager);
//        ClassifyAdapter adapter = new ClassifyAdapter(mainViewModel.getScienceList());

        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        fragmentScienceBinding.rvClassifyScience.setAdapter(adapter);


//        mainViewModel.getArticlePaging().observe(this.getActivity(), new Observer<PagingData<Article>>() {
//            @Override
//            public void onChanged(PagingData<Article> articlePagingData) {
//                adapter.submitData(getViewLifecycleOwner().getLifecycle(),articlePagingData);
//            }
//        });

        /*
        mainViewModel.getArticleScienceList().observe(this.getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles!=null){

                    mainViewModel.getScienceList().clear();
                    mainViewModel.setScienceList(articles);
                    adapter.setArticles(mainViewModel.getScienceList());
                    Log.e("TAG", "onChanged: "+"science" );
                }else {
                    Toast.makeText(getActivity(),"暂无文章",Toast.LENGTH_SHORT).show();
                }
            }
        });
          */

        return fragmentScienceBinding.getRoot();
    }
}