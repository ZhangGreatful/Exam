package com.wanzheng.driver.util;



public class Constants {

	public static final int ERROR = 1001;// 网络异常
	public static final int ROUTE_START_SEARCH = 2000;
	public static final int ROUTE_END_SEARCH = 2001;
	public static final int ROUTE_BUS_RESULT = 2002;// 路径规划中公交模式
	public static final int ROUTE_DRIVING_RESULT = 2003;// 路径规划中驾车模式
	public static final int ROUTE_WALK_RESULT = 2004;// 路径规划中步行模式
	public static final int ROUTE_NO_RESULT = 2005;// 路径规划没有搜索到结果

	public static final int GEOCODER_RESULT = 3000;// 地理编码或者逆地理编码成功
	public static final int GEOCODER_NO_RESULT = 3001;// 地理编码或者逆地理编码没有数据

	public static final int POISEARCH = 4000;// poi搜索到结果
	public static final int POISEARCH_NO_RESULT = 4001;// poi没有搜索到结果
	public static final int POISEARCH_NEXT = 5000;// poi搜索下一页

	public static final int BUSLINE_LINE_RESULT = 6001;// 公交线路查询
	public static final int BUSLINE_id_RESULT = 6002;// 公交id查询
	public static final int BUSLINE_NO_RESULT = 6003;// 异常情况


	public static final int PAGESIZE = 10;
	public static final String PHONE400 = "4008004650";

	// public static final String all = "all";
	// public static final String eDriver = "edriver";
	public static final String Peilian = "peilian";
	public static final String Peijia = "pejia";
	public static final String Driver = "driver";

	public static final String only = "only";
	public static final String multi = "multi";

	public static final String push2Review = "push2Review";
	public static final String curr2Review = "curr2Review";

	public static final String AuthorityS = "authorityS";// 驾校权限
	public static final String AuthorityP = "authorityP";// 陪练权限
	public static final String AuthoritySP = "authoritySP";// 驾校加陪练权限

	public static final String COUPON_DETAIL = "coupon_detail";
	public static final String COUPON_HOME = "coupon_home";

	public static final String DESCRIPTOR = "com.umeng.share";
}