package com.zyw.recommend_system.ui.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ItemRecommendBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.article.ArticleDetailActivity;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.ViewHolder> {

    private final List<Article> list ;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new ViewHolder(view);
    }

    public LikesAdapter(List<Article> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = list.get(position);
        holder.itemRecommendBinding.setArticle(article);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //错题跳转到一个Activity
                Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class);
                intent.putExtra("article", (Parcelable) article);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecommendBinding itemRecommendBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRecommendBinding= DataBindingUtil.bind(itemView);
        }
    }
}
