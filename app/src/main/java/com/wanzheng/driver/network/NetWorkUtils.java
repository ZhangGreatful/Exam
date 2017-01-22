package com.wanzheng.driver.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.util.AppConfig;
import com.wanzheng.driver.util.AppLogger;

/**
 * 
 * @author john.wang 检查网络
 */
public class NetWorkUtils {

	private static NetWorkUtils netWorkUtils;
	private static RequestQueue queue;

	public static NetWorkUtils getInstance() {
		if (netWorkUtils == null) {
			netWorkUtils = new NetWorkUtils();
			queue = InitVolley.getInstance().getHttpQueue();
		}
		return netWorkUtils;
	}

	public <T extends BaseResult> void work(RestEntity restEntity,
			final NetWorkCallBack<T> callBack) {
		this.work(restEntity, false, callBack);
	}

	public <T extends BaseResult> void work(final RestEntity restEntity,
			final boolean notCheckStatus, final NetWorkCallBack<T> callBack) {
		Type genericSuperclass = callBack.getClass().getGenericSuperclass();
		final Type type = ((ParameterizedType) genericSuperclass)
				.getActualTypeArguments()[0];
		AppLogger.i("--------------url----------------");
		AppLogger.i(restEntity.url);
		if (restEntity.requestData != null) {
			AppLogger.i("--------------requestData----------------");
			AppLogger.i(restEntity.requestData.toString());
		}
		StringRequest postRequest = new StringRequest(restEntity.method,
				restEntity.url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						AppLogger.i("--------------response----------------");
						AppLogger.i(response);
						/*
						 * T fromJson = InitVolley.getInstance().getGson()
						 * .fromJson(response, type); if (notCheckStatus) {
						 * fromJson.status = 1; } fromJson.msg= response;
						 * callBack.onComplete(fromJson);
						 * 
						 * //改
						 * 
						 * String from = InitVolley.getInstance().getGson()
						 * .fromJson(response, type);
						 */
						callBack.onComplete(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// T fromJson = (T) new BaseResult();
						// fromJson.status = 500;
						String errorResponse = "500";
						callBack.onComplete(errorResponse);
						System.out.println("577777777777777");
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return restEntity.requestData;
			}
		};
		postRequest.setTag(AppConfig.TAG);
		queue.add(postRequest);
	}

	class ClassAsFactory<T> {
		public ClassAsFactory(Class<T> t) {
			try {
				T tObj = t.newInstance();
				System.out
						.println(tObj.getClass().getSimpleName() + " object!");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	public <T> void work(final RestEntity restEntity,
			final NetWorkStringCallBack callBack) {

		AppLogger.i("--------------url----------------");
		AppLogger.i(restEntity.url);
		if (restEntity.requestData != null) {
			AppLogger.i("--------------requestData----------------");
			AppLogger.i(restEntity.requestData.toString());
		}
		StringRequest postRequest = new StringRequest(restEntity.method,
				restEntity.url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						AppLogger.i("--------------response----------------");
						AppLogger.i(response);
						callBack.onComplete(true, response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						callBack.onComplete(false, "无法连接到网络，请检查网络配置");
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return restEntity.requestData;
			}
		};
		postRequest.setTag(AppConfig.TAG);
		queue.add(postRequest);
	}

	public static boolean checkNet(Context context) {

		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
