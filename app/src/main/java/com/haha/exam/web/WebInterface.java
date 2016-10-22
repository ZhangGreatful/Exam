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
    public static String all_questions = "http://api.jiakao.exueche.com/index.php/Home/index/allquestions/cartype/hc/subject/1";//货车可以所有题目
//    http://api.jiakao.exueche.com/index.php/Home/index/issave/questionid/1/tel/18266142739
    public static String is_save="http://api.jiakao.exueche.com/index.php/Home/index/issave";//收藏接口 2个参数questionid  tel
//    http://api.jiakao.exueche.com/index.php/Home/index/adderror/questionid/1/option/1/tel/18266142739
    public static String add_error="http://api.jiakao.exueche.com/index.php/Home/index/adderror";//加入错题  3个参数questionid  option  tel
//    http://api.jiakao.exueche.com/index.php/Home/index/person/telphone/18266142739/name/%E6%9C%B1%E5%A0%83%E7%BD%A1/cartype/xc/area/100/picture/123/school/%E6%83%A0%E8%BE%BE%E9%A9%BE%E6%A0%A1
    public static String person="http://api.jiakao.exueche.com/index.php/Home/index/person";//设置个人信息
//    参数包括：tel(必须有)，name,cartype,area,pic,school 其余五个可以没有


//    http://api.jiakao.exueche.com/index.php/Home/index/getperson/telphone/18266142739
    public static String get_person="http://api.jiakao.exueche.com/index.php/Home/index/getperson";//获取个人信息  1个参数 tel


}
