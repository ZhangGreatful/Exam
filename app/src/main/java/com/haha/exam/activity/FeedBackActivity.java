package com.haha.exam.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.dialog.PraticeDialog;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import okhttp3.Call;
import okhttp3.Response;

public class FeedBackActivity extends BaseActivity implements  View.OnClickListener {

    private EditText feed, contact;
    private ImageView submmit;
    private String content, signid;
    private SystemUtil su;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        su = new SystemUtil(this);
        signid = su.showOnlyID();
        initTitle();
        initView();
    }

    private void initView() {
        feed = (EditText) findViewById(R.id.edit);
        contact = (EditText) findViewById(R.id.contact);
        submmit = (ImageView) findViewById(R.id.submmit);
        submmit.setOnClickListener(this);
    }

    private void initTitle() {
        setTitle("意见反馈");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onClick(View view) {
        content=feed.getText().toString().trim();
        if (content.equals("")){
            Toast.makeText(this, "请写下您宝贵的意见", Toast.LENGTH_SHORT).show();
        }else {
            OkGo.post(WebInterface.feed_back)
                    .tag(this)
                    .params("signid", signid)
                    .params("content", content)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            int num= JsonUtils.parseNum(s);
                            if (num==1){
                                PraticeDialog dialog=new PraticeDialog(FeedBackActivity.this);
                                dialog.setMessage("谢谢亲～的建议，我们已经记录下来，及时改进.");
                                dialog.show();
                                dialog.setYesOnclickListener("",new PraticeDialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        FeedBackActivity.this.finish();
                                    }
                                });
                            }
                        }
                    });
        }

    }
}
