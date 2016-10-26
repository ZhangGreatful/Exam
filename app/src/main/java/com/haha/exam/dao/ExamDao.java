package com.haha.exam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */
public class ExamDao {
    private MyDataBase dataBase;
    private Context mContext;

    public ExamDao(Context context) {
        this.mContext = context;
        dataBase = new MyDataBase(mContext, "exam.db", null, 1);
    }

    //      放入所有信息
    public void addAllQuestions(AllQuestions questions, String cartype) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        String table_name = cartype + "_questions";
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            List<AllQuestions.DataBean> question = questions.getData();
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            for (int i = 0; i < question.size(); i++) {
                values.put("sid", question.get(i).getSid());
                values.put("subject", question.get(i).getSubject());
                values.put("chapterid", question.get(i).getChapterid());
                values.put("type", question.get(i).getType());
                values.put("knowledgetype", question.get(i).getKnowledgetype());
                values.put("contenttype", question.get(i).getContenttype());
                values.put("question", question.get(i).getQuestion());
                values.put("answer", question.get(i).getAnswer());
                values.put("detail", question.get(i).getDetail());
                List<String> option = question.get(i).getOption();
                String options = null;
                for (int j = 0; i < option.size(); i++) {
                    if (j == option.size() - 1) {
                        options += option.get(j);
                    } else {
                        options = options + option.get(j) + ",";
                    }
                }

                values.put("option", options);
                values.put("image", question.get(i).getImage());
                values.put("video", question.get(i).getVideo());
                values.put("upstatus", question.get(i).getUpstatus());
                values.put("isdo", question.get(i).getIsdo());
                values.put("choose", question.get(i).getChoose());
                values.put("isshoucang", question.get(i).getIsshoucang());
                db.insert(table_name, "", values);
            }
            System.out.println("插入数据成功");
        }
        db.close();
    }

    //          获取表格内全部问题
    public List<AllQuestions.DataBean> queryAllQuestions(String cartype,String subject) {
        List<AllQuestions.DataBean> bean = null;
        String table_name = cartype + "_questions";
//        获取数据库实例
        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+table_name+" where subject=?;",new String[] {subject});
        while (cursor.moveToFirst()) {
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            bean = new ArrayList<>();

            bean.get(cursor.getPosition()).setSid(cursor.getString(cursor.getColumnIndex("sid")));
           bean.get(cursor.getPosition()).setSubject(cursor.getString(cursor.getColumnIndex("subject")));
           bean.get(cursor.getPosition()).setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
           bean.get(cursor.getPosition()).setType(cursor.getString(cursor.getColumnIndex("type")));
           bean.get(cursor.getPosition()).setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
           bean.get(cursor.getPosition()).setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
           bean.get(cursor.getPosition()).setQuestion(cursor.getString(cursor.getColumnIndex("question")));
           bean.get(cursor.getPosition()).setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
           bean.get(cursor.getPosition()).setDetail(cursor.getString(cursor.getColumnIndex("detail")));
            String str = cursor.getString(cursor.getColumnIndex("option"));
            String[] strs = str.split(",");
            List<String> option = Arrays.asList(strs);
            for (String s : option) {
                bean.get(cursor.getPosition()).setOption(option);
            }
            bean.get(cursor.getPosition()).setImage(cursor.getString(cursor.getColumnIndex("image")));
            bean.get(cursor.getPosition()).setVideo(cursor.getString(cursor.getColumnIndex("video")));
            bean.get(cursor.getPosition()).setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
            bean.get(cursor.getPosition()).setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
            bean.get(cursor.getPosition()).setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
            bean.get(cursor.getPosition()).setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));

        }
        cursor.close();
        db.close();
        return bean;
    }
}
