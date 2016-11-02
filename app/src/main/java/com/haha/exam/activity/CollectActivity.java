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
import com.haha.exam.adapter.CollectAdapter;
import com.haha.exam.adapter.ErrorAdapter;
import com.haha.exam.bean.AllCollect;
import com.haha.exam.bean.AllCollection;
import com.haha.exam.bean.ClearInfo;
import com.haha.exam.bean.CollectList;
import com.haha.exam.bean.DeleteAll;
import com.haha.exam.bean.ErrorList;
import com.haha.exam.dialog.MyDialog;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的收藏界面
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private ListView listView;
    private RelativeLayout allCollect;
    private TextView clear, collectCount, text_no_datas;
    private CollectAdapter adapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
//        获取全部收藏题目
        OkGo.post(WebInterface.check_save)
                .tag(this)
                .params("telphone", "18266142739")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        AllCollect collect = gson.fromJson(s, AllCollect.class);
                        if (collect.getNum()==1){
                            AllCollection collection=gson.fromJson(s,AllCollection.class);
                            collectCount.setText(String.valueOf(collection.getMsg()) + "道");
                        }else {
                            collectCount.setText( "0道");
                        }
//                        collectCount.setText(String.valueOf(collect.getMsg()) + "道");
                    }
                });
//        获取收藏题目列表
        OkGo.post(WebInterface.collect_list)
                .tag(this)
                .params("telphone", "18266142739")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        final CollectList collectList = gson.fromJson(s, CollectList.class);
//                        判断是否有数据
                        if (collectList.getNum()==0) {
                            text_no_datas.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(CollectActivity.this, collectList.getMsg(), Toast.LENGTH_SHORT).show();
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
                });
    }


    private void initView() {

        listView = (ListView) findViewById(R.id.collect_list);
        allCollect = (RelativeLayout) findViewById(R.id.all_collect);
        collectCount = (TextView) findViewById(R.id.collect_count);
        text_no_datas = (TextView) findViewById(R.id.text_no_datas);
        back = (ImageView) findViewById(R.id.back);
        clear= (TextView) findViewById(R.id.clear);

        back.setOnClickListener(this);
        clear.setOnClickListener(this);
        allCollect.setOnClickListener(this);

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
                                .params("telphone", "18266142739")
                                .params("deleteall", "1")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        DeleteAll deleteAll = gson.fromJson(s, DeleteAll.class);
                                        Toast.makeText(CollectActivity.this, deleteAll.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        initData();
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
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
