package com.haha.exam.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.AppointmentActivity;
import com.haha.exam.activity.ExaminationActivity;
import com.haha.exam.activity.HomeActivity;
import com.haha.exam.activity.TakeOrderActivity;
import com.haha.exam.adapter.MyFragmentPagerAdapter;
import com.haha.exam.bean.TimeBean;
import com.haha.exam.bean.TimeSub2;
import com.haha.exam.bean.TimeSub3;
import com.haha.exam.bean.TimeSub4;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wanzheng.driver.home.Activity_OrderShow;
import com.wanzheng.driver.home.Activity_PeriodShow;
import com.wanzheng.driver.home.Activity_QRcode;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.RecordsEntity;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 登录模式
 * Created by Administrator on 2016/12/21.
 */

public class HomePageFragmentNew1 extends Fragment implements View.OnClickListener {
    private ArrayList<Fragment> fragmentList;
    protected ImageLoader imageLoader;
    DisplayImageOptions options;
    private Fragment subjectOneFragment, subjectTwoFragment, subjectThreeFragment, subjectFourFragment, subjectFiveFragment;
    private TextView tab1Text, tab2Text, tab3Text, tab4Text, tab5Text;
    private ImageView image_suidao, image_xueshi, image_xueche, image_zaixian, image_lilun, image_jiaoxue, image_yuancheng, image_dingdan, image_jiaxiao, image_jiaolian;
    private ViewPager mViewPager;
    private View tabLine;
    private Animation animation;
    private Context mContext;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private int position_four;
    private LinearLayout jia_xiao_pai_ming, jiao_lian_pai_ming, li_lun_xue_shi, jiao_xue_shi_pin, xue_che_zi_xun, zai_xian_yue_kao,
            yuan_cheng_ke_tang, ding_dan, sui_dao_sui_xue, xue_shi_cha_kan;

