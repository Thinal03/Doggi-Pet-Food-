package com.example.doggi.Buyer;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggi.Database.DatabaseHelper;
import com.example.doggi.Database.Product;
import com.example.doggi.R;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContentAdapter.ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch products from database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        productList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String brand = cursor.getString(cursor.getColumnIndex("brand"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));

                Product product = new Product(id, name, brand, description, price, image);
                productList.add(product);

                if (productList.size() == 9) {
                    break; // Only take 9 products
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Set up the adapter
        productAdapter = new ContentAdapter.ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);
    }
}