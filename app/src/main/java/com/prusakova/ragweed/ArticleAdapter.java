package com.prusakova.ragweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    private RecyclerViewClickListener mListener;

    public ArticleAdapter(List<Article> articles, Context context,RecyclerViewClickListener listener) {
        this.articles = articles;
        this.context = context;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_article, parent, false);
        return new ViewHolder(view, mListener);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView articleName;
        private TextView articleLink;
        private ImageView articleImage;
        private TextView articleText;
        private FrameLayout mRowContainer;
        private RecyclerViewClickListener mListener;
        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            articleName = itemView.findViewById(R.id.txt_name);
//            articleLink = itemView.findViewById(R.id.txt_link);
            articleImage = itemView.findViewById(R.id.image_article);
            mRowContainer = itemView.findViewById(R.id.article_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.article_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }


    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);

    }
}