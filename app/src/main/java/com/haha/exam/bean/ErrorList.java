package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ErrorList {

    /**
     * flag : Success
     * msg : 12
     * data : [{"chapterid":"1","chaptername":"道路交通安全法律、法规和规章","errorcount":"8"},{"chapterid":"2","chaptername":"交通信号","errorcount":"2"},{"chapterid":"3","chaptername":"安全行车、文明驾驶基础知识","errorcount":"1"},{"chapterid":"4","chaptername":"机动车驾驶操作相关基础知识","errorcount":"1"}]
     * num : 1
     */

    private String flag;
    private String msg;
    private int num;
    /**
     * chapterid : 1
     * chaptername : 道路交通安全法律、法规和规章
     * errorcount : 8
     */

    private List<DataBean> data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
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
        private String chapterid;
        private String chaptername;
        private String errorcount;

        public String getChapterid() {
            return chapterid;
        }

        public void setChapterid(String chapterid) {
            this.chapterid = chapterid;
        }

        public String getChaptername() {
            return chaptername;
        }

        public void setChaptername(String chaptername) {
            this.chaptername = chaptername;
        }

        public String getErrorcount() {
            return errorcount;
        }

        public void setErrorcount(String errorcount) {
            this.errorcount = errorcount;
        }
    }
}
