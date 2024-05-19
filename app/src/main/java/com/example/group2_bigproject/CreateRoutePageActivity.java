package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateRoutePageActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    TextView createRoutePageBackButton;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_route_page);
        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();

        createRoutePageBackButton = findViewById(R.id.createRoutePageBackButton);
        String format = getIntent().getStringExtra("format");

        createRoutePageBackButton.setOnClickListener(v -> finish());
    }
}