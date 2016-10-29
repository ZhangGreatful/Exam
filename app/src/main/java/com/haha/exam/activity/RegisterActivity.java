package com.haha.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.RegisterInfo;
import com.haha.exam.bean.SendCodeInfo;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameEdt, passwordEdt;
    private Button submintBtn, checkBtn;
    private RelativeLayout usernameRela, passwordRela;
    private TextView login_pass;
    private String mobile;
    private Timer timer;
    private String yzm;
    private boolean show = false;
    private int haspass = 1;// 1是有密码
    private String openID;
    private int ispull = 1;
    //    private SystemUtil su;
    private int userState;// 用户状态 1新用户 2老用户
    private int infoState;// 信息完善状态 0未完善 1完善
    private int istrue;
    private String type;// 约车还是秘书
    private TextView tv_zhuce;
    private RelativeLayout set_password_rela2;
    private EditText set_password2;

    private ImageView eye2;
    private String headtag;
    /**
     * 按钮上的文字
     */
    private String btnMsg = "验证";
    private int msg;//验证码

    private Gson gson = new Gson();
    /**
     * 倒计时用计数
     */
    private int time;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:
                    // 按钮重新可以点击
                    checkBtn.setEnabled(true);
                    // 设置为原有的text
                    checkBtn.setText(btnMsg);
                    // timer 取消执行
                    timer.cancel();
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");
        setLeftBtnVisible(false);
        initView();
    }

    private void initView() {
        // 手机号
        userNameEdt = (EditText) findViewById(R.id.login_username);
        // 验证码
        passwordEdt = (EditText) findViewById(R.id.login_password);
        // 设置密码的按钮
        set_password2 = (EditText) findViewById(R.id.set_password2);
        // 注册的按钮
        submintBtn = (Button) findViewById(R.id.login_submit);
        // 获取验证码
        checkBtn = (Button) findViewById(R.id.login_check);
        eye2 = (ImageView) findViewById(R.id.eye2);
        usernameRela = (RelativeLayout) findViewById(R.id.login_username_rela);
        passwordRela = (RelativeLayout) findViewById(R.id.login_password_rela);
        set_password_rela2 = (RelativeLayout) findViewById(R.id.set_password_rela2);

        checkBtn.setEnabled(false);
        submintBtn.setEnabled(false);
        checkBtn.setOnClickListener(this);
        submintBtn.setOnClickListener(this);
        eye2.setOnClickListener(this);

        userNameEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (arg1) {
                    usernameRela
                            .setBackgroundResource(R.mipmap.shurukuang_press);
                    passwordRela.setBackgroundResource(R.mipmap.shurukuang);
                    set_password_rela2
                            .setBackgroundResource(R.mipmap.shurukuang);
                }
            }
        });
        passwordEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (arg1) {
                    passwordRela
                            .setBackgroundResource(R.mipmap.shurukuang_press);
                    usernameRela.setBackgroundResource(R.mipmap.shurukuang);
                    set_password_rela2
                            .setBackgroundResource(R.mipmap.shurukuang);
                }
            }
        });

        set_password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                // TODO Auto-generated method stub
                if (arg1) {
                    set_password_rela2
                            .setBackgroundResource(R.mipmap.shurukuang_press);
                    passwordRela.setBackgroundResource(R.mipmap.shurukuang);
                    usernameRela.setBackgroundResource(R.mipmap.shurukuang);
                }
            }
        });
        userNameEdt.addTextChangedListener(usernameWatcher);
        passwordEdt.addTextChangedListener(passwordWatcher);
        set_password2.addTextChangedListener(set_passwordWatcher);
    }

    TextWatcher usernameWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            // mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() == 11) {
                checkBtn.setEnabled(true);
            } else {
                checkBtn.setEnabled(false);
            }
        }
    };

    TextWatcher set_passwordWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            // mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() >= 6) {
                submintBtn.setEnabled(true);
            } else {
                submintBtn.setEnabled(false);
            }

        }
    };

    TextWatcher passwordWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            // mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() == 4) {
                set_password_rela2
                        .setBackgroundResource(R.mipmap.shurukuang_press);
                passwordRela.setBackgroundResource(R.mipmap.shurukuang);
                set_password2.requestFocus();
            } else {
                set_password_rela2.setBackgroundResource(R.mipmap.shurukuang);
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_check:// 获取验证码
                mobile = userNameEdt.getText().toString();
                if ("".equals(mobile) || mobile.length() < 11) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                passwordEdt.requestFocus();
                submintBtn.setClickable(true);
                passwordEdt.setFocusable(true);
                passwordEdt.setFocusableInTouchMode(true);
                userNameEdt.setFocusable(false);
                userNameEdt.setFocusableInTouchMode(true);
                // 获取验证码的接口
                OkGo.post(WebInterface.send_code)
                        .tag(this)
                        .params("tel", mobile)
                        .execute(sendCodeCallback);
                // 设置按钮不可点击
                checkBtn.setEnabled(false);
                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                time = 60;
                // 点击1毫秒开始执行，没个一秒执行一次
                timer.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        int i = getInt();
                        handler.post(new ButtonTextFresh(i));
                        // 若倒计时到0时
                        if (i == 0) {
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }

                    }
                }, 1, 1000);
                break;
            case R.id.eye2:
                if (!show) {
                    set_password2
                            .setTransformationMethod(HideReturnsTransformationMethod

                                    .getInstance());
                    eye2.setImageResource(R.mipmap.update_password_eye);
                    show = true;
                } else {
                    set_password2
                            .setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());
                    eye2.setImageResource(R.mipmap.yanjing);
                    show = false;
                }
                break;
            case R.id.login_submit:

