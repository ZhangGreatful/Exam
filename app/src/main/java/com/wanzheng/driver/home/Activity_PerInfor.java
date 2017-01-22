package com.wanzheng.driver.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haha.exam.MyApplication;
import com.haha.exam.R;
import com.haha.exam.activity.HomeActivity;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.Mine;
import com.wanzheng.driver.Entity.PersonalInfo;
import com.wanzheng.driver.Entity.phoMsgpwd;
import com.wanzheng.driver.cityselect.City_cn;
import com.wanzheng.driver.cityselect.MyAdapter;
import com.wanzheng.driver.cityselect.MyListItem;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.util.IDCardValidate;
import com.wanzheng.driver.util.ImageUtil;
import com.wanzheng.driver.util.ImageUtils;
import com.wanzheng.driver.util.Keys;
import com.wanzheng.driver.util.MyImgeview;
import com.wanzheng.driver.util.SDCardUtil;
import com.wanzheng.driver.util.SchoolBaoxian;
import com.wanzheng.driver.util.StringUtils;
import com.wanzheng.driver.util.SystemUtil;
import com.wanzheng.driver.util.UIHelper;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Response;

public class Activity_PerInfor extends Activity implements OnClickListener, TextWatcher {

    private Gson gson = new Gson();
    private String onlyId;
    private EditText userName, userSex, userNum;
    private Button submintBtn;
    private String phone;
    private Spinner spSex, spSheng, spShi, spQu, spJiaxiao;
    private int sexID = 0;
    private ArrayList data_list;
    private Context context;
    private MyImgeview take_pic;
    AlertDialog.Builder builder;
    private File mCurrentPhotoFile;
    private String pppPath;
    private Bitmap mBitmap;
    private String base64 = "";
    private int mWidth;
    private DisplayMetrics dm = new DisplayMetrics();
    private LinearLayout up_pic_layout;
    private int schoolBiaohao = 0;


    private IDCardValidate cardValidate;
    private String userSchool;
    private int from;
    private LinearLayout complete_Info, change_info;
    private EditText username, usernum;
    private Spinner usersex;
    private TextView sheng, shi, qu, school;

    /**
     * 倒计时用计数
     */
    private int time = 0;
    private ImageView titlebar_layout_left;
    private Timer timer;
    private CharSequence s;
    private phoMsgpwd phoMsg = new phoMsgpwd();
    private ArrayAdapter arr_adapter1;
    /**
     * 按钮上的文字
     */
    private String btnMsg = "";
    private TextView titleTxt;

    private int clickCheck = 0;// 验证码版本为1 登陆版本为0

    private int login = 0;

    protected static final int UI_LOG_TO_VIEW = 0;

