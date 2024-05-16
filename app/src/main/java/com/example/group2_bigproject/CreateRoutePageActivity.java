package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateRoutePageActivity extends AppCompatActivity {
    TextView createRoutePageBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_route_page);
        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID", "Default");

        createRoutePageBackButton = findViewById(R.id.createRoutePageBackButton);
        String format = getIntent().getStringExtra("format");

        createRoutePageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}