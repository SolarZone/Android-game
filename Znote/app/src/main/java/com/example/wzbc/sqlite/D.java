package com.example.wzbc.sqlite;

/**
 * Created by WZBC on 2018/4/16.
 */

public final class D {
    public static final String DATABASENAME="notes.db";
    public static final int DATABASE_VERSION=1;

    public static final class Tables{
        public static final String NOTES="notes";
    }

    public static final class Notes{
        public static final String KEY_ROWID="_id";
        public static final String FIELD_NOTE="note";
        public static final String FIELD_NOTEETAIL="note_detail";
    }



}
