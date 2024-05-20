package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class HomePageActivity extends AppCompatActivity {
    SharedPreferencesHelper spHelper;
    ConstraintLayout mapSuggestedRoutesButton;
    ConstraintLayout mapSavedRoutesButton;
    TextView mapSuggestedRoutesButtonText;
    TextView mapSavedRoutesButtonText;

    HorizontalScrollView mapSuggestedRoutesLayout;
    HorizontalScrollView mapSavedRoutesLayout;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;
    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;
    private String userID;


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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postItemList = new ArrayList<>();
        postItemList.add(new PostItem(R.drawable.image1, R.drawable.round_person_24, "25/5/2024", "Trung", "First Test"));
        postItemList.add(new PostItem(R.drawable.facebook_logo, R.drawable.google_logo,"26/5/2024", "Quan be", "Second Test"));
        postItemList.add(new PostItem(R.drawable.facebook_logo, android.R.drawable.ic_notification_overlay,"27/5/2024", "Quan lon", "Third Test"));
        postItemList.add(new PostItem(R.drawable.facebook_logo, R.drawable.ava,"28/5/2024", "Duc", "4th Test"));
        postItemList.add(new PostItem(R.drawable.ava, R.drawable.picture ,"29/5/2024", "Quang", "5th Test"));

        userID = spHelper.getSessionID();
        Toast.makeText(this, userID + "", Toast.LENGTH_SHORT).show();


        menuBarRoutesButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, RoutesPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, MapPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
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

    }
}
