package com.wanzheng.driver.Entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class Picture {

    /**
     * flag : Success
     * msg : {"returnData":[{"id":"2741","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927533131126199303040666.jpg","add_time":"2017-01-09 10:05:33","usetime":"8","remark":"PC视频"},{"id":"2742","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927541131126199303040666.jpg","add_time":"2017-01-09 10:05:41","usetime":"8","remark":"PC视频"},{"id":"2743","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927549131126199303040666.jpg","add_time":"2017-01-09 10:05:49","usetime":"8","remark":"PC视频"},{"id":"2744","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927557131126199303040666.jpg","add_time":"2017-01-09 10:05:57","usetime":"8","remark":"PC视频"},{"id":"2745","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927565131126199303040666.jpg","add_time":"2017-01-09 10:06:05","usetime":"8","remark":"PC视频"},{"id":"2746","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927573131126199303040666.jpg","add_time":"2017-01-09 10:06:13","usetime":"8","remark":"PC视频"},{"id":"2747","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927581131126199303040666.jpg","add_time":"2017-01-09 10:06:21","usetime":"8","remark":"PC视频"},{"id":"2748","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927589131126199303040666.jpg","add_time":"2017-01-09 10:06:29","usetime":"8","remark":"PC视频"},{"id":"2749","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927597131126199303040666.jpg","add_time":"2017-01-09 10:06:37","usetime":"8","remark":"PC视频"},{"id":"2750","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927605131126199303040666.jpg","add_time":"2017-01-09 10:06:45","usetime":"8","remark":"PC视频"},{"id":"2751","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927613131126199303040666.jpg","add_time":"2017-01-09 10:06:53","usetime":"8","remark":"PC视频"},{"id":"2752","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927621131126199303040666.jpg","add_time":"2017-01-09 10:07:01","usetime":"8","remark":"PC视频"},{"id":"2753","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927629131126199303040666.jpg","add_time":"2017-01-09 10:07:09","usetime":"8","remark":"PC视频"},{"id":"2754","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927637131126199303040666.jpg","add_time":"2017-01-09 10:07:17","usetime":"8","remark":"PC视频"},{"id":"2755","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927645131126199303040666.jpg","add_time":"2017-01-09 10:07:25","usetime":"8","remark":"PC视频"},{"id":"2756","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927653131126199303040666.jpg","add_time":"2017-01-09 10:07:33","usetime":"8","remark":"PC视频"},{"id":"2757","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927661131126199303040666.jpg","add_time":"2017-01-09 10:07:41","usetime":"8","remark":"PC视频"},{"id":"2758","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927669131126199303040666.jpg","add_time":"2017-01-09 10:07:49","usetime":"8","remark":"PC视频"},{"id":"2759","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927677131126199303040666.jpg","add_time":"2017-01-09 10:07:57","usetime":"8","remark":"PC视频"},{"id":"2760","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927685131126199303040666.jpg","add_time":"2017-01-09 10:08:05","usetime":"8","remark":"PC视频"}],"returnCode":1,"Total":2759,"PageSize":"20","CurrPage":"1","PageCount":138}
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
         * returnData : [{"id":"2741","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927533131126199303040666.jpg","add_time":"2017-01-09 10:05:33","usetime":"8","remark":"PC视频"},{"id":"2742","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927541131126199303040666.jpg","add_time":"2017-01-09 10:05:41","usetime":"8","remark":"PC视频"},{"id":"2743","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927549131126199303040666.jpg","add_time":"2017-01-09 10:05:49","usetime":"8","remark":"PC视频"},{"id":"2744","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927557131126199303040666.jpg","add_time":"2017-01-09 10:05:57","usetime":"8","remark":"PC视频"},{"id":"2745","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927565131126199303040666.jpg","add_time":"2017-01-09 10:06:05","usetime":"8","remark":"PC视频"},{"id":"2746","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927573131126199303040666.jpg","add_time":"2017-01-09 10:06:13","usetime":"8","remark":"PC视频"},{"id":"2747","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927581131126199303040666.jpg","add_time":"2017-01-09 10:06:21","usetime":"8","remark":"PC视频"},{"id":"2748","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927589131126199303040666.jpg","add_time":"2017-01-09 10:06:29","usetime":"8","remark":"PC视频"},{"id":"2749","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927597131126199303040666.jpg","add_time":"2017-01-09 10:06:37","usetime":"8","remark":"PC视频"},{"id":"2750","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927605131126199303040666.jpg","add_time":"2017-01-09 10:06:45","usetime":"8","remark":"PC视频"},{"id":"2751","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927613131126199303040666.jpg","add_time":"2017-01-09 10:06:53","usetime":"8","remark":"PC视频"},{"id":"2752","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927621131126199303040666.jpg","add_time":"2017-01-09 10:07:01","usetime":"8","remark":"PC视频"},{"id":"2753","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927629131126199303040666.jpg","add_time":"2017-01-09 10:07:09","usetime":"8","remark":"PC视频"},{"id":"2754","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927637131126199303040666.jpg","add_time":"2017-01-09 10:07:17","usetime":"8","remark":"PC视频"},{"id":"2755","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927645131126199303040666.jpg","add_time":"2017-01-09 10:07:25","usetime":"8","remark":"PC视频"},{"id":"2756","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927653131126199303040666.jpg","add_time":"2017-01-09 10:07:33","usetime":"8","remark":"PC视频"},{"id":"2757","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927661131126199303040666.jpg","add_time":"2017-01-09 10:07:41","usetime":"8","remark":"PC视频"},{"id":"2758","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927669131126199303040666.jpg","add_time":"2017-01-09 10:07:49","usetime":"8","remark":"PC视频"},{"id":"2759","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927677131126199303040666.jpg","add_time":"2017-01-09 10:07:57","usetime":"8","remark":"PC视频"},{"id":"2760","learnrecord_id":"942","photo_type":"2","photo_url":"http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927685131126199303040666.jpg","add_time":"2017-01-09 10:08:05","usetime":"8","remark":"PC视频"}]
         * returnCode : 1
         * Total : 2759
         * PageSize : 20
         * CurrPage : 1
         * PageCount : 138
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
             * id : 2741
             * learnrecord_id : 942
             * photo_type : 2
             * photo_url : http://oss.jiaolianmishu.com/zhaosheng/kaoshi/pc/upload/video/1483927533131126199303040666.jpg
             * add_time : 2017-01-09 10:05:33
             * usetime : 8
             * remark : PC视频
             */

            private String id;
            private String learnrecord_id;
            private String photo_type;
            private String photo_url;
            private String add_time;
            private String usetime;
            private String remark;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLearnrecord_id() {
                return learnrecord_id;
            }

            public void setLearnrecord_id(String learnrecord_id) {
                this.learnrecord_id = learnrecord_id;
            }

            public String getPhoto_type() {
                return photo_type;
            }

            public void setPhoto_type(String photo_type) {
                this.photo_type = photo_type;
            }

            public String getPhoto_url() {
                return photo_url;
            }

            public void setPhoto_url(String photo_url) {
                this.photo_url = photo_url;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getUsetime() {
                return usetime;
            }

            public void setUsetime(String usetime) {
                this.usetime = usetime;
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