    private HomePageFragmentNew.OnButtonClick onButtonClick;//2、定义接口成员变量
    private HomeActivity homeActivity;
    private SystemUtil su;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment_new1, container, false);
        jia_xiao_pai_ming = (LinearLayout) view.findViewById(R.id.jia_xiao_pai_ming);
        jiao_lian_pai_ming = (LinearLayout) view.findViewById(R.id.jiao_lian_pai_ming);
        li_lun_xue_shi = (LinearLayout) view.findViewById(R.id.li_lun_xue_shi);
        jiao_xue_shi_pin = (LinearLayout) view.findViewById(R.id.jiao_xue_shi_pin);
        xue_che_zi_xun = (LinearLayout) view.findViewById(R.id.xue_che_zi_xun);
        zai_xian_yue_kao = (LinearLayout) view.findViewById(R.id.zai_xian_yue_kao);
        yuan_cheng_ke_tang = (LinearLayout) view.findViewById(R.id.yuan_cheng_ke_tang);
        ding_dan = (LinearLayout) view.findViewById(R.id.ding_dan);
        sui_dao_sui_xue = (LinearLayout) view.findViewById(R.id.sui_dao_sui_xue);
        xue_shi_cha_kan = (LinearLayout) view.findViewById(R.id.xue_shi_cha_kan);
        tab1Text = (TextView) view.findViewById(R.id.tab1);
        tab2Text = (TextView) view.findViewById(R.id.tab2);
        tab3Text = (TextView) view.findViewById(R.id.tab3);
        tab4Text = (TextView) view.findViewById(R.id.tab4);
        tab5Text = (TextView) view.findViewById(R.id.tab5);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        tabLine = view.findViewById(R.id.tab_line);


        image_suidao = (ImageView) view.findViewById(R.id.image_suidaosuixue);
        image_xueshi = (ImageView) view.findViewById(R.id.image_xueshichakan);
        image_xueche = (ImageView) view.findViewById(R.id.image_xuechezixun);
        image_zaixian = (ImageView) view.findViewById(R.id.image_zaixianyuekao);
        image_lilun = (ImageView) view.findViewById(R.id.image_lilunxueshi);
        image_jiaoxue = (ImageView) view.findViewById(R.id.image_shipinjiaoxue);
        image_yuancheng = (ImageView) view.findViewById(R.id.image_yuanchengketang);
        image_dingdan = (ImageView) view.findViewById(R.id.image_dingdan);
        image_jiaxiao = (ImageView) view.findViewById(R.id.image_jiaxiaopaihang);
        image_jiaolian = (ImageView) view.findViewById(R.id.image_jiaolianpaiming);

        return view;
    }

    //定义接口变量的get方法
    public HomePageFragmentNew.OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(HomePageFragmentNew.OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    //1、定义接口
    public interface OnButtonClick {
        public void onClick(View view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ding_dan:
                if(!su.showOnlyID().equals("")){
                    Intent intent = new Intent(getActivity(), Activity_OrderShow.class);
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.xue_shi_cha_kan:
                if (!su.showOnlyID().equals("")){
                    startActivity(new Intent(getActivity(), Activity_PeriodShow.class));
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.jia_xiao_pai_ming:
                Intent intent2 = new Intent(getActivity(), AppointmentActivity.class);
                intent2.putExtra("tab", 0);
                startActivity(intent2);
                break;
            case R.id.jiao_lian_pai_ming:
                Intent intent3 = new Intent(getActivity(), AppointmentActivity.class);
                intent3.putExtra("tab", 1);
                startActivity(intent3);
                break;
            case R.id.yuan_cheng_ke_tang:
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sui_dao_sui_xue:
                if (!su.showOnlyID().equals("")){
                    Intent intent1 = new Intent(getActivity(), Activity_QRcode.class);
                    startActivity(intent1);
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.jiao_xue_shi_pin:
                homeActivity.mTabHost.setCurrentTab(1);
                break;
            case R.id.li_lun_xue_shi:
                homeActivity.mTabHost.setCurrentTab(2);
                break;
            case R.id.xue_che_zi_xun:
                Intent intent6 = new Intent(getActivity(), ExaminationActivity.class);
                startActivity(intent6);
                break;
            case R.id.zai_xian_yue_kao:
                if (!su.showOnlyID().equals("")){
                    Intent intent7 = new Intent(getActivity(), TakeOrderActivity.class);
                    startActivity(intent7);
                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeActivity = new HomeActivity();
        su = new SystemUtil(getActivity());
        mContext = getActivity();
        sui_dao_sui_xue.setOnClickListener(this);
        xue_shi_cha_kan.setOnClickListener(this);
        jia_xiao_pai_ming.setOnClickListener(this);
        jiao_lian_pai_ming.setOnClickListener(this);
        li_lun_xue_shi.setOnClickListener(this);
        jiao_xue_shi_pin.setOnClickListener(this);
        xue_che_zi_xun.setOnClickListener(this);
        zai_xian_yue_kao.setOnClickListener(this);
        yuan_cheng_ke_tang.setOnClickListener(this);
        ding_dan.setOnClickListener(this);

        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
//                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .cacheInMemory(true)
//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//设置图片加入缓存前，对bitmap进行设置
//.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .build();//构建完成
        initAdapter();
        initView();


        initWidth();
    }

    private void initImage() {
        if (isConnect(getActivity()) == true) {
            imageLoader.getInstance().displayImage(su.showAppIcon("qr_url"),image_suidao,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("recordview_url"),image_xueshi,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("information_url"),image_xueche,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("onlinetest_url"),image_zaixian,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("theoryicon_url"),image_lilun,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("videoicon_url"),image_jiaoxue,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("distanceclass_url"),image_yuancheng,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("order_url"),image_dingdan,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("schoolrank_url"),image_jiaxiao,options);
            imageLoader.getInstance().displayImage(su.showAppIcon("coachrank_url"),image_jiaolian,options);
        }
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

    private void initView() {


        tab1Text.setOnClickListener(new MyOnClickListener(0));
        tab2Text.setOnClickListener(new MyOnClickListener(2));
        tab3Text.setOnClickListener(new MyOnClickListener(3));
        tab4Text.setOnClickListener(new MyOnClickListener(4));
        tab5Text.setOnClickListener(new MyOnClickListener(1));


        //初始化ViewPager，并且设置ViewPager的监听器

        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));//解决fragment嵌套问题
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private void initAdapter() {
        fragmentList = new ArrayList<>();
        subjectOneFragment = new SubjectOneFragment();
        subjectFiveFragment = new SubjectThreeFragment();
        subjectTwoFragment = new SubjectTwoFragment();
        subjectThreeFragment = new SubjectThreeFragment();
        subjectFourFragment = new SubjectFourFragment();

        fragmentList.add(subjectOneFragment);
        fragmentList.add(subjectFiveFragment);
        fragmentList.add(subjectTwoFragment);
        fragmentList.add(subjectThreeFragment);
        fragmentList.add(subjectFourFragment);
    }

    private void initWidth() {

        bottomLineWidth = tabLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 5 - bottomLineWidth) / 2);

        position_one = (int) (screenW / 5.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
        position_four = position_one * 4;
    }


    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, 0, 0, 0);
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_one, 0, 0);
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_two, 0, 0);
                    }
                    break;
                case 3:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_three, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_three, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_three, 0, 0);
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_three, 0, 0);
                    }
                    break;
                case 4:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_four, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_four, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_four, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_four, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(100);
            tabLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        initImage();
    }

    /**
     * 请求所有的学习记录
     */
    private Gson gson = new Gson();
    private TimeBean subOnetime;
    private TimeBean subTwotime;
    private TimeBean subThreetime;
    private TimeBean subFourtime;
    private int xid;
    private String onlyID;
    private SystemUtil sys;
    private ArrayList<RecordsEntity> list;  //所有的学习记录

    private void initData() {
        sys = new SystemUtil(getActivity());
        xid = sys.showUid();
        onlyID = sys.showOnlyID();
        //二和三需要传递同一个ArrayList<RecordsEntity
        if (xid != -1) {
            String s = "http://120.26.118.158:8082/xueshi.ashx?do=querymyxueshi&xid=" + xid;
            OkGo.post(s)
                    .tag(this)
                    .execute(getRecordsCallBack);
        }

        //访问四次网络，请求到不同的四个学习记录
        if (onlyID != null) {
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "1")
                    .execute(getRecordsCallBack1);
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "2")
                    .execute(getRecordsCallBack2);
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "3")
                    .execute(getRecordsCallBack3);
            OkGo.post(WebInterface.get_li_lun_time)
                    .tag(this)
                    .params("signid", onlyID)
                    .params("subject", "4")
                    .execute(getRecordsCallBack4);

        }


    }

    private StringCallback getRecordsCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            list = JsonUtils.parseRecords(s);
            if (list.size() != 0) {
                EventBus.getDefault().post(list);

            }
        }
    };
    private StringCallback getRecordsCallBack1 = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                subOnetime = gson.fromJson(s, TimeBean.class);
                EventBus.getDefault().post(subOnetime);
            }
        }
    };
    private StringCallback getRecordsCallBack2 = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d("SubjectTwoFragment", "++++++++++++++++++++" + s);
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                subTwotime = gson.fromJson(s, TimeBean.class);
                SystemClock.sleep(100);
                EventBus.getDefault().post(new TimeSub2(subTwotime));

            }
        }
    };
    private StringCallback getRecordsCallBack3 = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d("SubjectTwoFragment", "++++++++++++++++++++" + s);
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                subThreetime = gson.fromJson(s, TimeBean.class);
                EventBus.getDefault().post(new TimeSub3(subThreetime));
            }
        }
    };
    private StringCallback getRecordsCallBack4 = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d("SubjectTwoFragment", "++++++++++++++++++++" + s);
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                subFourtime = gson.fromJson(s, TimeBean.class);
                SystemClock.sleep(100);
                EventBus.getDefault().post(new TimeSub4(subFourtime));
            }
        }
    };

}
