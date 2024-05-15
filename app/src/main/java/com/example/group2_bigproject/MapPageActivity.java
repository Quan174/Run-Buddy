package com.example.group2_bigproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MapPageActivity extends AppCompatActivity {
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
    ConstraintLayout mapStartTrackingButton;
    ImageView mapWalkingButton;
    ImageView mapCyclingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_page);

        mapSuggestedRoutesButton = findViewById(R.id.mapSuggestedRoutesButton);
        mapSavedRoutesButton = findViewById(R.id.mapSavedRoutesButton);
        mapSuggestedRoutesButtonText = findViewById(R.id.mapSuggestedRoutesButtonText);
        mapSavedRoutesButtonText = findViewById(R.id.mapSavedRoutesButtonText);
        mapSuggestedRoutesLayout = findViewById(R.id.mapSuggestedRoutesLayout);
        mapStartTrackingButton = findViewById(R.id.mapStartTrackingButton);

        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        mapWalkingButton = findViewById(R.id.mapWalkingButton);
        mapCyclingButton = findViewById(R.id.mapCyclingButton);

        menuBarMapButton.setTextColor(R.color.light_grey);
        mapSuggestedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mapSuggestedRoutesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mapSuggestedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                mapSavedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                mapSavedRoutesButtonText.setTypeface(null, Typeface.NORMAL);
            }
        });

        mapSavedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mapSavedRoutesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mapSavedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                mapSuggestedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                mapSuggestedRoutesButtonText.setTypeface(null, Typeface.NORMAL);
            }
        });

        mapStartTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, TrackingPageActivity.class);
                startActivity(intent);
            }
        });

        menuBarHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, RoutesPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, SocialPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, ProfilePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        mapCyclingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapCyclingButton.setImageResource(R.drawable.circle_shape);
                mapWalkingButton.setImageResource(R.drawable.black_circle_shape);
            }
        });

        mapWalkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapCyclingButton.setImageResource(R.drawable.black_circle_shape);
                mapWalkingButton.setImageResource(R.drawable.circle_shape);
            }
        });
    }
}
