package com.wanzheng.driver.home;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.order.OrderFragment;


public class Activity_OrderShow extends FragmentActivity implements OnClickListener {
    private ViewPager pager;
    private DisplayMetrics dm = new DisplayMetrics();
    private static int tabWidth;
    private ImageView tabLine;
    private TextView bookTxt, noReviewTxt, reviewTxt;
    private int position = 0;

    private LinearLayout coachCardShareLinear;
    private Button cardSubmitBtn, cardCancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        isConnect(this);
    }

    private void initDate() {
        // mController = UMServiceFactory.getUMSocialService("com.umeng.share",
        // RequestType.SOCIAL);
        // String appID = "wxfb60df58974e1118";
        // String appSecret = "17248fb5cd693350de681f1ec51ca8dc";
        // // 添加微信平台
        // UMWXHandler wxHandler = new UMWXHandler(OrderListActivity.this,
        // appID,
        // appSecret);
        // wxHandler.addToSocialSDK();
        // // 支持微信朋友圈
        // UMWXHandler wxCircleHandler = new UMWXHandler(OrderListActivity.this,
        // appID, appSecret);
        // wxCircleHandler.setToCircle(true);
        // wxCircleHandler.addToSocialSDK();
        // // 设置微信朋友圈分享内容
        //
        // CircleShareContent circleMedia = new CircleShareContent();
        // circleMedia.setShareContent("来自哈哈约车");
        // // 设置朋友圈title
        // circleMedia.setTitle("哈哈约车-朋友圈");
        // circleMedia.setShareImage(new UMImage(OrderListActivity.this,
        // R.drawable.zxing_icon));
        // circleMedia.setTargetUrl("https://www.1xueche.cn/");
        // mController.setShareMedia(circleMedia);
        //
        // // 设置微信好友分享内容
        // WeiXinShareContent weixinContent = new WeiXinShareContent();
        // // 设置分享文字
        // weixinContent.setShareContent("来自哈哈约车");
        // // 设置title
        // weixinContent.setTitle("哈哈约车-微信");
        // // 设置分享内容跳转URL
        // weixinContent.setTargetUrl("https://www.1xueche.cn/");
        // // 设置分享图片
        // weixinContent.setShareImage(new UMImage(OrderListActivity.this,
        // R.drawable.zxing_icon));
        // mController.setShareMedia(weixinContent);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        //initDate();
        initView();

    }

    private void initView() {
        // TODO Auto-generated method stub
        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
        TextView txt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
        txt.setText("订单列表");
        ImageView titleR = (ImageView) titlebar.findViewById(R.id.titlebar_back);
        titleR.setVisibility(View.VISIBLE);
        titleR.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();

            }
        });

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        tabWidth = dm.widthPixels / 3;
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentAdapter(this.getSupportFragmentManager()));
        pager.setOnPageChangeListener(new PageListener());
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(position);
        tabLine = (ImageView) findViewById(R.id.tabLine);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.tab_line);

        Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, tabWidth, 8);//
        // 设置tab的宽度和高度
        tabLine.setImageBitmap(b);

        bookTxt = (TextView) findViewById(R.id.orderlist_book);
        bookTxt.setOnClickListener(this);
        noReviewTxt = (TextView) findViewById(R.id.orderlist_noreview);
        noReviewTxt.setOnClickListener(this);
        reviewTxt = (TextView) findViewById(R.id.orderlist_review);
        reviewTxt.setOnClickListener(this);
        noReviewTxt.setTextColor(getResources().getColor(R.color.black));
        reviewTxt.setTextColor(getResources().getColor(R.color.black));
        bookTxt.setTextColor(getResources().getColor(R.color.black));
        switch (pager.getCurrentItem()) {
            case 0:
                bookTxt.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;
            case 1:
                noReviewTxt.setTextColor(getResources().getColor(
                        R.color.main_title_button));
                break;
            case 2:
                reviewTxt.setTextColor(getResources().getColor(
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
            return new OrderFragment(position, Activity_OrderShow.this);

        }

        @Override
        public int getCount() {
            return 3;
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

            }

            // 在滑动的过程中，计算出激活条应该要滑动的距离
            float t = (tabWidth) * arg1;

            // 使用post追加数值
            matrix.postTranslate(t, 0);

            tabLine.setImageMatrix(matrix);
        }

        @Override
        public void onPageSelected(int arg0) {
            bookTxt.setTextColor(getResources().getColor(R.color.black));
            noReviewTxt.setTextColor(getResources().getColor(R.color.black));
            reviewTxt.setTextColor(getResources().getColor(R.color.black));
            switch (arg0) {
                case 0:
                    bookTxt.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;
                case 1:
                    noReviewTxt.setTextColor(getResources().getColor(
                            R.color.main_title_button));
                    break;
                case 2:
                    reviewTxt.setTextColor(getResources().getColor(
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
            case R.id.orderlist_book:
                position = 0;
                pager.setCurrentItem(position);
                break;
            case R.id.orderlist_noreview:
                position = 1;
                pager.setCurrentItem(position);
                break;
            case R.id.orderlist_review:
                position = 2;
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

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		if (requestCode == 1) {
//			if (resultCode == RESULT_OK) {
//				coach = (Coach) data.getSerializableExtra(Keys.COACH);
//				new NetWorkUtils().getInstance().work(
//						new NetInterface().getInstance().carOrMoney(
//								coach.getPhone()), cardOrMoneyCallBack);
//
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}

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
