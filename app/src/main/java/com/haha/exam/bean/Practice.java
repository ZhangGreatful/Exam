package com.haha.exam.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class Practice  {


    /**
     * flag : Success
     * msg : {"returnData":[{"id":"503","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 08:57:49","end_time":null,"use_time":null,"title":"机动车驾驶证申领与使用","remark":null},{"id":"504","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:05:38","end_time":null,"use_time":null,"title":"机动车驾驶证申领与使用","remark":null},{"id":"505","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:11:40","end_time":"2017-01-03 09:16:38","use_time":"0","title":"机动车驾驶证申领与使用","remark":null},{"id":"506","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:18:23","end_time":null,"use_time":null,"title":"道路交通信号","remark":null},{"id":"507","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:26:49","end_time":null,"use_time":null,"title":"道路交通信号","remark":null}],"returnCode":1,"Total":38,"PageSize":"5","CurrPage":"1","PageCount":8}
     * data : []
     * num : 1
     */

    private String flag;
    private MsgBean msg;
    private int num;
    private List<?> data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class MsgBean {
        /**
         * returnData : [{"id":"503","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 08:57:49","end_time":null,"use_time":null,"title":"机动车驾驶证申领与使用","remark":null},{"id":"504","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:05:38","end_time":null,"use_time":null,"title":"机动车驾驶证申领与使用","remark":null},{"id":"505","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:11:40","end_time":"2017-01-03 09:16:38","use_time":"0","title":"机动车驾驶证申领与使用","remark":null},{"id":"506","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:18:23","end_time":null,"use_time":null,"title":"道路交通信号","remark":null},{"id":"507","signid":"6e7f8040-acde-4ffa-9ad2-d056f5c5bd78","type":"3","subject":"1","start_time":"2017-01-03 09:26:49","end_time":null,"use_time":null,"title":"道路交通信号","remark":null}]
         * returnCode : 1
         * Total : 38
         * PageSize : 5
         * CurrPage : 1
         * PageCount : 8
         */

        private int returnCode;
        private int Total;
        private String PageSize;
        private String CurrPage;
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

        public String getPageSize() {
            return PageSize;
        }

        public void setPageSize(String PageSize) {
            this.PageSize = PageSize;
        }

        public String getCurrPage() {
            return CurrPage;
        }

        public void setCurrPage(String CurrPage) {
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
             * id : 503
             * signid : 6e7f8040-acde-4ffa-9ad2-d056f5c5bd78
             * type : 3
             * subject : 1
             * start_time : 2017-01-03 08:57:49
             * end_time : null
             * use_time : null
             * title : 机动车驾驶证申领与使用
             * remark : null
             */

            private String rid;
            private String signid;
            private String type;
            private String subject;
            private String start_time;
            private String end_time;
            private String use_time;
            private String title;
            private String remark;

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            private String score;

            public String getId() {
                return rid;
            }

            public void setId(String id) {
                this.rid = id;
            }

            public String getSignid() {
                return signid;
            }

            public void setSignid(String signid) {
                this.signid = signid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getUse_time() {
                return use_time;
            }

            public void setUse_time(String use_time) {
                this.use_time = use_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
