package com.wanzheng.driver.Entity;

import java.io.Serializable;

public class Mine implements Serializable {

    private String cityName;

    public String getJiaxiao() {
        return jiaxiao;
    }

    public void setJiaxiao(String jiaxiao) {
        this.jiaxiao = jiaxiao;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private int uid;
    private int xid;
    private int schoolId;
    private int jid;
    private String jiaxiaobianhao;

    public String getJiaxiaobianhao() {
        return jiaxiaobianhao;
    }

    public void setJiaxiaobianhao(String jiaxiaobianhao) {
        this.jiaxiaobianhao = jiaxiaobianhao;
    }
    public String getOnlyId() {
        return onlyId;
    }

    public void setOnlyId(String onlyId) {
        this.onlyId = onlyId;
    }

    private String onlyId;

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }


    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    private String sheng;
    private String qu;
    private String jiaxiao;
    private String name;
    private String telphone;
    private String iconPath;
    private int price;
    private int free;
    private int haha;
    private int sex;
    private int country;
    private String authority;
    private String subject;
    private int canDiffrentBook;// 能否跨科目预约
    private int cancelTime;// 允许销单时间
    private String startBookTime;// 开始预约时间
    private String endBookTime;// 结束预约时间
    private int dongjie;

    private String coldEndDay;
    private int coldState;
    private int vip;
    private int showDay;

    private String coldStartTime;
    private String coldEndTime;

    private int zhuce;

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public int getZhuce() {
        return zhuce;
    }

    public void setZhuce(int zhuce) {
        this.zhuce = zhuce;
    }

    public String getColdStartTime() {
        return coldStartTime;
    }

    public void setColdStartTime(String coldStartTime) {
        this.coldStartTime = coldStartTime;
    }

    public String getColdEndTime() {
        return coldEndTime;
    }

    public void setColdEndTime(String coldEndTime) {
        this.coldEndTime = coldEndTime;
    }

    public int getShowDay() {
        return showDay;
    }

    public void setShowDay(int showDay) {
        this.showDay = showDay;
    }

    public String getColdEndDay() {
        return coldEndDay;
    }

    public void setColdEndDay(String coldEndDay) {
        this.coldEndDay = coldEndDay;
    }

    public int getColdState() {
        return coldState;
    }

    public void setColdState(int coldState) {
        this.coldState = coldState;
    }

    public int getVip() {
        return vip;
    }


    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getDongjie() {
        return dongjie;
    }

    public void setDongjie(int dongjie) {
        this.dongjie = dongjie;
    }

    public String getStartBookTime() {
        return startBookTime;
    }

    public void setStartBookTime(String startBookTime) {
        this.startBookTime = startBookTime;
    }

    public String getEndBookTime() {
        return endBookTime;
    }

    public void setEndBookTime(String endBookTime) {
        this.endBookTime = endBookTime;
    }

    public int getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(int cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getCanDiffrentBook() {
        return canDiffrentBook;
    }

    public void setCanDiffrentBook(int canDiffrentBook) {
        this.canDiffrentBook = canDiffrentBook;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getHaha() {
        return haha;
    }

    public void setHaha(int haha) {
        this.haha = haha;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getXid() {
        return xid;
    }

    public void setXid(int xid) {
        this.xid = xid;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
