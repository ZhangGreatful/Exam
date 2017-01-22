package com.wanzheng.driver.util;

/**
 * Created by Administrator on 2016/10/7 0007.
 */
public class dlduibi {
    private String session_id;
    private String errormsg;
    private int similarity;
    private int fail_flag;
    private int errorcode;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getFail_flag() {

        return fail_flag;
    }

    public void setFail_flag(int fail_flag) {
        this.fail_flag = fail_flag;
    }

    public int getSimilarity() {

        return similarity;
    }

    public void setSimilarity(int similarity) {
        this.similarity = similarity;
    }

    public String getErrormsg() {

        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getSession_id() {

        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
