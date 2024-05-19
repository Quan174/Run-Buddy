package com.example.group2_bigproject;

import static android.Manifest.permission.READ_MEDIA_IMAGES;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


public class ProfilePageActivity extends AppCompatActivity {

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

    ImageButton btn_editAvatar;
    ImageButton btn_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        profilePageEditInformationButton = findViewById(R.id.profilePageEditInformationButton);
        btn_editAvatar = findViewById(R.id.btn_editAvatar);
        btn_settings = findViewById(R.id.btn_Setting);


        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID", "Default");

        //menuBarProfileButton.setTextColor(R.color.light_grey);

        menuBarHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, RoutesPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, MapPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, SocialPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        profilePageEditInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileEditFragment profileEditDiaglog = new ProfileEditFragment();
                profileEditDiaglog.show(getSupportFragmentManager(), "profileedit");
            }
        });


        btn_editAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ProfilePageActivity.this,btn_editAvatar);
                getMenuInflater().inflate(R.menu.context_menu_avatar_profile,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int n = item.getItemId();
                        if (n == R.id.ProfileAvatarEditor) {
                            // Avatar change here
                        }
                        if (n == R.id.ProfileBackgroundAvatarEditor) {
                            // Background change here
                        }
                        return false;
                    }
                });
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ProfilePageActivity.this,btn_settings);
                getMenuInflater().inflate(R.menu.context_menu_setting_profile,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int n = item.getItemId();
                        if (n == R.id.BlockListViewer) {
                            // Block list view here
                        }
                        if (n == R.id.LogOutButton) {
                            // Log out here
                        }
                        return false;
                    }
                });
            }
        });
    }
}