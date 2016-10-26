package com.haha.exam.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/22.
 */
public class MyDataBase extends SQLiteOpenHelper {

    String xc_question_sql = "create  table  xc_questions(id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    String hc_question_sql = "create  table  hc_questions(id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    String kc_question_sql = "create  table  kc_questions(id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    String mtc_question_sql = "create  table  mtc_questions(id integer primary key autoincrement," +
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
        sqLiteDatabase.execSQL(xc_question_sql);//创建小车所有问题的表
        sqLiteDatabase.execSQL(hc_question_sql);//创建货车所有问题的表
        sqLiteDatabase.execSQL(kc_question_sql);//创建客车所有问题的表
        sqLiteDatabase.execSQL(mtc_question_sql);//创建摩托车所有问题的表
        System.out.println("创建数据库，执行sql语句，创建4种车类型的table表格，");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("更新数据库");
    }
}
