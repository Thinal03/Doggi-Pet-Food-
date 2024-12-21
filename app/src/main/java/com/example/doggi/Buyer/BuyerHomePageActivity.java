package com.example.doggi.Buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doggi.R;

public class BuyerHomePageActivity extends AppCompatActivity {

    private Button productsButton, educationalContentButton, personalInformationButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);

        productsButton = findViewById(R.id.productsButton);
        educationalContentButton = findViewById(R.id.educationalContentButton);
        personalInformationButton = findViewById(R.id.personalInformationButton);


        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerHomePageActivity.this, ProductActivity.class);
                startActivity(intent);

                educationalContentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle Educational Content button click
                        startActivity(new Intent(BuyerHomePageActivity.this, EduContentActivity.class));
                    }
                });

                personalInformationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle Personal Information button click
                        startActivity(new Intent(BuyerHomePageActivity.this, PersonalInformationActivity.class));
                    }
                });

            }
        });
    }

}

