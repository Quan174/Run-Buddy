package com.example.group2_bigproject;

import android.widget.ImageView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ViewProfilePageActivity extends AppCompatActivity{
    FirebaseHelper fbHelper;
    private SharedPreferencesHelper spHelper;
    ImageView backButton;
    TextView profilePageEditInformationButton;
    private String userID;
    private TextView nameProfile;
    private TextView genderProfile;
    private TextView birthdayProfile;
    private TextView phoneProfile;
    private TextView addressProfile;
    private TextView heightProfile;
    private TextView weightProfile;

    @SuppressLint("DefaultLocale")
    ImageButton btn_editAvatar;
    ImageButton btn_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_page);
        fbHelper = new FirebaseHelper(this);
        spHelper = new SharedPreferencesHelper(this);
        profilePageEditInformationButton = findViewById(R.id.profilePageEditInformationButton);
        nameProfile = findViewById(R.id.textView46);
        genderProfile = findViewById(R.id.profilePageGenderField);
        birthdayProfile = findViewById(R.id.textView59);
        phoneProfile = findViewById(R.id.textView60);
        addressProfile = findViewById(R.id.textView61);
        heightProfile = findViewById(R.id.textView62);
        weightProfile = findViewById(R.id.textView63);
        backButton = findViewById(R.id.backButton);

        userID = spHelper.getSessionID();
        Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();

        fbHelper.readUser(userID, user -> {
            nameProfile.setText(user.name);
            genderProfile.setText(user.gender);
            birthdayProfile.setText(user.birthday);
            phoneProfile.setText(user.phone);
            addressProfile.setText(user.address);
            heightProfile.setText(String.format("%s cm", user.height));
            weightProfile.setText(String.format("%s kg", user.weight));
        });

        backButton.setOnClickListener(v -> {
            finish();
        });
    }

}