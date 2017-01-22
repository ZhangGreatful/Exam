package com.haha.exam.web;

/**
 * Created by Administrator on 2016/10/20.
 */
public class WebInterface {

    //    http://api.jiakao.exueche.com/index.php/Home/index/register/username/[账号]/password/[密码]
    public static String register = "http://api.jiakao.exueche.com/index.php/Home/index/register";//注册接口  username  password

    //    http://api.jiakao.exueche.com/index.php/Home/index/checklogin/username/[账号]/password/[密码]
    public static String check_login = "http://api.jiakao.exueche.com/index.php/Home/index/checklogin";//登录接口  username  password
    //    http://api.jiakao.exueche.com/index.php/Home/index/sendcode/tel/[手机号]
    public static String send_code = "http://api.jiakao.exueche.com/index.php/Home/index/sendcode";//验证码  tel


    //    cartype 车辆类型（小车'xc' 货车'hc' 客车'kc' 摩托车'mtc' 公共'publics'）
//    接口都区分科目，字段是subject，1是科一，4是科四
//course (科目1/4)  chapter_id(题库章节)  type(1:多选 2:单选 3:判断)
// knowledgetyp (知识点类型 1：时间题2：速度题3：距离题4：罚款题5：记分题6：标志题7：标线题8：手势题9：信号灯10：灯光题11：仪表题12：装置题13：路况题14：酒驾题15：动画题16：情景题)
// contenttype(内容类型) detail (我也不知道干嘛的) image (图片地址) video (视频地址) comment (备注) hits (回答数) erranking (不知道干啥的) difficulty (困难程度)
// upstatus (1、已确认2、确认中3、未操作) name (章节名称) car_type (车型)
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/questionknowledgetype/cartype/xc/subject/1
    public static String knowledge_type = "http://api.jiakao.jpjgpt.com/index.php/Home/index/questionknowledgetype";//专项练习列表

//    http://api.jiakao.jpjgpt.com/index.php/Home/index/knowledgetype/knowtype/1/cartype/xc/subject/1

    public static String knowledge_get_type = "http://api.jiakao.jpjgpt.com/index.php/Home/index/knowledgetype/knowtype/1/cartype/xc/subject/1";//专项练习列表的题目
    public static String all_citys = "http://api.jiakao.exueche.com/index.php/Home/index/allcitys";
    //    http://api.jiakao.exueche.com/index.php/Home/index/drivingschool/cityid/[城市id]
    public static String driving_school = "http://api.jiakao.exueche.com/index.php/Home/index/drivingschool";//根据id查询驾校
    //    http://api.jiakao.jpjgpt.com/index.php/Home/index/allquestions/subject/1/cartype/xc/cityid/1111
    public static String all_questions = "http://api.jiakao.jpjgpt.com/index.php/Home/index/allquestions";//货车可以所有题目

    //    http://api.jiakao.exueche.com/index.php/Home/index/person/telphone/18266142739/name/%E6%9C%B1%E5%A0%83%E7%BD%A1/cartype/xc/area/100/picture/123/school/%E6%83%A0%E8%BE%BE%E9%A9%BE%E6%A0%A1
    public static String person = "http://api.jiakao.exueche.com/index.php/Home/index/person";//设置个人信息
//    参数包括：tel(必须有)，name,cartype,area,pic,school 其余五个可以没有


    //    http://api.jiakao.exueche.com/index.php/Home/index/getperson/telphone/18266142739
    public static String get_person = "http://api.jiakao.exueche.com/index.php/Home/index/getperson";//获取个人信息  1个参数 tel


    /**
     * 错题
     */
    //    http://api.jiakao.exueche.com/index.php/Home/index/adderror/questionid/1/option/1/tel/18266142739
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/adderror/
    public static String add_error = "http://api.jiakao.jpjgpt.com/index.php/Home/index/adderror";//加入错题  3个参数questionid  option  tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/checkerror/telphone/18266142739/chapterid/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/checkerror/signid/7548d9b3-1d77-43b6-84f2-409e883642ae/subject/1/chapterid/1
    public static String check_error = "http://api.jiakao.jpjgpt.com/index.php/Home/index/checkerror";//查看错题  2个参数 tel chapterid  没有chapterid则查看所有错题

