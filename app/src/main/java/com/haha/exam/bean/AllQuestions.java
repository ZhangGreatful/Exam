package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */
public class AllQuestions {


    /**
     * flag : Success
     * msg : 0
     * data : [{"option":["A、违章行为","B、违法行为","C、过失行为","D、违规行为"],"sid":"1","subject":"1","chapterid":"1","type":"2","knowledgetype":"16","contenttype":"2","question":"驾驶机动车在道路上违反道路交通安全法的行为，属于什么行为？","answer":"2","detail":"违反《道路交通安全法》，违反法律当然就是违法行为了。现在已经没有违规行为和违章行为一说了，都是违法行为。","image":"","video":"","comment":null,"hits":null,"erranking":null,"upstatus":"1","isdo":0,"choose":0,"isshoucang":0}]
     * num : 1
     */

    private String flag;
    private int msg;
    private int num;
    /**
     * option : ["A、违章行为","B、违法行为","C、过失行为","D、违规行为"]
     * sid : 1
     * subject : 1
     * chapterid : 1
     * type : 2
     * knowledgetype : 16
     * contenttype : 2
     * question : 驾驶机动车在道路上违反道路交通安全法的行为，属于什么行为？
     * answer : 2
     * detail : 违反《道路交通安全法》，违反法律当然就是违法行为了。现在已经没有违规行为和违章行为一说了，都是违法行为。
     * image :
     * video :
     * comment : null
     * hits : null
     * erranking : null
     * upstatus : 1
     * isdo : 0
     * choose : 0
     * isshoucang : 0
     */

    private List<DataBean> data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String sid;
        private String subject;
        private String chapterid;
        private String type;
        private String knowledgetype;
        private String contenttype;
        private String question;
        private String answer;
        private String detail;
        private String image;
        private String video;
        private Object comment;
        private Object hits;
        private Object erranking;
        private String upstatus;
        private int isdo;
        private int choose;
        private int isshoucang;
        private List<String> option;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getChapterid() {
            return chapterid;
        }

        public void setChapterid(String chapterid) {
            this.chapterid = chapterid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getKnowledgetype() {
            return knowledgetype;
        }

        public void setKnowledgetype(String knowledgetype) {
            this.knowledgetype = knowledgetype;
        }

        public String getContenttype() {
            return contenttype;
        }

        public void setContenttype(String contenttype) {
            this.contenttype = contenttype;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public Object getHits() {
            return hits;
        }

        public void setHits(Object hits) {
            this.hits = hits;
        }

        public Object getErranking() {
            return erranking;
        }

        public void setErranking(Object erranking) {
            this.erranking = erranking;
        }

        public String getUpstatus() {
            return upstatus;
        }

        public void setUpstatus(String upstatus) {
            this.upstatus = upstatus;
        }

        public int getIsdo() {
            return isdo;
        }

        public void setIsdo(int isdo) {
            this.isdo = isdo;
        }

        public int getChoose() {
            return choose;
        }

        public void setChoose(int choose) {
            this.choose = choose;
        }

        public int getIsshoucang() {
            return isshoucang;
        }

        public void setIsshoucang(int isshoucang) {
            this.isshoucang = isshoucang;
        }

        public List<String> getOption() {
            return option;
        }

        public void setOption(List<String> option) {
            this.option = option;
        }
    }
}
