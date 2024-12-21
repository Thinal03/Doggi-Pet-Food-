package com.example.doggi.Buyer;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggi.Database.Product;
import com.example.doggi.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private Context context;
    private Cursor cursor;

    public ContentAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_item, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

        holder.titleTextView.setText(title);
        holder.descriptionTextView.setText(description);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, descriptionTextView;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    public static class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private List<Product> productList;
        private Context context;

        public ProductAdapter(Context context, List<Product> productList) {
            this.context = context;
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.productName.setText(product.getName());
            holder.productBrand.setText(product.getBrand());
            holder.productPrice.setText(product.getPrice());

            // Load the product image, for example using Glide or Picasso
            // Glide.with(context).load(product.getImage()).into(holder.productImage);
            holder.productImage.setImageResource(R.drawable.ic_products); // For placeholder
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        static class ProductViewHolder extends RecyclerView.ViewHolder {
            ImageView productImage;
            TextView productName;
            TextView productBrand;
            TextView productPrice;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                productImage = itemView.findViewById(R.id.product_image);
                productName = itemView.findViewById(R.id.product_name);
                productBrand = itemView.findViewById(R.id.product_brand);
                productPrice = itemView.findViewById(R.id.product_price);
            }
        }
    }
}