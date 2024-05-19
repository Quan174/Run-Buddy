package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class RouteViewActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    String userID;
    ImageView routeViewBackButton;
    ImageView routeViewRouteImage;
    TextView routeViewRouteName;
    TextView routeViewRouteAuthor;
    TextView routeViewRouteCreatedDate;
    TextView routeViewRouteDescription;
    ImageView routeViewRouteType;
    TextView routeViewDistance;
    TextView routeViewLocation;
    TextView routeViewJoinChallengeButton;
    TextView routeViewReportButton;
    TextView routeViewSaveButton;
    TextView routeViewShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_view_page);

        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();

        routeViewBackButton = findViewById(R.id.routeViewBackButton);
        routeViewRouteImage = findViewById(R.id.routeViewRouteImage);
        routeViewRouteName = findViewById(R.id.routeViewRouteName);
        routeViewRouteAuthor = findViewById(R.id.routeViewRouteAuthor);
        routeViewRouteCreatedDate = findViewById(R.id.routeViewRouteCreatedDate);
        routeViewRouteDescription = findViewById(R.id.routeViewRouteDescription);
        routeViewRouteType = findViewById(R.id.routeViewRouteType);
        routeViewDistance = findViewById(R.id.routeViewDistance);
        routeViewLocation = findViewById(R.id.routeViewLocation);
        routeViewJoinChallengeButton = findViewById(R.id.routeViewJoinChallengeButton);
        routeViewReportButton = findViewById(R.id.routeViewReportButton);
        routeViewSaveButton = findViewById(R.id.routeViewSaveButton);
        routeViewShareButton = findViewById(R.id.routeViewShareButton);

        routeViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