//                首先判断验证码是否正确
                String code = passwordEdt.getText().toString();

                String username = userNameEdt.getText().toString();
                String password = set_password2.getText().toString();
                if (code.equals(String.valueOf(msg))) {//验证通过
//                    开始注册
                    OkGo.post(WebInterface.register)
                            .params("username", username)
                            .params("password", password)
                            .execute(registerCallback);
                }
//                if (yzm.equals(password)) {
//                    // 如果验证码正确
//                    if ("ms".equals(type)) {// 教练秘书用户
//                        su.saveTel(mobile);// 将手机号存入本地
//                        if (userState == 0) {// 没有此用户就应该创建新的用户
//                            Toast.makeText(LoginChangeZhuCe.this, "欢迎你，新朋友！", 1)
//                                    .show();
//                            String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
//                                    .getDeviceId();
//                            // 创建新用户
//                            new NetWorkUtils().getInstance().work(
//                                    new NetInterface().getInstance().initUser(
//                                            LoginChangeZhuCe.this, mobile, imei,
//                                            type), initCallBack);
//
//                        } else if (userState == 1) {// 有此用户
//                            Toast.makeText(LoginChangeZhuCe.this, "欢迎回来，老朋友！", 1)
//                                    .show();
//                            new NetWorkUtils().getInstance().work(
//                                    new NetInterface().getInstance()
//                                            .queryMineDetail(LoginChangeZhuCe.this,
//                                                    openID), mineCallBack);
//                        }
//                    } else if ("yc".equals(type)) {// 约车用户
//                        su.saveTel(mobile);
//                        if (userState == 0) {
//                            Toast.makeText(LoginChangeZhuCe.this, "欢迎你，新朋友！", 1)
//                                    .show();
//                            String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
//                                    .getDeviceId();
//                            new NetWorkUtils().getInstance().work(
//                                    new NetInterface().getInstance().initUser(
//                                            LoginChangeZhuCe.this, mobile, imei,
//                                            type), initCallBack);
//
//                        } else if (userState == 1) {
//                            Toast.makeText(LoginChangeZhuCe.this, "欢迎回来，老朋友！", 1)
//                                    .show();
//                            if (istrue == 0) {
//                                new NetWorkUtils().getInstance().work(
//                                        new NetInterface().getInstance()
//                                                .initHahaUser(
//                                                        LoginChangeZhuCe.this,
//                                                        mobile), initCallBack);
//                            } else if (istrue == 1) {
//                                new NetWorkUtils().getInstance().work(
//                                        new NetInterface().getInstance()
//                                                .queryMineDetail(
//                                                        LoginChangeZhuCe.this,
//                                                        openID), mineCallBack);
//                            }
//
//                        }
//                    }
//                } else {
//                    Toast.makeText(LoginChangeZhuCe.this, "验证码错误", 1).show();
//                }
                break;
        }
    }

    //      发送验证码
    StringCallback sendCodeCallback = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            SendCodeInfo sendcodeInfo = gson.fromJson(s, SendCodeInfo.class);
            msg = sendcodeInfo.getMsg();
        }
    };
    //      注册回调
    StringCallback registerCallback = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            RegisterInfo registerInfo = gson.fromJson(s, RegisterInfo.class);
            if (registerInfo.getNum() == 1) {//注册成功
                Toast.makeText(RegisterActivity.this, "欢迎你，新用户", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, registerInfo.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 获取倒计时的计数
     *
     * @return
     */
    private int getInt() {
        if (time != 0)
            time--;
        return time;
    }

    class ButtonTextFresh implements Runnable {

        private int iMsg;

        public ButtonTextFresh(int iMsg) {
            this.iMsg = iMsg;
        }

        @Override
        public void run() {
            checkBtn.setText(btnMsg + "(" + iMsg + ")");
        }

    }
}
