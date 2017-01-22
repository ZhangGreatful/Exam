package com.wanzheng.driver.home;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haha.exam.R;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.RelatedActivity.MoniFragment;
import com.wanzheng.driver.RelatedActivity.PeriodFragment;
import com.wanzheng.driver.RelatedActivity.SubjectFourFragment;
import com.wanzheng.driver.RelatedActivity.SubjectOneFragment;
import com.wanzheng.driver.RelatedActivity.SubjectThreeFragment;
import com.wanzheng.driver.RelatedActivity.SubjectTwoFragment;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.view.TitleBarView;

import java.util.ArrayList;


public class Activity_PeriodShow extends FragmentActivity implements OnClickListener {
    private ViewPager pager;
    private DisplayMetrics dm = new DisplayMetrics();
    private static int tabWidth;
    private ImageView tabLine;
    private TextView orderlist_1, orderlist_2, orderlist_3, orderlist_4, orderlist_5;
    private int position = 0;

    private ArrayList<Fragment> fragments;
    private Fragment subjectOneFragment, subjectTwoFragment, subjectThreeFragment, subjectFourFragment, moniFragment;
    private LinearLayout coachCardShareLinear;
    private Button cardSubmitBtn, cardCancelBtn;
    private TitleBarView titleBarView;
    private PeriodFragment periodFragment;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodshow);
        isConnect(this);
        type = 1;
        initFragment();
        initView();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        subjectOneFragment = new SubjectOneFragment();
        subjectTwoFragment = new SubjectTwoFragment();
        subjectThreeFragment = new SubjectThreeFragment();
        subjectFourFragment = new SubjectFourFragment();
        moniFragment = new MoniFragment();
        fragments.add(subjectOneFragment);
        fragments.add(moniFragment);
        fragments.add(subjectTwoFragment);
        fragments.add(subjectThreeFragment);
        fragments.add(subjectFourFragment);
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    private void initView() {
        // TODO Auto-generated method stub

        titleBarView = (TitleBarView) findViewById(R.id.title_bar);

        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
        TextView txt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
        txt.setText("学时列表");
        ImageView titleR = (ImageView) titlebar.findViewById(R.id.titlebar_back);
        titleR.setVisibility(View.VISIBLE);
        titleR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        tabWidth = dm.widthPixels / 5;
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        pager.setOnPageChangeListener(new PageListener());
        pager.setCurrentItem(position);
        pager.setOffscreenPageLimit(5);

        tabLine = (ImageView) findViewById(R.id.tabLine);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.tab_line);

        Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, tabWidth, 8);//
        // 设置tab的宽度和高度
        tabLine.setImageBitmap(b);

        orderlist_1 = (TextView) findViewById(R.id.orderlist_1);
        orderlist_1.setOnClickListener(this);
        orderlist_2 = (TextView) findViewById(R.id.orderlist_2);
        orderlist_2.setOnClickListener(this);
        orderlist_3 = (TextView) findViewById(R.id.orderlist_3);
        orderlist_3.setOnClickListener(this);
        orderlist_4 = (TextView) findViewById(R.id.orderlist_4);
        orderlist_4.setOnClickListener(this);
        orderlist_5 = (TextView) findViewById(R.id.orderlist_5);
        orderlist_5.setOnClickListener(this);
        orderlist_1.setTextColor(getResources().getColor(R.color.black));
        orderlist_2.setTextColor(getResources().getColor(R.color.black));
        orderlist_3.setTextColor(getResources().getColor(R.color.black));
        orderlist_4.setTextColor(getResources().getColor(R.color.black));
        orderlist_5.setTextColor(getResources().getColor(R.color.black));
        periodFragment = new PeriodFragment();
        switch (pager.getCurrentItem()) {
            case 0:
                titleBarView.setTitleLeft("视频");
                titleBarView.setTitleRight("测试");
                titleBarView.setVisibility(View.VISIBLE);
                orderlist_1.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;
            case 1:
                titleBarView.setTitleLeft("视频");
                titleBarView.setTitleRight("实操");
                titleBarView.setVisibility(View.VISIBLE);
                orderlist_2.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;
            case 2:
                titleBarView.setTitleLeft("视频");
                titleBarView.setTitleRight("实操");
                titleBarView.setVisibility(View.VISIBLE);
                orderlist_3.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;
            case 3:
                titleBarView.setTitleLeft("视频");
                titleBarView.setTitleRight("实操");
                titleBarView.setBtnRightVisible(View.VISIBLE);
                orderlist_4.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;
            case 4:
                titleBarView.setTitleLeft("练习");
                titleBarView.setTitleRight("练习");
                titleBarView.setVisibility(View.GONE);
                orderlist_5.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;

            default:
                break;
        }


        coachCardShareLinear = (LinearLayout) findViewById(R.id.coach_card_share_linear);
        cardSubmitBtn = (Button) findViewById(R.id.coach_card_submit_btn);
        cardCancelBtn = (Button) findViewById(R.id.coach_card_cancel_btn);
        cardSubmitBtn.setOnClickListener(this);
        cardCancelBtn.setOnClickListener(this);


        titleBarView.getTitleLeft().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleBarView.getTitleLeft().isEnabled()) {
                    type = 1;
                    if (pager.getCurrentItem() == 0) {
                        SubjectOneFragment fragment = new SubjectOneFragment();
                        fragment.listView.setVisibility(View.VISIBLE);
                        fragment.listView1.setVisibility(View.GONE);
                    } else if (pager.getCurrentItem() == 1) {
                    } else if (pager.getCurrentItem() == 2) {
                        SubjectTwoFragment fragment = new SubjectTwoFragment();
                        fragment.listView.setVisibility(View.VISIBLE);
                        fragment.listView1.setVisibility(View.GONE);
                    } else if (pager.getCurrentItem() == 3) {
                        SubjectThreeFragment fragment = new SubjectThreeFragment();
                        fragment.listView.setVisibility(View.VISIBLE);
                        fragment.listView1.setVisibility(View.GONE);
                    } else {
                        SubjectFourFragment fragment = new SubjectFourFragment();
                        fragment.listView.setVisibility(View.VISIBLE);
                        fragment.listView1.setVisibility(View.GONE);
                    }
                    titleBarView.getTitleLeft().setEnabled(false);
                    titleBarView.getTitleRight().setEnabled(true);
                }
