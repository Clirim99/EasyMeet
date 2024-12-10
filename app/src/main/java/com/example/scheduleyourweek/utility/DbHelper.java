package com.example.scheduleyourweek.utility;

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
        db.execSQL("PRAGMA foreign_keys=ON;");

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
        String profileTable = "CREATE TABLE IF NOT EXISTS ProfileData (" +
                "Id INTEGER PRIMARY KEY, " +
                "username TEXT," +
                "profilePic TEXT, " +
                "Description TEXT, " +
                "FOREIGN KEY(Id) REFERENCES Users(Id) ON DELETE CASCADE)";
        db.execSQL(profileTable);
      //  db.execSQL(profileTable);
        String insertProfileDataTrigger = "CREATE TRIGGER IF NOT EXISTS InsertProfileDataAfterUsersInsert " +
                "AFTER INSERT ON Users " +
                "FOR EACH ROW " +
                "BEGIN " +
                "   INSERT INTO ProfileData (Id,username,profilePic, Description) " +
                "   VALUES (NEW.Id,NEW.username ,NULL, NULL); " +
                "END;";
        db.execSQL(insertProfileDataTrigger);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("PRAGMA foreign_keys=OFF;");
        db.beginTransaction();
        try {
            // Drop tables in reverse dependency order
            db.execSQL("DROP TABLE IF EXISTS EventParticipants");
            db.execSQL("DROP TABLE IF EXISTS ProfileData");
            db.execSQL("DROP TABLE IF EXISTS Events");
            db.execSQL("DROP TABLE IF EXISTS Users");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.execSQL("PRAGMA foreign_keys=ON;");
        onCreate(db);
    }

    public SQLiteDatabase getWritableDb() {
        return getWritableDatabase();
    }

    public SQLiteDatabase getReadableDb() {
        return getReadableDatabase();
    }

}
