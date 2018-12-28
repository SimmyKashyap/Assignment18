package com.example.nitinsharma.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nitin sharma on 07-Dec-18.
 */

public class RegisteredUsers extends SQLiteOpenHelper {

    String query =" CREATE TABLE Users( Column_Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT,Email TEXT, Password TEXT,Mobile Text);";

    public RegisteredUsers(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Users", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
