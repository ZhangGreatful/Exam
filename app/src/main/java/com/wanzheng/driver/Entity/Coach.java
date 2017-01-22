package com.wanzheng.driver.Entity;

import java.io.Serializable;

public class Coach implements Serializable {
	private int id;
	private String coachId;
	private String name;
	private String sex;
	private String phone;
	private float star;
	private int score;
	private int teachinglife;
	private String iconpath;
	private String subject;
	private int teachNum;
	private int teachYear;
	private String home;
	private int schoolId;
	private String schoolName;
	private double per;
	private int countryId;
	private int shengyuNum;
	private String distance;
	private double lng;
	private double lat;
	private String car;
	private String carStyle;// 车型
	private String carPrice;// 车价格
	private String carPJPrice;// 陪驾价格

	private String bookPrice;
	private String oldPrice;
	private String area;
	private String service;
	private String stopEndTime;
	private String stopReason;
	private String stopStartTime;
	private int stopState;
    private String IsDongjie;
	public String getIsDongjie() {
		return IsDongjie;
	}

	public void setIsDongjie(String isDongjie) {
		IsDongjie = isDongjie;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getStopEndTime() {
		return stopEndTime;
	}

	public void setStopEndTime(String stopEndTime) {
		this.stopEndTime = stopEndTime;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getStopStartTime() {
		return stopStartTime;
	}

	public void setStopStartTime(String stopStartTime) {
		this.stopStartTime = stopStartTime;
	}

	public int getStopState() {
		return stopState;
	}

	public void setStopState(int stopState) {
		this.stopState = stopState;
	}

	public String getCarPJPrice() {
		return carPJPrice;
	}

	public void setCarPJPrice(String carPJPrice) {
		this.carPJPrice = carPJPrice;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public String getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(String carPrice) {
		this.carPrice = carPrice;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getShengyuNum() {
		return shengyuNum;
	}

	public void setShengyuNum(int shengyuNum) {
		this.shengyuNum = shengyuNum;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public double getPer() {
		return per;
	}

	public void setPer(double per) {
		this.per = per;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public int getTeachNum() {
		return teachNum;
	}

	public void setTeachNum(int teachNum) {
		this.teachNum = teachNum;
	}

	public int getTeachYear() {
		return teachYear;
	}

	public void setTeachYear(int teachYear) {
		this.teachYear = teachYear;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}

	public Coach() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTeachinglife() {
		return teachinglife;
	}

	public void setTeachinglife(int teachinglife) {
		this.teachinglife = teachinglife;
	}

	public String getIconpath() {
		return iconpath;
	}

	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}

	@Override
	public String toString() {
		return "Coach [id=" + id + ", coachId=" + coachId + ", name=" + name
				+ ", sex=" + sex + ", phone=" + phone + ", star=" + star
				+ ", score=" + score + ", teachinglife=" + teachinglife
				+ ", iconpath=" + iconpath + ", subject=" + subject
				+ ", teachNum=" + teachNum + ", teachYear=" + teachYear
				+ ", home=" + home + ", schoolId=" + schoolId + ", schoolName="
				+ schoolName + ", per=" + per + ", countryId=" + countryId
				+ ", shengyuNum=" + shengyuNum + ", distance=" + distance
				+ ", lng=" + lng + ", lat=" + lat + ", car=" + car
				+ ", carStyle=" + carStyle + ", carPrice=" + carPrice
				+ ", carPJPrice=" + carPJPrice + ", bookPrice=" + bookPrice
				+ ", oldPrice=" + oldPrice + ", area=" + area + ", service="
				+ service + ", stopEndTime=" + stopEndTime + ", stopReason="
				+ stopReason + ", stopStartTime=" + stopStartTime
				+ ", stopState=" + stopState +"]";
	}


}
