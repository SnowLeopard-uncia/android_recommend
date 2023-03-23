package com.zyw.recommend_system.ui.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zyw.recommend_system.MyApplication;
import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivityArticleDetailBinding;
import com.zyw.recommend_system.logic.model.Article;
import com.zyw.recommend_system.ui.collection.CollectionViewModel;

public class ArticleDetailActivity extends AppCompatActivity {

    ArticleDetailViewModel articleDetailViewModel;
    ActivityArticleDetailBinding articleDetailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
        articleDetailViewModel = new ViewModelProvider(this).get(ArticleDetailViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Article article= bundle.getParcelable("article");
        Log.e("ArticleDetailActivity", "onCreate: "+article.toString() );

        articleDetailBinding.setArticle(article);

        articleDetailViewModel.getArticleDetail(String.valueOf(article.getId())).observe(ArticleDetailActivity.this, new Observer<Article>() {
            @Override
            public void onChanged(Article article) {
                articleDetailBinding.cbLike.setChecked(article.isLiked());
                articleDetailBinding.cbStar.setChecked(article.isStared());
            }
        });

        //分享按钮
        articleDetailBinding.llArticleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//复制链接url到剪切板，‘Label’这是任意文字标签
                ClipData mClipData =ClipData.newRawUri("Label", Uri.parse(article.getLink()));
//                将ClipData数据复制到剪贴板
                manager.setPrimaryClip(mClipData);
                Toast.makeText(ArticleDetailActivity.this,"已将链接复制到剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
//收藏按钮
        articleDetailBinding.llArticleCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean state = articleDetailBinding.cbStar.isChecked();
                articleDetailBinding.cbStar.setChecked(!state);
                Log.e("ArticleDetailActivity", "onChanged: state"+state );
                if (state){
                    articleDetailViewModel.collectDelete(String.valueOf(article.getId())).observe(ArticleDetailActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            Log.e("ArticleDetailActivity", "onChanged: 取消收藏" );
                            Toast.makeText(ArticleDetailActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                        }
                    });
//                     poamBinding.cbCollect.setChecked(false);
                }else {
                    articleDetailViewModel.collect(String.valueOf(article.getId())).observe(ArticleDetailActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean){
                                Log.e("ArticleDetailActivity", "onChanged: 收藏成功" );
                                Toast.makeText(ArticleDetailActivity.this,"收藏成功!",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
//                     poamBinding.cbCollect.setChecked(true);

                }
            }

        });
//点赞按钮
        articleDetailBinding.llArticleLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean state = articleDetailBinding.cbLike.isChecked();

                articleDetailBinding.cbLike.setChecked(!state);

                if (state){
                    articleDetailViewModel.deleteLike(String.valueOf(article.getId())).observe(ArticleDetailActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {

                            Toast.makeText(ArticleDetailActivity.this,"取消喜欢",Toast.LENGTH_SHORT).show();
                        }
                    });
//                     poamBinding.cbCollect.setChecked(false);

                }else {
                    articleDetailViewModel.like(String.valueOf(article.getId())).observe(ArticleDetailActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            Toast.makeText(ArticleDetailActivity.this,"点赞成功!",Toast.LENGTH_SHORT).show();
                        }
                    });
//                     poamBinding.cbCollect.setChecked(true);
                }
            }

        });

    }
}