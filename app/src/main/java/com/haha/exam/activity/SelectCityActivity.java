//package com.haha.exam.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;
//import com.google.gson.Gson;
//import com.haha.exam.R;
//import com.haha.exam.adapter.SortGroupMemberAdapter;
//import com.haha.exam.bean.AllCity;
//import com.haha.exam.bean.GroupMemberBean;
//import com.haha.exam.utils.CharacterParser;
//import com.haha.exam.utils.PinyinComparator;
//import com.haha.exam.utils.SPUtils;
//import com.haha.exam.view.ClearEditText;
//import com.haha.exam.view.SideBar;
//import com.haha.exam.web.WebInterface;
//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.callback.StringCallback;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Response;
//
///**
// * 选择城市
// */
//public class SelectCityActivity extends BaseActivity {
//
//    private Gson gson = new Gson();
//
//    private SPUtils spUtils = new SPUtils();
//    private ListView sortListView;
//    private SideBar sideBar;
//    private TextView dialog;
//    private SortGroupMemberAdapter adapter;
//    private ClearEditText mClearEditText;
//
//    private LinearLayout titleLayout, ll_currentCity;
//    private TextView title;
//    private TextView tvNofriends;
//
//    /**
//     * 上次第一个可见元素，用于滚动时记录标识。
//     */
//    private int lastFirstVisibleItem = -1;
//    /**
//     * 汉字转换成拼音的类
//     */
//    private CharacterParser characterParser;
//    private List<GroupMemberBean> SourceDateList;
//
//    /**
//     * 根据拼音来排列ListView里面的数据类
//     */
//    private PinyinComparator pinyinComparator;
//
//    private AllCity allCity;
//    private List<String> city = new ArrayList<>();
//    private List<String> cityid = new ArrayList<>();
//    private TextView currentCity;
//    private String curCity;
//    private String currentCityid;
//    //声明AMapLocationClient类对象，定位发起端
//    private AMapLocationClient mLocationClient = null;
//    //声明mLocationOption对象，定位参数
//    public AMapLocationClientOption mLocationOption = null;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        allCity = new AllCity();
//        setTitle("选择城市");
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//        initLocation();
//        initData();
//
//        iniView();
//
//    }
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_select_city;
//    }
//
//
//    private void initData() {
//        OkGo.post(WebInterface.all_citys)
//                .tag(this)
//                .execute(myCallBack);
//    }
//
//
//    private void initLocation() {
//        //初始化AMapLocationClientOption对象
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
//        //设置是否强制刷新WIFI，默认为true，强制刷新。
//        mLocationOption.setWifiActiveScan(false);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
//    }
//
//    /**
//     * 声明定位回调监听器
//     */
//    public AMapLocationListener mLocationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation amapLocation) {
//            if (amapLocation != null) {
//                if (amapLocation.getErrorCode() == 0) {
//                    currentCity.setText(amapLocation.getCity());
//                    curCity = amapLocation.getCity();
//                    spUtils.put(SelectCityActivity.this, "cityname", curCity);
//                } else {
//                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                    Log.e("AmapError", "location Error, ErrCode:"
//                            + amapLocation.getErrorCode() + ", errInfo:"
//                            + amapLocation.getErrorInfo());
//                }
//            }
//        }
//    };
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mLocationClient.stopLocation();//停止定位
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
//        OkGo.getInstance().cancelTag(this);
//
//    }
//
//    private StringCallback myCallBack = new StringCallback() {
//        @Override
//        public void onSuccess(String s, Call call, Response response) {
//            allCity = gson.fromJson(s, AllCity.class);
//            getCityid(allCity);
//            System.out.println("citysize=========" + city.size());
//            System.out.println("------------" + allCity.getMsg().size());
//            String[] cityname = city.toArray(new String[city.size()]);
//            String[] id = cityid.toArray(new String[cityid.size()]);
//            System.out.println("cityname=========" + Arrays.toString(cityname));
//            SourceDateList = filledData(cityname, id);
//            Collections.sort(SourceDateList, pinyinComparator);
//            adapter = new SortGroupMemberAdapter(SelectCityActivity.this, SourceDateList);
//            sortListView.setAdapter(adapter);
//
//            sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem,
//                                     int visibleItemCount, int totalItemCount) {
//                    int section = getSectionForPosition(firstVisibleItem);
//                    int nextSection = getSectionForPosition(firstVisibleItem + 1);
//                    int nextSecPosition = getPositionForSection(+nextSection);
//                    if (firstVisibleItem != lastFirstVisibleItem) {
//                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
//                                .getLayoutParams();
//                        params.topMargin = 0;
//                        titleLayout.setLayoutParams(params);
//                        titleLayout.setBackgroundColor(getResources().getColor(R.color.mine_head_background));
//                        title.setText(SourceDateList.get(
//                                getPositionForSection(section)).getSortLetters());
//                    }
//                    if (nextSecPosition == firstVisibleItem + 1) {
//                        View childView = view.getChildAt(0);
//                        if (childView != null) {
//                            int titleHeight = titleLayout.getHeight();
//                            int bottom = childView.getBottom();
//                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
//                                    .getLayoutParams();
//                            if (bottom < titleHeight) {
//                                float pushedDistance = bottom - titleHeight;
//                                params.topMargin = (int) pushedDistance;
//                                titleLayout.setBackgroundColor(getResources().getColor(R.color.mine_head_background));
//                                titleLayout.setLayoutParams(params);
//                            } else {
//                                if (params.topMargin != 0) {
//                                    params.topMargin = 0;
//                                    titleLayout.setLayoutParams(params);
//                                }
//                            }
//                        }
//                    }
//                    lastFirstVisibleItem = firstVisibleItem;
//                }
//            });
//            // 根据输入框输入值的改变来过滤搜索
//            mClearEditText.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before,
//                                          int count) {
//                    // 这个时候不需要挤压效果 就把他隐藏掉
//                    titleLayout.setVisibility(View.GONE);
//                    // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                    filterData(s.toString());
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count,
//                                              int after) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
//    };
//
//    private void getCityid(AllCity allCity) {
//        for (int i = 0; i < allCity.getMsg().size(); i++) {
//            city.add(allCity.getMsg().get(i).getCityname());
//            cityid.add(allCity.getMsg().get(i).getCityid());
//            if (allCity.getMsg().get(i).getCityname().equals(curCity)) {
//                currentCityid = allCity.getMsg().get(i).getCityid();
//                spUtils.put(SelectCityActivity.this, "cityid", currentCityid);
//                System.out.println("currentCityid=====" + currentCityid);
//            }
//
//        }
//    }
//
//    private void iniView() {
//        currentCity = (TextView) findViewById(R.id.current_city);
//        ll_currentCity = (LinearLayout) findViewById(R.id.ll_currentCity);
//        titleLayout = (LinearLayout) findViewById(R.id.title_layout);
//        title = (TextView) this.findViewById(R.id.title_layout_catalog);
//        tvNofriends = (TextView) this
//                .findViewById(R.id.title_layout_no_friends);
//        // 实例化汉字转拼音类
//        characterParser = CharacterParser.getInstance();
//
//        pinyinComparator = new PinyinComparator();
//
//        sideBar = (SideBar) findViewById(R.id.sidrbar);
//        dialog = (TextView) findViewById(R.id.dialog);
//        sideBar.setTextView(dialog);
//        ll_currentCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SelectCityActivity.this, SelectSchoolActivity.class);
//                if (currentCityid != null) {
//                    intent.putExtra("cityid", currentCityid);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    initData();
//                }
//            }
//        });
//
//        // 设置右侧触摸监听
//        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
//
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                // 该字母首次出现的位置
//                int position = adapter.getPositionForSection(s.charAt(0));
//                if (position != -1) {
//                    sortListView.setSelection(position);
//                }
//
//            }
//        });
//        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
//        sortListView = (ListView) findViewById(R.id.country_lvcountry);
//        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                Intent intent = new Intent(SelectCityActivity.this, SelectSchoolActivity.class);
//                intent.putExtra("cityid",
//                        ((GroupMemberBean) adapter.getItem(position)).getCityid());
//                spUtils.put(SelectCityActivity.this, "cityname", ((GroupMemberBean) adapter.getItem(position)).getName());
//                spUtils.put(SelectCityActivity.this, "cityid", ((GroupMemberBean) adapter.getItem(position)).getCityid());
//                startActivity(intent);
//                finish();
//
//            }
//        });
//
////        SourceDateList = filledData(getResources().getStringArray(R.array.date));
//
//        // 根据a-z进行排序源数据
//
//    }
//
//    /**
//     * 为ListView填充数据
//     *
//     * @param date
//     * @return
//     */
//    private List<GroupMemberBean> filledData(String[] date, String[] id) {
//        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
//
//        for (int i = 0; i < date.length; i++) {
//            GroupMemberBean sortModel = new GroupMemberBean();
//            sortModel.setName(date[i]);
//            sortModel.setCityid(id[i]);
//            // 汉字转换成拼音
//            String pinyin = characterParser.getSelling(date[i]);
//            String sortString = pinyin.substring(0, 1).toUpperCase();
//
//            // 正则表达式，判断首字母是否是英文字母
//
//            if (sortString.matches("[A-Z]")) {
//                sortModel.setSortLetters(sortString.toUpperCase());
//            } else {
//                sortModel.setSortLetters("#");
//            }
//
//            mSortList.add(sortModel);
//        }
//        return mSortList;
//
//    }
//
//    /**
//     * 根据输入框中的值来过滤数据并更新ListView
//     *
//     * @param filterStr
//     */
//    private void filterData(String filterStr) {
//        List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();
//
//        if (TextUtils.isEmpty(filterStr)) {
//            filterDateList = SourceDateList;
//            tvNofriends.setVisibility(View.GONE);
//        } else {
//            filterDateList.clear();
//            for (GroupMemberBean sortModel : SourceDateList) {
//                String name = sortModel.getName();
//                if (name.indexOf(filterStr.toString()) != -1
//                        || characterParser.getSelling(name).startsWith(
//                        filterStr.toString())) {
//                    filterDateList.add(sortModel);
//                }
//            }
//        }
//
//        // 根据a-z进行排序
//        Collections.sort(filterDateList, pinyinComparator);
//        adapter.updateListView(filterDateList);
//        if (filterDateList.size() == 0) {
//            tvNofriends.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public Object[] getSections() {
//        return null;
//    }
//
//    /**
//     * 根据ListView的当前位置获取分类的首字母的Char ascii值
//     */
//    public int getSectionForPosition(int position) {
//        return SourceDateList.get(position).getSortLetters().charAt(0);
//    }
//
//    /**
//     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
//     */
//    public int getPositionForSection(int section) {
//        for (int i = 0; i < SourceDateList.size(); i++) {
//            String sortStr = SourceDateList.get(i).getSortLetters();
//            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//
//}
