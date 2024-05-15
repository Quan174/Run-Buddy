package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ResultPageActivity extends AppCompatActivity {
    ImageView resultPageHomeButton;
    TextView resultPageCreateRouteButton;
    TextView resultPageShareRouteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        resultPageHomeButton = findViewById(R.id.resultPageHomeButton);
        resultPageCreateRouteButton = findViewById(R.id.resultPageCreateRouteButton);
        resultPageShareRouteButton = findViewById(R.id.resultPageShareRouteButton);
        resultPageHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resultPageCreateRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultPageActivity.this, CreateRoutePageActivity.class);
                startActivity(intent);
            }
        });

        resultPageShareRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultPageActivity.this, CreatePostPageActivity.class);
                intent.putExtra("format", "result");
                startActivity(intent);
            }
        });
    }
}