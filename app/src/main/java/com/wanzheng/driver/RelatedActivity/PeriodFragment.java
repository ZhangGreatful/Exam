package com.wanzheng.driver.RelatedActivity;


import android.annotation.SuppressLint;
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
import android.widget.AdapterView.OnItemClickListener;
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
import com.wanzheng.driver.order.CustomListView.OnLoadMoreListener;
import com.wanzheng.driver.util.Keys;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */
@SuppressLint("ValidFragment")
public class PeriodFragment extends Fragment {

    private String signid;
    public static CustomListView listView, listView1;
    private PeriodAdapter orderAdapter;
    private ExamAdapter examAdapter;
    private PracticeAdapter practiceAdapter;
    private ImageView noOrderImg;

    private List<Practice.MsgBean.ReturnDataBean> praticeList0;
    private List<Practice.MsgBean.ReturnDataBean> praticeList1;
    private List<Practice.MsgBean.ReturnDataBean> praticeList2;
    private List<Practice.MsgBean.ReturnDataBean> praticeList3;
    private List<Practice.MsgBean.ReturnDataBean> praticeList4;

    private Gson gson = new Gson();

    private List<Practice.MsgBean.ReturnDataBean> ExamList1;
    private List<Practice.MsgBean.ReturnDataBean> ExamList4;
    private ArrayList<Period> orderList;
    private ArrayList<Period> orderList2;
    private ArrayList<Period> orderList3;

    private Context context;
    private final static int pagesize = 10;// 总页数
    private int position;
    int uid;
    private int pageCurrent0 = 1;// 当前页
    private int pageCount0;// 总页数

    private int pageCurrentExam0=1;
    private int pageCountExam0;// 总页数

    private int pageCurrent = 1;// 当前页
    private int pageCount;// 总页数

    private int pageCurrent2 = 1;// 当前页
    private int pageCount2;// 总页数
    private int pageCurrentPractice2 = 1;// 当前页
    private int pageCountPractice2;// 总页数

    private int pageCurrent3 = 1;// 当前页
    private int pageCount3;// 总页数
    private int pageCurrentPractice3 = 1;// 当前页
    private int pageCountPractice3;// 总页数

    private int pageCurrent4 = 1;// 当前页
    private int pageCount4;// 总页数
    private static final int LOAD_DATA_FINISH = 10;

    @SuppressWarnings("unchecked")
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

    public PeriodFragment() {

    }

    public PeriodFragment(int position, Context context) {
        super();
        this.position = position;
        this.context = context;
    }
    private void initData() {
        // TODO Auto-generated method stub
        //startProgressDialog();
        pageCurrent = 1;
        pageCurrent2 = 1;
        pageCurrent3 = 1;
        pageCurrent4 = 1;

        praticeList0 = new ArrayList<>();
        praticeList1 = new ArrayList<>();
        praticeList2 = new ArrayList<>();
        praticeList3 = new ArrayList<>();
        praticeList4 = new ArrayList<>();

        ExamList1 = new ArrayList<>();
        ExamList4 = new ArrayList<>();
        orderList = new ArrayList<Period>();
        orderList2 = new ArrayList<Period>();
        orderList3 = new ArrayList<Period>();
        SystemUtil su = new SystemUtil(getActivity());
        uid = su.showUid();
        signid = su.showOnlyID();
        if (uid != -1) {

            switch (position) {
                case 0:
                    OkGo.post(WebInterface.get_record_list)
                            .tag(this)
                            .params("signid", signid)
                            .params("subject", 1)
                            .params("type", 2)
                            .params("pagesize", pagesize)
                            .params("page", pageCurrent0)
                            .execute(subjectOneCallBace);
                    OkGo.post(WebInterface.get_record_list)
                            .tag(this)
                            .params("signid", signid)
                            .params("subject", 1)
                            .params("type", 3)
                            .params("pagesize", pagesize)
                            .params("page", pageCurrentExam0)
                            .execute(subjectOneCallBaceExam);
                    break;
                case 1:
                    break;
                case 2:
                    //  科目二理论列表
                    OkGo.post(WebInterface.get_record_list)
                            .tag(this)
                            .params("signid", signid)
                            .params("subject", 2)
                            .params("type", 3)
                            .params("pagesize", pagesize)
                            .params("page", pageCurrentPractice2)
                            .execute(subjectTwoCallBace);
                    //  科目二实操列表
                    new NetWorkUtils().getInstance().work(
                            new NetInterface().getInstance().queryOrderStudy(
                                    uid, 2, pagesize, pageCurrent2),
                            orderCurrenCallBack2);
                    break;
                case 3:
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
                    break;
                case 4:
                    OkGo.post(WebInterface.get_record_list)
                            .tag(this)
                            .params("signid", signid)
                            .params("subject", 4)
                            .params("type", 2)
                            .params("pagesize", pagesize)
                            .params("page", pageCurrent4)
                            .execute(subjectFourCallBace);
                    break;


                default:
                    break;
            }

        } else {
            //stopProgressDialog();
            //Toast.makeText(getActivity(), "您还没有登录，请先登录", 1).show();
            // noLogin();
        }

    }

