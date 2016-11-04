package com.haha.exam.bean;

/**
 * Created by Administrator on 2016/11/3.
 */
public class ErrorQuestion {
    private String sid;

    public int getIsdo() {
        return isdo;
    }

    public void setIsdo(int isdo) {
        this.isdo = isdo;
    }


    private int isdo;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    private int choose;
    private String chapterid;

    public String getChapterid() {
        return chapterid;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }
}
