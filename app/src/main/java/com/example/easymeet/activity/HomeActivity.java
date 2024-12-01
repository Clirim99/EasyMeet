package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easymeet.R;
import com.example.easymeet.model.Event;
import com.example.easymeet.repository.EventRepository;
import com.example.easymeet.utility.EventAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageView profileImage;
    ListView listView;
    Button gotoProfile;
    Button createEvent;
    EventAdapter eventAdapter = new EventAdapter();

    ArrayList<Event> events = new ArrayList<>();


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
        eventAdapter.events = events;
        listView = findViewById(R.id.eventsListView);
        listView.setAdapter(eventAdapter);
       // eventAdapter.customListener = event -> {};

    }
}
