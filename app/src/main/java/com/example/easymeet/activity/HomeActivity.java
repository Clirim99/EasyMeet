package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easymeet.R;
import com.example.easymeet.model.Event;
import com.example.easymeet.model.EventParticipants;
import com.example.easymeet.repository.EventRepository;
import com.example.easymeet.utility.EventAdapter;
import com.example.easymeet.utility.SessionManager;
import com.example.easymeet.repository.EventPRepository;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageView profileImage;
    ListView listView;
    Button gotoProfile;
    Button createEvent;
    EventAdapter eventAdapter = new EventAdapter();

    ArrayList<Event> events = new ArrayList<>();

    ArrayList<Event> eventsJoined = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        gotoProfile = findViewById(R.id.goToProfileButton);
        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        createEvent = findViewById(R.id.createEventButton);
        if (createEvent != null) {
            createEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, CreateEventActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("CreateEventActivity", "Button not found, check your layout.");
        }

        events = EventRepository.getAllEvents(HomeActivity.this);
        eventsJoined = EventPRepository.getEventsByParticipantId(HomeActivity.this,SessionManager.getUserId(HomeActivity.this));
        eventAdapter.events = events;
        eventAdapter.eventsJoined = eventsJoined;
        listView = findViewById(R.id.eventsListView);
        listView.setAdapter(eventAdapter);
        eventAdapter.customListener = event -> {
            int eventId = event.Id;
            int participantId = SessionManager.getUserId(HomeActivity.this);
            EventParticipants eventParticipants = new EventParticipants(eventId,participantId);

            if(!eventAdapter.eventsJoined.contains(event)){
                Event eventP = EventPRepository.addParticipant(HomeActivity.this, eventParticipants);
                int position = eventAdapter.events.indexOf(event);
                eventAdapter.events.set(position, eventP);
                eventAdapter.eventsJoined.add(eventP);
            }else{
                Event eventP = EventPRepository.removeParticipant(HomeActivity.this, eventParticipants);
                int position = eventAdapter.events.indexOf(event);
                eventAdapter.events.set(position, eventP);
                eventAdapter.eventsJoined.remove(eventP);
            }


            eventAdapter.notifyDataSetChanged();
//            if (success) {
//                Toast.makeText(HomeActivity.this, "You have joined the event!", Toast.LENGTH_SHORT).show();
//                // Optionally, update the UI or refresh the adapter
//                eventAdapter.notifyDataSetChanged();
//            } else {
//                Toast.makeText(HomeActivity.this, "Failed to join the event. Try again!", Toast.LENGTH_SHORT).show();
//            }
        };

    }
}
