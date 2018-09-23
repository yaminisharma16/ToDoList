package com.webteklabs.navigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shubhamgupta on 4/7/17.
 */

public class StoreData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO.db";
    private static final int DB_VERSION = 1;

    public StoreData(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //  String query = "CREATE TABLE IF NOT EXISTS TASK_TO ( _id INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT )";

        String query = "CREATE TABLE IF NOT EXISTS TASK_TODO ( _id INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT,HOUR TEXT,MIN TEXT,DAY TEXT,TYP TEXT,COMPLETE TEXT)";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS TASK_TO");
        db.execSQL("DROP TABLE IF EXISTS TASK_TODO");
        onCreate(db);

    }

    public boolean store(String title, String description, String hour, String min, String day, String spinner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("DESCRIPTION", description);
        cv.put("HOUR", hour);
        cv.put("MIN", min);
        cv.put("DAY", day);
        cv.put("TYP", spinner);
        cv.put("COMPLETE", "0");
        long Result = db.insert("TASK_TODO", null, cv);
        if (Result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("TASK_TODO", new String[]{"_id", "TITLE", "DESCRIPTION", "DAY", "TYP", "COMPLETE"}, null, null, null, null, null);
        return cursor;
    }

    public Cursor getCompletedData(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("TASK_TODO", new String[]{"_id", "TITLE", "DESCRIPTION", "DAY", "TYP", "COMPLETE"}, "COMPLETE=?", new String[]{status}, null, null, null, null);
        return cursor;
    }

    public Cursor getData(String str) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TASK_TODO", new String[]{"_id", "TITLE", "DESCRIPTION", "DAY", "TYP"}, "TITLE=?", new String[]{str}, null, null, null);
        return cursor;
    }

    public boolean update(String title, String description, String oldtitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("DESCRIPTION", description);
        long Result = db.update("TASK_TODO", cv, "TITLE=?", new String[]{oldtitle});
        if (Result == -1)
            return false;
        else
            return true;

    }

    public boolean updateCheckBox(String title, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("COMPLETE", value);
        long Result = db.update("TASK_TODO", cv, "TITLE=?", new String[]{title});
        if (Result == -1)
            return false;
        else
            return true;

    }

    public boolean delete(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        long Result = db.delete("TASK_TODO", "TITLE=?", new String[]{title});
        if (Result == -1)
            return false;
        else
            return true;
    }

    public long getMinValue() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TASK_TODO", new String[]{"_id", "TITLE", "MIN(MIN)"}, "MIN!=?", new String[]{"0"}, null, null, null);
        // String str  = cursor.getString( cursor.getColumnIndex("TASK_TODO.MIN"));
        //minvalue = Long.parseLong(str);
        String sb = "0";
        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext()) {
                sb = cursor.getString(2);
            }
        long minvalue = Long.parseLong(sb);
        return minvalue;
        }
        else {
            return 0;
        }
    }

    public boolean updateTime(String value) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MIN", "0");
        long Result = db.update("TASK_TODO", cv, "MIN=?", new String[]{value});
        if (Result == -1)
            return false;
        else
            return true;

    }
    public String getTitle(long value) {
        value=value/(1000*60);
        String str = Long.toString(value);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TASK_TODO", new String[]{"_id", "TITLE",}, "MIN=?", new String[]{str}, null, null, null);
        String sb="0";
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext()) {
                sb = cursor.getString(1);
            }
            return sb;

        }
        else {
            return "0";
        }
    }
}
