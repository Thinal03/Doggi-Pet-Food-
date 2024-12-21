package com.example.doggi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doggi.Buyer.BuyerHomePageActivity;
import com.example.doggi.Database.DatabaseHelper;
import com.example.doggi.Seller.SellerHomePageActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        View registerPrompt = findViewById(R.id.registerPrompt);

        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
        registerPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void handleLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUser(username, password)) {
            String role = databaseHelper.getUserRole(username);
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // Save user session
            getSharedPreferences("UserSession", MODE_PRIVATE)
                    .edit()
                    .putString("username", username)
                    .putString("role", role)
                    .apply();

            // Redirect to the main activity based on the user role
            Intent intent;
            if ("buyer".equalsIgnoreCase(role)) {
                intent = new Intent(this, BuyerHomePageActivity.class);
            } else {
                intent = new Intent(this, SellerHomePageActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
