package com.example.group2_bigproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);


        Button btn_SignUp = findViewById(R.id.btn_SignUp);

        TextView hereClickView = findViewById(R.id.textView_HereClicker);
        hereClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPageActivity.this,HomePageActivity.class);
                startActivity(intent);
                Toast.makeText(SignUpPageActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
