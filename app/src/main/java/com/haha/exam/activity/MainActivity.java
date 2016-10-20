package com.haha.exam.activity;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.haha.exam.fragment.HomeFragment;
import com.haha.exam.fragment.LeftFragment;
import com.haha.exam.fragment.OrderFragment;
import com.haha.exam.R;
import com.haha.exam.fragment.ToolsFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentTabHost mTabHost;
    private LayoutInflater inflater;
    private Class mFragmentArray[] = {HomeFragment.class, OrderFragment.class,ToolsFragment.class};
    private int mImageArray[] = {R.drawable.tab_home, R.drawable.tab_order, R.drawable.tab_favorable};
    private String textArray[] = {"考驾照", "驾考圈", "工具"};

    private DrawerLayout drawer_layout;
    private FrameLayout fly_content;
    private View topbar;
    private LeftFragment fg_left_menu;
    private FragmentManager fManager;
    private ImageView iv_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        fg_left_menu = (LeftFragment) fManager.findFragmentById(R.id.fg_left_menu);
        initViews();
    }

    private void initViews() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fly_content = (FrameLayout) findViewById(R.id.fly_content);
        topbar = findViewById(R.id.topbar);
        iv_mine = (ImageView) topbar.findViewById(R.id.mine);
        fg_left_menu.setDrawerLayout(drawer_layout);

        inflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.fly_content);
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);//去掉tabhost的分割线


        iv_mine.setOnClickListener(this);

        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
        }
        updateTab(mTabHost);
        mTabHost.setOnTabChangedListener(tabChangeListener);
    }

    private TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String s) {
            updateTab(mTabHost);
        }
    };

    //          更新tab键文字
    private void updateTab(FragmentTabHost mTabHost) {
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.textview);
            if (mTabHost.getCurrentTab() == i) {//选中
                tv.setTextColor(this.getResources().getColorStateList(
                        R.color.orange));
            } else {//不选中
                tv.setTextColor(this.getResources().getColorStateList(
                        android.R.color.darker_gray));
            }
        }
    }

    //      为tab键设置图片和文字
    private View getTabItemView(int index) {
        View view = inflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        imageView.setImageResource(mImageArray[index]);
        textView.setText(textArray[index]);
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine:
                drawer_layout.openDrawer(Gravity.LEFT);
                break;
        }
    }
}
