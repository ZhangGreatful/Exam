package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class PersonInfo {
    /**
     * flag : Success
     * msg : [{"id":"1","telphone":"18266142739","area":"100","school":"惠达驾校","car_type":"xc","pictrue":"123","name":"啧啧啧"}]
     * data : []
     * num : 1
     */

    private String flag;
    private int num;
    /**
     * id : 1
     * telphone : 18266142739
     * area : 100
     * school : 惠达驾校
     * car_type : xc
     * pictrue : 123
     * name : 啧啧啧
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
        private String id;
        private String telphone;
        private String area;
        private String school;
        private String car_type;
        private String pictrue;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
