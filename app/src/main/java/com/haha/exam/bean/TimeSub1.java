package com.haha.exam.bean;

/**
 * Created by Administrator on 2016/12/28.
 */

public class TimeSub1 {
    public  TimeSub1(){

    }
    public TimeSub1(TimeBean timeBean){
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
