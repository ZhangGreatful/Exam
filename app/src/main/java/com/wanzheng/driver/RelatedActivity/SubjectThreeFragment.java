package com.wanzheng.driver.RelatedActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.Practice;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.Entity.OrdershowEntity;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetInterface;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.network.NetWorkUtils;
import com.wanzheng.driver.order.CustomListView;
import com.wanzheng.driver.util.Keys;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/10.
 */

public class SubjectThreeFragment extends Fragment {
    private Context context;
    private final static int pagesize = 10;// 总页数
    private int position;
    int uid;
    private String signid;

    private ImageView noOrderImg;

    public static CustomListView listView, listView1;
    private static final int LOAD_DATA_FINISH = 10;

    private List<Practice.MsgBean.ReturnDataBean> praticeList3;
    private Gson gson = new Gson();

    private int pageCurrent3 = 1;// 当前页
    private int pageCount3;// 总页数
    private int pageCurrentPractice3 = 1;// 当前页
    private int pageCountPractice3;// 总页数

    private ArrayList<Period> orderList3;
    private ExamAdapter examAdapter;
    private PracticeAdapter practiceAdapter;
    private PeriodAdapter orderAdapter;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {

                case LOAD_DATA_FINISH:
                    listView.onLoadMoreCompleteAndRemoveFooter(); // 加载更多完成
                    break;
                case 2:
                    OrdershowEntity order = null;
                    order = (OrdershowEntity) msg.getData().getSerializable(Keys.ORDER);
                    Intent intent = new Intent(context, CurrentOrderActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(Keys.ORDER, order);
                    intent.putExtras(b);
                    context.startActivity(intent);
                    if (order.getMoneyState() == 1) {
                        ((Activity) context).finish();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_order_list, container,
                false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    /**
     * Fragment当前状态是否可见
     */
//    protected boolean isVisible;
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if(getUserVisibleHint()) {
//            isVisible = true;
//            if (praticeList3==null||praticeList3.size()==0){
//                initData();
//            }
//
//        }
//    }
    private void initView() {
        noOrderImg = (ImageView) getView().findViewById(R.id.no_order);
        listView = (CustomListView) getView().findViewById(R.id.list);
        listView1 = (CustomListView) getView().findViewById(R.id.list1);
        listView.onRefreshComplete();
        listView.setCanRefresh(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                    long arg3) {

            }
        });
        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                loadData(1);
            }
        });

        listView1.onRefreshComplete();
        listView1.setCanRefresh(false);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                    long arg3) {

            }
        });
        listView1.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                loadData(1);
            }
        });

    }

    public void loadData(final int type) {
        new Thread() {
            @Override
            public void run() {
                switch (type) {
                    case 0:// 下拉刷新 暂不适用
                        // refresh = true;
                        // int count = pageCurrent * NumUtil.NUM20;
                        pageCurrent3 = 1;// 如果下拉刷新就从新加载
                        initData();
                        break;
                    case 1:
                        if (pageCurrentPractice3 <= pageCountPractice3) {
                            OkGo.post(WebInterface.get_record_list)
                                    .tag(this)
                                    .params("signid", signid)
                                    .params("subject", 3)
                                    .params("type", 3)
                                    .params("pagesize", pagesize)
                                    .params("page", pageCurrentPractice3)
                                    .execute(subjectThreeCallBace);
//                                    new NetWorkUtils().getInstance().work(
//                                            new NetInterface().getInstance().queryStudentOrderCurr(
//                                                    uid, 1, pageCurrent0),
//                                            orderCurrenCallBack2);
                            pageCurrentPractice3++;
                        } else {// 如果是首页且数据不足一页 或者已经是最后一页
                            Message _Msg = mHandler
                                    .obtainMessage(LOAD_DATA_FINISH);
                            mHandler.sendMessage(_Msg);
                        }

                        if (pageCurrent3 <= pageCount3) {
                            new NetWorkUtils().getInstance().work(
                                    new NetInterface().getInstance().queryOrderStudy(
                                            uid, 3, pagesize, pageCurrent3),
                                    orderCurrenCallBack3);
                            pageCurrent3++;
                        } else {// 如果是首页且数据不足一页 或者已经是最后一页
                            Message _Msg = mHandler
                                    .obtainMessage(LOAD_DATA_FINISH);
                            mHandler.sendMessage(_Msg);
                        }
                        break;

                }
            }
        }.start();

    }

    private void initData() {
        // TODO Auto-generated method stub
        //startProgressDialog();


        SystemUtil su = new SystemUtil(getActivity());
        uid = su.showUid();
        signid = su.showOnlyID();
        if (uid != -1) {
            //  科目三理论列表
            OkGo.post(WebInterface.get_record_list)
                    .tag(this)
                    .params("signid", signid)
                    .params("subject", 3)
                    .params("type", 3)
                    .params("pagesize", pagesize)
                    .params("page", pageCurrentPractice3)
                    .execute(subjectThreeCallBace);
            //  科目三实操列表
            new NetWorkUtils().getInstance().work(
                    new NetInterface().getInstance().queryOrderStudy(
                            uid, 3, pagesize, pageCurrent3),
                    orderCurrenCallBack3);

        }

    }

    private StringCallback subjectThreeCallBace = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                pagingTheory3(s);
            }
        }
    };
    private NetWorkCallBack<BaseResult> orderCurrenCallBack3 = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            paging3(json);
        }
    };

    private void pagingTheory3(String json) {
        praticeList3 = new ArrayList<>();
        Practice practice = gson.fromJson(json, Practice.class);

        if (pageCurrentPractice3 == 1) {// 首次加载
            pageCountPractice3 = practice.getMsg().getPageCount();

            if (pageCountPractice3 >= pageCurrentPractice3) {
                pageCurrentPractice3++;
            } else if (pageCountPractice3 == 0) {
                return;
            }

            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
                praticeList3.addAll(firstList);
                initListViewTheory3();
            }

        } else {

            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
                practiceAdapter.AddFooterItem(firstList);
            }

//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
//            praticeList3.addAll(firstList);
//            practiceAdapter.notifyDataSetChanged();
            if (pageCurrentPractice3 >pageCountPractice3) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();

        }
    }

    // 科目三 分页
    private void paging3(String json) {
        orderList3 = new ArrayList<>();
        if (pageCurrent3 == 1) {// 首次加载
            pageCount3 = JsonUtils.parsePageCount(json);

            if (pageCount3 >= pageCurrent3) {
                pageCurrent3++;
            } else if (pageCount3 == 0) {
                return;
            }
            ArrayList<Period> firstList = JsonUtils.parseStudyorder(json);
            orderList3.addAll(firstList);
            initListView3();
        } else {

            ArrayList<Period> otherList = JsonUtils.parseStudyorder(json);
//            orderList3.addAll(otherList);
//            orderAdapter.notifyDataSetChanged();
            orderAdapter.AddFooterItem(otherList);
            if (pageCurrent3 > pageCount3) {// 如果没有更多页数
                listView1.setCanLoadMore(false);
            }
            listView1.onLoadMoreComplete();
        }
    }

    //      初始化科目三listview
    private void initListView3() {
        if (orderList3 != null && orderList3.size() != 0) {
            orderAdapter = new PeriodAdapter(getActivity(), orderList3);
            listView1.setAdapter(orderAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    private void initListViewTheory3() {
        if (praticeList3 != null && praticeList3.size() != 0) {
            practiceAdapter = new PracticeAdapter(getActivity(), praticeList3);
            listView.setAdapter(practiceAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }
}
