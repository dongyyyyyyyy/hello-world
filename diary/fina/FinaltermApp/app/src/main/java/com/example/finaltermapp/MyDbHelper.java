package com.example.finaltermapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper{
    public MyDbHelper(@Nullable Context context) {
        super(context, "noteDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table note("+
                "_id integer primary key autoincrement,"+//listview必须有这个字段
                "title text not null,"+
                "content text not null,"+
                "time text not null)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
