package com.example.group2_bigproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentItem> cmtList;
    public SharedPreferencesHelper pfHelper;
    public FirebaseHelper fbHelper;
    public Context context;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public ImageView avaUser;
        public TextView time, userName, cmtText;

        public CommentViewHolder(View itemView) {
            super(itemView);
            avaUser = itemView.findViewById(R.id.avaUser);
            userName = itemView.findViewById(R.id.textView_Username);
            cmtText = itemView.findViewById(R.id.cmtText);
            time = itemView.findViewById(R.id.time);
        }
    }

    public CommentAdapter(List<CommentItem> cmtList, Context context) {
        this.cmtList = cmtList;
        this.context = context;
        fbHelper = new FirebaseHelper(context);
        pfHelper = new SharedPreferencesHelper(context);
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_comment, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder holder, int position) {
        CommentItem currentItem = cmtList.get(position);
        holder.time.setText(currentItem.time);
        holder.cmtText.setText(currentItem.cmtText);
        holder.userName.setText(currentItem.userName);
        holder.avaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewProfilePageActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cmtList.size();
    }
}
