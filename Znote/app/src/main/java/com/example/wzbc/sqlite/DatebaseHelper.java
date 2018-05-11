package com.example.wzbc.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by WZBC on 2018/4/16.
 */

public class DatebaseHelper extends SQLiteOpenHelper{
    private static final String TAG="ch";
    private static DatebaseHelper mDatebaseHelper;
    private DatebaseHelper(Context context){
        super(context, D.DATABASENAME,null, D.DATABASE_VERSION);
        Log.d(TAG,"DB Version="+ D.DATABASE_VERSION);
    }

    public static DatebaseHelper getDatebaseHelper(Context context){
        if(mDatebaseHelper==null){
            mDatebaseHelper=new DatebaseHelper(context);
        }
        return mDatebaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableNotes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade=" + newVersion);
        dropTableNotes(db);
        createTableNotes(db);
        Log.d(TAG, "onUpgrade success");
    }

    private void createTableNotes(SQLiteDatabase db){
        String createTableNotes="create table "+ D.Tables.NOTES+"("
                + D.Notes.KEY_ROWID+" INTEGER PRIMARY KEY,"
                + D.Notes.FIELD_NOTE+" TEXT,"
                + D.Notes.FIELD_NOTEETAIL+" TEXT" +");";
        db.execSQL(createTableNotes);

    }

    private void dropTableNotes(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + D.Tables.NOTES);
    }

}
