package com.wanzheng.driver.network;

import com.wanzheng.driver.Entity.BaseResult;



public abstract class NetWorkCallBack<T extends BaseResult> extends
		NetWorkCallBackFather<T> {

	public abstract void onComplete(String json);

}
