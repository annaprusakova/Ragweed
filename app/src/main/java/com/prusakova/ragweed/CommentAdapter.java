package com.prusakova.ragweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.model.Comment;
import com.prusakova.ragweed.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> comments;
    private List<User> users;
    private Context context;
    private CommentAdapter.RecyclerViewClickListener mListener;

    public CommentAdapter(List<Comment> comments, Context context, CommentAdapter.RecyclerViewClickListener listener) {
        this.comments = comments;
        this.context = context;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commentText.setText(comments.get(position).getComment_text());
        holder.commentDate.setText(comments.get(position).getComment_date());
        holder.usercommentName.setText(comments.get(position).get_Name());
        Picasso.with(context)
                .load(comments.get(position).getUser_photo())
                .into(holder.usercommentImage);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView commentText;
        private TextView commentDate;
        private ImageView usercommentImage;
        private TextView usercommentName;
        private RelativeLayout mRowContainer;
        private RecyclerViewClickListener mListener;

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment);
            commentDate = itemView.findViewById(R.id.comment_time_posted);
            usercommentName = itemView.findViewById(R.id.comment_username);
            usercommentImage = itemView.findViewById(R.id.comment_profile_image);
            mRowContainer = itemView.findViewById(R.id.comment_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comment_container:
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
