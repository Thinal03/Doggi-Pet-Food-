package com.example.doggi.Seller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doggi.Buyer.BuyerHomePageActivity;
import com.example.doggi.Buyer.EduContentActivity;
import com.example.doggi.R;

public class SellerHomePageActivity extends AppCompatActivity {

    private Button productsButton, ordersButton, profileButton, settingsButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        productsButton = findViewById(R.id.productsButton);
        ordersButton = findViewById(R.id.ordersButton);
        View educationalContentButton = findViewById(R.id.educationalContentButton);
        profileButton = findViewById(R.id.profileButton);


        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Products button click
                startActivity(new Intent(SellerHomePageActivity.this, AddProductActivity.class));
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Orders button click
                startActivity(new Intent(SellerHomePageActivity.this, SellerOrdersActivity.class));
            }
        });

        educationalContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Educational Content button click
                startActivity(new Intent(SellerHomePageActivity.this, SellerEduContentActivity.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Profile button click
                startActivity(new Intent(SellerHomePageActivity.this, SellerPersonalInformationActivity.class));
            }
        });

    }
}
