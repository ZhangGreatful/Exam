package com.wanzheng.driver.network;


/**
 * John wang
 * json转换成map
 */

import com.haha.exam.bean.Four;
import com.haha.exam.bean.VideoCollect;
import com.wanzheng.driver.Entity.Coach;
import com.wanzheng.driver.Entity.Mine;
import com.wanzheng.driver.Entity.OrdershowEntity;
import com.wanzheng.driver.Entity.Version;
import com.wanzheng.driver.Entity.phoMsgpwd;
import com.wanzheng.driver.RelatedActivity.PassPicEntity;
import com.wanzheng.driver.RelatedActivity.Period;
import com.wanzheng.driver.cityselect.MyListItem;
import com.wanzheng.driver.util.Constants;
import com.wanzheng.driver.util.GpsEntity;
import com.wanzheng.driver.util.RecordsEntity;
import com.wanzheng.driver.util.SchoolBaoxian;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {


    public static ArrayList<Four> parseEsoterica(String json) {
        // TODO Auto-generated method stub

        ArrayList<Four> list = new ArrayList<Four>();
        try {
            JSONObject object = new JSONObject(json);
            JSONObject jsonObject = object.getJSONObject("mijilist");
            JSONArray jsonArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < jsonArray.length(); i++) {
                Four four = new Four();
                JSONObject object2 = jsonArray.getJSONObject(i);
                four.setWid(object2.getString("mid"));
                four.setBiaoti(object2.getString("biaoti"));
                four.setLogo(object2.getString("logo"));
                four.setIscollection(object2.getInt("iscollection"));
                four.setZuozhe(object2.getString("zuozhe"));
                four.setLiulanliang(object2.getString("liulanliang"));
                four.setUrl(object2.getString("url"));
                list.add(four);
            }
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }


    public static int parseOtherCount(String json) {
        // TODO Auto-generated method stub
        int count = 0;
        JSONObject object;
        try {
            object = new JSONObject(json);
            JSONObject jsonObject = object.getJSONObject("mijilist");
            count = jsonObject.optInt("Total");
            return count;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return count;
    }

    public static SchoolBaoxian parseBaoxian(String json) {
        SchoolBaoxian msg = new SchoolBaoxian();

        try {
            JSONObject msgObj = new JSONObject(json);
            JSONArray jsonArray = msgObj.getJSONArray("returnData");
            JSONObject object = jsonArray.getJSONObject(0);

            msg.setBaoxian(object.optInt("baoxian"));
            msg.setBaoxianfei(object.optDouble("baoxianfei"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static String parseInfo(String json) {
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.optString("msg");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static boolean parseResult(String json) {
        boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.optBoolean("result");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public static int parseNum(String json) {
        int num = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            num = jsonObject.optInt("num");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return num;
    }

    public static int parseOrderId(String json) {
        int num = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            num = jsonObject.getInt("data");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return num;
    }

    public static String parseStringOrderId(String json) {
        String num = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            num = jsonObject.getString("data");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return num;
    }

    public static Object parseMessage(String json) {
        Object msg = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            msg = jsonObject.getJSONObject("data");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    public static int parseCodeJson(String json) {
        int code = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            code = jsonObject.getInt("code");
            // if (code == CodeUtil.CODE) {
            // user.setRoleId(jsonObject.getInt("roleid"));
            // user.setRoleName(jsonObject.getString("rolename"));
            // }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return code;
    }

    public static int parseChangePwd(String json) {
        int code = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            code = jsonObject.getInt("isok");
            // if (code == CodeUtil.CODE) {
            // user.setRoleId(jsonObject.getInt("roleid"));
            // user.setRoleName(jsonObject.getString("rolename"));
            // }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return code;
    }


    public static int parseIMEI(String json) {

        int msg = -1;
        try {
            JSONObject msgObj = new JSONObject(json);

            msg = Integer.parseInt(msgObj.optString("ImeiStatus"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static String parseResponseId(String json) {

        String msg = "";
        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.optString("ResponseId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static int parsePageCount(String json) {
        int count = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            count = jsonObject.optInt("PageCount");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    public static int parseCount(String json) {
        int count = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            count = jsonObject.optInt("Total");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    public static Version parseVersion(String json) {
        Version version = new Version();
        try {
            JSONObject jsonObject = new JSONObject(json);
            version.setId(jsonObject.getInt("BanbenId"));
            version.setVersion(jsonObject.getString("Banbenhao"));
            version.setUrl(jsonObject.getString("DownUrl"));
            version.setDetail(jsonObject.getString("XiangQing"));
            version.setVersionCode(jsonObject.getInt("VersionCode"));
            version.setUpdate(jsonObject.getString("Gengxinneirong"));
            version.setName(jsonObject.getString("VersionName"));
            version.setImperative(jsonObject.getInt("IsQiangzhiUpdateApp"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    public static ArrayList<Coach> parseCoachList(String json) {
        ArrayList<Coach> coachList = new ArrayList<Coach>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray coachArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < coachArray.length(); i++) {
                Coach c = new Coach();
                JSONObject coachObj = coachArray.getJSONObject(i);
                c.setId(coachObj.optInt("Uid"));
                c.setCoachId(coachObj.optString("JiaolianId"));
                c.setSubject(coachObj.optString("Kemu"));
                c.setTeachNum(coachObj.optInt("Jiedanshu"));
                c.setTeachYear(coachObj.optInt("Jiaoling"));
                c.setStar((float) coachObj.optDouble("Xingji"));
                c.setName(coachObj.getString("Xingming"));
                c.setIconpath(coachObj.optString("TouxiangUrl"));
                c.setHome(coachObj.optString("Jiguan"));
                c.setSchoolId(coachObj.optInt("JxId"));
                c.setPhone(coachObj.optString("Dianhua"));
                c.setPer(coachObj.optDouble("Tichengbili"));
                c.setIsDongjie(coachObj.optString("IsDongjie"));
                if (coachObj.has("Shi"))
                    c.setCountryId(coachObj.getInt("Shi"));
                if (coachObj.has("YuyuerenshuShengyu"))
                    c.setShengyuNum(coachObj.getInt("YuyuerenshuShengyu"));
                if (coachObj.has("Jiaolianchexing"))
                    c.setCar(coachObj.getString("Jiaolianchexing"));
                if (coachObj.has("BaomingJiage"))
                    c.setBookPrice(coachObj.getString("BaomingJiage"));
                if (coachObj.has("BaomingJiageOld"))
                    c.setOldPrice(coachObj.getString("BaomingJiageOld"));
                if (coachObj.has("Juli"))
                    c.setDistance(coachObj.getString("Juli"));
                if (coachObj.has("ZengzhiFuwu"))
                    c.setService(coachObj.getString("ZengzhiFuwu"));
                if (coachObj.has("Xian"))
                    c.setArea(coachObj.getString("Xian"));
                if (coachObj.has("Jiaxiaomingcheng"))
                    c.setSchoolName(coachObj.getString("Jiaxiaomingcheng"));
                if (coachObj.has("ZangtingZhuangtai"))
                    c.setStopState(coachObj.getInt("ZangtingZhuangtai"));
                if (coachObj.has("ZantingShijianBegin"))
                    c.setStopStartTime(coachObj
                            .getString("ZantingShijianBegin"));
                if (coachObj.has("ZantingShijianEnd"))
                    c.setStopEndTime(coachObj.getString("ZantingShijianEnd"));
                if (coachObj.has("ZantingYuanyin"))
                    c.setStopReason(coachObj.getString("ZantingYuanyin"));
                coachList.add(c);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return coachList;
        }
        return coachList;
    }

    public static ArrayList<VideoCollect> parseVideoCollect(String json) {
        ArrayList<VideoCollect> videoCollects = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray videoArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < videoArray.length(); i++) {
                VideoCollect videoCollect = new VideoCollect();
                JSONObject videoObj = videoArray.getJSONObject(i);
                videoCollect.setVideo_id(videoObj.getString("video_id"));
                videoCollect.setShow_count(videoObj.getString("show_count"));
                videoCollect.setVideo_length(videoObj.getString("video_length"));
                videoCollect.setVideo_thumb(videoObj.getString("video_thumb"));
                videoCollect.setVideo_title(videoObj.getString("video_title"));
                videoCollect.setVideo_url(videoObj.getString("video_url"));
                videoCollects.add(videoCollect);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return videoCollects;
        }
        return videoCollects;
    }

    public static ArrayList<Coach> parsePeilianCoachList(String json) {
        ArrayList<Coach> coachList = new ArrayList<Coach>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray coachArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < coachArray.length(); i++) {
                Coach c = new Coach();
                JSONObject coachObj = coachArray.getJSONObject(i);
                c.setId(coachObj.getInt("Uid"));
                c.setTeachNum(coachObj.getInt("Jiedanshu"));
                c.setStar((float) (coachObj.getDouble("Xingji")));
                c.setName(coachObj.getString("Xingming"));
                c.setIconpath(coachObj.getString("Touxiang"));
                c.setCarStyle(coachObj.getString("CheXing"));
                if (coachObj.has("PCheBiansuxiang"))
                    c.setCar(coachObj.getString("PCheBiansuxiang"));
                c.setCarPrice(coachObj.getString("Jiage") + "元/小时");
                c.setLng(coachObj.getDouble("Lng"));
                c.setLat(coachObj.getDouble("Lat"));
                String juli = coachObj.getDouble("Juli") + "";
                c.setDistance(juli.substring(0, 3) + "公里");
                coachList.add(c);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return coachList;
        }
        return coachList;
    }

    public static Coach parseCoachDetail(String json) {
        Coach c = new Coach();

        try {
            JSONObject coachObj = new JSONObject(json);

            c.setId(coachObj.getInt("Uid"));
            if (coachObj.has("JiaolianId"))
                c.setCoachId(coachObj.getString("JiaolianId"));
            if (coachObj.has("Kemu"))
                c.setSubject(coachObj.getString("Kemu"));

            c.setTeachNum(coachObj.getInt("Jiedanshu"));
            c.setTeachYear(coachObj.getInt("Jiaoling"));
            c.setStar(coachObj.getInt("Xingji"));
            c.setName(coachObj.getString("Xingming"));
            c.setIconpath(coachObj.getString("TouxiangUrl"));
            c.setSchoolId(coachObj.optInt("JxId"));
            if (coachObj.has("Tichengbili"))
                c.setPer(coachObj.getDouble("Tichengbili") * 100);
            if (coachObj.has("Dianhua")) {
                c.setPhone(coachObj.getString("Dianhua"));
            }
            if (coachObj.has("Shi")) {
                c.setCountryId(coachObj.getInt("Shi"));
            }
            if (coachObj.has("Jiaolianchexing")) {
                c.setCar(coachObj.getString("Jiaolianchexing"));
            }
            if (coachObj.has("Jiguan"))
                c.setHome(coachObj.getString("Jiguan"));

            if (coachObj.has("jiaxiaomingcheng")) {
                c.setSchoolName(coachObj.getString("jiaxiaomingcheng"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public static Coach parsePeilianCoachDetail(String json) {
        Coach c = new Coach();

        try {
            JSONObject coachObj = new JSONObject(json);

            c.setId(coachObj.getInt("Uid"));
            if (coachObj.has("JiaolianId"))
                c.setCoachId(coachObj.getString("JiaolianId"));

            c.setTeachNum(coachObj.getInt("Jiedanshu"));
            c.setTeachYear(coachObj.getInt("Jiaoling"));
            c.setStar(coachObj.getInt("Xingji"));
            c.setName(coachObj.getString("Xingming"));
            if (coachObj.has("Touxiang"))
                c.setIconpath(coachObj.getString("Touxiang"));
            if (coachObj.has("Tichengbili"))
                c.setPer(coachObj.getDouble("Tichengbili") * 100);
            if (coachObj.has("Dianhua")) {
                c.setPhone(coachObj.getString("Dianhua"));
            }
            if (coachObj.has("Shi")) {
                c.setCountryId(coachObj.getInt("Shi"));
            }
            if (coachObj.has("Chexing")) {
                c.setCar(coachObj.getString("Chexing"));
            }
            if (coachObj.has("Jiguan"))
                c.setHome(coachObj.getString("Jiguan"));
            c.setCarStyle(coachObj.getString("PCheBiansuxiang"));
            c.setCarPrice(coachObj.getString("Jiage"));
            c.setCarPJPrice(coachObj.getString("JiagePeijia"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public static ArrayList<MyListItem> parseSchoolList(String json) {
        ArrayList<MyListItem> schoolList = new ArrayList<MyListItem>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray schoolArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < schoolArray.length(); i++) {
                MyListItem s = new MyListItem();
                JSONObject schoolObj = schoolArray.getJSONObject(i);

                s.setName(schoolObj.optString("name"));
                if (schoolObj.has("jiaxiaobianhao"))
                    s.setPcode(schoolObj.getInt("jiaxiaobianhao") + "");
                schoolList.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schoolList;
    }


    public static int parseMsg(String json) {
        int msg = -1;

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.optInt("msg");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static int parseisok(String json) {
        int msg = -1;

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.getInt("isok");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static int parseIsexists(String json) {
        int msg = -1;
        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.getInt("isexists");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static phoMsgpwd parsePhoPwd(String json) {
        phoMsgpwd p = new phoMsgpwd();
        try {
            JSONObject msgObj = new JSONObject(json);
            p.setIssend(msgObj.optInt("issend"));
            p.setCode(msgObj.optString("code"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return p;
    }

    public static String parseErrorMessage(String json) {
        String ErrorMessage = "";

        try {
            JSONObject msgObj = new JSONObject(json);
            ErrorMessage = msgObj.optString("ErrorMessage");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ErrorMessage;
    }

    public static int parseNumMax(String json) {
        int msg = -1;

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.getInt("NumMax");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static int parseData(String json) {
        int msg = -1;

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.getInt("data");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static String parseMsgReason(String json) {
        String msg = "";

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.optString("ErrorMessage");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static ArrayList<OrdershowEntity> parsePayorder(String json) {
        ArrayList<OrdershowEntity> schoolList = new ArrayList<OrdershowEntity>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray schoolArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < schoolArray.length(); i++) {
                OrdershowEntity s = new OrdershowEntity();

                JSONObject schoolObj = schoolArray.getJSONObject(i);
                s.setID(schoolObj.optInt("ID"));
                s.setXid(schoolObj.optInt("Xid"));
                s.setJid(schoolObj.optInt("jid"));
                s.setShiChang(schoolObj.optInt("ShiChang"));
                s.setZhuangtai(schoolObj.optInt("Zhuangtai"));
                s.setMoneyState(schoolObj.optInt("MoneyState"));
                s.setMoney(schoolObj.optDouble("Money"));
                s.setXueShiBianHao(schoolObj.optString("XueShiBianHao"));
                s.setSubject(schoolObj.optString("subject"));
                s.setDistance(schoolObj.optDouble("Distance"));
                s.setEnd(schoolObj.optString("End"));
                s.setStart(schoolObj.optString("Start"));
                s.setJiaolianxingming(schoolObj.optString("jiaolianxingming"));
                s.setJiaxiaobianhao(schoolObj.optInt("Jiaxiaobianhao"));
                s.setXingming(schoolObj.optString("Xingming"));
                s.setChepai(schoolObj.optString("Chepai"));
                s.setPingjiaContent(schoolObj.optString("pingjiaContent"));
                schoolList.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schoolList;
    }

    public static ArrayList<Period> parseStudyorder(String json) {
        ArrayList<Period> schoolList = new ArrayList<Period>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray schoolArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < schoolArray.length(); i++) {
                Period s = new Period();
                JSONObject schoolObj = schoolArray.getJSONObject(i);
                s.setId(schoolObj.optInt("ID"));
                s.setXid(schoolObj.optInt("Xid"));
                s.setJid(schoolObj.optInt("jid"));
                s.setShiChang(schoolObj.optInt("ShiChang"));
                s.setZhuangtai(schoolObj.optInt("Zhuangtai"));
                s.setMoneyState(schoolObj.optInt("MoneyState"));
                s.setMoney(schoolObj.optDouble("Money"));
                s.setXueShiBianHao(schoolObj.optString("XueShiBianHao"));
                s.setSubject(schoolObj.optString("subject"));
                s.setDistance(schoolObj.optDouble("Distance"));
                s.setEnd(schoolObj.optString("End"));
                s.setStart(schoolObj.optString("Start"));
                s.setJiaolianxingming(schoolObj.optString("jiaolianxingming"));
                s.setJiaxiaobianhao(schoolObj.optString("Jiaxiaobianhao"));
                s.setJiaolianxingming(schoolObj.optString("Xingming"));
                s.setChepai(schoolObj.optString("Chepai"));
                s.setJiaxiaomingcheng(schoolObj.optString("Jiaxiaomingcheng"));
                schoolList.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schoolList;
    }


    public static ArrayList<GpsEntity> periodGpsGuiji(String json) {
        ArrayList<GpsEntity> schoolList = new ArrayList<GpsEntity>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray schoolArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < schoolArray.length(); i++) {
                GpsEntity s = new GpsEntity();
                JSONObject schoolObj = schoolArray.getJSONObject(i);
                s.setLng(schoolObj.optDouble("lat"));
                s.setLat(schoolObj.optDouble("Lng"));
                schoolList.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schoolList;
    }

    public static ArrayList<PassPicEntity> periodPicGuiji(String json) {
        ArrayList<PassPicEntity> schoolList = new ArrayList<PassPicEntity>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray schoolArray = jsonObject.getJSONArray("returnData");
            for (int i = 0; i < schoolArray.length(); i++) {
                PassPicEntity s = new PassPicEntity();
                JSONObject schoolObj = schoolArray.getJSONObject(i);
                s.setId(schoolObj.optInt("id"));
                s.setXueshiid(schoolObj.optInt("xueshiid"));
                s.setDingdanid(schoolObj.optString("dingdanid"));
                s.setPicname(schoolObj.optString("picname"));
                s.setPicurl(schoolObj.optString("picurl"));
                s.setAddtime(schoolObj.optString("addtime"));
                s.setShibielv(schoolObj.optString("shibielv"));
                s.setZhuangtai(schoolObj.optInt("zhuangtai"));
                schoolList.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schoolList;
    }

    public static ArrayList<String> parseMsgOrder(String json) {
        ArrayList<String> list = new ArrayList<String>();

        try {
            JSONObject msgObj = new JSONObject(json);
            int msg = msgObj.getInt("msg");
            list.add(msg + "");
            if (msg == 1) {
                // 有数据
                list.add(msgObj.getString("orderid"));
                list.add(msgObj.getString("Shijianduan"));
            } else {

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public static int parseMsgLogin(String json) {

        int uid = -1;
        try {
            JSONObject msgObj = new JSONObject(json);

            uid = msgObj.optInt("Uid");

            // if (msg == 1) {
            // // 有数据
            // list.add(msgObj.getString("orderid"));
            // } else {
            //
            // }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return uid;
    }


    public static Mine parseMine(String json) {
        Mine mine = new Mine();

        try {
            JSONObject mineObject = new JSONObject(json);
            if (mineObject.has("Uid")) {
                mine.setUid(mineObject.optInt("Uid"));
            }
            if (mineObject.has("onlyID")) {
                mine.setOnlyId(mineObject.optString("onlyID"));
            }
            mine.setSchoolId(mineObject.optInt("jxid"));
            mine.setName(mineObject.optString("Xingming"));
            mine.setTelphone(mineObject.optString("UserId"));
            mine.setJiaxiaobianhao(mineObject.optString("jiaxiaobianhao"));
            if (mineObject.has("Jid"))
                mine.setJid(mineObject.getInt("Jid"));
            if (mineObject.has("Youhuiquan"))
                mine.setFree(mineObject.getInt("Youhuiquan"));
            if (mineObject.has("Ebi"))
                mine.setHaha(mineObject.getInt("Ebi"));
            if (mineObject.has("Yue"))
                mine.setPrice(mineObject.getInt("Yue"));
            if (mineObject.has("Xingbie"))
                mine.setSex(mineObject.getInt("Xingbie"));
            if (mineObject.has("Touxiang"))
                mine.setIconPath(mineObject.getString("Touxiang"));
            if (mineObject.has("KaishiYuyueShijian"))
                mine.setStartBookTime(mineObject
                        .getString("KaishiYuyueShijian"));
            if (mineObject.has("JieshuYuyueShijian")) {
                mine.setEndBookTime(mineObject.getString("JieshuYuyueShijian"));
            }
            if (mineObject.has("Kemu")) {
                mine.setSubject(mineObject.getString("Kemu"));
            }

            if (mineObject.has("IsYunxuKuakemu")) {
                mine.setCanDiffrentBook(mineObject.getInt("IsYunxuKuakemu"));
            }

            // if (mineObject.has("Shi")) {
            // mine.setCountry(mineObject.getInt("Shi"));
            // }

            if (mineObject.has("Sheng")) {
                mine.setSheng(mineObject.getString("Sheng"));
            }
            if (mineObject.has("Shi")) {
                mine.setCityName(mineObject.getString("Shi"));
            }
            if (mineObject.has("Xian")) {
                mine.setQu(mineObject.getString("Xian"));
            }
            if (mineObject.has("Jiaxiaomingcheng")) {
                mine.setJiaxiao(mineObject.getString("Jiaxiaomingcheng"));
            }

            if (mineObject.has("Quanxian_xueche")) {
                int authorityS = Integer.parseInt(mineObject
                        .getString("Quanxian_xueche"));
                int authorityP = Integer.parseInt(mineObject
                        .getString("Quanxian_peilian"));

                if (authorityS == 0 && authorityP == 1) {
                    mine.setAuthority(Constants.AuthorityP);// 陪练权限
                } else if (authorityS == 1 && authorityP == 0) {
                    mine.setAuthority(Constants.AuthorityS);// 驾校权限
                } else if (authorityS == 1 && authorityP == 1) {
                    mine.setAuthority(Constants.AuthoritySP);// 驾校权限
                }
            }
            if (mineObject.has("QuxiaoDingdanShichang"))
                mine.setCancelTime(mineObject.getInt("QuxiaoDingdanShichang"));

            if (mineObject.has("Dongjie"))
                mine.setDongjie(mineObject.getInt("Dongjie"));

            if (mineObject.has("QuxiaoDingdanShichang"))
                mine.setCancelTime(mineObject.getInt("QuxiaoDingdanShichang"));
            if (mineObject.has("ZantingZhuangtai"))
                mine.setColdState(mineObject.getInt("ZantingZhuangtai"));
            if (mineObject.has("ZantingShijian"))
                mine.setColdEndDay(mineObject.getString("ZantingShijian"));
            if (mineObject.has("ZantingShijianBegin"))
                mine.setColdStartTime(mineObject
                        .getString("ZantingShijianBegin"));
            if (mineObject.has("ZantingShijianEnd"))
                mine.setColdEndTime(mineObject.getString("ZantingShijianEnd"));
            if (mineObject.has("ZhuceFangshi"))
                mine.setZhuce(mineObject.getInt("ZhuceFangshi"));
            if (mineObject.has("IsVip")) {
                int vip = mineObject.getInt("IsVip");
                if (vip == 1) {
                    mine.setShowDay(mineObject
                            .getInt("ShiduanTianshuStudentVip"));
                } else {
                    mine.setShowDay(mineObject.getInt("ShiduanTianshuStudent"));
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mine;
    }


    public static int parseState(String json) {

        int msg = -1;
        try {
            JSONObject msgObj = new JSONObject(json);

            msg = msgObj.getInt("state");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public static ArrayList<Integer> parsePlan(String json) {
        ArrayList<Integer> planList = new ArrayList<Integer>();
        try {
            JSONObject planObj = new JSONObject(json);
            planList.add(planObj.getInt("kemu2"));
            planList.add(planObj.getInt("kemu3"));
            planList.add(planObj.getInt("YuyuekeshiKemu2Max"));
            planList.add(planObj.getInt("YuyuekeshiKemu3Max"));
            planList.add(planObj.getInt("kemu2Minute"));
            planList.add(planObj.getInt("kemu3Minute"));
            planList.add(planObj.getInt("Kemu2MinuteMax"));
            planList.add(planObj.getInt("Kemu3MinuteMax"));
            planList.add(planObj.getInt("KeshiMoshi"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return planList;
    }


    public static String parseBiaoyu(String json) {
        String data = "分享教练名片，帮助此教练招生！";
        try {
            final JSONObject cardJSONObject = new JSONObject(json);
            data = cardJSONObject.getString("data");
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
            return data;
        }
        return data;
    }

    public static String parseSelfinfo(String json) {
        String data = "未加载到数据";
        try {
            final JSONObject cardJSONObject = new JSONObject(json);
            data = cardJSONObject.getString("selfinfo");
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
            return data;
        }
        return data;
    }

    public static int CollectionState(String json) {
        int IsCollection = -1;

        try {
            JSONObject msgObj = new JSONObject(json);
            IsCollection = msgObj.getInt("IsCollection");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return IsCollection;
    }

    public static String parseStr(String json, String key) {
        String msg = "";

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.getString(key);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    //	[
//	    {
//	        "mins":180,
//	        "totalmins":150,
//	        “subject":"科二"
//	    },
//	    {
//	        "mins":60,
//	        "totalmins":150,
//	        “subject":"科三"
//	    }
//
//	]
    public static ArrayList<RecordsEntity> parseRecords(String json) {
        ArrayList<RecordsEntity> records = new ArrayList<RecordsEntity>();

        try {
            JSONObject msgObj = new JSONObject(json);
            JSONArray Array = msgObj.getJSONArray("array");
            for (int i = 0; i < Array.length(); i++) {
                RecordsEntity record = new RecordsEntity();
                JSONObject Obj = Array.getJSONObject(i);
                record.setHasTimes(Obj.optInt("mins"));
                record.setTotalTimes(Obj.optInt("totalmins"));
                record.setSubject(Obj.optString("subject"));
                records.add(record);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return records;
    }

    public static int parsePaystate(String json) {
        int msg = -1;

        try {
            JSONObject msgObj = new JSONObject(json);
            msg = msgObj.optInt("state");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }
}