package com.prusakova.ragweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articles;
    private Context context;

    public ArticleAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.articleName.setText(articles.get(position).getArticleName());
//        holder.articleLink.setText(articles.get(position).getArticleLink());
        Picasso.with(context)
                .load(articles.get(position).getArticle_img())
                .into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView articleName;
//        private TextView articleLink;
        private ImageView articleImage;
        public ViewHolder(View itemView) {
            super(itemView);
            articleName = itemView.findViewById(R.id.txt_name);
//            articleLink = itemView.findViewById(R.id.txt_link);
            articleImage = itemView.findViewById(R.id.image_article);
        }
    }
}
