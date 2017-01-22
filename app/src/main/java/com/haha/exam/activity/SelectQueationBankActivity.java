package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.exam.R;
import com.haha.exam.utils.SPUtils;

/**
 * 选择题库页面
 * 首次进入时，加载完引导页进入该页面，选择题库类型
 */
public class SelectQueationBankActivity extends BaseActivity implements View.OnClickListener {



    private RelativeLayout ll_xc, ll_hc, ll_kc, ll_mtc;
    private RelativeLayout city_school;
    private Button btn_sure;
    private TextView tv_city;
    private SPUtils spUtils = new SPUtils();
    private String cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择题库");
        setLeftBtnVisible(false);
        initView();

    }

    private void initView() {
        ll_xc = (RelativeLayout) findViewById(R.id.ll_xc);
        ll_hc = (RelativeLayout) findViewById(R.id.ll_hc);
        ll_kc = (RelativeLayout) findViewById(R.id.ll_kc);
        ll_mtc = (RelativeLayout) findViewById(R.id.ll_mtc);
        city_school = (RelativeLayout) findViewById(R.id.city_school);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        tv_city = (TextView) findViewById(R.id.tv_city);
        cityname = (String) spUtils.get(SelectQueationBankActivity.this, "cityname", "");
        tv_city.setText(cityname);

        ll_xc.setOnClickListener(this);
        ll_kc.setOnClickListener(this);
        ll_hc.setOnClickListener(this);
        ll_mtc.setOnClickListener(this);
        city_school.setOnClickListener(this);
        btn_sure.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        cityname = (String) spUtils.get(SelectQueationBankActivity.this, "cityname", "");
        tv_city.setText(cityname);
        if (ll_xc.isSelected() || ll_hc.isSelected() || ll_kc.isSelected() || ll_mtc.isSelected()) {
            if (!cityname.equals("")) {
                btn_sure.setEnabled(true);
            } else {
                btn_sure.setEnabled(false);
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_queation_bank;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_xc:
                ll_xc.setSelected(true);
                ll_hc.setSelected(false);
                ll_kc.setSelected(false);
                ll_mtc.setSelected(false);
                spUtils.put(this,"cartype","xc");
                if (!cityname.equals("")) {
                    btn_sure.setEnabled(true);
                } else {
                    btn_sure.setEnabled(false);
                }
                break;
            case R.id.ll_hc:
                ll_xc.setSelected(false);
                ll_hc.setSelected(true);
                ll_kc.setSelected(false);
                ll_mtc.setSelected(false);
                spUtils.put(this,"cartype","hc");
                if (!cityname.equals("")) {
                    btn_sure.setEnabled(true);
                } else {
                    btn_sure.setEnabled(false);
                }
                break;
            case R.id.ll_kc:
                ll_xc.setSelected(false);
                ll_hc.setSelected(false);
                ll_kc.setSelected(true);
                ll_mtc.setSelected(false);
                spUtils.put(this,"cartype","kc");
                if (!cityname.equals("")) {
                    btn_sure.setEnabled(true);
                } else {
                    btn_sure.setEnabled(false);
                }
                break;
            case R.id.ll_mtc:
                ll_xc.setSelected(false);
                ll_hc.setSelected(false);
                ll_kc.setSelected(false);
                ll_mtc.setSelected(true);
                spUtils.put(this,"cartype","mtc");
                if (!cityname.equals("")) {
                    btn_sure.setEnabled(true);
                } else {
                    btn_sure.setEnabled(false);
                }
                break;
            case R.id.city_school:
//                Intent intent = new Intent(SelectQueationBankActivity.this, SelectCityActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_sure:
                Intent intent1 = new Intent(SelectQueationBankActivity.this, HomeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
