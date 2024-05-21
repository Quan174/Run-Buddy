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

public class SearchPageActivity extends AppCompatActivity {

    private SharedPreferencesHelper spHelper;
    private String userID;
    ListView listView;
    ImageView backButton;
    EditText toolBarSearchInput;
    ArrayList<User> listResults;
    SearchPageUserAdapter SearchPageUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        spHelper = new SharedPreferencesHelper(this);
        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
        toolBarSearchInput = findViewById(R.id.toolBarSearchInput);
        userID = spHelper.getSessionID();

        listResults = new ArrayList<>();
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());
        listResults.add(new User());

        SearchPageUserAdapter = new SearchPageUserAdapter(listResults);
        listView.setAdapter(SearchPageUserAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Route route = (Route) activityHistoryListViewAdapter.getItem(position);

                Intent intent = new Intent(SearchPageActivity.this, ViewProfilePageActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(v -> finish());

        toolBarSearchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    Toast.makeText(SearchPageActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}