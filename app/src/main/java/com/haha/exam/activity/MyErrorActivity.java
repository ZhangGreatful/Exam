package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.ErrorAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.ErrorList;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.MyToggleButton;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Toggle键逻辑未做处理
 * 我的错题界面
 */
public class MyErrorActivity extends BaseActivity implements View.OnClickListener {

    private SystemUtil su;
    private String onlyID;
    private SPUtils spUtils = new SPUtils();
    private String subject, subject0;
    private ListView listView;
    private ImageView back;
    private TextView clear, errorCount, text_no_datas;
    private MyToggleButton toggleButton;
    private ErrorAdapter adapter;
    private RelativeLayout allError;
    private int auto_clear;
    private Gson gson = new Gson();
    private RelativeLayout btn;
    AllQuestions allErrorQuestion;
    public static List<AllQuestions.DataBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnect(this);
        auto_clear = 1;
        su = new SystemUtil(MyErrorActivity.this);
        onlyID = su.showOnlyID();
        subject = (String) spUtils.get(this, "subject", "one");
        subject0 = (String) spUtils.get(this, "subject0", "1");
        initView();
        initData();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_my_error;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.error_bar;
    }

    private void initData() {
        datas = new ArrayList<>();
        Log.d("MyErrorActivity", "onlyId===========" + onlyID);
//        获取全部错题
        OkGo.post(WebInterface.check_error)
                .tag(this)
                .params("signid", onlyID)
                .params("subject", subject0)
                .execute(allErrorCallBack);
    }

    private StringCallback allErrorCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 0) {
                allError.setEnabled(false);
                errorCount.setText("0 题");
            } else {
                allError.setEnabled(true);
                allErrorQuestion = gson.fromJson(s, AllQuestions.class);
                setData(allErrorQuestion.getData());
                allError.setOnClickListener(MyErrorActivity.this);
                //        获取错题列表,全部错题得到以后再去获取错题列表
                OkGo.post(WebInterface.error_list)
                        .tag(this)
                        .params("subject", subject0)
                        .params("signid", onlyID)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, final Call call, Response response) {

                                int num = JsonUtils.parseNum(s);
                                if (num == 0) {
                                    btn.setVisibility(View.VISIBLE);
                                    text_no_datas.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                } else {
                                    final ErrorList errorList = gson.fromJson(s, ErrorList.class);
                                    errorCount.setText(errorList.getMsg() + " 题");
                                    text_no_datas.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                    btn.setVisibility(View.VISIBLE);
                                    adapter = new ErrorAdapter(MyErrorActivity.this, errorList.getData());
                                    listView.setAdapter(adapter);
                                    setListViewHeightBasedOnChildren(listView);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            String chapterid = errorList.getData().get(i).getChapterid();
                                            Intent intent = new Intent(MyErrorActivity.this, ErrorQuestionActivity.class);
                                            intent.putExtra("allerror", chapterid);
                                            intent.putExtra("auto_clear", auto_clear);
                                            startActivity(intent);
                                        }
                                    });

                                }
                            }
                        });
            }

        }
    };

    private void initView() {
        listView = (ListView) findViewById(R.id.error_list);
        back = (ImageView) findViewById(R.id.back);
        clear = (TextView) findViewById(R.id.clear);
        allError = (RelativeLayout) findViewById(R.id.all_error);
        errorCount = (TextView) findViewById(R.id.error_count);
        text_no_datas = (TextView) findViewById(R.id.text_no_datas);
        btn = (RelativeLayout) findViewById(R.id.btn);
        toggleButton = (MyToggleButton) findViewById(R.id.auto_clear);
        toggleButton.setonSelectListener(toggleListener);
        back.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    public void setData(List<AllQuestions.DataBean> data) {
        this.datas = data;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listView.setAdapter(null);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private MyToggleButton.onSelectListener toggleListener = new MyToggleButton.onSelectListener() {

        @Override
        public void onSelected() {
            auto_clear = 0;
        }

        @Override
        public void onUnSelected() {
            auto_clear = 1;
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear:
//                显示dialog
                final MyDialog dialog = new MyDialog(MyErrorActivity.this);
                dialog.setMessage("是否清空所有错题");
                dialog.show();
                dialog.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        //清空错题数据库    网络数据
                        OkGo.post(WebInterface.delete_all)
                                .tag(this)
                                .params("subject", subject0)
                                .params("signid", onlyID)
                                .params("deleteall", "1")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            errorCount.setText("0 题");
                                            initData();
                                            listView.setVisibility(View.GONE);
                                        }
                                    }
                                });

                        dialog.dismiss();
                    }
                });
                break;
            case R.id.all_error:
                //全部错题
                Intent intent = new Intent(MyErrorActivity.this, ErrorQuestionActivity.class);
                intent.putExtra("allerror", "0");
                intent.putExtra("auto_clear", auto_clear);
                startActivity(intent);
                break;
        }
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
