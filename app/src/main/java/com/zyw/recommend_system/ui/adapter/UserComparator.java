package com.zyw.recommend_system.ui.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zyw.recommend_system.logic.model.Article;

//计算位置刷新的工具类
public class UserComparator extends DiffUtil.ItemCallback<Article> {
    @Override
    public boolean areItemsTheSame(@NonNull Article oldItem,
                                   @NonNull Article newItem) {
        // 返回两个item是否相同
        Log.e("UserComparator", "areItemsTheSame: "+oldItem.toString()+"  "+newItem.toString());
        return oldItem.getId().equals(newItem.getId());
    }
    // 当areItemsTheSame返回true时，我们还需要判断两个item的内容是否相同
    @Override
    public boolean areContentsTheSame(@NonNull Article oldItem,
                                      @NonNull Article newItem) {
        return oldItem.toString().equals(newItem.toString());
    }
}