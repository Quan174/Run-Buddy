package com.example.group2_bigproject;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;


import android.graphics.Typeface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfilePageActivity extends AppCompatActivity{
    FirebaseHelper fbHelper;
    private SharedPreferencesHelper spHelper;
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
    TextView  profilePageActivityHistoryButtonText;

    ConstraintLayout profilePagePersonalInformationLayout;

    LinearLayout profilePageActivityHistoryLayout;
    ListView activityHistoryListView;
    ArrayList<Route> listRoute;
    ActivityHistoryListViewAdapter activityHistoryListViewAdapter;
    ImageView profilePageAvatar;
    ImageView profilePageBackground;

    @SuppressLint("DefaultLocale")
    ImageButton btn_editAvatar;
    ImageButton btn_settings;
    int sentRequestCode;

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
        btn_editAvatar = findViewById(R.id.btn_editAvatar);
        btn_settings = findViewById(R.id.btn_Setting);
        profilePageAvatar = findViewById(R.id.profilePageAvatar);
        profilePageBackground = findViewById(R.id.profilePageBackground);


        profilePageCreatedRoutesButton = findViewById(R.id.profilePageCreatedRoutesButton);

        profilePageActivityHistoryButton = findViewById(R.id.profilePageActivityHistoryButton);

        profilePagePersonalInformationButtonText = findViewById(R.id.profilePagePersonalInformationButtonText);

        profilePageActivityHistoryButtonText = findViewById(R.id.profilePageActivityHistoryButtonText);

        profilePagePersonalInformationLayout =  findViewById(R.id.profilePagePersonalInformationLayout);

        profilePageActivityHistoryLayout = findViewById(R.id.profilePageActivityHistoryLayout);
        activityHistoryListView = findViewById(R.id.activityHistoryListView);

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

        listRoute = new ArrayList<>();
        fbHelper.readRoutes(userID, routes -> {
            listRoute = routes;
            Log.d("routesSize is ", listRoute.size() + "");
            activityHistoryListViewAdapter = new ActivityHistoryListViewAdapter(listRoute, this);
            activityHistoryListView.setAdapter(activityHistoryListViewAdapter);
            activityHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
                Route route = (Route) activityHistoryListViewAdapter.getItem(position);
                //Làm gì đó khi chọn sản phẩm (ví dụ tạo một Activity hiện thị chi tiết, biên tập ..)
//                Toast.makeText(ProfilePageActivity.this, route.routeName, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
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

            profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationLayout.setVisibility(View.VISIBLE);

            profilePageActivityHistoryLayout.setVisibility(View.GONE);

        });

        profilePageActivityHistoryButton.setOnClickListener(view -> {

//            openDatePickerDialog();

            profilePageActivityHistoryButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

            profilePageActivityHistoryButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationButton.setBackgroundColor(Color.parseColor("#BBBBBB"));

            profilePagePersonalInformationButtonText.setTypeface(null, Typeface.BOLD);

            profilePagePersonalInformationLayout.setVisibility(View.GONE);

            profilePageActivityHistoryLayout.setVisibility(View.VISIBLE);

        });

        profilePageEditInformationButton.setOnClickListener(v -> {

            ProfileEditFragment profileEditDiaglog = new ProfileEditFragment();

            profileEditDiaglog.show(getSupportFragmentManager(), "profileEdit");
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
                            sentRequestCode = 0;
                            ImageSelector.selectImage(ProfilePageActivity.this);
                        }
                        if (n == R.id.ProfileBackgroundAvatarEditor) {
                            sentRequestCode = 1;
                            ImageSelector.selectImage(ProfilePageActivity.this);                        }
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
                            Intent intent = new Intent(ProfilePageActivity.this, ViewBlockPageActivity.class);
                            startActivity(intent);
                        }
                        if (n == R.id.LogOutButton) {
                            SharedPreferencesHelper sps = new SharedPreferencesHelper(getApplicationContext());
                            sps.endSession();
                            Intent intent = new Intent(ProfilePageActivity.this, WelcomePageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        return false;
                    }
                });
            }
        });
    }

    // Use this to open date picker dialog
    public void openDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Querry database here
                Toast.makeText(ProfilePageActivity.this,"set dialog",Toast.LENGTH_LONG).show();
            }
        }, 0, 0, 0);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ImageSelector.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImageSelector.openImagePicker(this);
            } else {
                // Xử lý tình huống quyền bị từ chối
                Toast.makeText(this, "Permission have not been granted.", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (sentRequestCode == 0) {
                Glide.with(profilePageAvatar.getContext()).load(imageUri).into(profilePageAvatar);
            } else if (sentRequestCode == 1) {
                Glide.with(profilePageBackground.getContext()).load(imageUri).into(profilePageBackground);
            }
        }
    }


}