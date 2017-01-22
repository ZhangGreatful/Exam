package com.wanzheng.driver.network;

import java.io.Serializable;
import java.util.Map;

import com.android.volley.Request.Method;

public class RestEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5911309472155706739L;
	
	public String url;
	public Map<String, String> requestData;
	public int method = Method.GET;
	@SuppressWarnings("rawtypes")
	public Class clientClass;
	
	public RestEntity(String url){
		this.url = url;
	}
	
	public <T>RestEntity(String url, Class<T> classOfT){
		this(url);
		this.clientClass = classOfT;
	}
	
	public RestEntity(int method, String url){
		this(url);
		this.method = method;
	}
	
	public <T>RestEntity(int method, String url, Class<T> classOfT){
		this(method, url);
		this.clientClass = classOfT;
	}
	
	public <T>RestEntity(int method, String url, Map<String, String> data){
		this(method, url);
		this.requestData = data;
	}
	
	public <T>RestEntity(int method, String url, Map<String, String> data, Class<T> classOfT){
		this(method, url, classOfT);
		this.requestData = data;
	}
	
}
