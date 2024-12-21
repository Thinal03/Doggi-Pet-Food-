package com.example.doggi.Buyer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doggi.LoginActivity;
import com.example.doggi.R;
import com.example.doggi.Database.DatabaseHelper;

public class PersonalInformationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView itemImageView;
    private EditText emailEditText, usernameEditText, contactEditText, addressEditText;
    private Button uploadImageButton, saveButton, logoutButton;
    private Uri imageUri;
    private DatabaseHelper databaseHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation); // Change to the appropriate layout for an activity

        databaseHelper = new DatabaseHelper(this);

        itemImageView = findViewById(R.id.itemImageView);
        emailEditText = findViewById(R.id.profile_email);
        usernameEditText = findViewById(R.id.profile_username);
        contactEditText = findViewById(R.id.profile_contact);
        addressEditText = findViewById(R.id.profile_address);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        saveButton = findViewById(R.id.profile_save_button);
        logoutButton = findViewById(R.id.profile_logout_button);

        // Retrieve user email from intent
        if (getIntent().hasExtra("user_email")) {
            userEmail = getIntent().getStringExtra("user_email");
        }



        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            itemImageView.setImageURI(imageUri);
        }
    }



    private void saveChanges() {
        String username = usernameEditText.getText().toString();
        String contact = contactEditText.getText().toString();
        String address = addressEditText.getText().toString();

        boolean isUpdated = databaseHelper.checkUser(userEmail, username);

        if (isUpdated) {
            Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        // Clear user session, for example, SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to login activity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
