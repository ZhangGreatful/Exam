package com.wanzheng.driver.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.haha.exam.MyApplication;
import com.haha.exam.bean.Icon;

public class SystemUtil {
    private static final String MINE = "mine";
    private Context context;
    private static final String OPENID = "openID";
    private static final String RMEMBER = "remember";
    private static final String UID = "uid";
    private static final String ONLY_ID = "only_id";
    private static final String ZHIFU = "zhifurenmember";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String PWD = "password";
    private static final String USER_HEADER = "user_header";
    private static final String CITY_NAME = "city_name";
    private static final String SCHOOL_NAME = "school_name";
    private static final String START_TIMING = "start_timing";
    private static final String MODEL_STATE = "modle_state";
    private static final String IS_SHOW = "is_show";
    private static final String REGESTERSTATE = "regesterstate";
    private static final String LEARNED_TIME = "learned_time";
    private static final String APP_ICON = "app_icon";
    private static final String SHENG = "sheng";
    private static final String SHI = "shi";
    private static final String QU = "qu";
    private static final String JIAXIAOBIANHAO = "jiaxiaobianhao";


    public SystemUtil(Context context) {
        this.context = context;
    }


    public void saveIcon(Icon icon) {
        SharedPreferences preferences = context.getSharedPreferences(APP_ICON,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("banner_url", icon.getData().getBanner_url());
        editor.putString("qr_url", icon.getData().getQr_url());
        editor.putString("recordview_url", icon.getData().getRecordview_url());
        editor.putString("schoolrank_url", icon.getData().getSchoolrank_url());
        editor.putString("coachrank_url", icon.getData().getCoachrank_url());
        editor.putString("theoryicon_url", icon.getData().getTheoryicon_url());
        editor.putString("videoicon_url", icon.getData().getVideoicon_url());
        editor.putString("information_url", icon.getData().getInformation_url());
        editor.putString("onlinetest_url", icon.getData().getOnlinetest_url());
        editor.putString("distanceclass_url", icon.getData().getDistanceclass_url());
        editor.putString("order_url", icon.getData().getOrder_url());
        editor.putString("homepage_url", icon.getData().getHomepage_url());
        editor.putString("onlinelearn_url", icon.getData().getOnlinelearn_url());
        editor.putString("theoryhour_url", icon.getData().getTheoryhour_url());
        editor.putString("personalcenter_url", icon.getData().getPersonalcenter_url());
        editor.putString("homepage_click_url", icon.getData().getHomepage_click_url());
        editor.putString("onlinelearn_click_url", icon.getData().getOnlinelearn_click_url());
        editor.putString("theoryhour_click_url", icon.getData().getTheoryhour_click_url());
        editor.putString("personalcenter_click_url", icon.getData().getPersonalcenter_click_url());
        editor.commit();
    }

    public void saveJiaxiaobianhao(String bianhao) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(JIAXIAOBIANHAO, bianhao);
        editor.commit();
    }

    public String showJiaxiaobianhao() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String city = preferences.getString(JIAXIAOBIANHAO, "");
        return city;
    }

    public String showAppIcon(String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_ICON,
                Context.MODE_PRIVATE);
        String iconUrl = preferences.getString(key, "");
        return iconUrl;
    }

    public void saveSheng(String sheng) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(SHENG, sheng);
        editor.commit();
    }

    public String showSheng() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String city = preferences.getString(SHENG, "");
        return city;
    }

    public void saveShi(String shi) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(SHI, shi);
        editor.commit();
    }

    public String showShi() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String city = preferences.getString(SHI, "");
        return city;
    }

    public void saveQu(String qu) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(QU, qu);
        editor.commit();
    }

    public String showQu() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String city = preferences.getString(QU, "");
        return city;
    }


    public void saveLearnedTime(int time) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int state = preferences.getInt(LEARNED_TIME, 0);
        int learnedTime = time + state;
        Log.d("SystemUtil", "------------" + learnedTime);
        SharedPreferences preferences1 = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences1.edit();
        editor.putInt(LEARNED_TIME, learnedTime);
        editor.commit();

    }

    public int showLearnedTime() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int state = preferences.getInt(LEARNED_TIME, 0);
        return state;
    }

    public void removeLearnedTime() {

        Log.d("SystemUtil", "执行删除的方法");
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.remove(LEARNED_TIME);
        editor.commit();
    }


    public void saveRegesterState(int state) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(REGESTERSTATE, state);
        editor.commit();
    }

    public int showRegesterState() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int state = preferences.getInt(REGESTERSTATE, -1);
        return state;
    }

    public void saveIsShow(int num) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(IS_SHOW, num);
        editor.commit();
    }

    public int showIsShow() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int isShow = preferences.getInt(IS_SHOW, 0);
        return isShow;
    }

    public void saveTiming(int num) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(START_TIMING, num);
        editor.commit();
    }

    public int showTiming() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int isTiming = preferences.getInt(START_TIMING, 0);
        return isTiming;
    }

    public void saveModle1(int num) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(MODEL_STATE, num);
        editor.commit();
    }

    public int showModle() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int isTiming = preferences.getInt(MODEL_STATE, 0);
        return isTiming;
    }


    public void saveCity(String cityName) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(CITY_NAME, cityName);
        editor.commit();
    }

    public String showCity() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String city = preferences.getString(CITY_NAME, "");
        return city;
    }

    public void saveSchool(String schoolName) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(SCHOOL_NAME, schoolName);
        editor.commit();
    }

    public String showSchool() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String school = preferences.getString(SCHOOL_NAME, "");
        return school;
    }

    public void saveUserHeader(String url) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(USER_HEADER, url);
        editor.commit();
    }

    public String getUserHeader() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String url = preferences.getString(USER_HEADER, "");
        return url;

    }

    public String showUserHeader() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String url = preferences.getString(USER_HEADER, "");
        return url;
    }

    public void saveOnlyID(String onlyID) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(ONLY_ID, onlyID);
        editor.commit();
    }


    public String showOnlyID() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String onlyid = preferences.getString(ONLY_ID, "");
        return onlyid;
    }

    public void saveUid(int uid) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(UID, uid);
        editor.commit();
    }


    public int showUid() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int uid = preferences.getInt(UID, -1);
        return uid;
    }

    public void saveZhifuR(int state) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(ZHIFU, state);
        editor.commit();
    }

    public int showZhifuR() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int state = preferences.getInt(ZHIFU, -1);
        return state;
    }

    public void saveRemember(int remember) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(RMEMBER, remember);
        editor.commit();
    }

    public int showRemember() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        int remember = preferences.getInt(RMEMBER, -1);
        return remember;
    }

    public void saveName(String name) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(NAME, name);
        editor.commit();
    }

    public String showName() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String Name = preferences.getString(NAME, "");
        return Name;
    }

    public void savePhone(String num) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PHONE, num);
        editor.commit();
    }

    public String showPhone() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String Name = preferences.getString(PHONE, "");
        return Name;
    }

    public void savePwd(String num) {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PWD, num);
        editor.commit();
    }

    public String showPwd() {
        SharedPreferences preferences = context.getSharedPreferences(MINE,
                Context.MODE_PRIVATE);
        String Name = preferences.getString(PWD, "");
        return Name;
    }

    public static void saveOpenID(String openID) {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(
                MINE, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(OPENID, openID);
        editor.commit();
    }

    public static String showOpenID() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(
                MINE, Context.MODE_PRIVATE);
        String openID = preferences.getString(OPENID, "");
        return openID;
    }

    public static void clear() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(
                MINE, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear().commit();
    }


}
