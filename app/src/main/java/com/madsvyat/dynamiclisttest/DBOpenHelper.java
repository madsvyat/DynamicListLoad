package com.madsvyat.dynamiclisttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by N1 on 23.01.2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "items.db";
    private static final String CREATE_TABLE = "CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < 500; i++) {
            contentValues.put(DBData.Items.NAME, "Item " + (i + 1));
            db.insert(DBData.Items.TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
