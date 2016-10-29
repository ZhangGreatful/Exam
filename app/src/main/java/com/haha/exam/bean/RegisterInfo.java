package com.haha.exam.bean;

/**
 * Created by Administrator on 2016/10/27.
 */
public class RegisterInfo {
    /**
     * flag : Error
     * msg : 此账号已经注册过！
     * num : 0
     */

    private String flag;
    private String msg;
    private int num;

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
}
