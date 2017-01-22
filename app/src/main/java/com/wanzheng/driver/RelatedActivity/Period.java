package com.wanzheng.driver.RelatedActivity;


import java.io.Serializable;

public class Period implements Serializable {

	private int id;//ID	int	学时id
	private int code;//code	int	0 网络异常或参数错误
	private String xueShiBianHao;//XueShiBianHao	string	学时编号
	private String start;//St//End	string	学时结束时间
	private String End;//St//End	string	学时结束时间
	private int shiChang;//ShiChang	int	训练时长
	private int xid;//Xid	int	学员uid
	private int jid;//jid	int	教练uid

	private int zhuangtai;//Zhuangtai	int	学时是否有效状态
	private Double distance;//Distance	int	训练里程
	private String subject;//subject	string	训练科目
	private String studentName;
	private Double money;//Money	float	金额

	private int moneyState;// MoneyState	int	学时订单状态，0：待支付；1：已支付；2：已完成
	private String jiaolianxingming;// jiaolianxingming	string	教练姓名
	private String chepai;//Chepai	string	车牌号
	private String jiaxiaomingcheng;//Jiaxiaomingcheng	string	训练场
		private String jiaxiaobianhao;//Jiaxiaomingcheng	string	训练场

	public String getJiaxiaobianhao() {
		return jiaxiaobianhao;
	}

	public void setJiaxiaobianhao(String jiaxiaobianhao) {
		this.jiaxiaobianhao = jiaxiaobianhao;
	}

	public String getJiaxiaomingcheng() {
		return jiaxiaomingcheng;
	}

	public void setJiaxiaomingcheng(String jiaxiaomingcheng) {
		this.jiaxiaomingcheng = jiaxiaomingcheng;
	}

	public String getEnd() {
		return End;
	}

	public void setEnd(String end) {
		End = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public int getShiChang() {
		return shiChang;
	}

	public void setShiChang(int shiChang) {
		this.shiChang = shiChang;
	}

	public int getXid() {
		return xid;
	}

	public void setXid(int xid) {
		this.xid = xid;
	}

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public int getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(int zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public int getMoneyState() {
		return moneyState;
	}

	public void setMoneyState(int moneyState) {
		this.moneyState = moneyState;
	}

	public String getJiaolianxingming() {
		return jiaolianxingming;
	}

	public void setJiaolianxingming(String jiaolianxingming) {
		this.jiaolianxingming = jiaolianxingming;
	}

	public String getChepai() {
		return chepai;
	}

	public void setChepai(String chepai) {
		this.chepai = chepai;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getXueShiBianHao() {
		return xueShiBianHao;
	}

	public void setXueShiBianHao(String xueShiBianHao) {
		this.xueShiBianHao = xueShiBianHao;
	}
}
