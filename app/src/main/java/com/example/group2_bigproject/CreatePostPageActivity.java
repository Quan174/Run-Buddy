package com.example.group2_bigproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

public class CreatePostPageActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    TextView createPostPageBackButton;
    TextView createPostButton;
    ConstraintLayout createPostResultDisplay;
    ConstraintLayout createPostRouteDisplay;
    TextView chooseRouteButton;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_page);
        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();
        createPostPageBackButton = findViewById(R.id.createPostPageBackButton);
        createPostButton = findViewById(R.id.createPostButton);
        createPostResultDisplay = findViewById(R.id.createPostResultDisplay);
        createPostRouteDisplay = findViewById(R.id.createPostRouteDisplay);
//        String format = getIntent().getStringExtra("format");
//
//        if(format.equals("result")){
//            createPostResultDisplay.setVisibility(View.VISIBLE);
//        } else {
//            createPostRouteDisplay.setVisibility(View.VISIBLE);
//        }
        createPostPageBackButton.setOnClickListener(v -> finish());
        createPostButton.setOnClickListener(v -> finish());

        chooseRouteButton.setOnClickListener(v -> {
            //Open choose route/results dialog
        });
    }
}