package com.example.nitinsharma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nitin sharma on 07-Dec-18.
 */

public class ToDoItems extends SQLiteOpenHelper {

    String query =" CREATE TABLE Items ( Column_Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Description TEXT,Check_Box Text);";

    public ToDoItems(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Items", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
