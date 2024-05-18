package com.example.group2_bigproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ProfilePageActivity extends AppCompatActivity {

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
    TextView profilePageEditInformationButton;
    ConstraintLayout profilePagePersonalInformationButton;
    ConstraintLayout profilePageCreatedRoutesButton;
    ConstraintLayout profilePageActivityHistoryButton;
    TextView profilePagePersonalInformationButtonText;
    TextView profilePageCreatedRoutesButtonText;
    TextView  profilePageActivityHistoryButtonText;
    ConstraintLayout profilePagePersonalInformationLayout;
    LinearLayout profilePageActivityHistoryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        profilePageEditInformationButton = findViewById(R.id.profilePageEditInformationButton);
        profilePagePersonalInformationButton = findViewById(R.id.profilePagePersonalInformationButton);
        profilePageCreatedRoutesButton = findViewById(R.id.profilePageCreatedRoutesButton);
        profilePageActivityHistoryButton = findViewById(R.id.profilePageActivityHistoryButton);
        profilePagePersonalInformationButtonText = findViewById(R.id.profilePagePersonalInformationButtonText);
        profilePageCreatedRoutesButtonText = findViewById(R.id.profilePageCreatedRoutesButtonText);
        profilePageActivityHistoryButtonText = findViewById(R.id.profilePageActivityHistoryButtonText);
        profilePagePersonalInformationLayout =  findViewById(R.id.profilePagePersonalInformationLayout);
        profilePageActivityHistoryLayout = findViewById(R.id.profilePageActivityHistoryLayout);

        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID", "Default");

        menuBarProfileButton.setTextColor(R.color.light_grey);

        menuBarHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, RoutesPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, MapPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, SocialPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        profilePageEditInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditFragment profileEditDiaglog = new ProfileEditFragment();
                profileEditDiaglog.show(getSupportFragmentManager(), "profileedit");
            }
        });

        profilePagePersonalInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);
                profilePageCreatedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                profilePageCreatedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);
                profilePagePersonalInformationLayout.setVisibility(View.VISIBLE);
                profilePageActivityHistoryLayout.setVisibility(View.GONE);
            }
        });

        profilePageCreatedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePageCreatedRoutesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profilePageCreatedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);
                profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);
                profilePagePersonalInformationLayout.setVisibility(View.GONE);
                profilePageActivityHistoryLayout.setVisibility(View.GONE);
            }
        });

        profilePageActivityHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);
                profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);
                profilePageCreatedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                profilePageCreatedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                profilePagePersonalInformationLayout.setVisibility(View.GONE);
                profilePageActivityHistoryLayout.setVisibility(View.VISIBLE);

            }
        });
    }
}