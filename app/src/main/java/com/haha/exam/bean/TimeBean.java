package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class TimeBean {
    /**
     * flag : Success
     * msg : 获取理论时间成功
     * data : [{"exam_time":null,"video_time":663,"all_time":663,"course1":2,"course2":2,"course3":2,"course4":3}]
     * num : 1
     */

    private String flag;
    private String msg;
    private int num;
    /**
     * exam_time : null
     * video_time : 663
     * all_time : 663
     * course1 : 2
     * course2 : 2
     * course3 : 2
     * course4 : 3
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private Object exam_time;
        private int video_time;
        private int all_time;
        private int course1;
        private int course2;
        private int course3;
        private int course4;

        public Object getExam_time() {
            return exam_time;
        }

        public void setExam_time(Object exam_time) {
            this.exam_time = exam_time;
        }

        public int getVideo_time() {
            return video_time;
        }

        public void setVideo_time(int video_time) {
            this.video_time = video_time;
        }

        public int getAll_time() {
            return all_time;
        }

        public void setAll_time(int all_time) {
            this.all_time = all_time;
        }

        public int getCourse1() {
            return course1;
        }

        public void setCourse1(int course1) {
            this.course1 = course1;
        }

        public int getCourse2() {
            return course2;
        }

        public void setCourse2(int course2) {
            this.course2 = course2;
        }

        public int getCourse3() {
            return course3;
        }

        public void setCourse3(int course3) {
            this.course3 = course3;
        }

        public int getCourse4() {
            return course4;
        }

        public void setCourse4(int course4) {
            this.course4 = course4;
        }
    }
}
