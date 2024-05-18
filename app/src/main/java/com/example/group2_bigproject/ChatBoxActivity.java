package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatBoxActivity extends AppCompatActivity {

    ImageView chatScreenBackButton;
    ImageView chatScreenSettingsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen_page);

        chatScreenBackButton = findViewById(R.id.chatScreenBackButton);
        chatScreenSettingsButton = findViewById(R.id.chatScreenSettingsButton);

        chatScreenBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatScreenSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatBoxActivity.this, ChatSettingsPageActivity.class);
                startActivity(intent);
            }
        });
    }
}