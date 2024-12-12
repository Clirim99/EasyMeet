package com.example.easymeet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.model.Event;
import com.example.easymeet.model.ProfileData;
import com.example.easymeet.repository.EventRepository;
import com.example.easymeet.repository.ProfileDataRepository;
import com.example.easymeet.utility.SessionManager;

import java.io.File;

public class MeetingElements extends AppCompatActivity {

    ImageView imageView;
    TextView creatorName;
    EditText eventName;
    EditText eventTime;
    TextView numberOfMembers;
    EditText eventDescription;
    Button cancelEvent;
    Button editEvent;
    String id;
    boolean isEditing = false; // Track if editing mode is active

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemelements);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Event event = EventRepository.getEventById(MeetingElements.this, Integer.parseInt(id));
        ProfileData profileData = ProfileDataRepository.getProfileDataByUserId(MeetingElements.this, event.eventCreator);

        initializeComponents(event, profileData);
        setupEditEventButton(event);
    }

    public void initializeComponents(Event event, ProfileData profileData) {
        imageView = findViewById(R.id.eventImage);
        if (profileData.getProfilePic() != null && !profileData.getProfilePic().isEmpty()) {
            imageView.setImageURI(Uri.fromFile(new File(profileData.getProfilePic())));
        } else {
            imageView.setImageResource(R.drawable.avatar);
        }
        creatorName = findViewById(R.id.creatorName);
        creatorName.setText(profileData.getUsername());

        eventName = findViewById(R.id.eventName);
        eventName.setText(event.eventName);

        eventTime = findViewById(R.id.eventTime);
        eventTime.setText(event.eventTime);

        numberOfMembers = findViewById(R.id.numMembers);
        numberOfMembers.setText(String.valueOf(event.numMembers));

        eventDescription = findViewById(R.id.eventDescription);
        eventDescription.setText(event.eventDescription);

        eventName.setFocusable(false);
        eventName.setFocusableInTouchMode(false);
        eventName.setCursorVisible(false);
        eventTime.setFocusable(false);
        eventTime.setFocusableInTouchMode(false);
        eventTime.setCursorVisible(false);
        eventDescription.setFocusable(false);
        eventDescription.setFocusableInTouchMode(false);
        eventDescription.setCursorVisible(false);

        cancelEvent = findViewById(R.id.cancelEventButton);
        if (event.eventCreator == SessionManager.getUserId(MeetingElements.this)) {
            cancelEvent.setOnClickListener(v -> {
                EventRepository.deleteEvent(MeetingElements.this, event);
                Intent intent = new Intent(MeetingElements.this, HomeActivity.class);
                startActivity(intent);
            });
        } else {
            cancelEvent.setVisibility(View.INVISIBLE);
        }
    }

    private void setupEditEventButton(Event event) {
        editEvent = findViewById(R.id.editEventButton);
        editEvent.setOnClickListener(v -> {
            if (!isEditing) {
                // Enable editing mode
                isEditing = true;
                eventName.setFocusable(true);
                eventName.setFocusableInTouchMode(true);
                eventName.setCursorVisible(true);
                eventTime.setFocusable(true);
                eventTime.setFocusableInTouchMode(true);
                eventTime.setCursorVisible(true);
                eventDescription.setFocusable(true);
                eventDescription.setFocusableInTouchMode(true);
                eventDescription.setCursorVisible(true);

                editEvent.setText("Save Changes");

            } else {
                // Save changes
                isEditing = false;
                eventName.setFocusable(false);
                eventName.setFocusableInTouchMode(false);
                eventName.setCursorVisible(false);
                eventTime.setFocusable(false);
                eventTime.setFocusableInTouchMode(false);
                eventTime.setCursorVisible(false);
                eventDescription.setFocusable(false);
                eventDescription.setFocusableInTouchMode(false);
                eventDescription.setCursorVisible(false);
                String newEventName = eventName.getText().toString().trim();
                String newEventTime = eventTime.getText().toString().trim();
                String newEventDescription = eventDescription.getText().toString().trim();

                boolean isUpdated = EventRepository.editEvent(
                        MeetingElements.this,
                        event.Id,
                        newEventName,
                        newEventTime,
                        newEventDescription
                );

                if (isUpdated) {

                    event.eventName = newEventName;
                    event.eventTime = newEventTime;
                    event.eventDescription = newEventDescription;

                    eventName.setFocusable(false);
                    eventName.setFocusableInTouchMode(false);
                    eventName.setCursorVisible(false);
                    eventTime.setFocusable(false);
                    eventTime.setFocusableInTouchMode(false);
                    eventTime.setCursorVisible(false);
                    eventDescription.setFocusable(false);
                    eventDescription.setFocusableInTouchMode(false);
                    eventDescription.setCursorVisible(false);
//                    eventName.setEnabled(false);
//                    eventTime.setEnabled(false);
//                    eventDescription.setEnabled(false);

                    editEvent.setText("Edit Event");
                    Intent intent = new Intent(MeetingElements.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    // Handle save failure
                    editEvent.setText("Save Changes");

                }
            }
        });
    }
}
