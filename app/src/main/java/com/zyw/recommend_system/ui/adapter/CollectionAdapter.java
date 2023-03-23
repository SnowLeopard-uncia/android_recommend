package com.zyw.recommend_system.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private List<Article> articleList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new ViewHolder(view);
    }

    public CollectionAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(holder.getAdapterPosition());
        holder.itemRecommendBinding.setArticle(article);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("article",article);
                ArrayList list = new ArrayList();
                list.add(articleList);
                bundle.putParcelableArrayList("poem_list",list);
                intent.putExtra("bundle",bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemRecommendBinding itemRecommendBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRecommendBinding = DataBindingUtil.bind(itemView);
        }
    }
}
