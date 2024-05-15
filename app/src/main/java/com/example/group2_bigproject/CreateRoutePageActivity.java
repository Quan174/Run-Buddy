package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateRoutePageActivity extends AppCompatActivity {
    ImageView resultPageHomeButton;
    TextView resultPageCreateRouteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        resultPageHomeButton = findViewById(R.id.resultPageHomeButton);
        resultPageCreateRouteButton = findViewById(R.id.resultPageCreateRouteButton;
        resultPageHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        resultPageCreateRouteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ResultPageActivity.this, CreateRoutePageActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}