    //    http://api.jiakao.exueche.com/index.php/Home/index/deleteerror/telphone/18266142739/questionid/1/
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/deleteerror/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/questionid/1
    public static String delete_error = "http://api.jiakao.jpjgpt.com/index.php/Home/index/deleteerror";//删除错题  tel  questionid
    //    http://api.jiakao.exueche.com/index.php/Home/index/deleteerror/telphone/18266142739/deleteall/1
    public static String delete_all = "http://api.jiakao.jpjgpt.com/index.php/Home/index/deleteerror";//删除全部错题   tel   deleteall

    //    http://api.jiakao.exueche.com/index.php/Home/index/errorlist/telphone/18266142739
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/errorlist/signid/7548d9b3-1d77-43b6-84f2-409e883642ae/subject/1
    public static String error_list = "http://api.jiakao.jpjgpt.com/index.php/Home/index/errorlist";//错题分类列表  tel
    /**
     * 收藏
     */
    //    http://api.jiakao.exueche.com/index.php/Home/index/issave/tel/[手机号]/questionid/[题目id]
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/issave/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/questionid/1/subject/1
    public static String is_save = "http://api.jiakao.jpjgpt.com/index.php/Home/index/issave";//收藏试题  tel  questionid
    //    http://api.jiakao.exueche.com/index.php/Home/index/checksave/telphone/18266142739
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/checksave/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/chapterid/1/subject/1
    public static String check_save = "http://api.jiakao.jpjgpt.com/index.php/Home/index/checksave";//查看收藏 1个参数 tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/collectlist/telphone/18266142739
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/collectlist/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/subject/1
    public static String collect_list = "http://api.jiakao.jpjgpt.com/index.php/Home/index/collectlist";//收藏列表  tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/deletecollect/telphone/18266142739/deleteall/1
    public static String delete_all_collect = "http://api.jiakao.jpjgpt.com/index.php/Home/index/deletecollect";//清空收藏  tel  deteleall
    //    http://api.jiakao.exueche.com/index.php/Home/index/deletecollect/telphone/18266142739/questionid/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/deletecollect/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/questionid/1
    public static String delete_collect = "http://api.jiakao.jpjgpt.com/index.php/Home/index/deletecollect";//删除收藏 2个参数 tel  questionid
    //    http://api.jiakao.exueche.com/index.php/Home/index/addright/telphone/18266142739/questionid/5/subject/1
//    http://api.jiakao.exueche.com/index.php/Home/index/addright/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/questionid/1/subject/1
    public static String add_right = "http://api.jiakao.exueche.com/index.php/Home/index/addright";//加入正确试题  telphone  questionid

    //    http://api.jiakao.exueche.com/index.php/Home/index/statistics/telphone/18266142739
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/statistics/signid/7548d9b3-1d77-43b6-84f2-409e883642ae/subject/1/cartype/xc
    public static String statistics = "http://api.jiakao.jpjgpt.com/index.php/Home/index/statistics";//当前学生的试题统计  tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/chapterquestion/cartype/[车型]  xc小车  hc货车  kc客车  mtc摩托车
//    http://api.jiakao.exueche.com/index.php/Home/index/chapterQuestion/cartype/xc/subject/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/chapterQuestion/cartype/xc/subject/1
    public static String chapter_question = "http://api.jiakao.jpjgpt.com/index.php/Home/index/chapterQuestion";//章节划分     cartype
    //    http://api.jiakao.exueche.com/index.php/Home/index/chapteroutquestion/cartype/[车型]/chapterid/[章节id]/subject/[科目]
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/chapteroutquestion/cartype/xc/chapterid/1/subject/1
    public static String chapter_out_question = "http://api.jiakao.jpjgpt.com/index.php/Home/index/chapteroutquestion/cartype/xc/chapterid/1/subject/1";//章节出题  cartype chapterid subject

