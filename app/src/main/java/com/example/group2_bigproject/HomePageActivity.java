package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    SharedPreferencesHelper spHelper;
    ConstraintLayout mapSuggestedRoutesButton;
    ConstraintLayout mapSavedRoutesButton;
    TextView mapSuggestedRoutesButtonText;
    TextView mapSavedRoutesButtonText;
    RecyclerView recyclerView;
    Adapter adapter;
    List<PostItem> postItemList;
    String userID;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;
    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;
    EditText toolBarSearchInput;
    FirebaseHelper fbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        spHelper = new SharedPreferencesHelper(this);
        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        toolBarSearchInput = findViewById(R.id.toolBarSearchInput);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userID = spHelper.getSessionID();
        fbHelper = new FirebaseHelper(this);
        fbHelper.getPost(userID, spHelper.getUsername(), posts -> {
            adapter = new Adapter(posts, this);
            recyclerView.setAdapter(adapter);
        });
        fbHelper.postListener(userID, spHelper.getUsername(), postList -> {
            adapter.updatePostList(postList);
            adapter.notifyDataSetChanged();
        });

        menuBarHomeButton.setTextColor(getResources().getColor(R.color.light_grey));

        menuBarRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, RoutesPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MapPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarSocialButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, SocialPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, ProfilePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        toolBarSearchInput.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, SearchPageActivity.class);
            startActivity(intent);
        });
    }
}