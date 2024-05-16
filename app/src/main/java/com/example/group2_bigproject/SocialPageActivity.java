package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SocialPageActivity extends AppCompatActivity {

    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;

    ConstraintLayout message1;
    private int userID;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_page);

        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        message1 = findViewById(R.id.message1);
        userID = getIntent().getIntExtra("userID", -1 );
        menuBarSocialButton.setTextColor(R.color.light_grey);

        menuBarHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialPageActivity.this, HomePageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", "000");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialPageActivity.this, RoutesPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", "000");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialPageActivity.this, MapPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", "000");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialPageActivity.this, ProfilePageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", "000");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialPageActivity.this, ChatBoxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", "000");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}