package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class RoutesPage extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;
    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;
    String userID;
    ListView routesPageRoutesListView;
    ArrayList<Route> listRoute;
    RoutesPageRouteListViewAdapter routesPageRouteListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_page);

        spHelper = new SharedPreferencesHelper(this);

        menuBarHomeButton = findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        routesPageRoutesListView = findViewById(R.id.routesPageRoutesListView);


        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();

        listRoute = new ArrayList<>();
        listRoute.add(new Route());
        listRoute.add(new Route());
        listRoute.add(new Route());
        listRoute.add(new Route());
        listRoute.add(new Route());
        listRoute.add(new Route());
        listRoute.add(new Route());
        listRoute.add(new Route());

        routesPageRouteListViewAdapter = new RoutesPageRouteListViewAdapter(listRoute);
        routesPageRoutesListView.setAdapter(routesPageRouteListViewAdapter);
        routesPageRoutesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Route route = (Route) routesPageRouteListViewAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), RouteViewActivity.class);
                startActivity(intent);
            }
        });

        menuBarHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoutesPage.this, HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoutesPage.this, MapPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarSocialButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoutesPage.this, SocialPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoutesPage.this, ProfilePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
