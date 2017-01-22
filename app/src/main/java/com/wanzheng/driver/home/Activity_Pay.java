package com.wanzheng.driver.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.haha.exam.R;
import com.haha.exam.activity.HomeActivity;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.Mine;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.PayResult;
import com.wanzheng.driver.util.SchoolBaoxian;
import com.wanzheng.driver.util.SignUtils;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

public class Activity_Pay extends Activity implements OnClickListener {

    private RelativeLayout zhifubao_rela;
    private RelativeLayout weixin_rela;
    private ImageView zhifubao_choose_img;
    private ImageView weixin_choose_img;
    private Button queren;
    // 1为支付宝支付 ，2 为微信支付
    private static int PAY_TYPE = 1;
    private Context context;

    // 支付宝
    // 商户PID
    public String PARTNER = "2088811547722162";
    // 商户收款账号
    public String SELLER = "jpedt@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOGYJXfE4AjUl8JI8sEKrgnd0cYIKKFY4DWVd/1WJ8NDA4xgC1INLx+i9a1d/rfZU7EkfKUwQAyd/W4NAi9zAEbGAz2CfWUKe0YysyLUAehK1VLz1pQ57cCFoU8nvTqVTwBS9zQrF4eJItCnw6wZAvW6wxMc38fBAdBK9fRyybKrAgMBAAECgYBv+sjmaahXWce23eT546Y+KYheR2B7rpUko/v6mZCFQTd6r5YO9ZJlnstaciTk95CuhpEEoi241Zed0AE8slX43LW4OZjc/kPO/+yER/TNlB2bQCm1y+QBIrFKum+IyddROW6KbRMgLN6ZQEVNJJJzDZDgB0y1YkJKvUF0Q7NaGQJBAPr7awRWxs50kTMHEJc5WtECZSTCGxIewViZ+1SZrncm13znPUjFmYqwZvwhGf0UmSpuKVZr0SJHxTd4gQ2VfM0CQQDmGsm9T2KJPA9BUpgny4Xn+ICla2o28WrbApw/yC6AETxCh0QP/WL1BWmbyBzK90PMcl0dqfc98X2+thc6PW1XAkEAs4pbWfVBrrsQi7JIGgX7g3z1IcCBYQsDQQCWHET721y71iwNWRuR5PagFUkQsEyl7QZ1J89lAeLZn/7iQvdnGQJAYD3mETMp4IJpUwYLs+FRMZPjosyJkU9qRQ4Tci31HQWYca8HzbRKdnb8E/cf69h91/4hai3TxmnCEUkx798PYwJBAOpS378VA/y8gAaIXYdBBIj1C/kkOkUIVcAqiz3S/btHICjGvq0mBVbEx8mzMp7qWwjhzFu95wsHAx+TEfnlzn0=";
    // 支付宝公钥
    public static final String RSA_PUBLIC1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private ImageView back, pay_img;
    private SystemUtil sys;
    private String studentPhone;
    private int uid;
    private String qianshu, jiaxiaobianhao;
    private String studentName;
    private int modle, xueshiid;
    private TextView pay_txt;
    private String UU;

    SchoolBaoxian schoolBaoxian;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    qianshu = (float) schoolBaoxian.getBaoxianfei() + "";
                    pay_txt.setText("请支付" + qianshu + "元学车保险费\n(包含50万学车保险)");
                    queren.setEnabled(true);
                    break;
                case 0:
//                    String mobile1 = sys.showPhone();
//                    String passwordString2 = sys.showPwd();
//                    String s = "http://120.26.118.158:8082/user.ashx?do=login2222"
//                            + "&userid=" + mobile1 + "&password=" + passwordString2;
//                    OkGo.post(s)
//                            .tag(this)
//                            .execute(loginCallBack);

