package com.haha.exam.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/25.
 */
public class YanZhengLoginFragment extends Fragment implements View.OnClickListener {

    // 验证码
    private EditText userNameEdt, passwordEdt;
    private Button submintBtn, checkBtn;

    // 密码

    private EditText pwUsernameEdt, pwPasswordEdt;
    private Button pwSubmitBtn;
    private ImageView pwEysImg;
    private boolean show = false;

    private String btnMsg = "获取验证码";
    private String mobile;
    /**
     * 倒计时用计数
     */
    private int time;

    private Timer timer;

    private RelativeLayout usernameRela, passwordRela;
    private TextView loginQuestionTxt;

    private Gson gson = new Gson();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yan_zheng, container, false);
//        userNameEdt = (EditText) getView()
//                .findViewById(R.id.login_username);
//        passwordEdt = (EditText) getView()
//                .findViewById(R.id.login_password);
//        submintBtn = (Button) getView().findViewById(R.id.login_submit);
//        checkBtn = (Button) getView().findViewById(R.id.login_check);
//        usernameRela = (RelativeLayout) getView().findViewById(
//                R.id.login_username_rela);
//        passwordRela = (RelativeLayout) getView().findViewById(
//                R.id.login_password_rela);
//        loginQuestionTxt = (TextView) getView().findViewById(
//                R.id.login_question);
//        loginQuestionTxt.setOnClickListener(this);
//        checkBtn.setEnabled(false);
//        submintBtn.setEnabled(false);
//        checkBtn.setOnClickListener(this);
//        submintBtn.setOnClickListener(this);
//        userNameEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View arg0, boolean arg1) {
//                if (arg1) {
//                    usernameRela
//                            .setBackgroundResource(R.mipmap.shurukuang_press);
//                    passwordRela
//                            .setBackgroundResource(R.mipmap.shurukuang);
//                }
//            }
//        });
//        passwordEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View arg0, boolean arg1) {
//                if (arg1) {
//                    passwordRela
//                            .setBackgroundResource(R.mipmap.shurukuang_press);
//                    usernameRela
//                            .setBackgroundResource(R.mipmap.shurukuang);
//                }
//            }
//        });
//        userNameEdt.addTextChangedListener(usernameWatcher);
//        passwordEdt.addTextChangedListener(passwordWatcher);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_check:
                mobile = userNameEdt.getText().toString();
                if ("".equals(mobile) || mobile.length() < 11) {
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                submintBtn.setClickable(true);
//                new NetWorkUtils().getInstance().work(
//                        new NetInterface().getInstance().getIdentifyingCode(
//                                getActivity(), mobile), getCheckCallBack);

                // btnMsg = checkBtn.getText().toString();
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
        }
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

    TextWatcher pwPasswordWatcher = new TextWatcher() {
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
            if (temp.length() >= 6 || temp.length() <= 10) {
                pwSubmitBtn.setEnabled(true);
            } else {
                pwSubmitBtn.setEnabled(false);
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
            if (temp.length() >= 4) {
                submintBtn.setEnabled(true);
            } else {
                submintBtn.setEnabled(false);
            }
        }
    };
}
