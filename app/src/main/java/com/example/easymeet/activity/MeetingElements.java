package com.example.easymeet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.model.Event;
import com.example.easymeet.model.ProfileData;
import com.example.easymeet.repository.EventRepository;
import com.example.easymeet.repository.ProfileDataRepository;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class MeetingElements extends AppCompatActivity {

    ImageView imageView;
    TextView creatorName;
    TextView eventName;
    TextView eventTime;
    TextView numberOfMembers;
    TextView eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemelements);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Event event = EventRepository.getEventById(MeetingElements.this, Integer.parseInt(id));
        Log.d("eventID:", String.valueOf(event.Id));
        ProfileData profileData = ProfileDataRepository.getProfileDataByUserId(MeetingElements.this,Integer.parseInt(id));
        initializeComponents(event,profileData);

       // imageView = findViewById(R.id.eventImage); // Assuming the imageView ID in the layout is "imageView"
        imageView.setOnClickListener(v -> openProfileActivity(event.eventCreator));
    }

    public void initializeComponents(Event event, ProfileData profileData) {
        imageView = findViewById(R.id.eventImage);
        if (profileData.getProfilePic() != null && !profileData.getProfilePic().isEmpty()) {
            imageView.setImageURI(Uri.fromFile(new File(profileData.getProfilePic())));
        } else {
            imageView.setImageResource(R.drawable.avatar); // Set a default image
        }
        creatorName = findViewById(R.id.creatorName);
        creatorName.setText(profileData.getUsername());

        eventName = findViewById(R.id.eventName);
        eventName.setText(event.eventName);

        eventTime = findViewById(R.id.eventTime);
        eventTime.setText(event.eventTime);

        numberOfMembers = findViewById(R.id.numMembers);
        // Convert numMembers (int) to String
        numberOfMembers.setText(String.valueOf(event.numMembers));

        eventDescription = findViewById(R.id.eventDescription);
        eventDescription.setText(event.eventDescription);
    }

    private void openProfileActivity(int userId) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("id", String.valueOf(userId)); // Pass the userId to the ProfileActivity
        startActivity(intent);
    }
}
