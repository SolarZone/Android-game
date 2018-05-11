package com.example.wzbc.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wzbc.sqlite.D;
import com.example.wzbc.sqlite.DatebaseHelper;

/**
 * Created by WZBC on 2018/4/16.
 */

public class NotesDao {
    private DatebaseHelper mOpenHelper;
    private SQLiteDatabase mydb;
    private String TAG="Datebase:";
    public NotesDao(Context context){
        mOpenHelper= DatebaseHelper.getDatebaseHelper(context);
    }
    public void open(){
        try {
            mydb=mOpenHelper.getWritableDatabase();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close(){
        mydb.close();
        mOpenHelper.close();
    }

    public long insertNote(String noteName,String noteDetail){
        open();
        ContentValues cv=new ContentValues();
        cv.put(D.Notes.FIELD_NOTE,noteName);
        cv.put(D.Notes.FIELD_NOTEETAIL,noteDetail);
        return mydb.insert(D.Tables.NOTES,null,cv);

    }

    public int updateNote(long id,String noteName,String noteDetail){
        open();
        ContentValues cv=new ContentValues();
        cv.put(D.Notes.FIELD_NOTE,noteName);
        cv.put(D.Notes.FIELD_NOTEETAIL,noteDetail);
        return mydb.update(D.Tables.NOTES,cv," _id=?",new String[]{String.valueOf(id)});
    }
    public int delectNote(long id){
        open();
        ContentValues cv=new ContentValues();
        return mydb.delete(D.Tables.NOTES," _id=?",new String[]{String.valueOf(id)});
    }

    public Cursor getNotes() {
        Cursor cur = null;
        try {
            open();
            String col[] = {"_id", D.Notes.FIELD_NOTE, D.Notes.FIELD_NOTEETAIL};
            cur = mydb.query(D.Tables.NOTES, col, null, null, null, null, null);
            return cur;
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return cur;
    }

    public Cursor get(long rowID){
        Cursor cur = null;
        try {
            open();
            String col[] = {"_id", D.Notes.FIELD_NOTE, D.Notes.FIELD_NOTEETAIL};
            cur = mydb.query(D.Tables.NOTES, col,"_id=?",new String[]{rowID+""},null, null, null);

            return cur;
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return cur;
    }
}


