package com.wanzheng.driver.Entity;

import java.io.Serializable;

public class Version implements Serializable {
	private int id;
	private String version;
	private String url;
	private String detail;
	private String update;
	private int versionCode;
	private String name;
	private int imperative;// 强制升级

	public int getImperative() {
		return imperative;
	}

	public void setImperative(int imperative) {
		this.imperative = imperative;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public Version(int id, String version, String url, String detail,
			String update) {
		super();
		this.id = id;
		this.version = version;
		this.url = url;
		this.detail = detail;
		this.update = update;
	}

	public Version() {
		super();
	}

	@Override
	public String toString() {
		return "Version [id=" + id + ", version=" + version + ", url=" + url
				+ ", detail=" + detail + ", update=" + update + "]";
	}

}
