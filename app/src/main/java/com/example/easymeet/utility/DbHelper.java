package com.example.easymeet.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context.getApplicationContext(), "easymeet", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table first
        String userCreate = "CREATE TABLE IF NOT EXISTS Users (Id INTEGER PRIMARY KEY AUTOINCREMENT , firstName TEXT, lastName TEXT, username TEXT, email TEXT, password TEXT)";
        db.execSQL(userCreate);

        // Then create Events table, which references the Users table
        String eventCreate = "CREATE TABLE IF NOT EXISTS Events (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "eventName TEXT, " +
                "eventTime TEXT, " +
                "eventCreator INTEGER, " + // Foreign key column
                "eventDescription TEXT, " +
                "numMembers INTEGER, " +
                "FOREIGN KEY(eventCreator) REFERENCES Users(Id))"; // Foreign key constraint
        db.execSQL(eventCreate);
        String eventParticipantsCreate = "CREATE TABLE IF NOT EXISTS EventParticipants (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EventId INTEGER, " +
                "ParticipantId INTEGER, " + // Foreign key column
                "FOREIGN KEY(EventId) REFERENCES Events(Id), " +
                "FOREIGN KEY(ParticipantId) REFERENCES Users(Id))"; // Foreign key constraint
        db.execSQL(eventParticipantsCreate);
        String incrementTrigger = "CREATE TRIGGER IF NOT EXISTS IncrementNumMembers " +
                "AFTER INSERT ON EventParticipants " +
                "FOR EACH ROW " +
                "BEGIN " +
                "   UPDATE Events " +
                "   SET numMembers = numMembers + 1 " +
                "   WHERE Id = NEW.EventId; " +
                "END;";
        db.execSQL(incrementTrigger);
        String decrementTrigger = "CREATE TRIGGER IF NOT EXISTS DecrementMembers AFTER DELETE ON EventParticipants " +
                "BEGIN " +
                "   UPDATE Events SET numMembers = numMembers - 1 WHERE Id = OLD.EventId AND numMembers > 0; " +
                "END;";
        db.execSQL(decrementTrigger);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        String sql = "DROP TABLE IF EXISTS Users";
        db.execSQL("DROP TABLE IF EXISTS Events");
        db.execSQL("DROP TABLE IF EXISTS EventParticipants");
        db.execSQL(sql);
        this.onCreate(db);
        db.close();
    }

    public SQLiteDatabase getWritableDb() {
        return getWritableDatabase();
    }

    public SQLiteDatabase getReadableDb() {
        return getReadableDatabase();
    }

}
