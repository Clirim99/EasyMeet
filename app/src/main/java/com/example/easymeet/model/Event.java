package com.example.easymeet.model;

public class Event {

    public int Id;
    public String eventName;
    public String eventTime;
    public int eventCreator;
    public int numMembers;
    public String eventDescription;

    public Event(String eventName, String eventTime, int eventCreator, int numMembers, String eventDescription) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventCreator = eventCreator;
        this.numMembers = numMembers;
        this.eventDescription = eventDescription;
    }

}

