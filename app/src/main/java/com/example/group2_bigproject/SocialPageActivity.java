package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SocialPageActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;
    TextView socialPageMessageButton;
    TextView socialPageFriendsButton;

    ConstraintLayout message1;
    LinearLayout socialPageFriendsDisplayLayout;
    LinearLayout socialPageMessagesDisplayLayout;

    ListView socialPageFriendsListView;
    ArrayList<User> listUser;
    SocialPageFriendListViewAdapter socialPageFriendListViewAdapter;
    SocialPageFriendRequestListViewAdapter socialPageFriendRequestListViewAdapter;
    TextView socialPageFriendRequestButton;
    LinearLayout SocialPageFriendsListDisplayLayout;
    LinearLayout socialPageFriendsRequestDisplayLayout;
    ImageView socialPageFriendsRequestBackButton;
    ListView socialPageFriendsRequestListView;
    private String userID;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_page);
        spHelper = new SharedPreferencesHelper(this);

        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        message1 = findViewById(R.id.message1);
        socialPageMessageButton = findViewById(R.id.socialPageMessageButton);
        socialPageFriendsButton = findViewById(R.id.socialPageFriendsButton);
        socialPageFriendsDisplayLayout = findViewById(R.id.socialPageFriendsDisplayLayout);
        socialPageMessagesDisplayLayout = findViewById(R.id.socialPageMessagesDisplayLayout);
        socialPageFriendsListView = findViewById(R.id.socialPageFriendsListView);
        socialPageFriendRequestButton = findViewById(R.id.socialPageFriendRequestButton);
        SocialPageFriendsListDisplayLayout = findViewById(R.id.SocialPageFriendsListDisplayLayout);
        socialPageFriendsRequestDisplayLayout = findViewById(R.id.socialPageFriendsRequestDisplayLayout);
        socialPageFriendsRequestBackButton = findViewById(R.id.socialPageFriendsRequestBackButton);
        socialPageFriendsRequestListView = findViewById(R.id.socialPageFriendsRequestListView);

        userID = spHelper.getSessionID();
        menuBarSocialButton.setTextColor(R.color.light_grey);

        listUser = new ArrayList<>();
        listUser.add(new User());
        listUser.add(new User());
        listUser.add(new User());
        listUser.add(new User());
        listUser.add(new User());
        listUser.add(new User());
        listUser.add(new User());
        listUser.add(new User());

        socialPageFriendListViewAdapter = new SocialPageFriendListViewAdapter(listUser);
        socialPageFriendsListView.setAdapter(socialPageFriendListViewAdapter);

        socialPageFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User route = (User) socialPageFriendListViewAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), ChatBoxActivity.class);
                startActivity(intent);
            }
        });

        socialPageFriendRequestListViewAdapter = new SocialPageFriendRequestListViewAdapter(listUser);
        socialPageFriendsRequestListView.setAdapter(socialPageFriendRequestListViewAdapter);

        menuBarHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SocialPageActivity.this, HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarRoutesButton.setOnClickListener(v -> {
            Intent intent = new Intent(SocialPageActivity.this, RoutesPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(SocialPageActivity.this, MapPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(SocialPageActivity.this, ProfilePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        message1.setOnClickListener(v -> {
            Intent intent = new Intent(SocialPageActivity.this, ChatBoxActivity.class);
            startActivity(intent);
        });

        socialPageMessageButton.setOnClickListener(v -> {
            socialPageMessageButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sent_messages));
            socialPageFriendsButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.received_messages));
            socialPageFriendsDisplayLayout.setVisibility(View.GONE);
            socialPageMessagesDisplayLayout.setVisibility(View.VISIBLE);
        });

        socialPageFriendsButton.setOnClickListener(v -> {
            socialPageMessageButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.received_messages));
            socialPageFriendsButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sent_messages));
            socialPageFriendsDisplayLayout.setVisibility(View.VISIBLE);
            socialPageMessagesDisplayLayout.setVisibility(View.GONE);
            SocialPageFriendsListDisplayLayout.setVisibility(View.VISIBLE);
            socialPageFriendsRequestDisplayLayout.setVisibility(View.GONE);

        });

        socialPageFriendRequestButton.setOnClickListener(v -> {
            SocialPageFriendsListDisplayLayout.setVisibility(View.GONE);
            socialPageFriendsRequestDisplayLayout.setVisibility(View.VISIBLE);

        });

        socialPageFriendsRequestBackButton.setOnClickListener(v -> {
            SocialPageFriendsListDisplayLayout.setVisibility(View.VISIBLE);
            socialPageFriendsRequestDisplayLayout.setVisibility(View.GONE);

        });
    }
}