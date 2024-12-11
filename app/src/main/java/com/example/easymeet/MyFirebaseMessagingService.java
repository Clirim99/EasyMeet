package com.example.easymeet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import com.example.easymeet.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "Default";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle both notification and data payload
        Log.i(TAG, "onMessageReceived: 5466556");
        if (remoteMessage.getData().size() > 0) {
            // Handle data payload
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            // Handle notification payload (will show the notification)
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Show the notification (if app is in the foreground)
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.i(TAG, "New Token: " + token);
        // Optionally, send this token to your server or save it
    }

    // Create the notification channel if needed (Android 8.0+)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Check if the channel already exists
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null && notificationManager.getNotificationChannel( CHANNEL_ID) == null) {
                // Create the notification channel
                CharSequence name = "Default Channel";
                String description = "Channel for general notifications";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                // Register the channel with the system
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // Show the notification
    private void showNotification(String title, String message) {
        // Ensure the notification channel is created before showing the notification
        createNotificationChannel();

        // Intent to open MainActivity when the notification is tapped
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Create notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure you have this icon in your `res/drawable`
                .setContentIntent(pendingIntent);

        // Get the NotificationManager system service and display the notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
