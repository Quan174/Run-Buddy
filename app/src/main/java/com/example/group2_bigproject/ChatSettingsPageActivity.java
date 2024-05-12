package com.example.group2_bigproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ChatSettingsPageActivity extends AppCompatActivity {

    ImageView chatSettingsBackButton;
    ConstraintLayout chatSettingsPictureButton;
    TextView chatSettingsPictureButtonText;
    View chatSettingsPictureButtonLine;
    ConstraintLayout chatSettingsShareButton;
    TextView chatSettingsShareButtonText;
    View chatSettingsShareButtonLine;
    LinearLayout chatSettingsPicturesLayout;
    LinearLayout chatSettingsSharesLayout;
    ImageView chatSettingsAvatar;
    ImageView chatSettingsPicture1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_setting_page);

        chatSettingsBackButton = findViewById(R.id.chatSettingsBackButton);
        chatSettingsPictureButton = findViewById(R.id.chatSettingsPictureButton);
        chatSettingsPictureButtonText = findViewById(R.id.chatSettingsPictureButtonText);
        chatSettingsPictureButtonLine = findViewById(R.id.chatSettingsPictureButtonLine);
        chatSettingsShareButton = findViewById(R.id.chatSettingsShareButton);
        chatSettingsShareButtonText = findViewById(R.id.chatSettingsShareButtonText);
        chatSettingsShareButtonLine = findViewById(R.id.chatSettingsShareButtonLine);
        chatSettingsPicturesLayout = findViewById(R.id.chatSettingsPicturesLayout);
        chatSettingsSharesLayout = findViewById(R.id.chatSettingsSharesLayout);
        chatSettingsAvatar = findViewById(R.id.chatSettingsAvatar);
        chatSettingsPicture1 = findViewById(R.id.chatSettingsPicture1);

        chatSettingsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatSettingsPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatSettingsPictureButtonText.setTypeface(null, Typeface.BOLD);
                chatSettingsPictureButtonLine.setVisibility(View.VISIBLE);
                chatSettingsShareButtonText.setTypeface(null, Typeface.NORMAL);
                chatSettingsShareButtonLine.setVisibility(View.INVISIBLE);
                chatSettingsPicturesLayout.setVisibility(View.VISIBLE);
                chatSettingsSharesLayout.setVisibility(View.INVISIBLE);
            }
        });

        chatSettingsShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatSettingsShareButtonText.setTypeface(null, Typeface.BOLD);
                chatSettingsShareButtonLine.setVisibility(View.VISIBLE);
                chatSettingsPictureButtonText.setTypeface(null, Typeface.NORMAL);
                chatSettingsPictureButtonLine.setVisibility(View.INVISIBLE);
                chatSettingsPicturesLayout.setVisibility(View.INVISIBLE);
                chatSettingsSharesLayout.setVisibility(View.VISIBLE);
            }
        });

        chatSettingsAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatSettingsPageActivity.this, FullScreenActivity.class);
                // Optionally pass the image resource if it's dynamic
                intent.putExtra("imageRes", R.drawable.black_circle_shape);
                startActivity(intent);
            }
        });

        chatSettingsPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatSettingsPageActivity.this, FullScreenActivity.class);
                // Optionally pass the image resource if it's dynamic
                intent.putExtra("imageRes", R.drawable.screenshot_2024_05_06_160853);
                startActivity(intent);
            }
        });
    }
}