    //    http://api.jiakao.exueche.com/index.php/Home/index/findpersonalscore/telphone/18266142739/subject/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/getPersonalScore/signid/559514/subject/1/cartype/C1
    public static String find_personal_score = "http://api.jiakao.jpjgpt.com/index.php/Home/index/getPersonalScore";// 查询个人考试成绩 telphone  subject


//    http://api.jiakao.exueche.com/index.php/Home/index/personalscore/telphone/18266142739/score/100/subject/1/questioninfo/1/cartype/xc/time/500

    public static String personal_score = "http://api.jiakao.exueche.com/index.php/Home/index/personalscore";//上传个人成绩信息


    //    http://api.jiakao.exueche.com/index.php/Home/index/scoreranking/
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/scoreranking/subject/1/signid/7548d9b3-1d77-43b6-84f2-409e883642ae
    public static String score_ranking = "http://api.jiakao.jpjgpt.com/index.php/Home/index/scoreranking";//排行榜  telphone subject cartype cityid

    //    http://api.jiakao.exueche.com/index.php/Home/Video/videochapterlist/subject/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videolistbychapter/subject/1/signid/11111
    public static String video = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videolistbychapter";//获取视频  subject  signid


    //    http://api.jiakao.exueche.com/index.php/Home/Video/videoshowcount/videoid/2
//    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videoshowcount/videoid/1
    public static String video_showcount = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videoshowcount";//播放完视频    播放次数加一

    //    http://api.jiakao.exueche.com/index.php/Home/Video/videocollect/signid/1/videoid/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videocollect
    public static String video_collect = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videocollect";//收藏视频  signid  videoid

    //    http://api.jiakao.exueche.com/index.php/Home/Video/videocollectdelete/signid/1/videoid/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videocollectdelete
    public static String video_collect_delete = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videocollectdelete";//取消视频收藏  signid  videoid

    //    http://api.jiakao.exueche.com/index.php/Home/Video/videolist/signid/1
//    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videolist/signid/558425
    public static String video_list = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videolist";//视频收藏列表  signid


    //    http://api.jiakao.exueche.com/index.php/Home/index/Theoretical_timing/signid/1/
    public static String theoretical_timing = "http://api.jiakao.exueche.com/index.php/Home/index/Theoretical_timing";//开始计时的标志  siginid

    //    http://api.jiakao.exueche.com/index.php/Home/index/Theoretical_timing_two/signid/1/subject/1/type/1/time/1/picture/11111
    public static String theoretical_timing_two = "http://api.jiakao.exueche.com/index.php/Home/index/Theoretical_timing_two";//上传图片和时间  //signid  subject  type  time  picture(base64)

    //考试咨询
    public static String exam_message = "http://www.jiaolianmishu.com/cms.php/JK/getSkillInfo";

    public static String exam_info = "http://www.jiaolianmishu.com/cms.php/JK/getMiJiByTypeID/";

    //    http://api.jiakao.exueche.com/index.php/Home/Dring/technique
//    http://api.jiakao.jpjgpt.com/index.php/Home/Dring/technique
    public static String technique = "http://api.jiakao.jpjgpt.com/index.php/Home/Dring/technique";//考试技巧

    //    http://api.jiakao.exueche.com/index.php/Home/index/getliluntime/signid/457af66f-5c2c-4236-b981-3b85abf1ba26/subject/4
//    http://api.jiakao.jpjgpt.com/index.php/Home/index/getliluntime/signid/7548d9b3-1d77-43b6-84f2-409e883642ae/subject/1
    public static String get_li_lun_time = "http://api.jiakao.jpjgpt.com/index.php/Home/index/getliluntime";//获取理论学时  signid  subject

    //    http://api.jiakao.exueche.com/index.php/Home/index/scorder/signid/1/subject/1

    public static String scorder = "http://api.jiakao.exueche.com/index.php/Home/index/scorder";//生成订单


    //    http://api.jiakao.exueche.com/index.php/Home/index/lilun_pic_order_info/orderid/picture/1/time/1/signid/1
    public static String lilun_pic_order_infor = "http://api.jiakao.exueche.com/index.php/Home/index/lilun_pic_order_info";//上传照片