    //      科一视频学时列表
    private StringCallback subjectOneCallBace = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                paging0(s);
            }
        }
    };
    //    科一考试订单列表
    private StringCallback subjectOneCallBaceExam = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                pagingExam0(s);
            }
        }
    };
    private StringCallback subjectTwoCallBace = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                pagingTheory2(s);
            }
        }
    };
    private StringCallback subjectThreeCallBace = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                pagingTheory3(s);
            }
        }
    };
    private StringCallback subjectFourCallBace = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                pagingExam4(s);
            }
        }
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

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView() {
        noOrderImg = (ImageView) getView().findViewById(R.id.no_order);
        listView = (CustomListView) getView().findViewById(R.id.list);
        listView1 = (CustomListView) getView().findViewById(R.id.list1);
        listView.onRefreshComplete();
        listView.setCanRefresh(false);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                    long arg3) {

            }
        });
        listView.setOnLoadListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                loadData(1);
            }
        });

        listView1.onRefreshComplete();
        listView1.setCanRefresh(false);
        listView1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                    long arg3) {

            }
        });
        listView1.setOnLoadListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                loadData(1);
            }
        });

    }

    private NetWorkCallBack<BaseResult> orderDeilCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            ArrayList<OrdershowEntity> order1 = JsonUtils.parsePayorder(json);
            Message msg = new Message();
            msg.what = 2;
            Bundle bb = new Bundle();
            bb.putSerializable(Keys.ORDER, order1.get(0));
            msg.setData(bb);
            mHandler.sendMessage(msg);
        }
    };

    public void loadData(final int type) {
        new Thread() {
            @Override
            public void run() {
                switch (type) {
                    case 0:// 下拉刷新 暂不适用
                        // refresh = true;
                        // int count = pageCurrent * NumUtil.NUM20;
                        pageCurrent = 1;// 如果下拉刷新就从新加载
                        initData();
                        break;
                    case 1:
                        switch (position) {
                            case 0:
                                if (pageCurrent0 <= pageCount0) {
                                    OkGo.post(WebInterface.get_record_list)
                                            .tag(this)
                                            .params("signid", signid)
                                            .params("subject", 1)
                                            .params("type", 3)
                                            .params("pagesize", pagesize)
                                            .params("page", pageCurrent0)
                                            .execute(subjectOneCallBace);
//                                    new NetWorkUtils().getInstance().work(
//                                            new NetInterface().getInstance().queryStudentOrderCurr(
//                                                    uid, 1, pageCurrent0),
//                                            orderCurrenCallBack2);
                                    pageCurrent0++;
                                } else {// 如果是首页且数据不足一页 或者已经是最后一页
                                    Message _Msg = mHandler
                                            .obtainMessage(LOAD_DATA_FINISH);
                                    mHandler.sendMessage(_Msg);
                                }
                                break;
                            case 1:

                                break;
                            case 2:
                                if (pageCurrent0 <= pageCount0) {
                                    OkGo.post(WebInterface.get_record_list)
                                            .tag(this)
                                            .params("signid", signid)
                                            .params("subject", 2)
                                            .params("type", 3)
                                            .params("pagesize", pagesize)
                                            .params("page", pageCurrent0)
                                            .execute(subjectOneCallBace);
//                                    new NetWorkUtils().getInstance().work(
//                                            new NetInterface().getInstance().queryStudentOrderCurr(
//                                                    uid, 1, pageCurrent0),
//                                            orderCurrenCallBack2);
                                    pageCurrent0++;
                                } else {// 如果是首页且数据不足一页 或者已经是最后一页
                                    Message _Msg = mHandler
                                            .obtainMessage(LOAD_DATA_FINISH);
                                    mHandler.sendMessage(_Msg);
                                }


                                if (pageCurrent2 <= pageCount2) {
                                    new NetWorkUtils().getInstance().work(
                                            new NetInterface().getInstance().queryStudentOrderCurr(
                                                    uid, 1, pageCurrent2),
                                            orderCurrenCallBack2);
                                    pageCurrent2++;
                                } else {// 如果是首页且数据不足一页 或者已经是最后一页
                                    Message _Msg = mHandler
                                            .obtainMessage(LOAD_DATA_FINISH);
                                    mHandler.sendMessage(_Msg);
                                }

                                break;
                            case 3:

                                if (pageCurrent0 <= pageCount0) {
                                    OkGo.post(WebInterface.get_record_list)
                                            .tag(this)
                                            .params("signid", signid)
                                            .params("subject", 3)
                                            .params("type", 3)
                                            .params("pagesize", pagesize)
                                            .params("page", pageCurrent0)
                                            .execute(subjectOneCallBace);
//                                    new NetWorkUtils().getInstance().work(
//                                            new NetInterface().getInstance().queryStudentOrderCurr(
//                                                    uid, 1, pageCurrent0),
//                                            orderCurrenCallBack2);
                                    pageCurrent0++;
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
                            case 4:
                                if (pageCurrent0 <= pageCount0) {
                                    OkGo.post(WebInterface.get_record_list)
                                            .tag(this)
                                            .params("signid", signid)
                                            .params("subject", 4)
                                            .params("type", 3)
                                            .params("pagesize", pagesize)
                                            .params("page", pageCurrent0)
                                            .execute(subjectOneCallBace);
//                                    new NetWorkUtils().getInstance().work(
//                                            new NetInterface().getInstance().queryStudentOrderCurr(
//                                                    uid, 1, pageCurrent0),
//                                            orderCurrenCallBack2);
                                    pageCurrent0++;
                                } else {// 如果是首页且数据不足一页 或者已经是最后一页
                                    Message _Msg = mHandler
                                            .obtainMessage(LOAD_DATA_FINISH);
                                    mHandler.sendMessage(_Msg);
                                }


                                break;
                            default:
                                break;
                        }

                }
            }
        }.start();

    }

    //      初始化科目一listview
    private void initListView0() {
        if (praticeList0 != null && praticeList0.size() != 0) {
            practiceAdapter = new PracticeAdapter(getActivity(), praticeList0);
            listView1.setAdapter(practiceAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    private void initListViewExam0() {
        if (ExamList1 != null && ExamList1.size() != 0) {
            examAdapter = new ExamAdapter(getActivity(), ExamList1);
            listView.setAdapter(examAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    //      初始化科目四listview
    private void initListViewExam4() {
        if (ExamList4 != null && ExamList4.size() != 0) {
            examAdapter = new ExamAdapter(getActivity(), ExamList4);
            listView.setAdapter(examAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    //          初始化模拟器listview
    private void initListView() {
        if (orderList != null && orderList.size() != 0) {
            orderAdapter = new PeriodAdapter(getActivity(), orderList);
            listView.setAdapter(orderAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    //      初始化科目二listview
    private void initListView2() {
        if (orderList2 != null && orderList2.size() != 0) {
            orderAdapter = new PeriodAdapter(getActivity(), orderList2);
            listView.setAdapter(orderAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    private void initListViewTheory2() {
        if (praticeList2 != null && praticeList2.size() != 0) {
            practiceAdapter = new PracticeAdapter(getActivity(), praticeList2);
            listView1.setAdapter(practiceAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    //      初始化科目三listview
    private void initListView3() {
        if (orderList3 != null && orderList3.size() != 0) {
            orderAdapter = new PeriodAdapter(getActivity(), orderList3);
            listView.setAdapter(orderAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    private void initListViewTheory3() {
        if (praticeList3 != null && praticeList3.size() != 0) {
            practiceAdapter = new PracticeAdapter(getActivity(), praticeList3);
            listView1.setAdapter(practiceAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }


    private NetWorkCallBack<BaseResult> orderCurrenCallBack = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            paging(json);
            // orderList = JsonUtils.parseOrderHistory(json);

        }
    };
    private NetWorkCallBack<BaseResult> orderCurrenCallBack2 = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            paging2(json);

        }
    };

    private NetWorkCallBack<BaseResult> orderCurrenCallBack3 = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {
            paging3(json);
        }
    };

    // 科目一分页
    private void paging0(String json) {
        Practice practice = gson.fromJson(json, Practice.class);
        if (pageCurrent0 == 1) {// 首次加载
            pageCount0 = practice.getMsg().getPageCount();

            if (pageCount0 >= pageCurrent0) {
                pageCurrent0++;
            } else if (pageCount0 == 0) {
                return;
            }
            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            praticeList0.addAll(firstList);
            initListView0();
        } else {
            List<Practice.MsgBean.ReturnDataBean> otherList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            praticeList0.addAll(otherList);
            practiceAdapter.notifyDataSetChanged();
            if (pageCurrent0 >= pageCount0) {// 如果没有更多页数
                listView1.setCanLoadMore(false);
            }
            listView1.onLoadMoreComplete();

        }
    }

    private void pagingExam0(String json) {
        Practice practice = gson.fromJson(json, Practice.class);
        if (pageCurrentExam0 == 1) {// 首次加载
            pageCountExam0 = practice.getMsg().getPageCount();

            if (pageCountExam0 >= pageCurrentExam0) {
                pageCurrentExam0++;
            } else if (pageCountExam0 == 0) {
                return;
            }
            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            ExamList1.addAll(firstList);
            initListViewExam0();
        } else {
            List<Practice.MsgBean.ReturnDataBean> otherList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            ExamList1.addAll(otherList);
            examAdapter.notifyDataSetChanged();
            if (pageCurrentExam0 >= pageCountExam0) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();

        }
    }

    // 科目四分页
    private void pagingExam4(String json) {
        Practice practice = gson.fromJson(json, Practice.class);
        if (pageCurrent4 == 1) {// 首次加载
//            pageCount4 = JsonUtils.parsePageCount(json);
            pageCount4 = practice.getMsg().getPageCount();
            if (pageCount4 >= pageCurrent4) {
                pageCurrent4++;
            } else if (pageCount4 == 0) {
                return;
            }
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
            ExamList4.addAll(firstList);
            initListViewExam4();
        } else {
//            ArrayList<Practice> otherList = JsonUtils.parsePracticeorder(json);
            List<Practice.MsgBean.ReturnDataBean> otherList = practice.getMsg().getReturnData();
            ExamList4.addAll(otherList);
            examAdapter.notifyDataSetChanged();
            if (pageCurrent4 >= pageCount4) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();

        }
    }

    // 模拟器 分页
    private void paging(String json) {
        if (pageCurrent == 1) {// 首次加载
            pageCount = JsonUtils.parsePageCount(json);

            if (pageCount >= pageCurrent) {
                pageCurrent++;
            } else if (pageCount == 0) {
                return;
            }
            ArrayList<Period> firstList = JsonUtils.parseStudyorder(json);
            orderList.addAll(firstList);
            initListView();
        } else {
            ArrayList<Period> otherList = JsonUtils.parseStudyorder(json);
            orderList.addAll(otherList);
            orderAdapter.notifyDataSetChanged();
            if (pageCurrent >= pageCount) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();

        }
    }

    //  科目二 分页
    private void paging2(String json) {
        if (pageCurrent2 == 1) {// 首次加载
            pageCount2 = JsonUtils.parsePageCount(json);

            if (pageCount2 >= pageCurrent2) {
                pageCurrent2++;
            } else if (pageCount2 == 0) {
                return;
            }
            ArrayList<Period> firstList = JsonUtils.parseStudyorder(json);
            orderList2.addAll(firstList);
            initListView2();
        } else {

            ArrayList<Period> otherList = JsonUtils.parseStudyorder(json);
            orderList2.addAll(otherList);
            orderAdapter.notifyDataSetChanged();
            if (pageCurrent2 >= pageCount2) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();
        }
    }

    private void pagingTheory2(String json) {

        Practice practice = gson.fromJson(json, Practice.class);

        if (pageCurrentPractice2 == 1) {// 首次加载
            pageCountPractice2 = practice.getMsg().getPageCount();

            if (pageCountPractice2 >= pageCurrentPractice2) {
                pageCurrentPractice2++;
            } else if (pageCountPractice2 == 0) {
                return;
            }

            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            praticeList2.addAll(firstList);
            initListViewTheory2();
        } else {
            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            praticeList2.addAll(firstList);
            practiceAdapter.notifyDataSetChanged();
            if (pageCurrentPractice2 >= pageCountPractice2) {// 如果没有更多页数
                listView1.setCanLoadMore(false);
            }
            listView1.onLoadMoreComplete();

        }
    }

    private void pagingTheory3(String json) {

        Practice practice = gson.fromJson(json, Practice.class);

        if (pageCurrentPractice3 == 1) {// 首次加载
            pageCountPractice3 = practice.getMsg().getPageCount();

            if (pageCountPractice3 >= pageCurrentPractice3) {
                pageCurrentPractice3++;
            } else if (pageCountPractice3 == 0) {
                return;
            }

            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            praticeList3.addAll(firstList);
            initListViewTheory3();
        } else {
            List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
            praticeList3.addAll(firstList);
            practiceAdapter.notifyDataSetChanged();
            if (pageCurrentPractice3 >= pageCountPractice3) {// 如果没有更多页数
                listView1.setCanLoadMore(false);
            }
            listView1.onLoadMoreComplete();

        }
    }

    // 科目三 分页
    private void paging3(String json) {
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
            orderList3.addAll(otherList);
            orderAdapter.notifyDataSetChanged();
            if (pageCurrent3 >= pageCount3) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();
        }
    }
}
