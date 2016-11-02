package com.haha.exam.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.haha.exam.bean.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DBNAME = "exam.db";
    final String xc_question_sql = "create  table  xc_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String hc_question_sql = "create  table  hc_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String kc_question_sql = "create  table  kc_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String mtc_question_sql = "create  table  mtc_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";

    final String one_collect_sql = "create  table  one_collection_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String four_collect_sql = "create  table  four_collection_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String one_error_sql = "create  table  one_error_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String four_error_sql = "create  table  four_error_questions (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, type text, knowledgetype text," +
            " contenttype text, question text, answer text, detail text, option text, image text, video text," +
            " upstatus text, isdo integer, choose integer, isshoucang integer)";
    final String my_grade_sql = "create  table  grade (id integer primary key autoincrement," +
            " sid text, subject text, chapterid text, cartype text, date text, time text," +
            " rightcount integer, option text, grade text)";

    /**
     * 在SQLiteOpenHelper的子类当中，必须有该构造函数
     *
     * @param context 上下文对象
     * @param name    数据库名称
     * @param factory
     * @param version 当前数据库的版本，值必须是整数并且是递增的状态
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public DatabaseHelper(Context context) {
        this(context, DBNAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(xc_question_sql);//创建小车所有问题的表
        sqLiteDatabase.execSQL(hc_question_sql);//创建货车所有问题的表
        sqLiteDatabase.execSQL(kc_question_sql);//创建客车所有问题的表
        sqLiteDatabase.execSQL(mtc_question_sql);//创建摩托车所有问题的表
        sqLiteDatabase.execSQL(one_collect_sql);//创建收藏的表  科一
        sqLiteDatabase.execSQL(four_collect_sql);//创建收藏的表   科四
        sqLiteDatabase.execSQL(one_error_sql);//创建错题表   科一
        sqLiteDatabase.execSQL(four_error_sql);//创建错题表   科四
        sqLiteDatabase.execSQL(my_grade_sql);//我的成绩列表
        System.out.println("创建数据库，执行sql语句，创建8个table表格，");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("更新数据库");
    }


}
