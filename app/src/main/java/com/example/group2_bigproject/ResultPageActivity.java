package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ResultPageActivity extends AppCompatActivity {
    TextView distance;
    TextView time;
    TextView pace;
    TextView CaloriesBurned;
    ImageView resultPageHomeButton;
    TextView resultPageCreateRouteButton;
    TextView resultPageShareRouteButton;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        distance = findViewById(R.id.textView18);
        pace = findViewById(R.id.textView20);
        time = findViewById(R.id.textView21);
        CaloriesBurned = findViewById(R.id.textView22);
        Double distanceTraveled = getIntent().getDoubleExtra("totalDistance", 0);
        String elapsedTime = getIntent().getStringExtra("Time");
        Long timeInMili = getIntent().getLongExtra("TimeInNumeral", 0);
        Long velocity = Math.round((distanceTraveled/1000)/((double) timeInMili /1000));
        distance.setText(distanceTraveled.toString());
        pace.setText(velocity + "m/s");
        time.setText(elapsedTime.toString());
        resultPageHomeButton = findViewById(R.id.resultPageHomeButton);
        resultPageCreateRouteButton = findViewById(R.id.resultPageCreateRouteButton);
        resultPageShareRouteButton = findViewById(R.id.resultPageShareRouteButton);

        resultPageHomeButton.setOnClickListener(v -> finish());
        resultPageCreateRouteButton.setOnClickListener(v -> {
            CreateRouteFragment createRouteDiaglog = new CreateRouteFragment();
            createRouteDiaglog.show(getSupportFragmentManager(), "createroute");

        });

        resultPageShareRouteButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultPageActivity.this, CreatePostPageActivity.class);
            startActivity(intent);
        });
    }
}