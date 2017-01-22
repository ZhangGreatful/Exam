package com.haha.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.GradeAdapter;
import com.haha.exam.bean.MyGrade;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.GradePopupWindow;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.util.SystemUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的成绩页面
 */
public class MyGradeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private SystemUtil su;
    private String onlyID;
    private SPUtils spUtils = new SPUtils();
    private String subject0;
    private ListView listView;
    private GradeAdapter adapter;
    GradePopupWindow popupWindow;
    private Gson gson = new Gson();
    public static MyGrade myGrade;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的成绩");
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
        subject0 = (String) spUtils.get(this, "subject0", "1");
        su = new SystemUtil(MyGradeActivity.this);
        onlyID = su.showOnlyID();
        initView();
        initData();
    }

    private void initData() {
        position = -1;
        OkGo.post(WebInterface.find_personal_score)
                .tag(this)
                .params("signid", onlyID)
                .params("subject", subject0)
                .params("cartype","xc")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        myGrade = gson.fromJson(s, MyGrade.class);
                        if (myGrade.getNum()==1){
                            adapter = new GradeAdapter(MyGradeActivity.this, myGrade);
                            listView.setAdapter(adapter);
                        }else {
                            Toast.makeText(MyGradeActivity.this,myGrade.getMsg(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_grade;
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.grader_list);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        position = i;
        popupWindow = new GradePopupWindow(MyGradeActivity.this, itemsOnClick, myGrade, i);
        popupWindow.setBackgroundDrawable(null);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        //显示窗口
        popupWindow.showAtLocation(MyGradeActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()) {
                case R.id.check:
                    Intent intent = new Intent(MyGradeActivity.this, PracticeResultActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("msg", 0);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }


        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
