package com.wanzheng.driver.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.R.integer;
import android.content.Context;

import com.android.volley.Request.Method;

public class NetInterface {
    private static NetInterface netService;
    // public static String IP = "http://wz.1xueche.cn/";
    public static String IP = "http://121.40.223.100:88/";
    public static String IP2 = "http://mobpic.hahayueche.com/";

    private static String Public = "public/";
    private static String Student = "student/";
    private static String Order = "order/";
    private static String Setting = "setting/";
    public static String getShareInfo = "cms.php/JK/getShareInfo?";
    private static String Peilian = "peilian/";
    private static String School = "drivingschool.aspx?action=get_drivingschool_nearby";
    private static String NearSchool = "drivingschool.aspx?action=get_jiaxiaolist_nearby";
    private static String coachList = "coach.aspx?action=get_coachs_school_0723";
    private static String coachDetail = "coach.aspx?action=get_coach";
    private static String OrderState = "order.aspx?action=get_order_zhuangtai";
    private static String review = "evaluation.aspx?action=get_evaluation";
    private static String coachtime = "student.aspx?action=get_coachs_time";
    private static String submitOrder = "submit_order.aspx?action=submit_order";
    private static String login = "user.ashx?do=login2222";
    private static String myself = "student.aspx?action=get_mySelf";
    private static String OrderDetail = "order.aspx?action=get_order_student";
    private static String push = "push/PushExample.php?";
    private static String ORDERHISTORY = "order.aspx?action=get_order_list_history";
    private static String ORDERCURR = "order.aspx?action=get_order_list_curr";
    private static String CITYID = "city.aspx?action=get_cityid";
    private static String EasyOrder = "order.aspx?action=submit_order_student";
    private static String mall = "ebi.aspx?action=get_ebi";
    private static String FeedBackList = "fankui.aspx?action=get_fankui";
    private static String Message = "message.aspx?action=get_message";
    private static String Notice = "message.aspx?action=get_message_user";
    private static String deleteMessage = "message.aspx?action=delete_message_mid";
    private static String CANLCELORDER = "order.aspx?action=cancel_order";
    private static String PriceTable = "pricelist.aspx?action=get_pricelist_rule";
    private static String pay = "order.aspx?action=balance_order";
    private static String update = "user.aspx?action=update_password";
    private static String getCheck = "user.aspx?action=user_get_yzm";
    private static String yzmLogin = "user.aspx?action=user_login_yzm";
    private static String timeTitle = "coach.aspx?action=get_coachs_time_title";
    private static String DateTitle = "coach.aspx?action=get_coachs_time_day";
    private static String stuDateTitle = "student.aspx?action=get_my_yiyue_shijianduan";
    private static String updatePassword = "user.aspx?action=update_password";
    private static String plan = "plan.aspx?action=get_student_keshi";
    private static String verison = "versions.aspx?action=get_versions";
    public static String IMAGE = IP + "setting/Uploadfiles/IDCardImage/";
    private static String changeSubject = "xueyuan.aspx?action=set_xueyuan_kemu";
    private static String Ad = "public.aspx?action=get_guanggao";
    private static String key = "";
    private static String CityList = "city.aspx?action=get_city_xian_list";
    private static String MoshiByCity = "city.aspx?action=get_city_moshi";
    private static String CitySchool = "city.aspx?action=get_city_xian_list";
    private static String coachList2 = "coach.aspx?action=get_coachs_school_0723";
    private static String ORDERList = "order.aspx?action=get_order_list_curr";
    public static String IMAGEMALL = "http://admin.1xueche.cn/Files/test/";
    private static String MALLPRODUCT = "shangcheng/";
    private static String PRODUCT_DETAIL = "shangcheng_list.aspx?action=shangpin_xq";
    private static String queryOrderReady = "submit_order_ready.aspx?action=submit_order_ready_choose_coach";
    private static String submitOrderReady = "submit_order_ready.aspx?action=submit_order_ready_choose_shiduan";

    public static String IP_COACH = "http://www.jiaolianmishu.com/";
    public static String CoachMishu = "";
    public static String carOrMoney = "jk.php/User/getShezhiByTel?";
    public static String biaoyu = "jk.php/Share/getShareDisc?";

    // 获取修改评价参数
    public static String ip_Review = "http://doc.star7th.com/index.php";
    public static String id_ReviewRe = "953?page_id=4031";
    public static String id_ReviewChange = "953?page_id=4023";


    //查询版本更新
    public static String getUpdateApk = "http://120.26.118.158:8082/App.ashx";

    public static NetInterface getInstance() {
        if (netService == null) {
            netService = new NetInterface();
        }
        return netService;
    }

