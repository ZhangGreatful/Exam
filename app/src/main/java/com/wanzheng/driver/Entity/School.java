package com.wanzheng.driver.Entity;

import java.io.Serializable;

public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String img;
	private String name;
	private String province;
	private String city;
	private int coachNum;
	private int schoolStar;
	private int vip;
	private String distance;
	private double lng;
	private double lat;
	private float star;
	private int ZhuceJiaolianshuliang;

	private int maxBook;// 最大预约人数
	private int maxBook3;// 最大预约人数3
	private int maxMin;
	private int maxMin3;
	private int moshi;
	
	private String subject;// 科目

	private int bianhaoId;
	private int bianhaoId2;

	public int getMaxBook3() {
		return maxBook3;
	}

	public void setMaxBook3(int maxBook3) {
		this.maxBook3 = maxBook3;
	}

	public int getMaxMin() {
		return maxMin;
	}

	public void setMaxMin(int maxMin) {
		this.maxMin = maxMin;
	}

	public int getMaxMin3() {
		return maxMin3;
	}

	public void setMaxMin3(int maxMin3) {
		this.maxMin3 = maxMin3;
	}

	public int getMoshi() {
		return moshi;
	}

	public void setMoshi(int moshi) {
		this.moshi = moshi;
	}

	public int getBianhaoId() {
		return bianhaoId;
	}

	public void setBianhaoId(int bianhaoId) {
		this.bianhaoId = bianhaoId;
	}

	public int getBianhaoId2() {
		return bianhaoId2;
	}

	public void setBianhaoId2(int bianhaoId2) {
		this.bianhaoId2 = bianhaoId2;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getMaxBook() {
		return maxBook;
	}

	public void setMaxBook(int maxBook) {
		this.maxBook = maxBook;
	}

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
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

	public School(int id, String img, String name, String province,
			String city, int coachNum, int schoolStar, int vip,
			String distance, double lng, double lat) {
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.province = province;
		this.city = city;
		this.coachNum = coachNum;
		this.schoolStar = schoolStar;
		this.vip = vip;
		this.distance = distance;
		this.lng = lng;
		this.lat = lat;
	}

	public School() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCoachNum() {
		return coachNum;
	}

	public void setCoachNum(int coachNum) {
		this.coachNum = coachNum;
	}

	public int getSchoolStar() {
		return schoolStar;
	}

	public void setSchoolStar(int schoolStar) {
		this.schoolStar = schoolStar;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getZhuceJiaolianshuliang() {
		return ZhuceJiaolianshuliang;
	}

	public void setZhuceJiaolianshuliang(int jiaolianshuliang) {
		ZhuceJiaolianshuliang = jiaolianshuliang;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", img=" + img + ", name=" + name
				+ ", province=" + province + ", city=" + city + ", coachNum="
				+ coachNum + ", schoolStar=" + schoolStar + ", vip=" + vip
				+ ", distance=" + distance + ", lng=" + lng + ", lat=" + lat
				+ ", star=" + star + ", maxBook=" + maxBook + ", maxBook3="
				+ maxBook3 + ", maxMin=" + maxMin + ", maxMin3=" + maxMin3
				+ ", moshi=" + moshi + ", subject=" + subject + ", bianhaoId="
				+ bianhaoId + ", bianhaoId2=" + bianhaoId2 +"ZhuceJiaolianshuliang"+ZhuceJiaolianshuliang+ "]";
	}



}
