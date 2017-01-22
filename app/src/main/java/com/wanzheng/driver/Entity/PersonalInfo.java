package com.wanzheng.driver.Entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */

public class PersonalInfo {

    /**
     * returnData : [{"Uid":561475,"Xingming":"张玉","shenfenid":"371428199204031516","Xingbie":0,"peixunjigou":"","cardtype":1,"UserId":"15566668888","Dizhi":"","touxiang":"http://img.hahayueche.com/MobPic/636202462657898169.jpg","Yewuleixing":0,"traintype":"C1","jiarushijian":"2017-01-17 10:36:35","Sheng":"山东省","Chengshi":"德州市","Xian":"德城区"}]
     * returnCode : 1
     * Total : 1
     * PageSize : 1
     * CurrPage : 1
     * PageCount : 1
     */

    private int returnCode;
    private int Total;
    private int PageSize;
    private int CurrPage;
    private int PageCount;
    private List<ReturnDataBean> returnData;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public int getCurrPage() {
        return CurrPage;
    }

    public void setCurrPage(int CurrPage) {
        this.CurrPage = CurrPage;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public List<ReturnDataBean> getReturnData() {
        return returnData;
    }

    public void setReturnData(List<ReturnDataBean> returnData) {
        this.returnData = returnData;
    }

    public static class ReturnDataBean {
        /**
         * Uid : 561475
         * Xingming : 张玉
         * shenfenid : 371428199204031516
         * Xingbie : 0
         * peixunjigou :
         * cardtype : 1
         * UserId : 15566668888
         * Dizhi :
         * touxiang : http://img.hahayueche.com/MobPic/636202462657898169.jpg
         * Yewuleixing : 0
         * traintype : C1
         * jiarushijian : 2017-01-17 10:36:35
         * Sheng : 山东省
         * Chengshi : 德州市
         * Xian : 德城区
         */

        private int Uid;
        private String Xingming;
        private String shenfenid;
        private int Xingbie;
        private String peixunjigou;
        private int cardtype;
        private String UserId;
        private String Dizhi;
        private String touxiang;
        private int Yewuleixing;
        private String traintype;
        private String jiarushijian;
        private String Sheng;
        private String Chengshi;
        private String Xian;

        public int getUid() {
            return Uid;
        }

        public void setUid(int Uid) {
            this.Uid = Uid;
        }

        public String getXingming() {
            return Xingming;
        }

        public void setXingming(String Xingming) {
            this.Xingming = Xingming;
        }

        public String getShenfenid() {
            return shenfenid;
        }

        public void setShenfenid(String shenfenid) {
            this.shenfenid = shenfenid;
        }

        public int getXingbie() {
            return Xingbie;
        }

        public void setXingbie(int Xingbie) {
            this.Xingbie = Xingbie;
        }

        public String getPeixunjigou() {
            return peixunjigou;
        }

        public void setPeixunjigou(String peixunjigou) {
            this.peixunjigou = peixunjigou;
        }

        public int getCardtype() {
            return cardtype;
        }

        public void setCardtype(int cardtype) {
            this.cardtype = cardtype;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getDizhi() {
            return Dizhi;
        }

        public void setDizhi(String Dizhi) {
            this.Dizhi = Dizhi;
        }

        public String getTouxiang() {
            return touxiang;
        }

        public void setTouxiang(String touxiang) {
            this.touxiang = touxiang;
        }

        public int getYewuleixing() {
            return Yewuleixing;
        }

        public void setYewuleixing(int Yewuleixing) {
            this.Yewuleixing = Yewuleixing;
        }

        public String getTraintype() {
            return traintype;
        }

        public void setTraintype(String traintype) {
            this.traintype = traintype;
        }

        public String getJiarushijian() {
            return jiarushijian;
        }

        public void setJiarushijian(String jiarushijian) {
            this.jiarushijian = jiarushijian;
        }

        public String getSheng() {
            return Sheng;
        }

        public void setSheng(String Sheng) {
            this.Sheng = Sheng;
        }

        public String getChengshi() {
            return Chengshi;
        }

        public void setChengshi(String Chengshi) {
            this.Chengshi = Chengshi;
        }

        public String getXian() {
            return Xian;
        }

        public void setXian(String Xian) {
            this.Xian = Xian;
        }
    }
}
