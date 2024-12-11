package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.model.Event;
import com.example.easymeet.repository.EventRepository;
import com.example.easymeet.utility.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity {

    TextView eventName;
    TextView eventDescription;
    DatePicker datePicker;
    TimePicker timePicker;
    Button setEvent;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_event);
        eventName = findViewById(R.id.editTextTitle);
        eventDescription = findViewById(R.id.editTextDescription);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);


        setEvent = findViewById(R.id.btnSaveEvent);
        setEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eventNameTxt = String.valueOf(eventName.getText());
                String eventDescriptionTxt = String.valueOf(eventDescription.getText());
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth(); // Note: Month is zero-based (0 = January)
                int year = datePicker.getYear();

                // Create a Calendar object to easily format the date
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String datePickerTxt = sdf.format(calendar.getTime());

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar calendarForHour = Calendar.getInstance();
                calendarForHour.set(Calendar.HOUR_OF_DAY, hour);
                calendarForHour.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
                String timePickerTxt = sdfHour.format(calendar.getTime());

                int eventCreator = SessionManager.getUserId(CreateEventActivity.this);
                int members = 0;
                String eventTimeTxt = datePickerTxt + timePickerTxt;
                Event event = new Event(eventNameTxt,eventTimeTxt,eventCreator,members,eventDescriptionTxt);
                EventRepository.createEvent(CreateEventActivity.this,event);
                Intent intent = new Intent(CreateEventActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


    }
}
