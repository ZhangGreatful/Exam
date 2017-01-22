package com.wanzheng.driver.network;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class InitVolley {

	private static InitVolley initLocalInfo;
	private static Gson gson;

	public static InitVolley getInstance() {
		if (initLocalInfo == null) {
			initLocalInfo = new InitVolley();
			gson = new Gson();
		}
		return initLocalInfo;
	}

	private ImageLoader mImageLoader;
	private RequestQueue mHttpQueue;
	private RequestQueue mImageHttpQueue;

	public void init(Context context) {
		mHttpQueue = Volley.newRequestQueue(context);
		mImageHttpQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mImageHttpQueue, new BitmapCache());
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public RequestQueue getHttpQueue() {
		return mHttpQueue;
	}

	public void clearCache() {
		mHttpQueue.getCache().clear();
		mImageHttpQueue.getCache().clear();
	}
	
	public Gson getGson(){
		return gson;
	}
}
