package com.haha.exam.bean;

/**
 * Created by Administrator on 2017/1/3.
 */

public class Icon {

    /**
     * flag : Success
     * msg : 学时记录生成成功
     * data : {"id":"1","banner_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/banner.png","qr_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/qr.png","recordview_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/recordview.png","schoolrank_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/schoolrank.png","coachrank_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/coachrank.png","theoryicon_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/theoryicon.png","videoicon_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/videoicon.png","information_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/information.png","onlinetest_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/onlinetest.png","distanceclass_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/distanceclass.png","order_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/order.png","homepage_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/homepage.png","onlinelearn_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/onlinelearn.png","theoryhour_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/theoryhour.png","personalcenter_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/personalcenter.png","homepage_click_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/homepage_click.png","onlinelearn_click_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/onlinelearn_click.png","theoryhour_click_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/theoryhour_click.png","personalcenter_click_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/personalcenter_click.png"}
     * num : 1
     */

    private String flag;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static class DataBean {
        /**
         * id : 1
         * banner_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/banner.png
         * qr_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/qr.png
         * recordview_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/recordview.png
         * schoolrank_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/schoolrank.png
         * coachrank_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/coachrank.png
         * theoryicon_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/theoryicon.png
         * videoicon_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/videoicon.png
         * information_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/information.png
         * onlinetest_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/onlinetest.png
         * distanceclass_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/distanceclass.png
         * order_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/order.png
         * homepage_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/homepage.png
         * onlinelearn_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/onlinelearn.png
         * theoryhour_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/theoryhour.png
         * personalcenter_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/personalcenter.png
         * homepage_click_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/homepage_click.png
         * onlinelearn_click_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/onlinelearn_click.png
         * theoryhour_click_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/theoryhour_click.png
         * personalcenter_click_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/app/icon/personalcenter_click.png
         */

        private String id;
        private String banner_url;
        private String qr_url;
        private String recordview_url;
        private String schoolrank_url;
        private String coachrank_url;
        private String theoryicon_url;
        private String videoicon_url;
        private String information_url;
        private String onlinetest_url;
        private String distanceclass_url;
        private String order_url;
        private String homepage_url;
        private String onlinelearn_url;
        private String theoryhour_url;
        private String personalcenter_url;
        private String homepage_click_url;
        private String onlinelearn_click_url;
        private String theoryhour_click_url;
        private String personalcenter_click_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getQr_url() {
            return qr_url;
        }

        public void setQr_url(String qr_url) {
            this.qr_url = qr_url;
        }

        public String getRecordview_url() {
            return recordview_url;
        }

        public void setRecordview_url(String recordview_url) {
            this.recordview_url = recordview_url;
        }

        public String getSchoolrank_url() {
            return schoolrank_url;
        }

        public void setSchoolrank_url(String schoolrank_url) {
            this.schoolrank_url = schoolrank_url;
        }

        public String getCoachrank_url() {
            return coachrank_url;
        }

        public void setCoachrank_url(String coachrank_url) {
            this.coachrank_url = coachrank_url;
        }

        public String getTheoryicon_url() {
            return theoryicon_url;
        }

        public void setTheoryicon_url(String theoryicon_url) {
            this.theoryicon_url = theoryicon_url;
        }

        public String getVideoicon_url() {
            return videoicon_url;
        }

        public void setVideoicon_url(String videoicon_url) {
            this.videoicon_url = videoicon_url;
        }

        public String getInformation_url() {
            return information_url;
        }

        public void setInformation_url(String information_url) {
            this.information_url = information_url;
        }

        public String getOnlinetest_url() {
            return onlinetest_url;
        }

        public void setOnlinetest_url(String onlinetest_url) {
            this.onlinetest_url = onlinetest_url;
        }

        public String getDistanceclass_url() {
            return distanceclass_url;
        }

        public void setDistanceclass_url(String distanceclass_url) {
            this.distanceclass_url = distanceclass_url;
        }

        public String getOrder_url() {
            return order_url;
        }

        public void setOrder_url(String order_url) {
            this.order_url = order_url;
        }

        public String getHomepage_url() {
            return homepage_url;
        }

        public void setHomepage_url(String homepage_url) {
            this.homepage_url = homepage_url;
        }

        public String getOnlinelearn_url() {
            return onlinelearn_url;
        }

        public void setOnlinelearn_url(String onlinelearn_url) {
            this.onlinelearn_url = onlinelearn_url;
        }

        public String getTheoryhour_url() {
            return theoryhour_url;
        }

        public void setTheoryhour_url(String theoryhour_url) {
            this.theoryhour_url = theoryhour_url;
        }

        public String getPersonalcenter_url() {
            return personalcenter_url;
        }

        public void setPersonalcenter_url(String personalcenter_url) {
            this.personalcenter_url = personalcenter_url;
        }

        public String getHomepage_click_url() {
            return homepage_click_url;
        }

        public void setHomepage_click_url(String homepage_click_url) {
            this.homepage_click_url = homepage_click_url;
        }

        public String getOnlinelearn_click_url() {
            return onlinelearn_click_url;
        }

        public void setOnlinelearn_click_url(String onlinelearn_click_url) {
            this.onlinelearn_click_url = onlinelearn_click_url;
        }

        public String getTheoryhour_click_url() {
            return theoryhour_click_url;
        }

        public void setTheoryhour_click_url(String theoryhour_click_url) {
            this.theoryhour_click_url = theoryhour_click_url;
        }

        public String getPersonalcenter_click_url() {
            return personalcenter_click_url;
        }

        public void setPersonalcenter_click_url(String personalcenter_click_url) {
            this.personalcenter_click_url = personalcenter_click_url;
        }
    }
}
