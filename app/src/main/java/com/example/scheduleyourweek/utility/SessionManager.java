package com.example.scheduleyourweek.utility;
import android.content.Context;
import android.content.SharedPreferences;
public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USER_ID = "user_id";

    // Save user ID
    public static void saveUserId(Context context, int userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);  // Save the user ID
        editor.apply();  // Apply the changes asynchronously
    }

    // Get the saved user ID
    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 0);  // Return the user ID, or null if not found
    }

    // Check if user is logged in (i.e., if user ID exists in SharedPreferences)
    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_USER_ID);  // Check if user ID exists
    }

    // Clear the saved user ID (logout)
    public static void logout(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);  // Remove the user ID
        editor.apply();
    }

}