                    new SystemUtil(context).saveZhifuR(1);
                    Intent intent = new Intent(Activity_Pay.this,
                            LoginActivity.class);
                    startActivity(intent);
                    Activity_Pay.this.finish();
                    break;
                case 3:
                    break;
            }
        }
    };

    private StringCallback loginCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            // TODO Auto-generated method stub
            Mine m = JsonUtils.parseMine(s);
            sys.saveRemember(1);
            sys.saveOnlyID(m.getOnlyId());
            sys.saveUid(m.getUid());
            sys.savePhone(m.getTelphone());
            sys.saveName(m.getName());
            sys.saveUserHeader(m.getIconPath());

            sys.saveSheng(m.getSheng());
            sys.saveQu(m.getQu());
            sys.saveCity(m.getCityName());
            sys.saveSchool(m.getJiaxiao());
            sys.saveSchool(m.getJiaxiao());
            new SystemUtil(context).saveZhifuR(1);
            Intent intent = new Intent(Activity_Pay.this,
                    HomeActivity.class);
            startActivity(intent);
            Activity_Pay.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pay);
        context = this;
        sys = new SystemUtil(context);
        // -------------------依次为充值钱数、学员手机号、学员uid,需要自己进行指定<注意：下面是我自定义的>-----------------------------------------------------------------------------------------
        Bundle bundle = getIntent().getExtras();
        modle = bundle.getInt("modle");
        studentPhone = sys.showPhone();
        uid = sys.showUid();
        if (modle == 0) {
            // studentPhone = bundle.getString("phone");
            qianshu = "49";
            UU = getOutTradeNo(studentPhone);
        } else if (modle == 1) {
            qianshu = bundle.getString("money");
            xueshiid = bundle.getInt("xueshiid");
            UU = getOutTradeNo(xueshiid + "");
        }
        jiaxiaobianhao = sys.showJiaxiaobianhao();
        studentName = sys.showName();
        // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        initview();
        initdata();
    }


