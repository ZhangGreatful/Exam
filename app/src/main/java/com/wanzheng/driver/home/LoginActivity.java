package com.wanzheng.driver.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.activity.HomeActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.Mine;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.SchoolBaoxian;
import com.wanzheng.driver.util.SystemUtil;

import java.util.Timer;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends Activity implements OnClickListener, TextWatcher {
    private EditText userNameEdt, passwordEdt;
    private Button submintBtn, zhuceBtn;

    private boolean hasCheck = true;
    /**
     * 倒计时用计数
     */
    private int time;
    private Timer timer;
    private Intent intent;
    /**
     * 按钮上的文字
     */
    private String btnMsg = "";
    private TextView titleTxt, titleTxtRight, LandingDynamic, forgetPwd;

    private int clickCheck = 0;// 验证码版本为1 登陆版本为0

    private int login = 0;

    protected static final int UI_LOG_TO_VIEW = 0;

    public static Activity instance;
    private int pay = 0;

    private TextView loginQuestionTxt, Landing_dynamic, unregister_Login;
    private Mine m;
    private int id;
    private SystemUtil su;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 2:
                    su.saveRemember(1);
                    id = m.getUid();
                    su.saveOnlyID(m.getOnlyId());
                    su.saveUid(m.getUid());
                    su.savePhone(m.getTelphone());
                    su.saveName(m.getName());
                    su.savePwd(passwordEdt.getText().toString());
                    su.saveUserHeader(m.getIconPath());

                    su.saveSheng(m.getSheng());
                    su.saveQu(m.getQu());
                    su.saveCity(m.getCityName());
                    su.saveSchool(m.getJiaxiao());
                    su.saveSchool(m.getJiaxiao());

                    toast("登陆成功");
                    intent = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                    finish();

                    break;
                case 3:
                    su.saveRemember(1);
                    id = m.getUid();
                    su.saveOnlyID(m.getOnlyId());
                    su.saveUid(m.getUid());
                    su.savePhone(m.getTelphone());
                    su.savePwd(passwordEdt.getText().toString());
                    su.saveUserHeader(m.getIconPath());
                    su.saveSheng(m.getSheng());
                    su.saveQu(m.getQu());

                    su.saveCity(m.getCityName());
                    su.saveSchool(m.getJiaxiao());


                    toast("登陆成功");
                    intent = new Intent(LoginActivity.this,
                            Activity_PerInfor.class);
                    intent.putExtra("phone", m.getTelphone());
                    startActivity(intent);
                    finish();

                    break;
                case 4:
                    su.saveRemember(1);
                    id = m.getUid();
                    su.saveOnlyID(m.getOnlyId());
                    su.saveUid(m.getUid());
                    su.savePhone(m.getTelphone());
                    su.saveName(m.getName());
                    su.savePwd(passwordEdt.getText().toString());
                    su.saveUserHeader(m.getIconPath());

                    su.saveSheng(m.getSheng());
                    su.saveQu(m.getQu());
                    su.saveCity(m.getCityName());
                    su.saveSchool(m.getJiaxiao());
                    su.saveJiaxiaobianhao(m.getJiaxiaobianhao());
                    new NetWorkUtils().getInstance().work(new
                            NetInterface().getInstance().baoxianZhifu(m.getJiaxiaobianhao()), zhifuCallBack);
//                    Bundle bundle1 = new Bundle();
//                    toast("登陆成功");
//
//                    intent = new Intent(LoginActivity.this, Activity_Pay.class);
//                    bundle1.putString("phone", m.getTelphone());
//                    bundle1.putInt("modle", 0);
//                    intent.putExtras(bundle1);
//                    startActivity(intent);
//                    finish();

                    break;
                case 5://需要支付保险
                    Bundle bundle1 = new Bundle();
                    toast("登陆成功");

                    intent = new Intent(LoginActivity.this, Activity_Pay.class);
                    bundle1.putString("phone", m.getTelphone());
                    bundle1.putInt("modle", 0);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    break;
                case 6://不需要支付保险
                    intent = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        initView();
        initData();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private void initView() {
        // TODO Auto-generated method stub
//        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
//        titleTxt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
//        titleTxt.setText("登录");
        titleTxtRight = (TextView) findViewById(R.id.titlebar_right_text);
        titleTxtRight.setVisibility(View.VISIBLE);
        titleTxtRight.setOnClickListener(this);
//        titlebar_layout_left = (ImageView) titlebar
//                .findViewById(R.id.titlebar_back);
//        titlebar_layout_left.setOnClickListener(this);
        userNameEdt = (EditText) findViewById(R.id.login_username);

        unregister_Login = (TextView) findViewById(R.id.unregister_login);
        unregister_Login.setOnClickListener(this);

        userNameEdt.addTextChangedListener(this);

        userNameEdt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        passwordEdt = (EditText) findViewById(R.id.login_password);
        submintBtn = (Button) findViewById(R.id.login_submit);
        if (isConnect(LoginActivity.this) == true) {
            submintBtn.setOnClickListener(this);
        }

        zhuceBtn = (Button) findViewById(R.id.login_zhuce);
        zhuceBtn.setOnClickListener(this);

        LandingDynamic = (TextView) findViewById(R.id.Landing_dynamic);
        LandingDynamic.setOnClickListener(this);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        forgetPwd.setOnClickListener(this);
        Landing_dynamic = (TextView) findViewById(R.id.Landing_dynamic);
        Landing_dynamic.setOnClickListener(this);
    }

    private void initData() {
        su = new SystemUtil(LoginActivity.this);
        userNameEdt.setText(su.showPhone());
        passwordEdt.setText(su.showPwd());
        su.saveZhifuR(-1);
        su.saveUid(-1);
    }

    String imei;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_submit:

                String mobile1 = userNameEdt.getText().toString();

                if ("".equals(mobile1)) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码",
                            Toast.LENGTH_LONG).show();
                    ;
                    return;
                }
                String passwordString2 = passwordEdt.getText().toString();
                if ("".equals(passwordString2)) {
                    toast("密码不能为空");
                    return;
                }

                TelephonyManager telephonyManager = (TelephonyManager) this
                        .getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();

                String province = "";
                String city = "";
                String district = "";
