package com.wanzheng.driver.network;


public class NetPathUtils {

	public static final String NETPATH_COUPONS = "life/coupons/";
	public static final String NETPATH_FOCUS = "life/focus/";
	public static final String NETPATH_PHOTO = "life/photo/";
	public static final String NETPATH_PUSH = "life/push/";
	public static final String NETPATH_NEWS = "";
	public static final String NETPATH_HOME_INDEX = "index/";
	public static final String NETPATH_ADVERTISING = "advertising/";
	public static final String IMG_HOST = "http://file.m0546.com/";

	private static NetPathUtils netpath;

	public static NetPathUtils getInstance() {
		if (netpath == null) {
			netpath = new NetPathUtils();
		}
		return netpath;
	}

	public String getNetPath(String subPath, String parentPath) {
		String image_path = IMG_HOST + parentPath + subPath;
		return image_path;
	}
}
