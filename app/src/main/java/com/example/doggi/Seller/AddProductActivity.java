package com.example.doggi.Seller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doggi.Database.DatabaseHelper;
import com.example.doggi.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddProductActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE_REQUEST_CODE = 1;

    private EditText etProductName, etProductBrand, etProductDescription, etProductPrice;
    private ImageView ivProductImagePreview;
    private Button btnAddProduct, btnSelectImage, productsButton;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        etProductName = findViewById(R.id.et_product_name);
        etProductBrand = findViewById(R.id.et_product_brand);
        etProductDescription = findViewById(R.id.et_product_description);
        etProductPrice = findViewById(R.id.et_product_price);
        ivProductImagePreview = findViewById(R.id.iv_product_image_preview);
        btnAddProduct = findViewById(R.id.btn_add_product);
        btnSelectImage = findViewById(R.id.btn_select_image);
        productsButton = findViewById(R.id.productsButton);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelector();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProductToDatabase();
            }
        });

        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SellerProductsActivity
                Intent intent = new Intent(AddProductActivity.this, SellerProductsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImageBitmap = BitmapFactory.decodeStream(imageStream);
                ivProductImagePreview.setImageBitmap(selectedImageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveProductToDatabase() {
        String productName = etProductName.getText().toString().trim();
        String productBrand = etProductBrand.getText().toString().trim();
        String productDescription = etProductDescription.getText().toString().trim();
        String productPrice = etProductPrice.getText().toString().trim();

        if (productName.isEmpty() || productBrand.isEmpty() || productDescription.isEmpty() || productPrice.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] productImage = outputStream.toByteArray();

        SQLiteDatabase database = new DatabaseHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", productName);
        values.put("brand", productBrand);
        values.put("description", productDescription);
        values.put("price", productPrice);
        values.put("image", productImage);

        long newRowId = database.insert("products", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity and return to the previous screen
        } else {
            Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }
}
