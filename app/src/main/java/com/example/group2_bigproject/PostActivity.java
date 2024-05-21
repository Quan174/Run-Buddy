package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageView postImg;
    TextView description, date, username;

    RecyclerView commentRecyclerView;
    List<CommentItem> cmtItemList;
    CommentAdapter commentAdapter;
    ImageButton avaUser, likeBtn, commentBtn, shareBtn, optionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);

        backBtn = findViewById(R.id.backBtn);
        likeBtn = findViewById(R.id.btn_like);
        commentBtn = findViewById(R.id.cmtBtn);
        shareBtn = findViewById(R.id.shareBtn);
        optionBtn = findViewById(R.id.optionButton);
        avaUser = findViewById(R.id.avaUser);

        username = findViewById(R.id.textView_Username);
        date = findViewById(R.id.textView_Date);
        description = findViewById(R.id.textView_PostText);

        postImg = findViewById(R.id.imgPost);

        PostItem postItem = getIntent().getParcelableExtra("post_item");
        if (postItem != null){
            username.setText(postItem.getUserName());
            date.setText(postItem.getDate());
            description.setText(postItem.getDescription());
            MyUtil.setImageButtonBackground(this, postItem.getAvaUser(), avaUser);
            postImg.setImageResource(postItem.getImageResource());
        }


        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cmtItemList = new ArrayList<>();
        cmtItemList.add(new CommentItem(R.drawable.facebook_logo, "Duc",
                "It's so bad", "1 day"));
        cmtItemList.add(new CommentItem(R.drawable.ava, "Quan be",
                "It's normal", "3h"));
        cmtItemList.add(new CommentItem(R.drawable.image1, "Trung",
                "Dc dey chu", "45m"));
        cmtItemList.add(new CommentItem(R.drawable.google_logo, "Quan Lon",
                "kimchi han wuoc", "3 days"));






        commentAdapter = new CommentAdapter(cmtItemList);
        commentRecyclerView.setAdapter(commentAdapter);



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        avaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostActivity.this, "Clicked ava", Toast.LENGTH_SHORT).show();
                /* Navigating to author's profile */

            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUtil.containsSensitiveWords(PostActivity.this, postItem.getDescription()) ){
                    Toast.makeText(PostActivity.this, "Noi dung tuc tiu", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostActivity.this, "Noi dung oke", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostActivity.this, "Clicked share", Toast.LENGTH_SHORT).show();
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PostActivity.this, optionBtn);
                popupMenu.inflate(R.menu.menu_options); // Load menu resource
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.saveRouteBtn) {
                            Toast.makeText(PostActivity.this, "Clicked save route", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.blockBtn) {
                            Toast.makeText(PostActivity.this, "Clicked block", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.reportBtn) {
                            Toast.makeText(PostActivity.this, "Clicked reported", Toast.LENGTH_SHORT).show();
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
}
