package com.haha.exam.bean;

/**
 * Created by Administrator on 2016/12/28.
 */

public class TimeSub4 {
    public  TimeSub4(){

    }
    public TimeSub4(TimeBean timeBean){
        this.timeBean=timeBean;
    }
    private TimeBean timeBean;
    public void setTimeBean(TimeBean timeBean){
        this.timeBean=timeBean;
    }
    public TimeBean getTimeBean(){
        return  timeBean;
    }
}
