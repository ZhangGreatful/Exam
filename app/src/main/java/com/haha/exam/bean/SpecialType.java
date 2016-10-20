package com.haha.exam.bean;

import java.util.List;

/**
 * 专项练习列表
 * Created by Administrator on 2016/10/20.
 */
public class SpecialType {

    /**
     * flag : Success
     * msg : [{"sid":1,"num":"68","name":"时间题"},{"sid":2,"num":"76","name":"速度题"},{"sid":3,"num":"99","name":"距离题"},{"sid":4,"num":"48","name":"罚款题"},{"sid":5,"num":"68","name":"计分题"},{"sid":6,"num":"578","name":"标志题"},{"sid":7,"num":"118","name":"标线题"},{"sid":8,"num":"17","name":"手势题"},{"sid":9,"num":"92","name":"信号灯"},{"sid":10,"num":"122","name":"灯光题"},{"sid":11,"num":"1","name":"仪表题"},{"sid":12,"num":"36","name":"装置题"},{"sid":13,"num":"363","name":"路况题"},{"sid":14,"num":"36","name":"酒驾题"},{"sid":15,"num":"21","name":"动画题"},{"sid":16,"num":"1122","name":"情景题"}]
     * data : []
     * num : 1
     */

    private String flag;
    private int num;
    /**
     * sid : 1
     * num : 68
     * name : 时间题
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
        private int sid;
        private String num;
        private String name;

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
