package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ChatBoxActivity extends AppCompatActivity {
    private TextView upperUsername;
    private String targetUsername;
    private String currentUsername;
    private FirebaseHelper fbHelper;

    private TextInputEditText chatTyper;
    private ImageView sendButton;
    ImageView chatScreenBackButton;
    ImageView chatScreenSettingsButton;
    private SharedPreferencesHelper spHelper;
    ListView messageListView;
    MessageListViewAdapter messageListViewAdapter;
    ArrayList<Message> listMessage;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen_page);

        upperUsername = findViewById(R.id.textView32);
        chatTyper = findViewById(R.id.chatTyper);
        sendButton = findViewById(R.id.sendButton);
        spHelper = new SharedPreferencesHelper(this);
        chatScreenBackButton = findViewById(R.id.chatScreenBackButton);
        chatScreenSettingsButton = findViewById(R.id.chatScreenSettingsButton);
        messageListView = findViewById(R.id.messageListView);
        userID = spHelper.getSessionID();
        targetUsername = getIntent().getStringExtra("targetUsername");
        currentUsername = spHelper.getUsername();
        Log.d("FROM CHAT BOX", "USERNAME1 is " + currentUsername + " USERNAME2 IS " + targetUsername);
        fbHelper = new FirebaseHelper(this);
        upperUsername.setText(targetUsername);

        fbHelper.getMessageDialog(currentUsername, targetUsername, messageDialog -> {
            listMessage = messageDialog.messageList;
            messageListViewAdapter = new MessageListViewAdapter(listMessage, currentUsername);
            messageListView.setAdapter(messageListViewAdapter);
            chatScreenBackButton.setOnClickListener(v -> finish());
            fbHelper.messageDialogListener(currentUsername, targetUsername, messages -> {
                messageListViewAdapter.setListMessage(messages);
                messageListViewAdapter.notifyDataSetChanged();
            });
        });


        sendButton.setOnClickListener(v -> {
            if (!chatTyper.getText().toString().isEmpty()) {
                fbHelper.addMessagesToDialog(currentUsername, targetUsername, chatTyper.getText().toString());
            }
        });

        chatScreenSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChatBoxActivity.this, ChatSettingsPageActivity.class);
            startActivity(intent);
        });
    }
}