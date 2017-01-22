package com.wanzheng.driver.Entity;

import java.io.Serializable;

public class OrdershowEntity implements Serializable {
	private int ID;		//学时id
	private String XueShiBianHao;	//string	学时编号
	private String Start;//	string	学时开始时间
	private String End;//	string	学时结束时间
	private int ShiChang;//	int	训练时长
	private int Xid;//	int	学员uid
	private int jid	;//int	教练uid
	private int Zhuangtai;//	int	学时是否有效状态
	private double Distance;//	int	训练里程
	private String subject;//	string	训练科目
	private double Money;//	float	金额
	private int MoneyState;//	int	学时订单状态，0：待支付；1：已支付；2：已完成
	private String jiaolianxingming;//	string	教练姓名
	private String pingjiaContent;//	string	评价内容
	private int Jiaxiaobianhao;//"Jiaxiaobianhao": 0,
	private String Xingming;	//"Xingming": "周杰伦",
	private String Chepai;	//"Chepai": "鲁NN8Q0学",

	public int getJiaxiaobianhao() {
		return Jiaxiaobianhao;
	}

	public void setJiaxiaobianhao(int jiaxiaobianhao) {
		Jiaxiaobianhao = jiaxiaobianhao;
	}

	public String getXingming() {
		return Xingming;
	}

	public void setXingming(String xingming) {
		Xingming = xingming;
	}

	public String getChepai() {
		return Chepai;
	}

	public void setChepai(String chepai) {
		Chepai = chepai;
	}

	public String getPingjiaContent() {
		return pingjiaContent;
	}
	public void setPingjiaContent(String pingjiaContent) {
		this.pingjiaContent = pingjiaContent;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getXueShiBianHao() {
		return XueShiBianHao;
	}
	public void setXueShiBianHao(String xueShiBianHao) {
		XueShiBianHao = xueShiBianHao;
	}
	public String getStart() {
		return Start;
	}
	public void setStart(String start) {
		Start = start;
	}
	public String getEnd() {
		return End;
	}
	public void setEnd(String end) {
		End = end;
	}
	public int getShiChang() {
		return ShiChang;
	}
	public void setShiChang(int shiChang) {
		ShiChang = shiChang;
	}
	public int getXid() {
		return Xid;
	}
	public void setXid(int xid) {
		Xid = xid;
	}
	public int getJid() {
		return jid;
	}
	public void setJid(int jid) {
		this.jid = jid;
	}
	public int getZhuangtai() {
		return Zhuangtai;
	}
	public void setZhuangtai(int zhuangtai) {
		Zhuangtai = zhuangtai;
	}
	public double getDistance() {
		return Distance;
	}
	public void setDistance(double distance) {
		Distance = distance;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public double getMoney() {
		return Money;
	}
	public void setMoney(double money) {
		Money = money;
	}
	public int getMoneyState() {
		return MoneyState;
	}
	public void setMoneyState(int moneyState) {
		MoneyState = moneyState;
	}
	public String getJiaolianxingming() {
		return jiaolianxingming;
	}
	public void setJiaolianxingming(String jiaolianxingming) {
		this.jiaolianxingming = jiaolianxingming;
	}
	

}
