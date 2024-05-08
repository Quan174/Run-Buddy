package com.example.group2_bigproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        TextView hereClickView = findViewById(R.id.textView_HereClicker);
        Intent signUpClick = new Intent(this, SignUpPageActivity.class);
        Button btn_SignIn = findViewById(R.id.btn_SignIn);

        hereClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpClick);
            }
        });

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInPageActivity.this,HomePageActivity.class);
                startActivity(intent);
                Toast.makeText(SignInPageActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
