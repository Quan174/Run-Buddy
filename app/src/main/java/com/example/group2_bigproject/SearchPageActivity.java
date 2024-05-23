package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchPageActivity extends AppCompatActivity {
    private FirebaseHelper fbHelper;
    TextView peopleButton, routesButton;

    private SharedPreferencesHelper spHelper;
    private String userID;
    ListView listView;
    ImageView backButton;
    EditText toolBarSearchInput;
    ArrayList<User> listResults;
    ArrayList<Route> listRoutes;
    SearchPageUserAdapter SearchPageUserAdapter;
    RoutesPageRouteListViewAdapter routesPageRouteListViewAdapter;
    Boolean isPeople = true;

//    @SuppressLint("WrongViewCast")
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();
        fbHelper = new FirebaseHelper(this);
        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
        peopleButton = findViewById(R.id.peopleButton);
        routesButton = findViewById(R.id.routesButton);

        toolBarSearchInput = findViewById(R.id.toolBarSearchInput);
        userID = spHelper.getSessionID();

        listResults = new ArrayList<>();
        listRoutes = new ArrayList<>();

        peopleButton.setOnClickListener(v -> {
            if(!isPeople){
                peopleButton.setBackground(getResources().getDrawable(R.drawable.sent_messages));
                routesButton.setBackground(getResources().getDrawable(R.drawable.received_messages));
                isPeople = true;
            }
        });
        routesButton.setOnClickListener(v -> {
            if(isPeople){
                peopleButton.setBackground(getResources().getDrawable(R.drawable.received_messages));
                routesButton.setBackground(getResources().getDrawable(R.drawable.sent_messages));
                isPeople = false;
            }
        });

        backButton.setOnClickListener(v -> finish());

        toolBarSearchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    if(isPeople){
                        fbHelper.searchForUser(userID ,s.toString(), users -> {
                            listResults = users;
                            SearchPageUserAdapter = new SearchPageUserAdapter(listResults, SearchPageActivity.this);
                            listView.setAdapter(SearchPageUserAdapter);
                            listView.setOnItemClickListener((parent, view, position, id) -> {

//                            Intent intent = new Intent(SearchPageActivity.this, ViewProfilePageActivity.class);
//                            startActivity(intent);
                            });
                        });
                    } else {
                        fbHelper.searchRouteByName(s.toString(), routes -> {
                            listRoutes = routes;
                            routesPageRouteListViewAdapter = new RoutesPageRouteListViewAdapter(listRoutes, SearchPageActivity.this);
                            listView.setAdapter(routesPageRouteListViewAdapter);
                        });
                    }

                }
            }
        });
    }
}