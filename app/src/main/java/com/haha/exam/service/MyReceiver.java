package com.haha.exam.service;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
//public class MyReceiver extends BroadcastReceiver {
//	private static final String TAG = "JPush";
//	private ArrayList<String> list = new ArrayList<String>();
//	private String openID;
//	private Context mContext;
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		// Toast.makeText(context, "极光推送接收成功", Toast.LENGTH_SHORT).show();
//		Bundle bundle = intent.getExtras();
//		String s = (String) bundle.getSerializable(JPushInterface.EXTRA_MESSAGE);
//		String id = (String) bundle.getSerializable(JPushInterface.EXTRA_MSG_ID);
//		mContext = context;
//		if (s != null)
//			MyApplication.put(id, s);
//
//		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
//		openID = SystemUtil.showOpenID();
//		if (!TextUtils.isEmpty(openID)) {
//			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//				Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//				Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//				processCustomMessage(context, bundle);
//
//			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//				String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//				String clickId = (String) bundle.getSerializable(JPushInterface.EXTRA_MSG_ID);
//				String content = (String) MyApplication.get(clickId);
//				if (content == null) {
//					return;
//				}
//				// Toast.makeText(mContext, content, Toast.LENGTH_SHORT)
//				// .show();
//				Order order = parsecontent(content);
//				Log.d(TAG, "[MyReceiver] messagemessagemessagemessagemessage可伶可领可伶可领可伶可领可伶可领: " + message);
//				ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//				String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
//				int position = runningActivity.lastIndexOf(".");
//				String substring = runningActivity.substring(position + 1);
//				// Toast.makeText(context, runningActivity, 1).show();
//				if (message.contains("下线提醒")) {
//					// 跳转页面
//					// Intent intentAlarm = new Intent(context,
//					// OutAlertFullScreen.class);
//					// intentAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					// intentAlarm.putExtras(bundle);
//					// // App.getInstance().startActivity(intentAlarm);
//					// context.startActivity(intentAlarm);
//				} else if (message.contains("新订单")) {
////					if (!"GrabOrderActivity".equals(substring)) {
////						// Toast.makeText(context, "收到推送", Toast.LENGTH_SHORT)
////						// .show();
////
////						Intent intent2Grab = new Intent(context, OrderAlertFullScreen.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.putExtra("order", order);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					}
//
//				} else if (message.contains("冻结提醒")) {
//					// Intent intentAlarm = new Intent(context,
//					// MissOutAlertFullScreen.class);
//					// intentAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					// intentAlarm.putExtras(bundle);
//					// // App.getInstance().startActivity(intentAlarm);
//					// context.startActivity(intentAlarm);
//				}
//			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//
//				// 新加的
//				String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//				Log.e("tag", "messagemessagemessagemessagemessagemessagemessage" + message);
//				String clickId = (String) bundle.getSerializable(JPushInterface.EXTRA_MSG_ID);
//				String content = (String) MyApplication.get(clickId);
//				Log.e("tag", "contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent"
//						+ content);
//				// parsecontent(content);
////				if (!TextUtils.isEmpty(content)) {
////					int which = JsonUtils.parseWhich(content);
////					String orderid = JsonUtils.parseOrderid(content);
////					Log.e("tag", "whichwhichwhichwhichwhichwhichwhichwhichwhichwhichwhichwhich" + which);
////					if (message.contains("下课提醒")) {
////						// 打开自定义的review
////						// Intent i = new Intent(context,
////						// WriteReviewActivity.class);
////						// bundle.putString(Keys.WHICHREVIEW,
////						// Constants.push2Review);
////						// String[] split = message.split("订单ID:");
////						// bundle.putString(Keys.OrderId, split[1]);
////						// i.putExtras(bundle);
////						// // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
////						// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						// context.startActivity(i);
////					} else if (message.contains("学车预约")) {
////						Intent i = new Intent(context, UnDealOrderActivity.class);
////						i.putExtras(bundle);
////						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						context.startActivity(i);
////					} else if (message.contains("取消预约")) {
////						Intent i = new Intent(context, UnDealOrderActivity.class);
////						i.putExtras(bundle);
////						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						context.startActivity(i);
////					} else if (message.contains("已经成功支付")) {
////						Intent payIntent = new Intent(context, GrabOrderDetailAcitviy.class);
////						payIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						payIntent.putExtra("orderid", orderid);
////						context.startActivity(payIntent);
////					} else if (message.contains("新学员咨询啦")) {
////						Intent stuIntent = new Intent(context, MainTabActivity.class);
////						stuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						stuIntent.putExtra("from", "from");
////						context.startActivity(stuIntent);
////					} else if (message.contains("新订单") || which == 1) {
////
////						Intent intent2Grab = new Intent(context, GrabOrderActivity.class);
////						// intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 2) {
////						String url = JsonUtils.parseurl(content);
////						int isopen = JsonUtils.parseIsopen(content);
////						if (isopen == 0) {
////							Bundle bundle2 = new Bundle();
////							bundle2.putString(Keys.WebURL, url);
////							Intent intent2 = new Intent(context, WebActivity.class);
////							intent2.putExtras(bundle2);
////							intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////							context.startActivity(intent2);
////						} else if (isopen == 1) {
////							String showOpenID = new SystemUtil(context).showOpenID();
////							Bundle bundle2 = new Bundle();
////							bundle2.putString(Keys.WebURL, url + "/openid/" + showOpenID);
////							Intent intent2 = new Intent(context, WebActivity.class);
////							intent2.putExtras(bundle2);
////							intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////							context.startActivity(intent2);
////						}
////					} else if (which == 3) {
////						Intent intent2Grab = new Intent(context, MainTabActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 4) {
////						Intent intent2Grab = new Intent(context, MessageListActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);s
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 5) {
////						Intent intent2Grab = new Intent(context, DriveHeadLingActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 6) {
////						Intent intent2Grab = new Intent(context, ExamCalenderActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 7) {
////						Intent intent2Grab = new Intent(context, CourseTwoReportActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 8) {
////						Intent intent2Grab = new Intent(context, CourseThreeReportActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					} else if (which == 9) {
////						Intent intent2Grab = new Intent(context, MoreSMSActivity.class);
////						// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////						intent2Grab.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////						context.startActivity(intent2Grab);
////					}
////				}
//
//				// 打开自定义的Activity
//				// Intent i = new Intent(context, TestActivity.class);
//				// i.putExtras(bundle);
//				// // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//				// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				// context.startActivity(i);
//
//			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//				Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//				// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
//				// 打开一个网页等..
//
//			} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//				Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
//			} else {
//				Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
//			}
//		}
//
//	}
//
//	private Order parsecontent(String content) {
//		// TODO Auto-generated method stub
//		Order order = new Order();
//		JSONObject object;
//		try {
//			object = new JSONObject(content);
//			JSONObject object2 = object.getJSONObject("condition");
//			Iterator keys = object2.keys();
//			while (keys.hasNext()) {
//
//				String key = (String) keys.next();
//				String value = object2.getString(key);
//				list.add(value);
//			}
////			order.setNeedList(list);
////			order.setId(object.optInt("id"));
////			order.setRemark(object.optString("message"));
////			order.setAddTime(object.optString("addtime"));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return order;
//	}
//
//	// 打印所有的 intent extra 数据
//	private static String printBundle(Bundle bundle) {
//		StringBuilder sb = new StringBuilder();
//		for (String key : bundle.keySet()) {
//			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//			} else {
//				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//			}
//		}
//		return sb.toString();
//	}
//
//	// send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		// if (FragmentChangeActivity.isForeground) {
//		// String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		// String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		// Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//		// msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//		// if (!ExampleUtil.isEmpty(extras)) {
//		// try {
//		// JSONObject extraJson = new JSONObject(extras);
//		// if (null != extraJson && extraJson.length() > 0) {
//		// msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//		// }
//		// } catch (JSONException e) {
//		//
//		// }
//		//
//		// }
//		// context.sendBroadcast(msgIntent);
//		// }
//	}

//}
