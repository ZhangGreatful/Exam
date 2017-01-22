package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class Ranking {

    /**
     * flag : Success
     * msg : [{"signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","studentname":"彬彬","headpic":"http://img.hahayueche.com/MobPic/636189725436561123.jpg","studentscore":"28","use_time":"633"},{"signid":"457af66f-5c2c-4236-b981-3b85abf1ba26","studentname":"张","headpic":"http://img.hahayueche.com/MobPic/636167068213770297.jpg","studentscore":"25","use_time":"228"},{"signid":"ab9a2d0f-19c2-4f88-ad63-a547f587df2e","studentname":null,"headpic":null,"studentscore":"13","use_time":"14"},{"signid":"1112ac32-c854-4f91-9bbf-052d84f450db","studentname":null,"headpic":null,"studentscore":"8","use_time":"66"},{"signid":"7548d9b3-1d77-43b6-84f2-409e883642ae","studentname":"彬","headpic":"http://img.hahayueche.com/MobPic/636171342379049462.jpg","studentscore":"7","use_time":"34"},{"signid":"cdc1931a-e1e2-431b-a519-43977540e38c","studentname":null,"headpic":null,"studentscore":"1","use_time":"96"}]
     * data : [{"signid":"7548d9b3-1d77-43b6-84f2-409e883642ae","personalname":"彬","personalscore":"7","personaltime":"34","personalmingci":5}]
     * num : 1
     */

    private String flag;
    private int num;
    private List<MsgBean> msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MsgBean {
        /**
         * signid : 6e7f8040-acde-4ffa-9ad2-d056f5c5bd78
         * studentname : 彬彬
         * headpic : http://img.hahayueche.com/MobPic/636189725436561123.jpg
         * studentscore : 28
         * use_time : 633
         */

        private String signid;
        private String studentname;
        private String headpic;
        private String studentscore;
        private String use_time;

        public String getSignid() {
            return signid;
        }

        public void setSignid(String signid) {
            this.signid = signid;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getStudentscore() {
            return studentscore;
        }

        public void setStudentscore(String studentscore) {
            this.studentscore = studentscore;
        }

        public String getUse_time() {
            return use_time;
        }

        public void setUse_time(String use_time) {
            this.use_time = use_time;
        }
    }

    public static class DataBean {
        /**
         * signid : 7548d9b3-1d77-43b6-84f2-409e883642ae
         * personalname : 彬
         * personalscore : 7
         * personaltime : 34
         * personalmingci : 5
         */

        private String signid;
        private String personalname;
        private String personalscore;
        private String personaltime;
        private int personalmingci;

        public String getSignid() {
            return signid;
        }

        public void setSignid(String signid) {
            this.signid = signid;
        }

        public String getPersonalname() {
            return personalname;
        }

        public void setPersonalname(String personalname) {
            this.personalname = personalname;
        }

        public String getPersonalscore() {
            return personalscore;
        }

        public void setPersonalscore(String personalscore) {
            this.personalscore = personalscore;
        }

        public String getPersonaltime() {
            return personaltime;
        }

        public void setPersonaltime(String personaltime) {
            this.personaltime = personaltime;
        }

        public int getPersonalmingci() {
            return personalmingci;
        }

        public void setPersonalmingci(int personalmingci) {
            this.personalmingci = personalmingci;
        }
    }
}
