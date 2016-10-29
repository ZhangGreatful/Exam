package com.haha.exam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.haha.exam.R;
import com.haha.exam.adapter.GradeAdapter;
import com.haha.exam.view.GradePopupWindow;

/**
 * 我的成绩页面
 */
public class MyGradeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private GradeAdapter adapter;
    Button button;
    GradePopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的成绩");
        initView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_grade;
    }

    private void initView() {
        button = (Button) findViewById(R.id.btn);
        listView = (ListView) findViewById(R.id.grader_list);
        adapter = new GradeAdapter(MyGradeActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow = new GradePopupWindow(MyGradeActivity.this, itemsOnClick);
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
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        popupWindow = new GradePopupWindow(MyGradeActivity.this, itemsOnClick);
        //显示窗口
        popupWindow.showAtLocation(MyGradeActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()) {
                case R.id.check:
                    break;
                default:
                    break;
            }


        }

    };
}
