package com.zyw.recommend_system.ui.main.classify;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.FragmentClassifyBinding;
import com.zyw.recommend_system.ui.adapter.PagerAdapter;


public class ClassifyFragment extends Fragment {

    FragmentClassifyBinding fragmentClassifyBinding;

    private Activity activity;

    public ClassifyFragment() {

    }
//用于解决getActivity返回null的方法：
//    原因：调用了getActivity()时，当前的Fragment已经onDetach()了宿主Activity
//    在pop了Fragment之后，该Fragment的异步任务仍然在执行，并且在执行完成后调用了getActivity()方法，这样就会空指针。
//在Fragment基类里设置一个Activity mActivity的全局变量，在onAttach(Activity activity)里赋值，使用mActivity代替getActivity()，保证Fragment即使在onDetach后，仍持有Activity的引用（有引起内存泄露的风险，但是相比空指针闪退，这种做法“安全”些）
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity=(Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentClassifyBinding=FragmentClassifyBinding.inflate(inflater); //fragment使用databinding
//        View view = inflater.inflate(R.layout.fragment_study, container, false);
        PagerAdapter pagerAdapter = new PagerAdapter((FragmentActivity) activity);
        //这里出现过空指针异常，因为Activity找不到
        //Return the FragmentActivity this fragment is currently associated with. May return null if the fragment is associated with a Context instead.
//        如果此fragment绑定的是一个context的话，可能会返回null
//        fragmentStudyBinding= DataBindingUtil.setContentView(activity,R.layout.fragment_study);
        //然而是databinding为null,没有绑定 怀疑是这个语句把navigation导航栏覆盖掉↑ 所以查了一下fragment使用databinding,结果还真是
        fragmentClassifyBinding.vp2Study.setAdapter(pagerAdapter);
        pagerAdapter.addFragment(new ScienceFragment());
        pagerAdapter.addFragment(new TechnologyFragment());

        fragmentClassifyBinding.tbStudy.setTabTextColors(R.color.unselect_color,R.color.select_blue);
        fragmentClassifyBinding.tbStudy.addTab(fragmentClassifyBinding.tbStudy.newTab().setText("科学"));
        fragmentClassifyBinding.tbStudy.addTab(fragmentClassifyBinding.tbStudy.newTab().setText("技术"));

//fragment初始化的界面
        fragmentClassifyBinding.vp2Study.setCurrentItem(0);

        fragmentClassifyBinding.tbStudy.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentClassifyBinding.vp2Study.setCurrentItem(tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //fragment与view pager绑定
        fragmentClassifyBinding.vp2Study.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                fragmentClassifyBinding.tbStudy.setScrollPosition(position, 0, false);
            }
        });

        return fragmentClassifyBinding.getRoot();
    }
}