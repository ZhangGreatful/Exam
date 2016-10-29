package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.haha.exam.activity.MainActivity;
import com.haha.exam.activity.RegisterActivity;
import com.haha.exam.bean.LoginInfor;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/25.
 */
public class PasswordLoginFragment extends Fragment implements View.OnClickListener {
    private EditText pwUsernameEdt, pwPasswordEdt;
    private Button pwSubmitBtn;
    private ImageView pwEysImg;
    private boolean show = false;
    private String openID;
    private String telold;
    private int ispull = 1;
    private String uid;
    private String yzm;
    private String type;// 约车还是秘书
    private int userState;// 用户状态 1新用户 2老用户
    private int infoState;// 信息完善状态 0未完善 1完善
    private int istrue;

    private TextView login_yzm;
    private ImageView login_question;
    private TextView tv_falsepass;
    private TextView tv_zhuce;
    private ImageView loginqq;
    private ImageView loginweibo;
    private ImageView loginweixin;
//    private UMSocialService mController;
    private RelativeLayout login_password_rela, login_username_rela;

    String appID = "wx20073334af65f734";
    String appSecret = "701431d67ffec594f1ea4ec943a9475c";
    private String typestyle = "";
    private Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_login, container, false);
        pwUsernameEdt = (EditText) view.findViewById(R.id.pw_login_username);
        pwPasswordEdt = (EditText) view.findViewById(R.id.pw_login_password);
        login_question = (ImageView) view.findViewById(R.id.login_question);
        login_yzm = (TextView) view.findViewById(R.id.login_yzm);

        login_username_rela = (RelativeLayout) view.findViewById(R.id.login_username_rela);
        login_password_rela = (RelativeLayout)view. findViewById(R.id.login_password_rela);

        pwSubmitBtn = (Button) view.findViewById(R.id.pw_login_submit);
        tv_falsepass = (TextView) view.findViewById(R.id.tv_falsepass);
//        tv_zhuce = (TextView) view.findViewById(R.id.tv_zhuce);

//        loginweixin = (ImageView)view. findViewById(R.id.iv_weixin);

        pwEysImg = (ImageView) view.findViewById(R.id.eye);
        pwPasswordEdt.addTextChangedListener(pwPasswordWatcher);
        pwUsernameEdt.addTextChangedListener(usernameWatcher);
        pwEysImg.setOnClickListener(this);
//        login_question.setOnClickListener(this);
//        login_yzm.setOnClickListener(this);
        tv_falsepass.setOnClickListener(this);
//        tv_zhuce.setOnClickListener(this);
        pwSubmitBtn.setOnClickListener(this);
        pwSubmitBtn.setEnabled(false);

//        loginweixin.setOnClickListener(this);
        return view;
    }
    TextWatcher usernameWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
            // mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() == 11) {
                login_password_rela.setBackgroundResource(R.mipmap.shurukuang_press);
                login_username_rela.setBackgroundResource(R.mipmap.shurukuang);
                pwPasswordEdt.requestFocus();
            } else {
                login_password_rela.setBackgroundResource(R.mipmap.shurukuang);
            }
        }
    };

    TextWatcher pwPasswordWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
            // mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() >= 6) {
                pwSubmitBtn.setEnabled(true);
            } else {
                pwSubmitBtn.setEnabled(false);
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pw_login_submit:
                String username = pwUsernameEdt.getText().toString();
                String pw = pwPasswordEdt.getText().toString();
                if ("".equals(username)) {
                    Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(pw)) {
                    Toast.makeText(getActivity(), "请输入6位以上密码", Toast.LENGTH_SHORT).show();
                } else {
                    OkGo.post(WebInterface.check_login)
                            .tag(this)
                            .params("username", username)
                            .params("password", pw)
                            .execute(loginCallback);
                }
                break;
            case R.id.eye:
                if (!show) {
                    pwPasswordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwEysImg.setImageResource(R.mipmap.update_password_eye);
                    show = true;
                } else {
                    pwPasswordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwEysImg.setImageResource(R.mipmap.yanjing);
                    show = false;
                }
                break;
            case R.id.tv_falsepass://忘记密码
                Intent intent3 = new Intent(getActivity(), RegisterActivity.class);
                intent3.putExtra("tag", "设置密码");
                startActivity(intent3);
                getActivity().finish();
                break;
//            case R.id.tv_zhuce://注册
//                Intent intent4 = new Intent(LoginChangePass.this, LoginChangeZhuCe.class);
//                intent4.putExtra("tag", "快速注册");
//                startActivity(intent4);
//                finish();
//                break;
            default:
                break;
        }
    }

    StringCallback loginCallback = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            LoginInfor loginInfor = gson.fromJson(s, LoginInfor.class);
            if (loginInfor.getNum() == 0) {
                Toast.makeText(getActivity(), loginInfor.getMsg(), Toast.LENGTH_SHORT).show();
            } else if (loginInfor.getNum() == 1) {
                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    };
}
