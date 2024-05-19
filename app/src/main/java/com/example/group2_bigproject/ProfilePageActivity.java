package com.example.group2_bigproject;


import android.graphics.Color;
import android.widget.LinearLayout;


import android.graphics.Typeface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ProfilePageActivity extends AppCompatActivity{
    FirebaseHelper fbHelper;
    private SharedPreferencesHelper spHelper;
    ConstraintLayout mapSuggestedRoutesButton;
    ConstraintLayout mapSavedRoutesButton;
    TextView mapSuggestedRoutesButtonText;
    TextView mapSavedRoutesButtonText;

    HorizontalScrollView mapSuggestedRoutesLayout;
    HorizontalScrollView mapSavedRoutesLayout;
    TextView menuBarHomeButton;
    TextView menuBarRoutesButton;
    TextView menuBarMapButton;
    TextView menuBarSocialButton;
    TextView menuBarProfileButton;
    TextView profilePageEditInformationButton;
    private String userID;
    private TextView nameProfile;
    private TextView genderProfile;
    private TextView birthdayProfile;
    private TextView phoneProfile;
    private TextView addressProfile;
    private TextView heightProfile;
    private TextView weightProfile;
    ConstraintLayout profilePagePersonalInformationButton;

    ConstraintLayout profilePageCreatedRoutesButton;

    ConstraintLayout profilePageActivityHistoryButton;

    TextView profilePagePersonalInformationButtonText;

    TextView profilePageCreatedRoutesButtonText;

    TextView  profilePageActivityHistoryButtonText;

    ConstraintLayout profilePagePersonalInformationLayout;

    LinearLayout profilePageActivityHistoryLayout;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        fbHelper = new FirebaseHelper(this);
        spHelper = new SharedPreferencesHelper(this);
        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        profilePageEditInformationButton = findViewById(R.id.profilePageEditInformationButton);
        nameProfile = findViewById(R.id.textView46);
        genderProfile = findViewById(R.id.profilePageGenderField);
        birthdayProfile = findViewById(R.id.textView59);
        phoneProfile = findViewById(R.id.textView60);
        addressProfile = findViewById(R.id.textView61);
        heightProfile = findViewById(R.id.textView62);
        weightProfile = findViewById(R.id.textView63);
        profilePagePersonalInformationButton = findViewById(R.id.profilePagePersonalInformationButton);

        profilePageCreatedRoutesButton = findViewById(R.id.profilePageCreatedRoutesButton);

        profilePageActivityHistoryButton = findViewById(R.id.profilePageActivityHistoryButton);

        profilePagePersonalInformationButtonText = findViewById(R.id.profilePagePersonalInformationButtonText);

        profilePageCreatedRoutesButtonText = findViewById(R.id.profilePageCreatedRoutesButtonText);

        profilePageActivityHistoryButtonText = findViewById(R.id.profilePageActivityHistoryButtonText);

        profilePagePersonalInformationLayout =  findViewById(R.id.profilePagePersonalInformationLayout);

        profilePageActivityHistoryLayout = findViewById(R.id.profilePageActivityHistoryLayout);

        userID = spHelper.getSessionID();
        Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();

        fbHelper.readUser(userID, user -> {
            nameProfile.setText(user.name);
            genderProfile.setText(user.Gender);
            birthdayProfile.setText(user.Birthday);
            phoneProfile.setText(user.phone);
            addressProfile.setText(user.address);
            heightProfile.setText(String.format("%s cm", user.height));
            weightProfile.setText(String.format("%s kg", user.weight));
        });

        menuBarProfileButton.setTextColor(R.color.light_grey);

        menuBarHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarRoutesButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePageActivity.this, RoutesPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePageActivity.this, MapPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        menuBarSocialButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePageActivity.this, SocialPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        profilePageEditInformationButton.setOnClickListener(v -> {

            ProfileEditFragment profileEditDiaglog = new ProfileEditFragment();

            profileEditDiaglog.show(getSupportFragmentManager(), "profileedit");

        });



        profilePagePersonalInformationButton.setOnClickListener(view -> {

            profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

            profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);

            profilePageCreatedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePageCreatedRoutesButtonText.setTypeface(null, Typeface.BOLD);

            profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationLayout.setVisibility(View.VISIBLE);

            profilePageActivityHistoryLayout.setVisibility(View.GONE);

        });
        profilePageCreatedRoutesButton.setOnClickListener(view -> {

            profilePageCreatedRoutesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

            profilePageCreatedRoutesButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);

            profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationLayout.setVisibility(View.GONE);

            profilePageActivityHistoryLayout.setVisibility(View.GONE);

        });

        profilePageActivityHistoryButton.setOnClickListener(view -> {

            profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

            profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);

            profilePageCreatedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePageCreatedRoutesButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationLayout.setVisibility(View.GONE);

            profilePageActivityHistoryLayout.setVisibility(View.VISIBLE);

        });

        profilePageEditInformationButton.setOnClickListener(v -> {

            ProfileEditFragment profileEditDiaglog = new ProfileEditFragment();

            profileEditDiaglog.show(getSupportFragmentManager(), "profileEdit");
        });
    }
}