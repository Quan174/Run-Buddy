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
        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID", "Default");
        resultPageHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resultPageCreateRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResultPageActivity.this, CreateRoutePageActivity.class);
//                startActivity(intent);
                CreateRouteFragment createRouteDiaglog = new CreateRouteFragment();
                createRouteDiaglog.show(getSupportFragmentManager(), "createroute");

            }
        });

        resultPageShareRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultPageActivity.this, CreatePostPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.putExtra("format", "result");
                startActivity(intent);
            }
        });
    }
}