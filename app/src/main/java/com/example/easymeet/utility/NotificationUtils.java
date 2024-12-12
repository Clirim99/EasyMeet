package com.example.easymeet.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.easymeet.R;

public class NotificationUtils {

    private static final String CHANNEL_ID = "login_channel"; // Notification channel ID
    private static final String CHANNEL_NAME = "Login Notifications"; // Channel name
    private static final String CHANNEL_DESCRIPTION = "Notifications for login-related events"; // Channel description

    /**
     * Creates a notification channel for Android 8.0 and above.
     */
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Sends a notification.
     */
    public static void sendLoginNotification(Context context, String title, String message) {
        // Create the notification
        Notification notification = new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.easymeet) // Use your own icon
                .build();

        // Get the notification manager and send the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification); // 1 is the notification ID
        }
    }
}
