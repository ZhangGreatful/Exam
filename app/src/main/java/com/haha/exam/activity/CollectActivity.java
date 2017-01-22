package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.CollectAdapter;
import com.haha.exam.bean.AllQuestions;
import com.haha.exam.bean.CollectList;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.utils.SPUtils;
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
 * 我的收藏界面
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener {

    private SPUtils spUtils = new SPUtils();
    private String subject0;
    private SystemUtil su;
    private String onlyid;
    private ImageView back;
    private ListView listView;
    private RelativeLayout allCollect;
    private TextView clear, collectCount, text_no_datas;
    private CollectAdapter adapter;
    private Gson gson = new Gson();
    public static List<AllQuestions.DataBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnect(this);
        su = new SystemUtil(CollectActivity.this);
        onlyid = su.showOnlyID();
        subject0 = (String) spUtils.get(this, "subject0", "1");
        initView();
        initData();
    }

    private void initData() {
//        获取全部收藏题目
        datas = new ArrayList<>();
        System.out.println("uid=========" + onlyid);
        OkGo.post(WebInterface.check_save)
                .tag(this)
                .params("subject", subject0)
                .params("signid", onlyid)
                .execute(collectCallBack);
    }

    private StringCallback collectCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 0) {
                allCollect.setEnabled(false);
                collectCount.setText("0 题");
            } else {
                allCollect.setEnabled(true);
                AllQuestions allQuestions = gson.fromJson(s, AllQuestions.class);
                setData(allQuestions.getData());
                //        获取收藏题目列表
                OkGo.post(WebInterface.collect_list)
                        .tag(this)
                        .params("subject", subject0)
                        .params("signid", onlyid)
                        .execute(new StringCallback() {
                                     @Override
                                     public void onSuccess(String s, Call call, Response response) {

//                        判断是否有数据
                                         int num = JsonUtils.parseNum(s);
                                         if (num == 0) {

                                             text_no_datas.setVisibility(View.VISIBLE);
                                             listView.setVisibility(View.GONE);
                                         } else {
                                             final CollectList collectList = gson.fromJson(s, CollectList.class);
                                             collectCount.setText(collectList.getMsg() + " 题");
                                             text_no_datas.setVisibility(View.GONE);
                                             listView.setVisibility(View.VISIBLE);
                                             adapter = new CollectAdapter(CollectActivity.this, collectList.getData());
                                             listView.setAdapter(adapter);
                                             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                     String chapterid = collectList.getData().get(i).getChapterid();
                                                     Intent intent = new Intent(CollectActivity.this, CollectQuestionActivity.class);
                                                     intent.putExtra("allcollect", chapterid);
                                                     startActivity(intent);
                                                 }
                                             });
                                         }
                                     }
                                 }

                        );
            }
        }
    };

    private void initView() {

        listView = (ListView) findViewById(R.id.collect_list);
        allCollect = (RelativeLayout) findViewById(R.id.all_collect);
        collectCount = (TextView) findViewById(R.id.collect_count);
        text_no_datas = (TextView) findViewById(R.id.text_no_datas);
        back = (ImageView) findViewById(R.id.back);
        clear = (TextView) findViewById(R.id.clear);

        back.setOnClickListener(this);
        clear.setOnClickListener(this);
        allCollect.setOnClickListener(this);

    }

    private void setData(List<AllQuestions.DataBean> data) {
        this.datas = data;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_collect;
    }

    @Override
    protected int getTitlebarResId() {
        return R.layout.collect_bar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_collect:
                //全部错题
                Intent intent = new Intent(CollectActivity.this, CollectQuestionActivity.class);
                intent.putExtra("allcollect", "0");
                startActivity(intent);
                break;
            case R.id.clear:
                //                显示dialog
                final MyDialog dialog = new MyDialog(CollectActivity.this);
                dialog.setMessage("是否清空所有收藏");
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
                        OkGo.post(WebInterface.delete_all_collect)
                                .tag(this)
                                .params("subject", subject0)
                                .params("signid", onlyid)
                                .params("deleteall", "1")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int num = JsonUtils.parseNum(s);
                                        if (num == 1) {
                                            collectCount.setText("0 题");
                                            listView.setVisibility(View.GONE);
                                            initData();
                                        }
                                    }
                                });

                        dialog.dismiss();
                    }
                });
                break;
            case R.id.back:
                finish();
                break;
        }
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

}
