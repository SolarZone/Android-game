package com.example.wzbc.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.wzbc.sqlite.NotesDao;


/**
 * My File Created by xyz on 2018/4/12.
 */

public class NotesLoader extends AsyncTaskLoader {

    private NotesDao dao;
    public NotesLoader(Context context) {
        super(context);
        dao = new NotesDao(context);
    }
    @Override
    protected void onStartLoading() {
        Log.v("onStartLoading", "OK");
        forceLoad();    //强制加载
    }
    @Override
    public Cursor loadInBackground() {
        Log.v("loadInBackground","OK");
        return dao.getNotes();
    }
    @Override
    protected void onStopLoading() {
        Log.v("onStopLoading","OK");
    }
}






