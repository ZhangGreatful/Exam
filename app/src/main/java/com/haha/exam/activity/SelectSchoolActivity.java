package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.SchoolAdapter;
import com.haha.exam.bean.AllSchool;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SelectSchoolActivity extends BaseActivity {

    private Gson gson = new Gson();
    private ListView listView;
    private SchoolAdapter adapter;
    private SPUtils spUtils = new SPUtils();
    private String currentCityid;
    private TextView tv_unchoice;
    private EditText editText;
    private List<AllSchool.DataBean> list = new ArrayList<>();
    Handler myhandler = new Handler();
    private AllSchool allSchool = new AllSchool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择驾校");
        initView();
        initData();
//        setOnTextChanged();
    }

    private void initData() {
        final Intent intent = getIntent();
        String cityid = intent.getStringExtra("cityid");

        System.out.println("currentCityid========" + cityid);
        OkGo.post(WebInterface.driving_school)
                .tag(this)
                .params("cityid", cityid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        final AllSchool school = gson.fromJson(s, AllSchool.class);
                        System.out.println("共有驾校======" + school.getData().size());
                        setData(school);
                        list = school.getData();
                        adapter = new SchoolAdapter(SelectSchoolActivity.this, list);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                spUtils.put(SelectSchoolActivity.this, "schoolname", school.getData().get(i).getSchoolname());
                                spUtils.put(SelectSchoolActivity.this, "schoolid", school.getData().get(i).getSid());
                                Intent intent1 = new Intent(SelectSchoolActivity.this, SelectQueationBankActivity.class);
                                startActivity(intent1);
                                finish();

                            }
                        });


                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                System.out.println("allSchool=====" + allSchool.getData().size());
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                System.out.println("allSchool=====" + allSchool.getData().size());
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                System.out.println("allSchool=====" + allSchool.getData().size());
                                String data = editText.getText().toString();
                                list.clear();
                                AllSchool school = gson.fromJson(s, AllSchool.class);
                                if (data.equals(null)) {
                                    list = school.getData();
                                    System.out.println("data===========" + data);
                                    System.out.println("lsitasdfa====="+list.size());
                                    System.out.println("dataaws===========" + school.getData().size());
                                } else {
                                    System.out.println("data===========" + data.length());
                                    int length = school.getData().size();
                                    System.out.println("length=======" + length);
                                    for (int i = 0; i < length; i++) {
                                        if (school.getData().get(i).getSchoolname().contains(data)) {
                                            list.add(school.getData().get(i));
                                        }
                                    }
                                }
                                System.out.println("size=======" + list.size());
//                                getmDataSub(list, data);//获取更新数据
                                adapter.notifyDataSetChanged();
                            }
                        });
                        listView.setAdapter(adapter);
//                        setOnTextChanged();
                    }
                });
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.edit_school);
        listView = (ListView) findViewById(R.id.school_list);
        tv_unchoice = (TextView) findViewById(R.id.tv_unchoice);
        tv_unchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spUtils.put(SelectSchoolActivity.this, "schoolname", "");
                spUtils.put(SelectSchoolActivity.this, "schoolid", "");
                Intent intent = new Intent(SelectSchoolActivity.this, SelectQueationBankActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void setData(AllSchool school) {
        this.allSchool = school;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_school;
    }
}
