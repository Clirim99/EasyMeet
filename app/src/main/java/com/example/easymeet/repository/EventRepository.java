package com.example.easymeet.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easymeet.model.Event;
import com.example.easymeet.utility.DbHelper;

import java.util.ArrayList;

public class EventRepository {

    public static boolean createEvent(Context context, Event event) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("eventName", event.eventName);
        values.put("eventTime", event.eventTime);
        values.put("eventCreator", event.eventCreator);
        values.put("eventDescription", event.eventDescription);
        values.put("numMembers", 1);

        long id = -1;
        try {
            id = db.insert("events", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return id != -1;
    }

    public static Event getEventById(Context context, int eventId) {
        Event event = null;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        String query = "SELECT * FROM Events WHERE Id = ?";
        String[] args = {String.valueOf(eventId)};

        try (Cursor cursor = db.rawQuery(query, args)) {
            if (cursor.moveToFirst()) {
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow("eventName"));
                String eventTime = cursor.getString(cursor.getColumnIndexOrThrow("eventTime"));
                int eventCreator = cursor.getInt(cursor.getColumnIndexOrThrow("eventCreator"));
                String eventDescription = cursor.getString(cursor.getColumnIndexOrThrow("eventDescription"));
                int numMembers = cursor.getInt(cursor.getColumnIndexOrThrow("numMembers"));

                event = new Event(eventName, eventTime, eventCreator, numMembers, eventDescription);
                event.Id = eventId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return event;
    }

    public static ArrayList<Event> getAllEvents(Context context) {
        ArrayList<Event> events = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDb();

        String query = "SELECT * FROM Events";
        try (Cursor cursor = db.rawQuery(query, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow("eventName"));
                String eventTime = cursor.getString(cursor.getColumnIndexOrThrow("eventTime"));
                int eventCreator = cursor.getInt(cursor.getColumnIndexOrThrow("eventCreator"));
                String eventDescription = cursor.getString(cursor.getColumnIndexOrThrow("eventDescription"));
                int numMembers = cursor.getInt(cursor.getColumnIndexOrThrow("numMembers"));

                Event event = new Event(eventName, eventTime, eventCreator, numMembers, eventDescription);
                event.Id = id;
                events.add(event);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return events;
    }


    public static boolean deleteEvent(Context context, Event event) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = 0;

        try {
            // Attempt to delete the event by its ID
            rowsDeleted = db.delete("events", "Id = ?", new String[]{String.valueOf(event.Id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        // Return true if at least one row was deleted
        return rowsDeleted > 0;
    }

    public static boolean editEvent(Context context, int eventId, String eventName, String eventTime, String eventDescription) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("eventName", eventName);
        values.put("eventTime", eventTime);
        values.put("eventDescription", eventDescription);

        int rowsUpdated = 0;
        try {
            rowsUpdated = db.update(
                    "events",
                    values,
                    "Id = ?",
                    new String[]{String.valueOf(eventId)}
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return rowsUpdated > 0;
    }

}
