package com.wanzheng.driver.order;


import java.io.Serializable;

public class Order implements Serializable {
	private int id;
	private String orderId;
	private String time;
	private String date;
	private String coachName;
	private double price;
	private String subject;
	private int state;
	private String bookingTime;
	private String type;
	private String studentName;
	private int jid;
	

	private String Jid;
	private double yuePrice;// 余额
	private double free;// 优惠券
	private double money;// 现金
	private int star;// 订单评价后星级
	private int orderType;// 区分订单是e学车e陪练还是e陪驾
	private String car;
	private String startLocation;
	private String endLocation;
	private String week;
	private int shichang;
	private double online;
	private String phone;
	private int IsCanUpdate;
	private int Jpid;
	
	private String Xid;
	private String Pingjia;
	private String Shijian;
	private int Xingji;
	private int zhiliang;
	private int taidu;
	private int weisheng;
	private int kaimen;



	  




	public String getXid() {
		return Xid;
	}
	public void setXid(String xid) {
		Xid = xid;
	}
	public String getPingjia() {
		return Pingjia;
	}
	public void setPingjia(String pingjia) {
		Pingjia = pingjia;
	}
	public String getShijian() {
		return Shijian;
	}
	public void setShijian(String shijian) {
		Shijian = shijian;
	}
	public int getXingji() {
		return Xingji;
	}
	public void setXingji(int xingji) {
		Xingji = xingji;
	}
	public int getZhiliang() {
		return zhiliang;
	}
	public void setZhiliang(int zhiliang) {
		this.zhiliang = zhiliang;
	}
	public int getTaidu() {
		return taidu;
	}
	public void setTaidu(int taidu) {
		this.taidu = taidu;
	}
	public int getWeisheng() {
		return weisheng;
	}
	public void setWeisheng(int weisheng) {
		this.weisheng = weisheng;
	}
	public int getKaimen() {
		return kaimen;
	}
	public void setKaimen(int kaimen) {
		this.kaimen = kaimen;
	}

	private String pay;

	

	public void setPay(String pay) {
		this.pay = pay;
	}
	public int getJpid() {
		return Jpid;
	}
	public void setJpid(int jpid) {
		Jpid = jpid;
	}
	public void setIsCanUpdate(int isCanUpdate) {
		this.IsCanUpdate = isCanUpdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getOnline() {
		return online;
	}

	public void setOnline(double online) {
		this.online = online;
	}
	
	public String getPay() {
		return pay;
	}

	public int getShichang() {
		return shichang;
	}

	public void setShichang(int shichang) {
		this.shichang = shichang;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getWeek() {
		return week;
	}

	public int getIsCanUpdate() {
		return IsCanUpdate;
	}
	public void setWeek(String week) {
		this.week = week;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public double getYuePrice() {
		return yuePrice;
	}

	public void setYuePrice(double yuePrice) {
		this.yuePrice = yuePrice;
	}

	public double getFree() {
		return free;
	}

	public void setFree(double free) {
		this.free = free;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	
	

	public Order(int id, String orderId, String time, String date,
			String coachName, double price, String subject, int state,
			 String Pingjia,String Shijian,int Xingji,int zhiliang,int taidu
			 ,int weisheng,int kaimen,int IsCanUpdate) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.time = time;
		this.date = date;
		this.coachName = coachName;
		this.price = price;
		this.subject = subject;
		this.state = state;
		this.Pingjia=Pingjia;
		this.Shijian=Shijian;
		this.Xingji=Xingji;
		this.zhiliang=zhiliang;
		this.taidu=taidu;
		this.weisheng=weisheng;
		this.kaimen=kaimen;
		this.IsCanUpdate=IsCanUpdate;
	}

	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderId=" + orderId + ", time=" + time
				+ ", date=" + date + ", coachName=" + coachName + ", price="
				+ price + ", subject=" + subject + ", state=" + state
				+ ", bookingTime=" + bookingTime + ", type=" + type
				+ ", studentName=" + studentName  +", IsCanUpdate="
				+ IsCanUpdate +  ", jid=" + jid +", Xid=" + Xid +", Pingjia=" + Pingjia +", Shijian=" + Shijian +
				", Xingji=" + Xingji +", zhiliang=" + zhiliang +", taidu=" + taidu +
				", weisheng=" + weisheng +", kaimen=" + kaimen +", Jpid=" + Jpid +"]";
	}

}
