package com.example.easymeet.model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easymeet.R;

public class EventRow {


    public TextView eventName;
    public TextView eventTime;
    public TextView eventCreator;
    public TextView numMembers;
    public Button joinBtn;



    public EventRow(View view ) {
        this.eventName = view.findViewById(R.id.eventName);
        this.eventTime = view.findViewById(R.id.eventCreator);
        this.eventCreator = view.findViewById(R.id.eventTime);
        this.numMembers = view.findViewById(R.id.numMembers);
        this.joinBtn = view.findViewById(R.id.joinButton);
    }



}
