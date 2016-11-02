package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class Statistics {
    /**
     * flag : Success
     * msg : [{"rightcount":"12","errorcount":"30","allcount":"0"}]
     * data : []
     * num : 1
     */

    private String flag;
    private int num;
    /**
     * rightcount : 12
     * errorcount : 30
     * allcount : 0
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
        private String rightcount;
        private String errorcount;
        private String allcount;

        public String getRightcount() {
            return rightcount;
        }

        public void setRightcount(String rightcount) {
            this.rightcount = rightcount;
        }

        public String getErrorcount() {
            return errorcount;
        }

        public void setErrorcount(String errorcount) {
            this.errorcount = errorcount;
        }

        public String getAllcount() {
            return allcount;
        }

        public void setAllcount(String allcount) {
            this.allcount = allcount;
        }
    }
}
