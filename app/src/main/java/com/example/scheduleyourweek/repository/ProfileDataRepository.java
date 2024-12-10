package com.example.scheduleyourweek.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scheduleyourweek.model.ProfileData;
import com.example.scheduleyourweek.utility.DbHelper;

public class ProfileDataRepository {

    // Fetch ProfileData by userId
    public static ProfileData getProfileDataByUserId(Context context, int userId) {
        ProfileData profileData = null;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        String query = "SELECT * FROM ProfileData WHERE Id = ?";
        String[] args = {String.valueOf(userId)};

        try (Cursor cursor = db.rawQuery(query, args)) {
            if (cursor.moveToFirst()) {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String profilePic = cursor.getString(cursor.getColumnIndexOrThrow("profilePic"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));

                profileData = new ProfileData(userId, username, profilePic, description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close(); // Close database connection
        }

        return profileData;
    }

    // Save or update ProfileData
    public static boolean saveProfileData(Context context, ProfileData profileData) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDb();

        ContentValues values = new ContentValues();
        values.put("username", profileData.getUsername());
        values.put("profilePic", profileData.getProfilePic()); // Save the profilePic path
        values.put("description", profileData.getDescription());

        long result;

        // Check if ProfileData exists by Id
        ProfileData existingProfileData = getProfileDataByUserId(context, profileData.getId());

        if (existingProfileData == null) {
            // Insert new ProfileData
            values.put("Id", profileData.getId()); // Add Id for new entries
            result = db.insert("ProfileData", null, values);
        } else {
            // Update existing ProfileData
            result = db.update("ProfileData", values, "Id = ?", new String[]{String.valueOf(profileData.getId())});
        }

        db.close();
        return result != -1;
    }


    public static boolean insertProfileData(Context context, ProfileData profileData) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDb();

        ContentValues values = new ContentValues();
        values.put("Id", profileData.getId());
        values.put("username", profileData.getUsername());
        values.put("profilePic", profileData.getProfilePic());
        values.put("description", profileData.getDescription());

        long result = db.insert("ProfileData", null, values);
        db.close();

        return result != -1;
    }



    public static boolean deleteProfileData(Context context, int userId) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDb();

        // Delete by the correct column "Id" (not "userId")
        int rowsDeleted = db.delete("ProfileData", "Id = ?", new String[]{String.valueOf(userId)});
        db.close();

        return rowsDeleted > 0;
    }



}
