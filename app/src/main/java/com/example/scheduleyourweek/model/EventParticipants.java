package com.example.scheduleyourweek.model;

public class EventParticipants {
    public int Id;
    public int EventId;
    public int ParticipantId;

    public EventParticipants(int eventId, int participantId) {
        EventId = eventId;
        ParticipantId = participantId;
    }
}
