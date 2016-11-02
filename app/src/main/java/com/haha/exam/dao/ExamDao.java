package com.haha.exam.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haha.exam.bean.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 执行数据库的增，删，改，差操作
 * Created by Administrator on 2016/10/22.
 */
public class ExamDao {
    private DatabaseHelper dbHelper;
    private Context mContext;
    SQLiteDatabase db;

    public ExamDao(Context context) {
        this.mContext = context;
        dbHelper = new DatabaseHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    //      放入所有信息(xc_questions,hc_questions,kc_questions,mtc_questions)
    public void addAllQuestions(AllQuestions questions, String cartype) {

        String table_name = cartype + "_questions";
        if (db.isOpen()) {
            System.out.println("数据库是否打开=======" + db.isOpen());
            ContentValues values = new ContentValues();
            List<AllQuestions.DataBean> question = questions.getData();
            System.out.println("插入数据共======" + question.size() + "条");
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
                String options = "";
                if (option.size() != 0) {
                    for (int j = 0; j < option.size(); j++) {
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

    //    "create  table  grade (id integer primary key autoincrement," +
//            " sid text, subject text, chapterid text, cartype text, date text, time text," +
//            " rightcount integer, option text, grade text)";
    public void addMyGrade(String date, String time, String subject, String cartype, String sid,
                           String chapterid, int rightcount, String option, String grade) {
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("sid", sid);
            values.put("subject", subject);
            values.put("chapterid", chapterid);
            values.put("cartype", cartype);
            values.put("date", date);
            values.put("time", time);
            values.put("rightcount", rightcount);
            values.put("option", option);
            values.put("grade", grade);
            db.insert("grade", "", values);
        }
        System.out.println("我的成绩插入数据看成功");
        db.close();
    }

    //          收藏数据
    public void addCollectQuestions(AllQuestions.DataBean dataBean, String subject) {
        String table_name = subject + "_collection_questions";
        if (db.isOpen()) {
            System.out.println("数据库是否打开=======" + db.isOpen());
            ContentValues values = new ContentValues();
            values.put("sid", dataBean.getSid());
            values.put("subject", dataBean.getSubject());
            values.put("chapterid", dataBean.getChapterid());
            values.put("type", dataBean.getType());
            values.put("knowledgetype", dataBean.getKnowledgetype());
            values.put("contenttype", dataBean.getContenttype());
            values.put("question", dataBean.getQuestion());
            values.put("answer", dataBean.getAnswer());
            values.put("detail", dataBean.getDetail());

            List<String> option = dataBean.getOption();
            String options = "";
            if (option.size() != 0) {
                for (int j = 0; j < option.size(); j++) {
                    options = options + option.get(j) + ",";
                }
            }
            values.put("option", options);
            values.put("image", dataBean.getImage());
            values.put("video", dataBean.getVideo());
            values.put("upstatus", dataBean.getUpstatus());
            values.put("isdo", dataBean.getIsdo());
            values.put("choose", dataBean.getChoose());
            values.put("isshoucang", dataBean.getIsshoucang());
            db.insert(table_name, "", values);
        }
        System.out.println("收藏数据成功");
        db.close();
    }

    //      添加错题
    public void addErrorQuestions(AllQuestions.DataBean dataBean, String subject) {
        String table_name = subject + "_error_questions";
        if (db.isOpen()) {
            System.out.println("数据库是否打开=======" + db.isOpen());
            ContentValues values = new ContentValues();
            values.put("sid", dataBean.getSid());
            values.put("subject", dataBean.getSubject());
            values.put("chapterid", dataBean.getChapterid());
            values.put("type", dataBean.getType());
            values.put("knowledgetype", dataBean.getKnowledgetype());
            values.put("contenttype", dataBean.getContenttype());
            values.put("question", dataBean.getQuestion());
            values.put("answer", dataBean.getAnswer());
            values.put("detail", dataBean.getDetail());

            List<String> option = dataBean.getOption();
            String options = "";
            if (option.size() != 0) {
                for (int j = 0; j < option.size(); j++) {
                    options = options + option.get(j) + ",";
                }
            }
            values.put("option", options);
            values.put("image", dataBean.getImage());
            values.put("video", dataBean.getVideo());
            values.put("upstatus", dataBean.getUpstatus());
            values.put("isdo", dataBean.getIsdo());
            values.put("choose", dataBean.getChoose());
            values.put("isshoucang", dataBean.getIsshoucang());
            db.insert(table_name, "", values);

        }
        System.out.println("添加错题数据成功");
        db.close();
    }


    //          获取表格内全部问题
    public List<AllQuestions.DataBean> queryAllQuestions(String cartype) {
        List<AllQuestions.DataBean> list = new ArrayList<AllQuestions.DataBean>();
        String table_name = cartype + "_questions";
//        获取数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name, null);
        System.out.println("cursor============" + cursor.getCount());
        if (cursor.moveToFirst()) {
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        System.out.println("shuzuchangdu =========" + list.size());
        System.out.println("shuzuneirong =========" + list.get(2).getOption());
        cursor.close();
        return list;
    }

    //          获取表格内全部问题
    public List<AllQuestions.DataBean> queryAllErrorQuestions(String cartype, String subject) {
        List<AllQuestions.DataBean> list = new ArrayList<AllQuestions.DataBean>();
        String table_name = subject + "_error_questions";
//        获取数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name, null);
        System.out.println("cursor============" + cursor.getCount());
        if (cursor.moveToFirst()) {
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        System.out.println("shuzuchangdu =========" + list.size());
        cursor.close();
        return list;
    }

    //      随机练习
    public List<AllQuestions.DataBean> getRandomPractiseQuestions(String cartype) {
        List<AllQuestions.DataBean> list = new ArrayList<>();
        String table_name = cartype + "_questions";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name + " order by random() ;", null);
        if (cursor.moveToFirst()) {
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);

            } while (cursor.moveToNext());
        }
        System.out.println("获取随机练习数据成功，共有数据：" + list.size());
        return list;
    }

    //      科一模拟考试
    public List<AllQuestions.DataBean> getSubject1PractiseQuestions(String cartype) {
        List<AllQuestions.DataBean> list = new ArrayList<>();
        String table_name = cartype + "_questions";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name + " where type = 2 " + " order by random() limit 80 ;", null);
        if (cursor.moveToFirst()) {
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);

            } while (cursor.moveToNext());
            System.out.println("添加完80道单选后的题目长度：  " + list.size());
        }
        cursor = db.rawQuery("select * from " + table_name + " where type = 3 order by random() limit 20 ", null);
        if (cursor.moveToFirst()) {
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);
            } while (cursor.moveToNext());
            System.out.println("添加完20道判断后的题目长度：  " + list.size());
        }
        return list;
    }


    //    获取对应章节内的所有信息
    public List<AllQuestions.DataBean> getChapterQuestions(String cartype, String chapterid) {
        List<AllQuestions.DataBean> list = new ArrayList<AllQuestions.DataBean>();
        String table_name = cartype + "_questions";
        //        获取数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name + " where chapterid=?;", new String[]{chapterid});
        System.out.println("cursor============" + cursor.getCount());
        if (cursor.moveToFirst()) {
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        System.out.println("shuzuchangdu =========" + list.size());
        System.out.println("shuzuneirong =========" + list.get(0).getOption());
        cursor.close();
        return list;
    }

    //    获取对应知识点内的所有信息
    public List<AllQuestions.DataBean> getKnowledgetypeQuestions(String cartype, String knowledgetype) {
        List<AllQuestions.DataBean> list = new ArrayList<AllQuestions.DataBean>();
        String table_name = cartype + "_questions";
        //        获取数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table_name + " where knowledgetype=?;", new String[]{knowledgetype});
        System.out.println("cursor============" + cursor.getCount());
        if (cursor.moveToFirst()) {
//            "create  table  card  (id integer primary key autoincrement," +
//                    "sid text,subject text,chapterid text,type text,knowledgetype text," +
//                    "contenttype text,question text,answer text,detail text,option text,image text,video text," +
//                    "upstatus text,isdo integer,choose integer,isshoucang integer);";
            do {
                AllQuestions.DataBean bean = new AllQuestions.DataBean();
                bean.setSid(cursor.getString(cursor.getColumnIndex("sid")));
                bean.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                bean.setChapterid(cursor.getString(cursor.getColumnIndex("chapterid")));
                bean.setType(cursor.getString(cursor.getColumnIndex("type")));
                bean.setKnowledgetype(cursor.getString(cursor.getColumnIndex("knowledgetype")));
                bean.setContenttype(cursor.getString(cursor.getColumnIndex("contenttype")));
                bean.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
                bean.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                bean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                String str = cursor.getString(cursor.getColumnIndex("option"));
                if (str != null) {
                    String[] strs = str.split(",");
                    List<String> option = Arrays.asList(strs);
                    for (String s : option) {
                        bean.setOption(option);
                    }
                } else {
                    bean.setOption(null);
                }
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
                bean.setVideo(cursor.getString(cursor.getColumnIndex("video")));
                bean.setUpstatus(cursor.getString(cursor.getColumnIndex("upstatus")));
                bean.setIsdo(cursor.getInt(cursor.getColumnIndex("isdo")));
                bean.setChoose(cursor.getInt(cursor.getColumnIndex("choose")));
                bean.setIsshoucang(cursor.getInt(cursor.getColumnIndex("isshoucang")));
                list.add(bean);
            } while (cursor.moveToNext());
        }
        System.out.println("shuzuchangdu =========" + list.size());
        cursor.close();
        return list;
    }

    //    删除错题
    public void deleteQuestion(String subject, String sid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table_name = subject + "_error_sql";
        db.delete(table_name, "sid= ?", new String[]{sid});
    }

    //      删除表
    public void DeleteDatabase(String cartype) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = cartype + "_questions";
        db.execSQL("DELETE FROM " + table);
        db.close();
    }

    //    更新表
    public void updateQuestions() {

    }

    // 获取长度
    public int getContactsCount(String cartype) {
        String table_name = cartype + "_questions";
        String countQuery = "SELECT  * FROM " + table_name;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    //    判断表是否存在
    public boolean exits(String table) {
        boolean exits = false;
        String tablename = table + "_questions";
        String sql = "select * from sqlite_master where name=" + "'" + tablename + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() != 0) {
            exits = true;
            System.out.println(tablename + "表存在");
        }
        return exits;
    }


    public boolean tabbleIsExist(String cartype) {
        System.out.println("判断表是否存在");
        String tableName = cartype + "_questions";
        boolean result = false;
        if (tableName == null) {
            System.out.println(tableName + "不存在");
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String sql = "select count(*) as c from " + "exam.db" + " where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                    System.out.println(tableName + "存在");
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
}
