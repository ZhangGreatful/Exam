package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class Fee {

    /**
     * returnData : [{"baoxian":1,"baoxianfei":"49"}]
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
         * baoxian : 1
         * baoxianfei : 49
         */

        private int baoxian;
        private String baoxianfei;

        public int getBaoxian() {
            return baoxian;
        }

        public void setBaoxian(int baoxian) {
            this.baoxian = baoxian;
        }

        public String getBaoxianfei() {
            return baoxianfei;
        }

        public void setBaoxianfei(String baoxianfei) {
            this.baoxianfei = baoxianfei;
        }
    }
}
