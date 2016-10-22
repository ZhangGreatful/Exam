package com.haha.exam.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/22.
 */
public class MyDataBase extends SQLiteOpenHelper {

    String sql = "create  table  questions(id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";

    public MyDataBase(Context context) {
        
        super(context, "exam.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
