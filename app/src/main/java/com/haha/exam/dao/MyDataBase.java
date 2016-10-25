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

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, "exam.db", factory, version);
        System.out.println("创建db");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("执行oncreat方法");
        sqLiteDatabase.execSQL(sql);
        System.out.println("创建数据库，执行sql语句");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("更新数据库");
    }
}
