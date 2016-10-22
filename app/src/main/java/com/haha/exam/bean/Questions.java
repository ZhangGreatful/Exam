package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */
public class Questions {
    private String sid;
    private String subject;
    private String chapterid;
    private String type;

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
}
