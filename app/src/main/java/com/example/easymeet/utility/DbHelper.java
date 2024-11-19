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
        String sql = "CREATE TABLE IF NOT EXISTS Users (Id INTEGER PRIMARY KEY AUTOINCREMENT , firstName TEXT, lastName TEXT, username TEXT, email TEXT, password TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        String sql = "DROP TABLE IF EXISTS Users";
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
