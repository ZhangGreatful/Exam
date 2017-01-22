package com.haha.exam.bean;

/**
 * Created by Administrator on 2016/11/29.
 */

public class VideoCollect {

    /**
     * flag : Success
     * msg : 成功
     * data : [{"video_id":"23","video_title":"机动车驾驶证申领与使用","video_url":"http://cloud.video.taobao.com/play/u/2421138284/p/1/e/6/t/1/47497574.mp4","video_thumb":"http://zhaosheng.oss-cn-hangzhou.aliyuncs.com/kaoshi/pc/img/suoluetu/QQ%E6%88%AA%E5%9B%BE20161126115020.png","video_length":"00:17:43","show_count":"16"},{"video_id":"24","video_title":"道路交通信号","video_url":"http://cloud.video.taobao.com/play/u/2421138284/p/1/e/6/t/1/47497711.mp4","video_thumb":"http://zhaosheng.oss-cn-hangzhou.aliyuncs.com/kaoshi/pc/img/suoluetu/QQ%E6%88%AA%E5%9B%BE20161126115619.png","video_length":"00:06:38","show_count":"9"},{"video_id":"1","video_title":"直角转弯","video_url":"http://cloud.video.taobao.com/play/u/262359412/p/1/e/6/t/1/42937404.mp4","video_thumb":"http://zhaosheng.oss-cn-hangzhou.aliyuncs.com/kaoshi/pc/video/pic/kmezjgw.png","video_length":"00:00:51","show_count":"0"}]
     * num : 1
     */

    /**
     * video_id : 23
     * video_title : 机动车驾驶证申领与使用
     * video_url : http://cloud.video.taobao.com/play/u/2421138284/p/1/e/6/t/1/47497574.mp4
     * video_thumb : http://zhaosheng.oss-cn-hangzhou.aliyuncs.com/kaoshi/pc/img/suoluetu/QQ%E6%88%AA%E5%9B%BE20161126115020.png
     * video_length : 00:17:43
     * show_count : 16
     */


    private String video_id;
    private String video_title;
    private String video_url;
    private String video_thumb;
    private String video_length;
    private String show_count;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setVideo_length(String video_length) {
        this.video_length = video_length;
    }

    public String getShow_count() {
        return show_count;
    }

    public void setShow_count(String show_count) {
        this.show_count = show_count;
    }
}