    private TextView loginQuestionTxt;
    private List<MyListItem> list1 = new ArrayList<MyListItem>();
    private ArrayList<String> listString1 = new ArrayList<String>();
    private List<MyListItem> list2 = new ArrayList<MyListItem>();
    private ArrayList<String> listString2 = new ArrayList<String>();
    private List<MyListItem> list3 = new ArrayList<MyListItem>();
    private ArrayList<String> listString3 = new ArrayList<String>();
    private Mine m;
    private City_cn cityS;
    private String province = null;
    private String city = null;
    private String district = null;
    private SystemUtil sys;
    private Handler handler = new Handler() {
        @SuppressLint({"ResourceAsColor", "NewApi"})
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:
                    // 按钮重新可以点击
                    // checkBtn.setEnabled(true);
                    // 设置为原有的text
                    // checkBtn.setText(btnMsg);
                    // timer 取消执行
                    timer.cancel();
                    break;
                case 2:
                    toast("提交成功");
                    // Bundle bundle = new Bundle();
                    // startActivity(new Intent(Activity_PerInfor.this,
                    // LoginActivity.class));
                    finish();
                    break;
                case 3:
                    toast("提交成功");

                    break;
                case 4:
                    toast("提交成功");

//                    new SystemUtil(context).saveJiaxiaobianhao(schoolBiaohao + "");

                    new NetWorkUtils().getInstance().work(new
                            NetInterface().getInstance().baoxianZhifu(schoolBiaohao + ""), zhifuCallBack);

                    break;

                case 5://不需要支付保险

                    String mobile1 = sys.showPhone();
                    String passwordString2 = sys.showPwd();
                    String s = "http://120.26.118.158:8082/user.ashx?do=login2222"
                            + "&userid=" + mobile1 + "&password=" + passwordString2;
                    OkGo.post(s)
                            .tag(this)
                            .execute(loginCallBack);

//                    Intent intent1 = new Intent(Activity_PerInfor.this,
//                            HomeActivity.class);
//                    intent1.putExtra("phone", phone);
//                    startActivity(intent1);
//                    finish();
                    break;
                case 6://需要支付保险
                    Intent intent2 = new Intent(Activity_PerInfor.this,
                            Activity_Pay.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("model", 0);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                    finish();
                default:
                    break;
            }
            sys.saveRegesterState(1);

        }
    };

    private StringCallback loginCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            // TODO Auto-generated method stub
            m = JsonUtils.parseMine(s);
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
            Intent intent1 = new Intent(Activity_PerInfor.this,
                    HomeActivity.class);
            intent1.putExtra("phone", phone);
            startActivity(intent1);
            finish();

        }
    };


    private NetWorkCallBack<BaseResult> zhifuCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            SchoolBaoxian msg = JsonUtils.parseBaoxian(json);
            Message message = new Message();
            Log.d("Activity_Pay", "是否支付保险===========" + msg.getBaoxian());
            Log.d("Activity_Pay", "支付保险费用========" + msg.getBaoxianfei());
            if (msg.getBaoxian() == 0) {
                handler.sendEmptyMessage(5);
            } else if (msg.getBaoxian() == 1) {
                message.what = 6;
                handler.sendMessage(message);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__per_infor);
        cardValidate = new IDCardValidate();
        context = this;
        sys = new SystemUtil(context);
        onlyId = sys.showOnlyID();
        userSchool = sys.showSchool();
        Intent intent = getIntent();
        from = intent.getIntExtra("From", 0);
        initView();
        if (from == 1) {
            int sex = intent.getIntExtra("sex", 0);
            String name = intent.getStringExtra("name");
            String Sheng = intent.getStringExtra("sheng");
            String Shi = intent.getStringExtra("shi");
            String Qu = intent.getStringExtra("qu");
            String num = intent.getStringExtra("id");
            usernum.setText(num);
            username.setText(name);
            if (sex == 0) {
                usersex.setSelection(0, true);
            } else {
                usersex.setSelection(1, true);
            }
            sheng.setText(Sheng);
            shi.setText(Shi);
            qu.setText(Qu);
            school.setText(userSchool);
            ImageLoader.getInstance().displayImage(sys.showUserHeader(), take_pic);
        } else {
            initData();
        }
    }

    private StringCallback changePersonalInfo = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            PersonalInfo info = gson.fromJson(s, PersonalInfo.class);
            ImageLoader.getInstance().displayImage(info.getReturnData().get(0).getTouxiang(), take_pic);
            username.setText(info.getReturnData().get(0).getXingming());
            if (info.getReturnData().get(0).getXingbie() == 0) {
                usersex.setSelection(0, true);
            } else {
                usersex.setSelection(1, true);
            }
            usernum.setText(info.getReturnData().get(0).getShenfenid());
            sheng.setText(info.getReturnData().get(0).getSheng());
            shi.setText(info.getReturnData().get(0).getChengshi());
            qu.setText(info.getReturnData().get(0).getXian());
            school.setText(userSchool);
        }
    };

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private void initView() {
        // TODO Auto-generated method stub

        complete_Info = (LinearLayout) findViewById(R.id.complete_info);
        change_info = (LinearLayout) findViewById(R.id.change_info);


        RelativeLayout titlebar = (RelativeLayout) findViewById(R.id.titlebar);
        titleTxt = (TextView) titlebar.findViewById(R.id.titlebar_tv);
        titleTxt.setText("个人信息");
        titlebar_layout_left = (ImageView) titlebar
                .findViewById(R.id.titlebar_back);
        titlebar_layout_left.setOnClickListener(this);
        userName = (EditText) findViewById(R.id.info_username);
        spSex = (Spinner) findViewById(R.id.info_usersex);
        spJiaxiao = (Spinner) findViewById(R.id.info_jiaxiao);
        spQu = (Spinner) findViewById(R.id.info_qu);
        spSheng = (Spinner) findViewById(R.id.info_sheng);
        spShi = (Spinner) findViewById(R.id.info_shi);
        userNum = (EditText) findViewById(R.id.info_usernum);
        submintBtn = (Button) findViewById(R.id.info_submit);
        take_pic = (MyImgeview) findViewById(R.id.take_pic);
        up_pic_layout = (LinearLayout) findViewById(R.id.up_pic_layout);
        take_pic.setOnClickListener(this);
        submintBtn.setOnClickListener(this);
        // 数据
        data_list = new ArrayList<String>();
        data_list.add("男");
        data_list.add("女");

        username = (EditText) findViewById(R.id.info_username1);
        usersex = (Spinner) findViewById(R.id.info_usersex1);
        usernum = (EditText) findViewById(R.id.info_usernum1);
        sheng = (TextView) findViewById(R.id.info_sheng1);
        shi = (TextView) findViewById(R.id.info_shi1);
        qu = (TextView) findViewById(R.id.info_qu1);
        school = (TextView) findViewById(R.id.info_jiaxiao1);
        if (from == 1) {
            change_info.setVisibility(View.VISIBLE);
            complete_Info.setVisibility(View.GONE);
            submintBtn.setVisibility(View.GONE);
        } else {
            change_info.setVisibility(View.GONE);
            complete_Info.setVisibility(View.VISIBLE);
            submintBtn.setVisibility(View.VISIBLE);
        }

        // 适配器
        ArrayAdapter arr_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data_list);
        // 设置样式
        arr_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spSex.setAdapter(arr_adapter);
        // 添加事件Spinner事件监听
        spSex.setOnItemSelectedListener(new SpinnerSelectedListener());
        spSex.setVisibility(View.VISIBLE);

        usersex.setAdapter(arr_adapter);
        usersex.setOnItemSelectedListener(new SpinnerSelectedListener());

        cityS = new City_cn(this);
        list1 = cityS.initSpinner1();
        MyAdapter myAdapter = new MyAdapter(context, list1);
        spSheng.setAdapter(myAdapter);
        spSheng.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
        spShi.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
        spQu.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
        spJiaxiao.setOnItemSelectedListener(new SpinnerOnSelectedListener4());
        spSheng.setPrompt("省");
        spShi.setPrompt("城市");
        spQu.setPrompt("地区");
        spJiaxiao.setPrompt("驾校");

        username.addTextChangedListener(this);
        usernum.addTextChangedListener(this);
    }

    private void initData() {

        Bundle bundle = getIntent().getExtras();
        phone = getIntent().getExtras().getString("phone");
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels / 3;

//		spSheng.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
//		MyAdapter myAdapter = new MyAdapter(context,list1);
//		spSheng.setAdapter(myAdapter);
//		spSheng.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.info_submit:
                if (from == 1) {

                    try {
                        Log.d("Activity_PerInfor", "---------" + cardValidate.IDCardValidate(usernum.getText().toString()));
                        Log.d("Activity_PerInfor", "---------" + usernum.getText().toString());
                        if (cardValidate.IDCardValidate(usernum.getText().toString()).equals("")) {
                            OkGo.post(WebInterface.update_user_info)
                                    .tag(this)
                                    .params("do", "updateUserInfo")
                                    .params("onlyid", onlyId)
                                    .params("name", username.getText().toString())
                                    .params("gender", sexID)
                                    .params("shenfenid", usernum.getText().toString())
                                    .execute(updateInfo);
                        } else {
                            Toast.makeText(this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    String name = userName.getText().toString();
                    if ("".equals(name)) {
                        toast("请填写姓名");
                        return;
                    }
                    String passwordString2 = userNum.getText().toString();
                    if ("".equals(passwordString2)) {
                        toast("身份证不能为空");
                        return;
                    }
                    if (base64.equals("")) {
                        toast("请上传照片");
                        return;
                    }
                    if (schoolBiaohao == 0) {
                        toast("请选择驾校");
                        return;
                    }
                    try {
                        if (cardValidate.IDCardValidate(passwordString2).equals("")) {
                            submintBtn.setEnabled(false);
                            new NetWorkUtils().getInstance().work(
                                    new NetInterface().getInstance().registerJishi2(phone,
                                            name, passwordString2, sexID, base64, schoolBiaohao),
                                    registerCallBack);
                        } else {
                            Toast.makeText(this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }


                break;
            case R.id.take_pic:
                showDialog();
                break;
            case R.id.titlebar_back:
                finish();
                break;
            default:
                break;
        }
    }

    private StringCallback updateInfo = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            boolean msg = JsonUtils.parseResult(s);
            String str = JsonUtils.parseInfo(s);
            if (msg == true) {
                sys.saveName(username.getText().toString());
                finish();
            } else {
                Toast.makeText(Activity_PerInfor.this, str, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        submintBtn.setVisibility(View.VISIBLE);
    }

    // 使用数组形式操作
    class SpinnerSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            sexID = arg2;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private NetWorkCallBack<BaseResult> registerCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            // TODO Auto-generated method stub
            submintBtn.setEnabled(true);
            Log.d("Activity_PerInfor", "registerCallBack========" + json);
            int msg = JsonUtils.parseisok(json);
            int uid = new SystemUtil(context).showUid();
            if (msg == 1) {
                new NetWorkUtils().getInstance()
                        .work(new NetInterface().getInstance().getPayState(
                                phone), PayStateCallBack);
//                if (uid != -1) {
//                    new NetWorkUtils().getInstance()
//                            .work(new NetInterface().getInstance().getPayState(
//                                    phone), PayStateCallBack);
//                } else {
//                    Message message = new Message();
//                    message.what = 2;
//                    handler.sendMessage(message);
//                }

            } else if (msg == 0) {
                toast("提交失败");
            } else if (msg == 3) {
                toast("请确保上传照片包含脸部");
            } else if (msg == 4) {
                Toast.makeText(Activity_PerInfor.this, "该身份证已经注册过", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private NetWorkCallBack<BaseResult> PayStateCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            // TODO Auto-generated method stub
            Log.d("EEEE", "json=======" + json);
            sys.saveJiaxiaobianhao(schoolBiaohao + "");
            int msg = JsonUtils.parsePaystate(json);
            if (msg == 1) {//支付成功
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            } else if (msg == 0) {//未支付
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            } else if (msg == 2) {

            } else {

            }
        }
    };

    private void toast(final String str) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showDialog() {
        builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        builder.setTitle("选择图片");

        builder.setItems(new String[]{"拍照", "图库", "取消"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:// 拍照上传
                                doTakePhoto();
                                break;
                            case 1:// 从gallery选择
                                doPickPhotoFromGallery();
                                break;
                            case 2:// 取消
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });

        builder.create().show();
    }

    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            if (SDCardUtil.checkSDCard()) {
                UIHelper.PHOTO_DIR.mkdirs();
                mCurrentPhotoFile = new File(UIHelper.PHOTO_DIR,
                        UIHelper.getPhotoFileName());
                pppPath = mCurrentPhotoFile.getPath();
                MyApplication.put(Keys.CAMERA, pppPath);
                // appContext.setProperty(MERCHANT_PIC_PATH, pppPath);
            } else {
                UIHelper.PHOTO_DIR_NO_CARD.mkdirs();
                mCurrentPhotoFile = new File(UIHelper.PHOTO_DIR_NO_CARD,
                        UIHelper.getPhotoFileName());
            }
            final Intent intent = UIHelper.getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, UIHelper.CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "not find photo", Toast.LENGTH_LONG).show();
        }
    }

    protected void doPickPhotoFromGallery() {
        try {
            // Launch picker to choose photo for selected contact
            if (SDCardUtil.checkSDCard()) {
                UIHelper.PHOTO_DIR.mkdirs();
                mCurrentPhotoFile = new File(UIHelper.PHOTO_DIR,
                        UIHelper.getPhotoFileName());
            } else {
                UIHelper.PHOTO_DIR_NO_CARD.mkdirs();
                mCurrentPhotoFile = new File(UIHelper.PHOTO_DIR_NO_CARD,
                        UIHelper.getPhotoFileName());
            }
            final Intent intent = UIHelper
                    .getPhotoPickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, UIHelper.PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "not find photo", Toast.LENGTH_LONG).show();
        }
    }

    private int getTime() {
        time--;
        handler.sendEmptyMessageDelayed(5, 1000);
        return time;
    }

    /***
     * {@inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Ignore failed requests
        if (resultCode != RESULT_OK)
            return;
        String p = "";

        // Intent intent = new Intent(this, FilterPicActivity.class);
        if (requestCode == UIHelper.PHOTO_PICKED_WITH_DATA) {
            ContentResolver cr = this.getContentResolver();
            p = ImageUtils.getUriString(data.getData(), cr);// AbsoluteImagePath(this,
            if (StringUtils.isEmpty(p)) {
                Toast.makeText(context, "找不到此图片", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mBitmap != null) {// 如果不释放的话，不断取图片，将会内存不够
                mBitmap.recycle();
            }

            mBitmap = ImageUtil.parseBitmap(p, mWidth);
            if (mBitmap == null) {
                Toast.makeText(context, "图片过大，易导致内存溢出，请慎重选择",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            take_pic.setImageBitmap(mBitmap);
            base64 = ImageUtil.Bitmap2StrByBase64(mBitmap);
        } else if (requestCode == UIHelper.CAMERA_WITH_DATA) {
            if (StringUtils.isEmpty((String) MyApplication.get(Keys.CAMERA))) {
                Toast.makeText(context, "找不到此图片", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mBitmap != null) {
                mBitmap.recycle();
            }

            mBitmap = ImageUtil.parseBitmap((String) MyApplication.get(Keys.CAMERA),
                    mWidth);
            if (mBitmap == null) {
                Toast.makeText(context, "图片过大，易导致内存溢出，请慎重选择",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            take_pic.setImageBitmap(mBitmap);
            base64 = ImageUtil.Bitmap2StrByBase64(mBitmap);
        }
    }

    class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view,
                                   int position, long id) {
            province = ((MyListItem) adapterView.getItemAtPosition(position))
                    .getName();
            String pcode = ((MyListItem) adapterView
                    .getItemAtPosition(position)).getPcode();

            list2 = cityS.initSpinner2(pcode);
            list3 = cityS.initSpinner3(pcode);
            MyAdapter myAdapter2 = new MyAdapter(context, list2);
            spShi.setAdapter(myAdapter2);
            MyAdapter myAdapter3 = new MyAdapter(context, list3);
            spQu.setAdapter(myAdapter3);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

    class SpinnerOnSelectedListener2 implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view,
                                   int position, long id) {
            city = ((MyListItem) adapterView.getItemAtPosition(position))
                    .getName();
            String pcode = ((MyListItem) adapterView
                    .getItemAtPosition(position)).getPcode();

            list3 = cityS.initSpinner3(pcode);
            MyAdapter myAdapter3 = new MyAdapter(context, list3);
            spQu.setAdapter(myAdapter3);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

    class SpinnerOnSelectedListener3 implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view,
                                   int position, long id) {
            district = ((MyListItem) adapterView.getItemAtPosition(position))
                    .getName();
            getJx();
//			Toast.makeText(context, province + " " + city + " " + district,
//					Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

    class SpinnerOnSelectedListener4 implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view,
                                   int position, long id) {
            String code = ((MyListItem) adapterView.getItemAtPosition(position)).getPcode();
            if (code != null && !code.equals("")) {
                schoolBiaohao = Integer.valueOf(((MyListItem) adapterView.getItemAtPosition(position))
                        .getPcode());
            } else {
                schoolBiaohao = 0;
            }

//            getJx();
//			Toast.makeText(context, schoolBiaohao+ " " ,
//					Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

    private void getJx() {

        String s = "http://120.26.118.158:8082/jiaxiao.ashx?do=queryjiaxiaolist&province=" + province.trim() +
                "&city=" + city.trim() + "&area=" + district.trim();


        OkGo.post(s)
                .tag(this)
                .execute(JxCallBack);
//
//        new NetWorkUtils().getInstance()
//                .work(new NetInterface().getInstance().getJxbyDress(province, city, district), JxCallBack);
    }

    private StringCallback JxCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            Log.d("Activity_PerInfor", "json==========" + s);
            ArrayList<MyListItem> list4 = JsonUtils.parseSchoolList(s);
            if (list4.size() == 1 && list4.get(0).getPcode() == null) {
                list4 = new ArrayList<MyListItem>();
                MyListItem list_my = new MyListItem();
                list_my.setName("无");
                list_my.setPcode("");
                list4.add(list_my);
            }
            MyAdapter myAdapter4 = new MyAdapter(context, list4);
            spJiaxiao.setAdapter(myAdapter4);
        }
    };
}

//    private NetWorkCallBack<BaseResult> JxCallBack = new NetWorkCallBack<BaseResult>() {
//
//        @Override
//        public void onComplete(String json) {
//            // TODO Auto-generated method stub
//            ArrayList<MyListItem> list4 = JsonUtils.parseSchoolList(json);
//            if (list4.size() == 1 && list4.get(0).getPcode() == null) {
//                list4 = new ArrayList<MyListItem>();
//                MyListItem list_my = new MyListItem();
//                list_my.setName("无");
//                list_my.setPcode("");
//                list4.add(list_my);
//            }
//            MyAdapter myAdapter4 = new MyAdapter(context, list4);
//            spJiaxiao.setAdapter(myAdapter4);
//
//        }
//    };
//}