//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		setIntent(intent);
//		msgApi.handleIntent(intent, this);
//	}

    private void initdata() {
        if (modle == 0) {

            Log.d("Activity_Pay", "jiaxiaobianhao=========" + jiaxiaobianhao);
            new NetWorkUtils().getInstance().work(new
                    NetInterface().getInstance().baoxianZhifu(jiaxiaobianhao), zhifuCallBack);
        }
    }

    // private NetWorkCallBack<BaseResult> zhifuCallBack = new
    // NetWorkCallBack<BaseResult>() {
    //
    // @Override
    // public void onComplete(String json) {
    // PARTNER = JsonUtils.parseStr(json, "Column1");
    // SELLER = JsonUtils.parseStr(json, "Column2");
    // }
    // };

    private void initview() {

        TextView titlebar = (TextView) findViewById(R.id.titlebar_tv);
        if (modle == 1) {
            titlebar.setText("计时学费支付");
        } else
            titlebar.setText("保险支付");
        back = (ImageView) findViewById(R.id.titlebar_back);
        zhifubao_rela = (RelativeLayout) findViewById(R.id.zhifubao_rela);
        weixin_rela = (RelativeLayout) findViewById(R.id.weixin_rela);

        zhifubao_choose_img = (ImageView) findViewById(R.id.zhifubao_choose_img);
        weixin_choose_img = (ImageView) findViewById(R.id.weixin_choose_img);

        queren = (Button) findViewById(R.id.add_price_submit);
        queren.setOnClickListener(this);
        zhifubao_rela.setOnClickListener(this);
        weixin_rela.setOnClickListener(this);
        zhifubao_choose_img.setOnClickListener(this);
        weixin_choose_img.setOnClickListener(this);
        back.setOnClickListener(this);
        pay_txt = (TextView) findViewById(R.id.pay_txt);
        pay_img = (ImageView) findViewById(R.id.pay_img);
        if (modle == 1) {
            pay_txt.setText("请支付" + qianshu + "元学车费用\n(计时学费)");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.zhifubao_rela:
            case R.id.zhifubao_choose_img:
                zhifubao_choose_img.setImageResource(R.drawable.mycountok);
                weixin_choose_img.setImageResource(R.drawable.mycountno);
                PAY_TYPE = 1;
                break;
            case R.id.weixin_rela:
            case R.id.weixin_choose_img:
                zhifubao_choose_img.setImageResource(R.drawable.mycountno);
                weixin_choose_img.setImageResource(R.drawable.mycountok);
                PAY_TYPE = 2;
                break;
            case R.id.titlebar_back:
                finish();
                break;
            case R.id.add_price_submit:
                queren.setEnabled(false);
                if (PAY_TYPE == 1) {
                    // 支付宝支付
                    if ("".equals(PARTNER) || "".equals(SELLER)) {
                        Toast.makeText(getApplicationContext(), "正在加载数据请耐心等待", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        pay();
                    }

                } else {
                    queren.setEnabled(true);
                    // 微信支付
                    Toast.makeText(getApplicationContext(), "暂不可用偶！", Toast.LENGTH_SHORT).show();
                }
                // new SystemUtil(context).saveZhifuR(1);
                // Intent intent = new Intent(Activity_Pay.this,
                // Activity_Home.class);
                // startActivity(intent);
                // finish();
                break;

            default:
                break;
        }
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */


    public void pay() {
        // 钱数
        String price = qianshu;
        String orderInfo = null;
        // price
        if (modle == 1) {
            orderInfo = getOrderInfo("dingdanzhifu", studentPhone + "-" + "手机计时订单", price);
        } else
            orderInfo = getOrderInfo("shoujijishi", studentPhone + "-" + "baoxianzhifu", price);

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(Activity_Pay.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
        // 商户网站唯一订单号-----*********************************************************************此处设置学员的uid
//		orderInfo += "&out_trade_no=" + "\"" +UU+ "\"";
        orderInfo += "&out_trade_no=" + "\"" + UU + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://alipayhuidiao.jishipeixun.com/notify_url.aspx"
                + "\"";
        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&show_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo(String uid) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        // Random r = new Random();
        key = key + uid;
        // key = key.substring(0, 15);
        return key;
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    queren.setEnabled(true);
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                        if (modle == 0) {
//						new NetWorkUtils().getInstance().work(
//								new NetInterface().getInstance().successPay(
//										studentPhone), zhifuCallBack);
                            new SystemUtil(context).saveZhifuR(1);
                            Intent intent = new Intent(Activity_Pay.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (modle == 1) {
                            finish();
//						new NetWorkUtils().getInstance().work(
//								new NetInterface().getInstance().successPayByTime(xueshiid), zhifuCallBack2);
                        }
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(getApplicationContext(), "支付结果确认中", Toast.LENGTH_SHORT)
                                    .show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;

                case SDK_CHECK_FLAG:
                    Toast.makeText(getApplicationContext(), "检查结果为：" + msg.obj, Toast.LENGTH_SHORT)
                            .show();
                    queren.setEnabled(true);
                    break;

                default:
                    break;
            }
        }

        ;
    };
    private NetWorkCallBack<BaseResult> zhifuCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {

           Log.d("Activity_Pay", "json==========" + json);
            schoolBaoxian = JsonUtils.parseBaoxian(json);
            Message message = new Message();
            Log.d("Activity_Pay", "是否支付保险===========" + schoolBaoxian.getBaoxian());
            Log.d("Activity_Pay", "支付保险费用========" + schoolBaoxian.getBaoxianfei());
            if (schoolBaoxian.getBaoxian() == 0) {
                handler.sendEmptyMessage(0);
            } else if (schoolBaoxian.getBaoxian() == 1) {
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    };
    private NetWorkCallBack<BaseResult> zhifuCallBack2 = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            int msg = JsonUtils.parseCodeJson(json);
            if (msg != 1) {
                ToastUtil.show(context, "请联系相关人员");
            }
            finish();
        }
    };

}
