package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TrackingPageActivity extends AppCompatActivity{

    TextView trackingPageBackButton;
    ConstraintLayout trackingPagePauseButton;
    TextView trackingPagePauseButtonText;
    TextView trackingPageFinishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_page);

        trackingPageBackButton = findViewById(R.id.trackingPageBackButton);
        trackingPagePauseButton = findViewById(R.id.trackingPagePauseButton);
        trackingPagePauseButtonText = findViewById(R.id.trackingPagePauseButtonText);
        trackingPageFinishButton = findViewById(R.id.trackingPageFinishButton);
        trackingPageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        trackingPagePauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackingPagePauseButtonText.getText()=="Pause") {
                    trackingPagePauseButtonText.setText("Continue");
                    trackingPageFinishButton.setVisibility(View.VISIBLE);
                } else {
                    trackingPagePauseButtonText.setText("Pause");
                    trackingPageFinishButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        trackingPageFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackingPageActivity.this, ResultPageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}