package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */
public class SendCodeInfo {
    /**
     * flag : Success
     * msg : 6868
     * data : []
     * num : 1
     */

    private String flag;
    private int msg;
    private int num;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
