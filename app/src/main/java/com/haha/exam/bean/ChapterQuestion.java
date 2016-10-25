package com.haha.exam.bean;

import java.util.List;

/**
 * 章节分类
 * Created by Administrator on 2016/10/25.
 */
public class ChapterQuestion {

    /**
     * flag : Success
     * msg : [{"sid":"4","chaptername":"机动车驾驶操作相关基础知识","questionnum":"139"}]
     * data : []
     * num : 1
     */

    private String flag;
    private int num;
    /**
     * sid : 4
     * chaptername : 机动车驾驶操作相关基础知识
     * questionnum : 139
     */

    private List<MsgBean> msg;
    private List<?> data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class MsgBean {
        private String sid;
        private String chaptername;
        private String questionnum;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getChaptername() {
            return chaptername;
        }

        public void setChaptername(String chaptername) {
            this.chaptername = chaptername;
        }

        public String getQuestionnum() {
            return questionnum;
        }

        public void setQuestionnum(String questionnum) {
            this.questionnum = questionnum;
        }
    }
}
