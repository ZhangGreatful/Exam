package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.ErrorAdapter;
import com.haha.exam.adapter.TopicAdapter;
import com.haha.exam.bean.AllCollect;
import com.haha.exam.bean.AllErrorQuestions;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.DeleteAll;
import com.haha.exam.bean.ErrorList;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.view.MyHorizontalScrollView;
import com.haha.exam.view.MyToggleButton;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Toggle键逻辑未做处理
 * 我的错题界面
 */
public class MyErrorActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;
    private ImageView back;
    private TextView clear, errorCount, text_no_datas;
    private MyToggleButton toggleButton;
    private ErrorAdapter adapter;
    private TopicAdapter topicAdapter;
    private RelativeLayout allError;
    public static int auto_clear ;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//        获取全部错题
        OkGo.post(WebInterface.check_error)
                .tag(this)
                .params("telphone", "18266142739")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        AllCollect error=gson.fromJson(s,AllCollect.class);

                        if (error.getNum()==0){
                            errorCount.setText("0题");
                        }else {
                            AllQuestions allErrorQuestion = gson.fromJson(s, AllQuestions.class);
//                        Toast.makeText(MyErrorActivity.this, allErrorQuestion.getMsg(), Toast.LENGTH_SHORT).show();
                            errorCount.setText(allErrorQuestion.getMsg() + "题");
                            allError.setOnClickListener(MyErrorActivity.this);
                        }



                    }
                });
//        获取错题列表
        OkGo.post(WebInterface.error_list)
                .tag(this)
                .params("telphone", "18266142739")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, final Call call, Response response) {

                        final ErrorList errorList = gson.fromJson(s, ErrorList.class);
//                        判断是否有数据
                        if (errorList.getMsg().equals("暂无数据")) {
                            text_no_datas.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(MyErrorActivity.this, errorList.getMsg(), Toast.LENGTH_SHORT).show();
                            text_no_datas.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            adapter = new ErrorAdapter(MyErrorActivity.this, errorList.getData());
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String chapterid = errorList.getData().get(i).getChapterid();
                                    Intent intent = new Intent(MyErrorActivity.this, ErrorQuestionActivity.class);
                                    intent.putExtra("allerror", chapterid);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.error_list);
        back = (ImageView) findViewById(R.id.back);
        clear = (TextView) findViewById(R.id.clear);
        allError = (RelativeLayout) findViewById(R.id.all_error);
        errorCount = (TextView) findViewById(R.id.error_count);
        text_no_datas = (TextView) findViewById(R.id.text_no_datas);
        toggleButton = (MyToggleButton) findViewById(R.id.auto_clear);
        toggleButton.setonSelectListener(toggleListener);
        back.setOnClickListener(this);
        clear.setOnClickListener(this);
//        allError.setOnClickListener(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
            auto_clear = 1;
            Toast.makeText(MyErrorActivity.this, "自动删除", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUnSelected() {
            auto_clear = 0;
            Toast.makeText(MyErrorActivity.this, "不自动删除", Toast.LENGTH_SHORT).show();
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
                                .params("telphone", "18266142739")
                                .params("deleteall", "1")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        DeleteAll deleteAll = gson.fromJson(s, DeleteAll.class);
                                        Toast.makeText(MyErrorActivity.this, deleteAll.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        initData();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.all_error:
                //全部错题
                Intent intent = new Intent(MyErrorActivity.this, ErrorQuestionActivity.class);
                intent.putExtra("allerror", "0");
                startActivity(intent);
                break;
        }
    }
}
