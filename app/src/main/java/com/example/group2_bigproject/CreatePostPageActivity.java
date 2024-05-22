package com.example.group2_bigproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;

public class CreatePostPageActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    TextView createPostPageBackButton;
    TextView createPostButton;
    ConstraintLayout createPostResultDisplay;
    ConstraintLayout createPostRouteDisplay;
    EditText contentEditText;
    TextView resultDisplayDetailText;
    TextView routeNameTextView;
    private String userID;
    FirebaseHelper fbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_page);
        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();
        createPostPageBackButton = findViewById(R.id.createPostPageBackButton);
        createPostButton = findViewById(R.id.createPostButton);
        createPostResultDisplay = findViewById(R.id.createPostResultDisplay);
        createPostRouteDisplay = findViewById(R.id.createPostRouteDisplay);
        contentEditText = findViewById(R.id.contentEditText);
        resultDisplayDetailText = findViewById(R.id.resultDisplayDetailText);
        routeNameTextView = findViewById(R.id.routeNameTextView);

        String routeID = getIntent().getStringExtra("routeID");
        String format = getIntent().getStringExtra("format");
        if(format.equals("result")){
            createPostResultDisplay.setVisibility(View.VISIBLE);

        } else {
            createPostRouteDisplay.setVisibility(View.VISIBLE);
        }
        fbHelper = new FirebaseHelper(this);

        createPostPageBackButton.setOnClickListener(v -> finish());
        createPostButton.setOnClickListener(v -> {
            if(!contentEditText.getText().toString().isEmpty()){
                Date currentTime = Calendar.getInstance().getTime();
                fbHelper.savePost(currentTime.toString(), spHelper.getUsername(), contentEditText.getText().toString(), spHelper.getSessionID(), routeID);
                finish();
            } else {
                Toast.makeText(this, "No content", Toast.LENGTH_SHORT).show();
            }

        });
    }
}