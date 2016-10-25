package com.haha.exam.web;

/**
 * Created by Administrator on 2016/10/20.
 */
public class WebInterface {

    //    cartype 车辆类型（小车'xc' 货车'hc' 客车'kc' 摩托车'mtc' 公共'publics'）
//    接口都区分科目，字段是subject，1是科一，4是科四
//course (科目1/4)  chapter_id(题库章节)  type(1:多选 2:单选 3:判断)
// knowledgetyp (知识点类型 1：时间题2：速度题3：距离题4：罚款题5：记分题6：标志题7：标线题8：手势题9：信号灯10：灯光题11：仪表题12：装置题13：路况题14：酒驾题15：动画题16：情景题)
// contenttype(内容类型) detail (我也不知道干嘛的) image (图片地址) video (视频地址) comment (备注) hits (回答数) erranking (不知道干啥的) difficulty (困难程度)
// upstatus (1、已确认2、确认中3、未操作) name (章节名称) car_type (车型)
    public static String knowledge_type = "http://api.jiakao.exueche.com/index.php/Home/index/questionknowledgetype";//专项练习列表
    public static String knowledge_get_type = "http://api.jiakao.exueche.com/index.php/Home/index/knowledgetype/knowtype/1/cartype/publics";//专项练习列表的题目
    public static String all_citys = "http://api.jiakao.exueche.com/index.php/Home/index/allcitys";
    public static String all_questions = "http://api.jiakao.exueche.com/index.php/Home/index/allquestions";//货车可以所有题目

    //    http://api.jiakao.exueche.com/index.php/Home/index/person/telphone/18266142739/name/%E6%9C%B1%E5%A0%83%E7%BD%A1/cartype/xc/area/100/picture/123/school/%E6%83%A0%E8%BE%BE%E9%A9%BE%E6%A0%A1
    public static String person = "http://api.jiakao.exueche.com/index.php/Home/index/person";//设置个人信息
//    参数包括：tel(必须有)，name,cartype,area,pic,school 其余五个可以没有


    //    http://api.jiakao.exueche.com/index.php/Home/index/getperson/telphone/18266142739
    public static String get_person = "http://api.jiakao.exueche.com/index.php/Home/index/getperson";//获取个人信息  1个参数 tel


    /**
     * 错题
     */
    //    http://api.jiakao.exueche.com/index.php/Home/index/adderror/questionid/1/option/1/tel/18266142739
    public static String add_error = "http://api.jiakao.exueche.com/index.php/Home/index/adderror";//加入错题  3个参数questionid  option  tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/checkerror/telphone/18266142739/chapterid/1
    public static String check_error = "http://api.jiakao.exueche.com/index.php/Home/index/checkerror";//查看错题  2个参数 tel chapterid  没有chapterid则查看所有错题

    //    http://api.jiakao.exueche.com/index.php/Home/index/deleteerror/telphone/18266142739/questionid/1/
    public static String delete_error = "http://api.jiakao.exueche.com/index.php/Home/index/deleteerror/telphone";//删除错题  tel  questionid
    //    http://api.jiakao.exueche.com/index.php/Home/index/deleteerror/telphone/18266142739/deleteall/1
    public static String delete_all = "http://api.jiakao.exueche.com/index.php/Home/index/deleteerror";//删除全部错题   tel   deleteall

    //    http://api.jiakao.exueche.com/index.php/Home/index/errorlist/telphone/18266142739
    public static String error_list = "http://api.jiakao.exueche.com/index.php/Home/index/errorlist";//错题分类列表  tel
    /**
     * 收藏
     */
    //    http://api.jiakao.exueche.com/index.php/Home/index/issave/tel/[手机号]/questionid/[题目id]
    public static String is_save = "http://api.jiakao.exueche.com/index.php/Home/index/issave";//收藏试题  tel  questionid
    //    http://api.jiakao.exueche.com/index.php/Home/index/checksave/telphone/18266142739
    public static String check_save = "http://api.jiakao.exueche.com/index.php/Home/index/checksave";//查看收藏 1个参数 tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/collectlist/telphone/18266142739
    public static String collect_list = "http://api.jiakao.exueche.com/index.php/Home/index/collectlist";//收藏列表  tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/deletecollect/telphone/18266142739/deleteall/1
    public static String delete_all_collect = "http://api.jiakao.exueche.com/index.php/Home/index/deletecollect";//清空收藏  tel  deteleall
    //    http://api.jiakao.exueche.com/index.php/Home/index/deletecollect/telphone/18266142739/questionid/1
    public static String delete_collect = "http://api.jiakao.exueche.com/index.php/Home/index/deletecollect";//删除收藏 2个参数 tel  questionid
    //    http://api.jiakao.exueche.com/index.php/Home/index/addright/telphone/18266142739/questionid/5
    public static String add_right = "http://api.jiakao.exueche.com/index.php/Home/index/addright";//加入正确试题  tel  questionid

    //    http://api.jiakao.exueche.com/index.php/Home/index/statistics/telphone/18266142739
    public static String statistics = "http://api.jiakao.exueche.com/index.php/Home/index/statistics";//当前学生的试题统计  tel
    //    http://api.jiakao.exueche.com/index.php/Home/index/chapterquestion/cartype/[车型]  xc小车  hc货车  kc客车  mtc摩托车
    public static String chapter_question = "http://api.jiakao.exueche.com/index.php/Home/index/chapterquestion";//章节划分     cartype
    //    http://api.jiakao.exueche.com/index.php/Home/index/chapteroutquestion/cartype/[车型]/chapterid/[章节id]/subject/[科目]
    public static String chapter_out_question = "http://api.jiakao.exueche.com/index.php/Home/index/chapteroutquestion";//章节出题  cartype chapterid subject


}