//                submintBtn.setEnabled(false);
                String s = "http://120.26.118.158:8082/user.ashx?do=login2222"
                        + "&userid=" + mobile1 + "&password=" + passwordString2;
                OkGo.post(s)
                        .tag(this)
                        .execute(loginCallBack);
//                关闭输入框
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(passwordEdt.getWindowToken(), 0);

                break;
            case R.id.titlebar_right_text:
                startActivity(new Intent(LoginActivity.this, Activity_Zhuce.class));
                break;
//            case R.id.titlebar_back:
//                finish();
//                break;
            case R.id.Landing_dynamic:
                Intent intent = new Intent();
                intent.putExtra("phone", text);
                intent.putExtra("from", 0);
                intent.setClass(LoginActivity.this, FaceLogin.class);
                startActivity(intent);
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(LoginActivity.this, Activity_ForgetPwd.class));
                break;
            case R.id.unregister_login:
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                break;
            default:
                break;
        }
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
        Toast.makeText(context, "请检查你的网络", Toast.LENGTH_SHORT).show();
        return false;
    }

    private StringCallback loginCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            // TODO Auto-generated method stub

            int uid = JsonUtils.parseMsgLogin(s);
            int msg = JsonUtils.parseMsg(s);

            String ErrorMessage = JsonUtils.parseErrorMessage(s);
            submintBtn.setEnabled(true);
            if (-1 == uid || 0 == uid) {
                submintBtn.setEnabled(true);
                switch (msg) {
                    case 0:
                        toast(ErrorMessage);
                        break;
                    case 2:
                        toast("登陆失败，用户名不存在");
                        break;
                    case 3:
                        toast("登陆失败，密码错误");
                        break;
                    case 4:
                        toast("登陆失败，您的账号不是学员权限");
                        break;
                    case 20:
                        toast(JsonUtils.parseMsgReason(s));
                        break;
                    default:
                        break;

                }
            } else {
                m = JsonUtils.parseMine(s);
                System.out.println("s==============" + s);
                Message message = new Message();
//                JPushInterface.setAlias(LoginActivity.this, userNameEdt.getText().toString(),
//                        new TagAliasCallback() {
//
//                            @Override
//                            public void gotResult(int arg0, String arg1,
//                                                  Set<String> arg2) {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
                if (m.getIconPath() == null || "".equals(m.getIconPath())
                        || m.getName() == null || "".equals(m.getName())) {
                    message.what = 3;
                    handler.sendMessage(message);

                } else {

                    new SystemUtil(LoginActivity.this).saveRegesterState(1);
                    String url = "http://120.26.118.158:8082/pay.ashx?do=getPayState&mobile="
                            + m.getTelphone();
                    OkGo.post(url)
                            .tag(this)
                            .execute(payStateCallBack);
                        Log.d("LoginActivity", "Jiaxiaobianhao" + m.getJiaxiaobianhao());

                }
                // }
            }


        }
    };

    private NetWorkCallBack<BaseResult> zhifuCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            Log.d("LoginActivity", "json=========="+json);
            SchoolBaoxian msg = JsonUtils.parseBaoxian(json);
            Message message = new Message();

            Log.d("Activity_Pay", "是否支付保险===========" + msg.getBaoxian());
            Log.d("Activity_Pay", "支付保险费用========" + msg.getBaoxianfei());
            if (msg.getBaoxian() == 0) {//未支付保险
               handler.sendEmptyMessage(6);
            } else if (msg.getBaoxian() == 1) {//需要支付保险
               handler.sendEmptyMessage(5);
            }
        }
    };

    private StringCallback payStateCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int msg = JsonUtils.parsePaystate(s);
            submintBtn.setEnabled(true);
            if (msg == 1) {
                su.saveZhifuR(1);
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            } else if (msg == 0) {
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            } else if (msg == 2) {

            } else {

            }
        }
    };

    private String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    private int first = 1;// 验证码版本为0 登陆版本为1


    private void toast(String str) {
        Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        text = userNameEdt.getText().toString().trim();
        if (text.length() == 11) {
            unregister_Login.setVisibility(View.GONE);
            if (LandingDynamic != null) {
                LandingDynamic.setVisibility(View.VISIBLE);
            }

        } else {
            if (LandingDynamic != null) {
                LandingDynamic.setVisibility(View.GONE);
            }
        }

    }

    String text;
}
