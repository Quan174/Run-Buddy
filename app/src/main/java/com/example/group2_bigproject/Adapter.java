package com.example.group2_bigproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.PostViewHolder> {
    private List<PostItem> postList;

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public ImageView postImage;
        public ImageButton avaUser, likeBtn, commentBtn, shareBtn, optionBtn;
        public TextView postDate;
        public TextView userName;
        public TextView postDescription;

        public PostViewHolder(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.imgPost);
            avaUser = itemView.findViewById(R.id.avaUser);
            likeBtn = itemView.findViewById(R.id.btn_like);
            commentBtn = itemView.findViewById(R.id.cmtBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            optionBtn = itemView.findViewById(R.id.optionButton);
            userName = itemView.findViewById(R.id.textView_Username);
            postDescription = itemView.findViewById(R.id.textView_PostText);
            postDate = itemView.findViewById(R.id.textView_Date);
        }
    }

    public Adapter(List<PostItem> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostItem currentItem = postList.get(position);
        holder.postImage.setImageResource(currentItem.getImageResource());
        holder.postDate.setText(currentItem.getDate());
        holder.postDescription.setText(currentItem.getDescription());
        holder.userName.setText(currentItem.getUserName());
        MyUtil.setImageButtonBackground(holder.itemView.getContext(), currentItem.getAvaUser(),holder.avaUser);
        holder.avaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Clicked ava", Toast.LENGTH_SHORT).show();
                /* Navigating to author's profile */

            }
        });

        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PostActivity.class);
                intent.putExtra("post_item", currentItem);
                holder.itemView.getContext().startActivity(intent);            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Clicked like", Toast.LENGTH_SHORT).show();
            }
        });

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Clicked share", Toast.LENGTH_SHORT).show();
            }
        });

        holder.optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.optionBtn);
                popupMenu.inflate(R.menu.menu_options); // Load menu resource
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.saveRouteBtn) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked save route", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.blockBtn) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked block", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.reportBtn) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked reported", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

