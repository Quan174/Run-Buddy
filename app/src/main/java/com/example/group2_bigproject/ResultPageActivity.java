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