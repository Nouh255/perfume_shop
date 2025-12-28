package com.example.perfume_shop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.perfume_shop.models.Perfume;
import com.example.perfume_shop.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PerfumeShop.db";
    private static final int DATABASE_VERSION = 2;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone";
    
    // Perfumes Table
    private static final String TABLE_PERFUMES = "perfumes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_YEAR = "year";

    // Create Users Table SQL
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FULL_NAME + " TEXT NOT NULL,"
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE,"
            + COLUMN_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_PHONE + " TEXT NOT NULL"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        
        // Create perfumes table
        String CREATE_PERFUMES_TABLE = "CREATE TABLE " + TABLE_PERFUMES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_BRAND + " TEXT NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_YEAR + " TEXT)";
        db.execSQL(CREATE_PERFUMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add perfumes table if upgrading from version 1
            String CREATE_PERFUMES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PERFUMES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_BRAND + " TEXT NOT NULL, " +
                    COLUMN_PRICE + " REAL NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_YEAR + " TEXT)";
            db.execSQL(CREATE_PERFUMES_TABLE);
        }
    }

    /**
     * Insert a new user into the database
     * @param user User object containing user details
     * @return true if insertion successful, false otherwise
     */
    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_FULL_NAME, user.getFullName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE, user.getPhone());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        
        return result != -1;
    }

    /**
     * Check if email already exists in database
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_EMAIL},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        
        return exists;
    }

    /**
     * Verify user credentials for login
     * @param email User email
     * @param password User password
     * @return true if credentials are valid, false otherwise
     */
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);
        
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        
        return isValid;
    }

    /**
     * Get user details by email
     * @param email User email
     * @return User object or null if not found
     */
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                null,
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
            user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
        }
        
        cursor.close();
        db.close();
        
        return user;
    }
    
    // ========== PERFUME CRUD OPERATIONS ==========
    
    /**
     * Insert a new perfume into the database
     */
    public long insertPerfume(Perfume perfume) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_NAME, perfume.getName());
        values.put(COLUMN_BRAND, perfume.getBrand());
        values.put(COLUMN_PRICE, perfume.getPrice());
        values.put(COLUMN_DESCRIPTION, perfume.getDescription());
        values.put(COLUMN_IMAGE_URL, perfume.getImageUrl());
        values.put(COLUMN_GENDER, perfume.getGender());
        values.put(COLUMN_YEAR, perfume.getYear());

        long id = db.insert(TABLE_PERFUMES, null, values);
        db.close();
        return id;
    }

    /**
     * Get all perfumes from database
     */
    public java.util.List<Perfume> getAllPerfumes() {
        java.util.List<Perfume> perfumeList = new java.util.ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERFUMES + " ORDER BY " + COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Perfume perfume = new Perfume();
                perfume.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                perfume.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                perfume.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)));
                perfume.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                perfume.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                perfume.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
                perfume.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                perfume.setYear(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YEAR)));
                
                perfumeList.add(perfume);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return perfumeList;
    }

    /**
     * Update perfume
     */
    public int updatePerfume(Perfume perfume) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, perfume.getName());
        values.put(COLUMN_BRAND, perfume.getBrand());
        values.put(COLUMN_PRICE, perfume.getPrice());
        values.put(COLUMN_DESCRIPTION, perfume.getDescription());
        values.put(COLUMN_IMAGE_URL, perfume.getImageUrl());
        values.put(COLUMN_GENDER, perfume.getGender());
        values.put(COLUMN_YEAR, perfume.getYear());

        int rowsAffected = db.update(TABLE_PERFUMES, values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(perfume.getId())});
        
        db.close();
        return rowsAffected;
    }

    /**
     * Delete perfume
     */
    public int deletePerfume(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_PERFUMES,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}
