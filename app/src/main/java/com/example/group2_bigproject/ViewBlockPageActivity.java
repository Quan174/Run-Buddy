package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewBlockPageActivity extends AppCompatActivity {
    private FirebaseHelper fbHelper;

    private SharedPreferencesHelper spHelper;
    private String userID;
    ListView listView;
    ImageView backButton;
    ArrayList<User> listResults;
    BlockedUsersListViewAdapter blockedUsersListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_block_page);
        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();
        fbHelper = new FirebaseHelper(this);
        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
        userID = spHelper.getSessionID();

        backButton.setOnClickListener(v -> finish());

        listResults = new ArrayList<>();
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());listResults.add(new User());listResults.add(new User());listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());listResults.add(new User());listResults.add(new User());
        listResults.add(new User());


        blockedUsersListViewAdapter = new BlockedUsersListViewAdapter(listResults);
        listView.setAdapter(blockedUsersListViewAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ViewBlockPageActivity.this, ViewProfilePageActivity.class);
            startActivity(intent);
        });
    }
}