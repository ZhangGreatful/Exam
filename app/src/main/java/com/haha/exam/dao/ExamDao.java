package com.haha.exam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.Questions;

import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */
public class ExamDao {
    private MyDataBase dataBase;
    private Context context;

    public ExamDao(Context context){
        this.context=context;
        dataBase=new MyDataBase(context);
    }

    public void addAllQuestions(AllQuestions questions){
        SQLiteDatabase db=dataBase.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values=new ContentValues();
            List<AllQuestions.DataBean> question=questions.getData();
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            for (int i=0;i<question.size();i++){
                values.put("sid",question.get(i).getSid());
                values.put("subject",question.get(i).getSubject());
                values.put("chapterid",question.get(i).getChapterid());
                values.put("type",question.get(i).getType());
                values.put("knowledgetype",question.get(i).getKnowledgetype());
                values.put("contenttype",question.get(i).getContenttype());
                values.put("question",question.get(i).getQuestion());
                values.put("answer",question.get(i).getAnswer());
                values.put("detail",question.get(i).getDetail());
                values.put("option", String.valueOf(question.get(i).getOption()));
                values.put("image",question.get(i).getImage());
                values.put("video",question.get(i).getVideo());
                values.put("upstatus",question.get(i).getUpstatus());
                values.put("isdo",question.get(i).getIsdo());
                values.put("choose",question.get(i).getChoose());
                values.put("isshoucang",question.get(i).getIsshoucang());
                db.insert("questions","",values);
            }
        }
        db.close();
    }
}