    //    http://api.jiakao.exueche.com/index.php/Home/index/lilun_order_info/orderid/1/time/1/questioninfo/1/score/1
    public static String lilun_order_info = "http://api.jiakao.exueche.com/index.php/Home/index/lilun_order_info";//上传考试成绩


    //    http://api.jiakao.jpjgpt.com/index.php/Home/Index/theoreticalRecordCreate/signid/111/type/3/subject/1/videoid/222
    public static String theoretical_record_create = "http://api.jiakao.jpjgpt.com/index.php/Home/Index/theoreticalRecordCreate";//创建视频学时订单3

    //    http://api.jiakao.jpjgpt.com/index.php/Home/Index/theoreticalPicInfo
    public static String theoretical_pic_info = "http://api.jiakao.jpjgpt.com/index.php/Home/Index/theoreticalPicInfo";//抓拍图片  signid  orderid  picture  time  type(1.练习，2.模拟考试 3.视频)

    //    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videoProcessRefresh
    public static String video_process_refress = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videoProcessRefresh";//视频观看进度刷新  signid  orderid  playposition


    //    http://api.jiakao.jpjgpt.com/index.php/Home/Index/theoreticalOrderSubmit
    public static String theoretical_order_submit = "http://api.jiakao.jpjgpt.com/index.php/Home/Index/theoreticalOrderSubmit";//仿真测试，视频结束提交状态
    //type 1.练习 2.仿真考试  3.视频 ; orderid  订单id;  signid  唯一编码 ； use_time  用时 ；all_question_info  问题列表；score 分数
    //pass_status  合格标志；  totalnumber  题目总数； correctnumber  正确题数；  wrongnumber  错误题数
    //end_status   结束标志（0.未结束 1.已结束）；playposition  视频播放位置


    //    http://api.jiakao.jpjgpt.com/index.php/Home/Index/getAppIcon
    public static String get_app_icon = "http://api.jiakao.jpjgpt.com/index.php/Home/Index/getAppIcon";//获取app图标

    //    http://api.jiakao.jpjgpt.com/index.php/Home/Index/feedBack/signid/111/content/这款软件不错，非常有用
    public static String feed_back = "http://api.jiakao.jpjgpt.com/index.php/Home/Index/feedBack";//意见反馈

    //    http://api.jiakao.jpjgpt.com/index.php/Home/Video/videoRecordEnd/signid/111/orderid/2234
    public static String video_record_end = "http://api.jiakao.jpjgpt.com/index.php/Home/Video/videoRecordEnd";//视频观看学时结束 signid  orderid

    //    http://api.jiakao.jpjgpt.com/index.php/Home/Index/getRecordlist/signid/4897bab6-7697-42cd-b7d7-11c20dc12e02/subject/1/type/2/pagesize/5/page/1
    public static String get_record_list = "http://api.jiakao.jpjgpt.com/index.php/Home/Index/getRecordlist";//查询学时列表  type 1.练习 2.考试 3.视频  typesize 每页条数

//    http://api.jiakao.jpjgpt.com/index.php/Home/Index/getRecorddetail/recordid/942/pagesize/20/page/1
    public static String get_record_detail="http://api.jiakao.jpjgpt.com/index.php/Home/Index/getRecorddetail";//查询学时详情

//  http://120.26.118.158:8082/user.ashx?do=querystudentForlilun&uid=561475&onlyid=0

    public static String get_personal_info="http://120.26.118.158:8082/user.ashx?do=querystudentForlilun";//获取用户个人信息


//    http://120.26.118.158:8082/user.ashx?do=updateUserInfo
    public static String update_user_info="http://120.26.118.158:8082/user.ashx?do=updateUserInfo";//更新个人信息


//    http://120.26.118.158:8082/user.ashx?do=SelectJiaxiaobaoxian&jiaxiaobianhao=558417
    public static String get_fee="http://120.26.118.158:8082/user.ashx?";//获取保险费用
}