//                periodFragment.setListViewVisible(2);
            }
        });
        titleBarView.getTitleLeft().performClick();
        titleBarView.getTitleRight().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleBarView.getTitleRight().isEnabled()) {
                    type = 2;
                    if (pager.getCurrentItem() == 0) {
                        SubjectOneFragment fragment = new SubjectOneFragment();
                        fragment.listView.setVisibility(View.GONE);
                        fragment.listView1.setVisibility(View.VISIBLE);
                    } else if (pager.getCurrentItem() == 1) {
                    } else if (pager.getCurrentItem() == 2) {
                        SubjectTwoFragment fragment = new SubjectTwoFragment();
                        fragment.listView.setVisibility(View.GONE);
                        fragment.listView1.setVisibility(View.VISIBLE);
                    } else if (pager.getCurrentItem() == 3) {
                        SubjectThreeFragment fragment = new SubjectThreeFragment();
                        fragment.listView.setVisibility(View.GONE);
                        fragment.listView1.setVisibility(View.VISIBLE);
                    } else {
                        SubjectFourFragment fragment = new SubjectFourFragment();
                        fragment.listView.setVisibility(View.GONE);
                        fragment.listView1.setVisibility(View.VISIBLE);
                    }
                    titleBarView.getTitleLeft().setEnabled(true);
                    titleBarView.getTitleRight().setEnabled(false);
                }
