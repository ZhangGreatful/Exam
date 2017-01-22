package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class Technique {

    /**
     * flag : Success
     * msg : 成功
     * data : [{"sid":"2","title":"酒驾新规","url":"http://api.jiakao.exueche.com/index.php/Home/Dring/techniqueshow/sid/2"},{"sid":"3","title":"2015新交规扣分标准","url":"http://api.jiakao.exueche.com/index.php/Home/Dring/techniqueshow/sid/3"},{"sid":"4","title":"易混淆知识汇总","url":"http://api.jiakao.exueche.com/index.php/Home/Dring/techniqueshow/sid/4"},{"sid":"5","title":"日期类题巧记","url":"http://api.jiakao.exueche.com/index.php/Home/Dring/techniqueshow/sid/5"}]
     * num : 1
     */

    private String flag;
    private String msg;
    private int num;
    /**
     * sid : 2
     * title : 酒驾新规
     * url : http://api.jiakao.exueche.com/index.php/Home/Dring/techniqueshow/sid/2
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
        private String sid;
        private String title;
        private String url;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
