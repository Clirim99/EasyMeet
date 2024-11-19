package com.example.easymeet.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.easymeet.model.User;
import com.example.easymeet.utility.DbHelper;

public class UserRepository {

    // Fetch user by email
    public static User getUserByEmail(Context context, String email) {
        User user = null;
        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();
        String query = "SELECT * FROM users WHERE email = ?";
        String[] args = {email};

        try (Cursor cursor = db.rawQuery(query, args)) {
            if (cursor.moveToFirst()) {
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String emailFromDb = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                user = new User(firstName, lastName, username, emailFromDb, password, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean insertUser(Context context, User user) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Prepare the user data to insert
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        long id = -1;
        try {
            id = db.insert("users", null, values); // Insert user into the database
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(); // Ensure database connection is closed
        }

        // Return true if the insertion was successful, otherwise false
        return id != -1;
    }

    public static User loginUser(Context context, String email, String password) {
        User user = null;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        String[] args = {email, password};

        try (Cursor cursor = db.rawQuery(query, args)) {
            if (cursor.moveToFirst()) {
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String emailFromDb = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String passwordFromDb = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                // Create a User object if credentials match
                user = new User(firstName, lastName, username, emailFromDb, passwordFromDb, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(); // Ensure database is closed after the operation
        }

        return user;
    }



    public static boolean isEmailTaken(Context context, String email) {
        User user = getUserByEmail(context, email);
        return user != null;
    }
}
