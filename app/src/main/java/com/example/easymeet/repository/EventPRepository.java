package com.example.easymeet.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeet.model.Event;
import com.example.easymeet.model.EventParticipants;
import com.example.easymeet.utility.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class EventPRepository {

    // Method to add a participant to an event
    public static Event addParticipant(Context context, EventParticipants eventP) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("EventId", eventP.EventId);
        values.put("ParticipantId", eventP.ParticipantId);

        long id = -1;
        try {
            id = db.insert("EventParticipants", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return EventRepository.getEventById(context,eventP.EventId);
    }

    // Method to get all participants of a specific event
    public static List<Integer> getParticipantsByEventId(Context context, int eventId) {
        List<Integer> participantIds = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        String query = "SELECT ParticipantId FROM EventParticipants WHERE EventId = ?";
        String[] args = {String.valueOf(eventId)};

        try (Cursor cursor = db.rawQuery(query, args)) {
            while (cursor.moveToNext()) {
                int participantId = cursor.getInt(cursor.getColumnIndexOrThrow("ParticipantId"));
                participantIds.add(participantId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return participantIds;
    }

    // Method to get all events a user is participating in
    public static ArrayList<Event> getEventsByParticipantId(Context context, int participantId) {
        ArrayList<Event> events = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        String query = "SELECT EventId FROM EventParticipants WHERE ParticipantId = ?";
        String[] args = {String.valueOf(participantId)};

        try (Cursor cursor = db.rawQuery(query, args)) {
            while (cursor.moveToNext()) {
                int eventId = cursor.getInt(cursor.getColumnIndexOrThrow("EventId"));
                events.add(EventRepository.getEventById(context,eventId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return events;
    }

    // Method to remove a participant from an event
    public static Event removeParticipant(Context context, EventParticipants eventParticipants) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = 0;
        try {
            rowsDeleted = db.delete(
                    "EventParticipants",
                    "EventId = ? AND ParticipantId = ?",
                    new String[]{String.valueOf(eventParticipants.EventId), String.valueOf(eventParticipants.ParticipantId)}
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return EventRepository.getEventById(context,eventParticipants.EventId);
    }

    // Method to remove all participants from a specific event
//    public static boolean removeAllParticipantsByEventId(Context context, int eventId) {
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        int rowsDeleted = 0;
//        try {
//            rowsDeleted = db.delete(
//                    "EventParticipants",
//                    "EventId = ?",
//                    new String[]{String.valueOf(eventId)}
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.close();
//        }
//
//        return rowsDeleted > 0;
//    }
}
