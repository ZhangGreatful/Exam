package com.wanzheng.driver.RelatedActivity;


import java.io.Serializable;

public class PassPicEntity implements Serializable {

	private int id;//ID	int	学时id
	private int xueshiid;
	private String picname;
	private String dingdanid;
	private String picurl;
	private int zhuangtai;
	private String addtime;
	private String shibielv;

	public int getXueshiid() {
		return xueshiid;
	}

	public void setXueshiid(int xueshiid) {
		this.xueshiid = xueshiid;
	}

	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getDingdanid() {
		return dingdanid;
	}

	public void setDingdanid(String dingdanid) {
		this.dingdanid = dingdanid;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public int getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(int zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getShibielv() {
		return shibielv;
	}

	public void setShibielv(String shibielv) {
		this.shibielv = shibielv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
