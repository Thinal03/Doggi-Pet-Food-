package com.example.doggi.Seller;


import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggi.Buyer.ContentAdapter;
import com.example.doggi.Database.DatabaseHelper;
import com.example.doggi.R;

public class SellerEduContentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_educontent);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        Cursor cursor = db.getAllEducationalContent();

        adapter = new ContentAdapter(this, cursor);
        recyclerView.setAdapter(adapter);
    }
}