    public RestEntity loginCheck(String userid, String password) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        map.put("password", password);
        String s = "http://120.26.118.158:8082/user.ashx?do=login2222"
                + "&userid=" + userid + "&password=" + password;
        return new RestEntity(Method.POST, "http://120.26.118.158:8082/"
                + login, map);// 测试
        // return new RestEntity(Method.POST, "urlurl~~~~~~~~~~~~~", loginMap);
    }// 已入文档

    public RestEntity version() {

        String s = IP + Public + verison + "&yonghuleixing=" + 0 + "&xitong="
                + 0;
        return new RestEntity(Method.GET, IP + Public + verison
                + "&yonghuleixing=" + 0 + "&xitong=" + 0);// 测试
        // return new RestEntity(Method.POST, "urlurl~~~~~~~~~~~~~", loginMap);
    }

    public RestEntity updatePassword(int uid, String oldPassword,
                                     String newPassword) {

        String s = IP + Public + updatePassword + "&uid=" + uid
                + "&password_old=" + oldPassword + "&password=" + newPassword;
        return new RestEntity(Method.GET, IP + Public + updatePassword
                + "&uid=" + uid + "&password_old=" + oldPassword + "&password="
                + newPassword);// 测试
        // return new RestEntity(Method.POST, "urlurl~~~~~~~~~~~~~", loginMap);
    }

    /**
     * 注册用户是否已存在
     *
     * @param phone
     * @return
     */
    public RestEntity isexists(String phone) {
        // TODO Auto-generated method stub

        String s = "http://120.26.118.158:8082/user.ashx?do=isexists&username="
                + phone;
        // return new RestEntity(Method.POST, IP
        // + "find/ZizhuYuekao.aspx?action=get_ZizhuYuekao_Url", map);
        return new RestEntity(Method.POST, s);

    }

    /**
     * 获取手机验证码
     *
     * @param phone
     * @return
     */
    public RestEntity getPhonepwd(String phone) {
        // TODO Auto-generated method stub

        String s = "http://120.26.118.158:8082/push.ashx?do=pushmessage&mobile="
                + phone;
        // return new RestEntity(Method.POST, IP
        // + "find/ZizhuYuekao.aspx?action=get_ZizhuYuekao_Url", map);
        return new RestEntity(Method.POST, s);

    }

    public RestEntity baoxianZhifu(String jiaxiaobianhao) {
        //http://120.26.118.158:8082/user.ashx?do=SelectJiaxiaobaoxian&jiaxiaobianhao=558417
        String s2 = "http://120.26.118.158:8082/user.ashx?do=SelectJiaxiaobaoxian&jiaxiaobianhao=" + jiaxiaobianhao;
        return new RestEntity(Method.POST, s2);
    }

    /**
     * 用户注册第一步
     *
     * @param phone
     * @param password
     * @return
     */
    public RestEntity registerJishi1(String phone, String password) {
        // TODO Auto-generated method stub

        String s = "http://120.26.118.158:8082/user.ashx?do=register1&username="
                + phone + "&password=" + password;
        // return new RestEntity(Method.POST, IP
        // + "find/ZizhuYuekao.aspx?action=get_ZizhuYuekao_Url", map);
        return new RestEntity(Method.POST, s);
    }

    /**
     * 用户注册第2步
     *
     * @param phone
     * @param password
     * @return
     */
    public RestEntity registerJishi2(String phone, String name,
                                     String shenfenzheng, int xingbie, String facebase64, int jiaxiaobianhao) {
        // TODO Auto-generated method stub
        String s = "http://120.26.118.158:8082/user.ashx?do=register2";
        // return new RestEntity(Method.POST, IP
        // + "find/ZizhuYuekao.aspx?action=get_ZizhuYuekao_Url", map);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobile", phone);
        map.put("name", name);
        map.put("shenfenzheng", shenfenzheng);
        map.put("xingbie", xingbie + "");
        map.put("facebase64", facebase64);
        map.put("jiaxiaobianhao", jiaxiaobianhao + "");
        return new RestEntity(Method.POST, s, map);
    }

    /**
     * 支付状态获取
     *
     * @param phone
     * @return
     */
    public RestEntity getPayState(String phone) {
        // TODO Auto-generated method stub
        String s = "http://120.26.118.158:8082/pay.ashx?do=getPayState&mobile="
                + phone;
        return new RestEntity(Method.POST, s);
    }

    public RestEntity tuiSong(String doDO, String uid, String jid,
                              String xmobile, String jmobile, String guid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("do", doDO);
        map.put("xid", uid);
        map.put("jid", jid);
        map.put("xmobile", xmobile);
        map.put("jmobile", jmobile);
        map.put("guid", guid);
        return new RestEntity(Method.POST,
                "http://120.26.118.158:8082/QRcode.ashx", map);
    }

    public RestEntity tuiSongSms(String doDO, String uid, String jid,
                                 String xmobile, String jmobile, String guid) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("do", doDO);
        map.put("xid", uid);
        map.put("jid", jid);
        map.put("xmobile", xmobile);
        map.put("jmobile", jmobile);
        map.put("guid", guid);
        map.put("version", "sms");

        return new RestEntity(Method.POST,
                "http://120.26.118.158:8082/QRcode.ashx", map);
    }

    public RestEntity queryZhifu(int uid) {
        String s2 = "http://wz.90xueche.com/student/student.aspx?action=get_studentsjiaxiaozhifubao&U=uid";
        return new RestEntity(Method.GET, s2);
    }

    /**
     * 提交保险支付状态
     *
     * @param mobile
     * @return
     */
    public RestEntity successPay(String mobile) {
        String s2 = "http://120.26.118.158:8082/pay.ashx?do=successPay&mobile="
                + mobile;
        return new RestEntity(Method.POST, s2);
    }

    /**
     * 提交学时费用支付状态
     *
     * @param xueshiid
     * @return
     */
    public RestEntity successPayByTime(int xueshiid) {
        String s2 = "http://120.26.118.158:8082/xueshi.ashx?do=paysuccess&xueshiid="
                + xueshiid;
        return new RestEntity(Method.POST, s2);
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @return
     */

    public RestEntity forgetpwd(String phone, String password) {

        String s = "http://120.26.118.158:8082/user.ashx?do=forgetPassword&mobile="
                + phone + "&newpass=" + password;
        return new RestEntity(Method.POST, s);
    }

    /**
     * 获取学员
     *
     * @param xid
     * @return
     */

    public RestEntity getRecords(int xid) {
        String s = "http://120.26.118.158:8082/xueshi.ashx?do=querymyxueshi&xid=" + xid;
        return new RestEntity(Method.POST, s);
    }

    /**
     * 获取地区驾校
     *
     * @param province
     * @param city
     * @param area
     * @return
     */

    public RestEntity getJxbyDress(String province, String city, String area) {
        String s = "http://120.26.118.158:8082/jiaxiao.ashx?do=queryjiaxiaolist&province=" + province.trim() +
                "&city=" + city.trim() + "&area=" + area.trim();
        return new RestEntity(Method.POST, s);
    }

    /**
     * 获取学员计时订单列表
     *
     * @param xid
     * @param moneystate
     * @param pageindex
     * @return
     */
    public RestEntity queryStudentOrderCurr(int xid, int moneystate, int pageindex) {
        String s = "http://120.26.118.158:8082/xueshi.ashx?do=queryorderlist&xid=" + xid +
                "&moneystate=" + moneystate + "&pageindex=" + pageindex;
        return new RestEntity(Method.POST, s);
    }

    /**
     * 获取学时信息列表
     *
     * @param xid
     * @param kemu
     * @return
     */
    public RestEntity queryStudentPeriod(int xid, int kemu) {
        String s = "http://120.26.118.158:8082/xueshi.ashx?do=selectorderlist&xueyuanid=" + xid +
                "&kemu=" + kemu;
        return new RestEntity(Method.POST, s);
    }

    /**
     * 获取计时查询订单详情
     *
     * @param xueshiid
     * @return
     */
    public RestEntity queryStudentOrderDeil(int xueshiid) {
        String s = "http://120.26.118.158:8082/xueshi.ashx?do=selectorderbyid&xueshiid=" + xueshiid;
        return new RestEntity(Method.POST, s);
    }

    /**
     * 提交评价
     *
     * @param xid
     * @param xueshiid
     * @param jid
     * @param content
     * @return
     */
    public RestEntity submitReview(int xid, int xueshiid, int jid, String content) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("do", "submit");
        map.put("xid", xid + "");
        map.put("xueshiid", xueshiid + "");
        map.put("jid", jid + "");
        map.put("content", content);
        return new RestEntity(Method.POST,
                "http://120.26.118.158:8082/pingjia.ashx", map);
    }

    /**
     * 根据学员编号，科目查询学时列表
     *
     * @param xid      学员编号
     * @param kemu     2 科二，3 科三
     * @param pagesize 每页显示多少条
     * @param currpage 第几页
     * @return
     */
    public RestEntity queryOrderStudy(int xid, int kemu, int pagesize, int currpage) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("do", "selectorderlist");
        map.put("xueyuanid", xid + "");
        map.put("kemu", kemu + "");
        map.put("pagesize", pagesize + "");
        map.put("currpage", currpage + "");
        return new RestEntity(Method.POST,
                "http://120.26.118.158:8082/xueshi.ashx", map);
    }

    /**
     * 订单详情，有效、无效图片接口
     *
     * @param xueshiid 学时编号
     * @param typeid   图片是否有效 0 无效 ，1 有效
     * @return
     */
    public RestEntity queryStudyPic(String xueshiid, String typeid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("do", "selectpicbyid");
        map.put("xueshiid", xueshiid + "");
        map.put("typeid", typeid + "");
        return new RestEntity(Method.POST,
                "http://120.26.118.158:8082/xueshi.ashx", map);
    }

    /**
     * 根据学时编号-查询学时轨迹
     *
     * @param xueshiid 学时编号
     * @return
     */
    public RestEntity StudyGPS(String xueshiid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("do", "selectgpsbyid");
        map.put("xueshiid", xueshiid + "");
        return new RestEntity(Method.POST,
                "http://120.26.118.158:8082/xueshi.ashx", map);
    }


}
