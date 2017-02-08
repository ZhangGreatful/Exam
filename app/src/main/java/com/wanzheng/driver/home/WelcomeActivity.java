package com.wanzheng.driver.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.HomeActivity;
import com.haha.exam.bean.Icon;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Response;


public class WelcomeActivity extends Activity {


    //                else{
    //                    if (su.showUserHeader().equals("") || "".equals(su.showName())){
    //                        intent.putExtra("phone",su.showPhone());
    //                        intent.setClass(WelcomeActivity.this,Activity_PerInfor.class);
    //                    }else {
    //                        intent.setClass(WelcomeActivity.this,HomeActivity.class);
    //                    }
    //    			}

    private SystemUtil su;
    private RelativeLayout rl_welcome_bg;
    private ImageView imageView;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        su = new SystemUtil(this);
        initData();
        initView();
        getNetTime();
        swichIcon();
    }

    private void swichIcon() {
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        int useCode;
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int month = c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        Log.d("WelcomeActivity", "现在的月份是===========" + month+"现在的日期是==========="+day);
        if (month > 2 && month < 12) {
            useCode = 1;
        } else {
            useCode = 2;
        }
        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag_normal = "com.haha.exam.icon_tag_normal";
            String icon_tag = "com.haha.exam.icon_tag";

            if (useCode != 3) {

                PackageManager pm = getPackageManager();

                ComponentName normalComponentName = new ComponentName(
                        getBaseContext(),
                        icon_tag);
                //正常图标新状态
                int normalNewState = useCode == 2 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            normalComponentName,
                            normalNewState,
                            PackageManager.DONT_KILL_APP);
                }

                ComponentName actComponentName = new ComponentName(
                        getBaseContext(),
                        icon_tag_normal);
                //正常图标新状态
                int actNewState = useCode == 1 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {//新状态跟当前状态不一样才执行

                    pm.setComponentEnabledSetting(
                            actComponentName,
                            actNewState,
                            PackageManager.DONT_KILL_APP);
                }

            }
        } catch (Exception e) {
        }
    }

    private Gson gson = new Gson();

    private void initData() {
        OkGo.post(WebInterface.get_app_icon)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {
//                            Toast.makeText(WelcomeActivity.this, "获取图片地址信息", Toast.LENGTH_SHORT).show();
                            Icon icon = gson.fromJson(s, Icon.class);
                            su.saveIcon(icon);
                        }
                    }
                });

    }

    public static long shiJian = 0;

    private void getNetTime() {
        new Thread(new Runnable() {
            private long currentTimeMillis;
            private long shoujishijian;

            public void run() {
                if (isConnect(getApplicationContext())) {
                    URL url = null;// 取得资源对象
                    try {
                        url = new URL("http://www.baidu.com");
                        URLConnection uc = url.openConnection();// 生成连接对象
                        uc.connect(); // 发出连接
                        currentTimeMillis = uc.getDate(); // 取得网站日期时间
                        shoujishijian = System.currentTimeMillis();
                        if ((shoujishijian - currentTimeMillis) > 0) {
                            shiJian = shoujishijian - currentTimeMillis;
                        } else if ((shoujishijian - currentTimeMillis) < 0) {
                            shiJian = shoujishijian - currentTimeMillis;
                        } else {
                            shiJian = 0;
                        }
                    } catch (Exception e) {

                    }

                }
            }
        }).start();

    }

    public boolean isConnect(Context context) {
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
        return false;
    }

    /*------------------------------自动更新--------------------------------------*/
    public void gotoActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                SystemUtil su = new SystemUtil(WelcomeActivity.this);
                int uid = su.showUid();
                if (uid == -1 || su.showZhifuR() != 1) {
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                } else if (su.showRegesterState() != 1) {
                    intent = new Intent(WelcomeActivity.this,
                            Activity_PerInfor.class);
                    intent.putExtra("phone", su.showPhone());
                }
//        else if (su.showZhifuR() != 1) {
//                                Bundle budle = new Bundle();
//                                budle.putInt("modle", 0);
//                                intent.putExtras(budle);
//                                intent.setClass(WelcomeActivity.this, Activity_Pay.class);
////            intent.setClass(WelcomeActivity.this,HomeActivity.class);
//        }
                else {
                    intent.setClass(WelcomeActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);

    }

    private void initView() {
        rl_welcome_bg = (RelativeLayout) findViewById(R.id.rl_welcome_bg);
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(3000);
        rl_welcome_bg.startAnimation(aa);
        //渐变过程监听
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                checkVersion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
    }


    /**
     * 下载包
     *
     * @param downloadurl 下载的url
     */
    @SuppressLint("SdCardPath")
    protected void setDownLoad(String downloadurl) {
        RequestParams params = new RequestParams(downloadurl);
        params.setAutoRename(true);//断点下载
        params.setSaveFilePath("/mnt/sdcard/demo.apk");
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onCancelled(CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                System.out.println("提示更新失败");
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(File arg0) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "demo.apk")),
                        "application/vnd.android.package-archive");
                startActivityForResult(intent, 0);
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean arg2) {
                // TODO Auto-generated method stub
                progressDialog.setMax((int) arg0);
                progressDialog.setProgress((int) arg1);
            }

            @Override
            public void onStarted() {
                // TODO Auto-generated method stub
                System.out.println("开始下载");
                progressDialog = new ProgressDialog(WelcomeActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置为水平进行条
                progressDialog.setMessage("正在下载中...");
                progressDialog.setProgress(0);
                progressDialog.show();
            }

            @Override
            public void onWaiting() {

            }
        });
    }

    ProgressDialog progressDialog;
    String apkurl;

    /**
     * 检出版本号
     */
    private void checkVersion() {

        RequestParams params = new RequestParams(NetInterface.getUpdateApk);

        params.addBodyParameter("do", "updateVersion");
        params.addBodyParameter("type", "0");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    // 创建JsonObject对象
                    JSONObject jo = new JSONObject(result);
                    // 获得版本名称
                    //						versionName = jo.getString("versionName");
                    // 获得版本号
                    int versionCode = Integer.valueOf(jo.getString("Code"));
                    // 获得描述信息

                    description = jo.getString("UpdateContent");
                    // 获得下载的url
                    apkurl = jo.getString("DownloadURL");
                    // 判断当前的版本号小于服务器端返回的版本号
                    //Toast.makeText(getApplicationContext(), result, 1).show();
                    if (getVersionCode() < versionCode) {
                        // 执行更新逻辑
                        creatUpdateDialog();
                    } else {
                        // 跳转主界面
                        gotoActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    gotoActivity();

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(getApplicationContext(), "检查更新异常。", Toast.LENGTH_SHORT).show();
//                System.out.print("+++++++++++++++++++++++++++++22222"+ex.toString());
                // 跳转主界面
                gotoActivity();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            //15288897460
            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 创建提示框并显示
     */
    protected void creatUpdateDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                //设置弹窗标题
                builder.setTitle("更新提示");
                //设置弹窗内容信息
                builder.setMessage(description);
                //点击返回键,取消弹窗并跳转到主界面
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //关闭弹窗
                        dialog.dismiss();
                        //跳转界面
                        gotoActivity();
                    }
                });
                //取消下载更新
                builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭dialog弹窗
                        dialog.dismiss();
                        //跳转到主界面
                        gotoActivity();
                    }
                });

                //点击下载apk
                builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //下载apk
                        setDownLoad(apkurl);
                    }
                });
                //显示弹窗
                builder.show();
            }
        });

    }

    /**
     * 获取当前应用程序的版本号
     */
    private int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            int version = packinfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gotoActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
    }
}
