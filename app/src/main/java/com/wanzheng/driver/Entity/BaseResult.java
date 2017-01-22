package com.wanzheng.driver.Entity;


import java.io.Serializable;

public class BaseResult implements Serializable {

	private static final long serialVersionUID = -2806704748193847566L;

	public int status;
	public String msg;

	@Override
	public String toString() {
		return "BaseResult [status=" + status + ", msg=" + msg + "]";
	}

	public boolean isOK() {
		return status == 1;
	}

	public boolean isUnLogin() {
		return status == -1;
	}

}
