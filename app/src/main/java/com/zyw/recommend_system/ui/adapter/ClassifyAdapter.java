package com.zyw.recommend_system.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ItemRecommendBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.article.ArticleDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClassifyAdapter extends PagingDataAdapter<Article,ClassifyAdapter.ViewHolder> {

    private List<Article> articleList;

    public ClassifyAdapter(@NotNull DiffUtil.ItemCallback<Article> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new ViewHolder(view);
//        return new ViewHolder(parent);
    }


//    public ClassifyAdapter(List<Article> articleList) {
//        this.articleList = articleList;
//    }

//        public void setArticles(List<Article> articleList){
//        this.articleList =articleList;
//        //recycler View改变
//        notifyDataSetChanged(); //这个放在外面还是放在里面效果都是一样
//    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Article article = articleList.get(holder.getAdapterPosition());
        Article article = getItem(position);

        holder.itemRecommendBinding.setArticle(article);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("article",article);
//                ArrayList list = new ArrayList();
//                list.add(articleList);
//                bundle.putParcelableArrayList("poem_list",list);
                intent.putExtra("bundle",bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

//    @Override
//    public int getItemCount() {
//        return articleList.size();
//    }
    //这个大概是初始化recyclerview的时候用的，因为我写过如果list是null就返回0，一开始应该所有list都是null，所以就没有返回【？】

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecommendBinding itemRecommendBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRecommendBinding= DataBindingUtil.bind(itemView);
        }
    }


}
