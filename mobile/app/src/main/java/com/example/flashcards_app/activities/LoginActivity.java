package com.example.flashcards_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flashcards_app.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button signup;
    Button signin;
    ImageView back;
    TextInputLayout username_email;
    TextInputLayout password;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.btn_signup);
        signin = findViewById(R.id.btn_signin);
        back = findViewById(R.id.btn_back);
        username_email = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);

        back.setOnClickListener(v -> {
            accessMainActivity();
        });

        signup.setOnClickListener(v -> {
            accessRegisterScreen();
        });

        signin.setOnClickListener(v -> {
            accessHomeActivity();
        });
    }

    private void accessRegisterScreen() {
        Toast.makeText(this, username_email.getEditText().getText() + " " + password.getEditText().getText(), Toast.LENGTH_LONG).show();
        Intent in = new Intent(this, RegisterActivity.class);
        startActivity(in);
    }

    private void accessMainActivity() {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }

    private void accessHomeActivity() {
        Intent in = new Intent(this, HomeActivity.class);
        startActivity(in);
    }
}