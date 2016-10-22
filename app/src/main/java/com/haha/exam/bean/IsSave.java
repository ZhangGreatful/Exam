package com.haha.exam.bean;

import java.util.List;

/**
 * 收藏
 * Created by Administrator on 2016/10/22.
 */
public class IsSave {

    /**
     * flag : Success
     * msg : 收藏成功
     * data : []
     * num : 1
     */

    private String flag;
    private String msg;
    private int num;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
