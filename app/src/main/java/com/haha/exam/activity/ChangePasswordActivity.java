package com.haha.exam.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haha.exam.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;

import okhttp3.Call;
import okhttp3.Response;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private EditText phone, oldPassword, newPassword, ensurePassword;
    private Button change_password;
    private String tel, newPwd, oldPwd, ensurePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initView();
    }

    private void initView() {
        phone = (EditText) findViewById(R.id.phone);
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        ensurePassword = (EditText) findViewById(R.id.ensure_password);
        change_password = (Button) findViewById(R.id.change_password);

        phone.addTextChangedListener(this);
        oldPassword.addTextChangedListener(this);
        newPassword.addTextChangedListener(this);
        ensurePassword.addTextChangedListener(this);
        change_password.setOnClickListener(this);
    }

    private void initTitle() {
        setTitle("修改密码");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_change_password;
    }

    @Override
    public void onClick(View view) {
        if (tel.equals("") || tel.length() != 11) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (oldPwd.equals("")) {
            Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPwd.equals("")) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ensurePwd.equals("")) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = "http://120.26.118.158:8082/user.ashx?do=changePassword&mobile="
                + tel + "&oldpass=" + oldPwd + "&newpass=" + newPwd;
        OkGo.post(s)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseChangePwd(s);
                        if (num == 1) {
                            Toast.makeText(ChangePasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "修改密码失败，请确认信息是否正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        tel = phone.getText().toString().trim();
        newPwd = newPassword.getText().toString().trim();
        oldPwd = oldPassword.getText().toString().trim();
        ensurePwd = ensurePassword.getText().toString().trim();
    }
}
