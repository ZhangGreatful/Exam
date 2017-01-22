package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ExamQuestions {

    /**
     * subject : 1
     * cartype : xc
     * score : 0
     * telphone : 18266142739
     * time : 11
     * questioninfo : [{"choose":2,"isdo":1,"sid":"643"},{"choose":4,"isdo":1,"sid":"1066"},{"choose":1,"isdo":1,"sid":"322"}]
     */

    private String subject;
    private String cartype;
    private String score;
    private String telphone;
    private String time;
    /**
     * choose : 2
     * isdo : 1
     * sid : 643
     */

    private List<QuestioninfoBean> questioninfo;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<QuestioninfoBean> getQuestioninfo() {
        return questioninfo;
    }

    public void setQuestioninfo(List<QuestioninfoBean> questioninfo) {
        this.questioninfo = questioninfo;
    }

    public static class QuestioninfoBean {
        private int choose;
        private int isdo;
        private String sid;

        public int getChoose() {
            return choose;
        }

        public void setChoose(int choose) {
            this.choose = choose;
        }

        public int getIsdo() {
            return isdo;
        }

        public void setIsdo(int isdo) {
            this.isdo = isdo;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }
}
