package com.zyw.recommend_system.ui.main.classify;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;



import com.zyw.recommend_system.databinding.FragmentTechnologyBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.adapter.UserComparator;
import com.zyw.recommend_system.ui.main.MainViewModel;
import com.zyw.recommend_system.ui.adapter.ClassifyAdapter;

import java.util.List;


public class TechnologyFragment extends Fragment {
    FragmentTechnologyBinding fragmentTechnologyBinding;

    MainViewModel mainViewModel;
    public TechnologyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTechnologyBinding=FragmentTechnologyBinding.inflate(inflater);
        mainViewModel=new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this.getActivity());

        fragmentTechnologyBinding.rvHotTechnology.setLayoutManager(linearLayoutManager);
        ClassifyAdapter adapter = new ClassifyAdapter(new UserComparator());
        fragmentTechnologyBinding.rvHotTechnology.setAdapter(adapter);

//        mainViewModel.getHotArticlePaging("技术").observe(this.getActivity(), new Observer<PagingData<Article>>() {
//            @Override
//            public void onChanged(PagingData<Article> articlePagingData) {
//                adapter.submitData(getViewLifecycleOwner().getLifecycle(),articlePagingData);
//            }
//        });


        return fragmentTechnologyBinding.getRoot();
    }
}