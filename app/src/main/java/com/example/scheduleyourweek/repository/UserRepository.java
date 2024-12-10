package com.example.scheduleyourweek.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.scheduleyourweek.model.ProfileData;
import com.example.scheduleyourweek.model.User;
import com.example.scheduleyourweek.utility.DbHelper;

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

    public static User getUserById(Context context, int userId) {
        User user = null;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        // Query to get the user by Id
        String query = "SELECT * FROM users WHERE Id = ?";
        String[] args = {String.valueOf(userId)};

        try (Cursor cursor = db.rawQuery(query, args)) {
            if (cursor.moveToFirst()) {
                // Fetch the user data from the cursor
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                // Create and return the User object
                user = new User(firstName, lastName, username, email, password, null);
                user.setId(userId);
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
            String query = "SELECT Id FROM users Order by Id desc";

            try (Cursor cursor = db.rawQuery(query, null)) {
                if (cursor.moveToFirst()) {
                    // Fetch the user data from the cursor
                    int Id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
                    ProfileData profileData = new ProfileData(Id, user.getUsername(),"","");
                    Log.d("msgid", String.valueOf(profileData.getId()));
                    if (profileData.getId() != 0){
                        ProfileDataRepository.insertProfileData(context,profileData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
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
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String emailFromDb = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String passwordFromDb = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                // Create a User object if credentials match
                user = new User(firstName, lastName, username, emailFromDb, passwordFromDb, null);
                user.setId(id);
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

    public static boolean doesUserExist(Context context, String email) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();
        boolean userExists = false;

        String query = "SELECT 1 FROM users WHERE email = ?";
        String[] args = {email};

        try (Cursor cursor = db.rawQuery(query, args)) {
            userExists = cursor.moveToFirst(); // If a result exists, the user exists
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(); // Ensure database is closed
        }

        return userExists;
    }

    public static boolean updatePassword(Context context, String email, String newPassword) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean isUpdated = false;

        try {
            ContentValues values = new ContentValues();
            values.put("password", newPassword);

            // Update the password where the email matches
            int rowsAffected = db.update("users", values, "email = ?", new String[]{email});
            isUpdated = rowsAffected > 0; // Returns true if at least one row was updated
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(); // Ensure the database is closed
        }

        return isUpdated;
    }
}