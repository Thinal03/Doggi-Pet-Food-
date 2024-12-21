package com.example.doggi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.doggi.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Doggi.db";
    private static final int DATABASE_VERSION = 1;

    // Users table details
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_PASSWORD = "password";

    // Products table details
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_BRAND = "brand";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE = "image";

    // Educational content table details
    private static final String TABLE_EDUCATIONAL_CONTENT = "educational_content";
    private static final String COLUMN_CONTENT_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";

    // Cart table details
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_CART_ID = "id";
    private static final String COLUMN_CART_PRODUCT_ID = "product_id";
    private static final String COLUMN_CART_QUANTITY = "quantity";

    // SQL statements to create tables
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USERNAME + " TEXT UNIQUE, "
            + COLUMN_EMAIL + " TEXT UNIQUE, "
            + COLUMN_ROLE + " TEXT, "
            + COLUMN_PASSWORD + " TEXT)";

    private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " ("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PRODUCT_NAME + " TEXT, "
            + COLUMN_PRODUCT_BRAND + " TEXT, "
            + COLUMN_PRODUCT_DESCRIPTION + " TEXT, "
            + COLUMN_PRODUCT_PRICE + " TEXT, "
            + COLUMN_PRODUCT_IMAGE + " TEXT)";

    private static final String CREATE_EDUCATIONAL_CONTENT_TABLE = "CREATE TABLE " + TABLE_EDUCATIONAL_CONTENT + " ("
            + COLUMN_CONTENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_DESCRIPTION + " TEXT)";

    private static final String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " ("
            + COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CART_PRODUCT_ID + " INTEGER, "
            + COLUMN_CART_QUANTITY + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCT_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_EDUCATIONAL_CONTENT_TABLE);
        db.execSQL(CREATE_CART_TABLE);


        insertProduct(db, "Chicken Crunchies", "Paws & Tails", "Crunchy chicken-flavored kibbles with essential nutrients.", "Rs.1200", String.valueOf(R.drawable.image_1));
        insertProduct(db, "Salmon Delight", "Fins & Fur", "Delicious salmon bits rich in omega-3 for a shiny coat.", "Rs.1400", String.valueOf(R.drawable.image_2));
        insertProduct(db, "Beefy Bites", "Mighty Paws", "Tasty beef chunks packed with protein and vitamins.", "Rs.1600", String.valueOf(R.drawable.image_3));
        insertProduct(db, "Lamb Feast", "Happy Pets", "Savory lamb-flavored food with added fiber for digestion.", "Rs.1800", String.valueOf(R.drawable.image_4));
        insertProduct(db, "Turkey Treats", "Feather & Fur", "Nutritious turkey treats to keep your pet happy and healthy.", "Rs.2000", String.valueOf(R.drawable.image_5));
        insertProduct(db, "Veggie Crunch", "Green Paws", "Healthy vegetable-based kibbles for a balanced diet.", "Rs.2200", String.valueOf(R.drawable.image_6));
        insertProduct(db, "Fishy Fancies", "Ocean Bites", "Tasty fish-flavored kibbles with added vitamins and minerals.", "Rs.2400", String.valueOf(R.drawable.image_7));
        insertProduct(db, "Pork Pleasers", "Meat Lovers", "Flavorful pork-based kibbles for a satisfying meal.", "Rs.2600", String.valueOf(R.drawable.image_8));
        insertProduct(db, "Chicken & Rice", "NutriPets", "Balanced chicken and rice formula for sensitive stomachs.", "Rs.2800", String.valueOf(R.drawable.image_9));



        insertEducationalContent(db, "The Solar System", "An overview of the solar system including planets, moons, and the sun.");
        insertEducationalContent(db, "Photosynthesis", "A process used by plants to convert light energy into chemical energy.");
        insertEducationalContent(db, "The Water Cycle", "A continuous cycle involving evaporation, condensation, and precipitation.");
        insertEducationalContent(db, "The Human Digestive System", "How the human body breaks down and absorbs nutrients from food.");
        insertEducationalContent(db, "Basics of Programming", "Introduction to programming concepts and languages.");
    }

    // Users table methods
    public boolean addUser(String name, String username, String email, String role, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_ROLE, role);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getUserRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ROLE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
        if (cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();
            return role;
        }
        cursor.close();
        return null;
    }

    // Products table methods
    public long addProduct(String name, String brand, String description, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_BRAND, brand);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_IMAGE, image);
        return db.insert(TABLE_PRODUCTS, null, values);
    }

    private void insertProduct(SQLiteDatabase db, String name, String brand, String description, String price, String image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_BRAND, brand);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_IMAGE, image);
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    public Cursor getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int updateProduct(int id, String name, String brand, String description, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_BRAND, brand);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_IMAGE, image);
        return db.update(TABLE_PRODUCTS, values, COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Educational content table methods
    public void insertEducationalContent(SQLiteDatabase db, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(TABLE_EDUCATIONAL_CONTENT, null, values);
    }

    public Cursor getAllEducationalContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EDUCATIONAL_CONTENT, null);
    }

    public Cursor getEducationalContentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EDUCATIONAL_CONTENT + " WHERE " + COLUMN_CONTENT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int updateEducationalContent(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        return db.update(TABLE_EDUCATIONAL_CONTENT, values, COLUMN_CONTENT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteEducationalContent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EDUCATIONAL_CONTENT, COLUMN_CONTENT_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Cart table methods
    public long addToCart(int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRODUCT_ID, productId);
        values.put(COLUMN_CART_QUANTITY, quantity);
        return db.insert(TABLE_CART, null, values);
    }

    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + TABLE_CART + ".*, "
                + TABLE_PRODUCTS + "." + COLUMN_PRODUCT_NAME + ", "
                + TABLE_PRODUCTS + "." + COLUMN_PRODUCT_BRAND + ", "
                + TABLE_PRODUCTS + "." + COLUMN_PRODUCT_PRICE + " FROM "
                + TABLE_CART + " INNER JOIN " + TABLE_PRODUCTS + " ON "
                + TABLE_CART + "." + COLUMN_CART_PRODUCT_ID + " = " + TABLE_PRODUCTS + "." + COLUMN_PRODUCT_ID, null);
    }

    public int updateCartQuantity(int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_QUANTITY, quantity);
        return db.update(TABLE_CART, values, COLUMN_CART_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)});
    }

    public void removeFromCart(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_CART_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EDUCATIONAL_CONTENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public boolean addEducationalContent(String title, String description) {
        return false;
    }
}