//                periodFragment.setListViewVisible(1);
            }
        });

    }

    public boolean isConnect(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        Toast.makeText(context, "请检查你的网络", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 页面适配器
     */
    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new PeriodFragment(position, Activity_PeriodShow.this);

        }

        @Override
        public int getCount() {
            return 5;
        }

    }

    /**
     * 监听
     */
    public class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // new一个矩阵
            Matrix matrix = new Matrix();
            // 设置激活条的最终位置
            switch (arg0) {
                case 0:
                    // 使用set直接设置到那个位置
                    matrix.setTranslate(0, 0);
                    break;
                case 1:
                    matrix.setTranslate(tabWidth, 0);
                    break;
                case 2:
                    matrix.setTranslate(tabWidth * 2, 0);
                    break;
                case 3:
                    matrix.setTranslate(tabWidth * 3, 0);
                    break;
                case 4:
                    matrix.setTranslate(tabWidth * 4, 0);
                    break;

            }

            // 在滑动的过程中，计算出激活条应该要滑动的距离
            float t = (tabWidth) * arg1;

            // 使用post追加数值
            matrix.postTranslate(t, 0);

            tabLine.setImageMatrix(matrix);
        }

        @Override
        public void onPageSelected(int arg0) {
            orderlist_1.setTextColor(getResources().getColor(R.color.black));
            orderlist_2.setTextColor(getResources().getColor(R.color.black));
            orderlist_3.setTextColor(getResources().getColor(R.color.black));
            orderlist_4.setTextColor(getResources().getColor(R.color.black));
            orderlist_5.setTextColor(getResources().getColor(R.color.black));
            switch (arg0) {
                case 0:
                    titleBarView.setTitleLeft("视频");
                    titleBarView.setTitleRight("测试");
                    titleBarView.setVisibility(View.VISIBLE);
                    if (type == 1) {
                        SubjectOneFragment fragment = new SubjectOneFragment();
                        fragment.listView.setVisibility(View.VISIBLE);
                        fragment.listView1.setVisibility(View.GONE);
                    } else {
                        SubjectOneFragment fragment = new SubjectOneFragment();
                        fragment.listView.setVisibility(View.GONE);
                        fragment.listView1.setVisibility(View.VISIBLE);
                    }
                    orderlist_1.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;
                case 1:
                    titleBarView.setTitleLeft("视频");
                    titleBarView.setTitleRight("实操");
                    titleBarView.setBtnRightVisible(View.VISIBLE);
                    orderlist_2.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;
                case 2:
                    SubjectTwoFragment subjectTwoFragment = new SubjectTwoFragment();
                    if (type == 1) {

                        subjectTwoFragment.listView.setVisibility(View.VISIBLE);
                        subjectTwoFragment.listView1.setVisibility(View.GONE);
                    } else {
                        subjectTwoFragment.listView.setVisibility(View.GONE);
                        subjectTwoFragment.listView1.setVisibility(View.VISIBLE);
                    }
                    titleBarView.setTitleLeft("视频");
                    titleBarView.setTitleRight("实操");
                    titleBarView.setVisibility(View.VISIBLE);
                    orderlist_3.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;
                case 3:
                    SubjectThreeFragment subjectThreeFragment = new SubjectThreeFragment();
                    if (type == 1) {

                        subjectThreeFragment.listView.setVisibility(View.VISIBLE);
                        subjectThreeFragment.listView1.setVisibility(View.GONE);
                    } else {
                        subjectThreeFragment.listView.setVisibility(View.GONE);
                        subjectThreeFragment.listView1.setVisibility(View.VISIBLE);
                    }
                    titleBarView.setTitleLeft("视频");
                    titleBarView.setTitleRight("实操");
                    titleBarView.setVisibility(View.VISIBLE);
                    orderlist_4.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;
                case 4:
                    SubjectFourFragment subjectFourFragment = new SubjectFourFragment();
                    if (type == 1) {

                        subjectFourFragment.listView.setVisibility(View.VISIBLE);
                        subjectFourFragment.listView1.setVisibility(View.GONE);
                    } else {
                        subjectFourFragment.listView.setVisibility(View.GONE);
                        subjectFourFragment.listView1.setVisibility(View.VISIBLE);
                    }
                    titleBarView.setTitleLeft("测试");
                    titleBarView.setTitleRight("练习");
                    titleBarView.setVisibility(View.GONE);
                    orderlist_5.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;


                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderlist_1:
                position = 0;
                pager.setCurrentItem(position);
                break;
            case R.id.orderlist_2:
                position = 1;
                pager.setCurrentItem(position);
                break;
            case R.id.orderlist_3:
                position = 2;
                pager.setCurrentItem(position);
                break;
            case R.id.orderlist_4:
                position = 3;
                pager.setCurrentItem(position);
                break;
            case R.id.orderlist_5:
                position = 4;
                pager.setCurrentItem(position);
                break;
            case R.id.coach_card_cancel_btn:
                coachCardShareLinear.setVisibility(View.GONE);
                break;
            case R.id.coach_card_submit_btn:
                postShare();
                break;
            default:
                break;
        }
    }

    /**
     * 调用postShare分享。跳转至分享编辑页，然后再分享。</br> [注意]<li>
     * 对于新浪，豆瓣，人人，腾讯微博跳转到分享编辑页，其他平台直接跳转到对应的客户端
     */
    private void postShare() {
        // CustomShareBoard shareBoard = new CustomShareBoard(this,
        // coach.getPhone());
        // shareBoard.showAtLocation(this.getWindow().getDecorView(),
        // Gravity.BOTTOM, 0, 0);
    }

    private NetWorkCallBack<BaseResult> cardOrMoneyCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            // TODO Auto-generated method stub
            int msg = JsonUtils.parseMsg(json);
            if (msg == 1) {
                coachCardShareLinear.setVisibility(View.VISIBLE);
            }
        }
    };
}
