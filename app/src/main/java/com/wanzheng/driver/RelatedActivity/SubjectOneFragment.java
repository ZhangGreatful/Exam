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
import com.wanzheng.driver.Entity.OrdershowEntity;
import com.wanzheng.driver.network.JsonUtils;
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

public class SubjectOneFragment extends Fragment {

    private Context context;
    private final static int pagesize = 10;// 总页数
    private int position;
    int uid;
    private String signid;
    private int pageCurrent0 = 1;// 当前页
    private int pageCount0;// 总页数

    private ImageView noOrderImg;

    private int pageCurrentExam0 = 1;
    private int pageCountExam0;// 总页数
    public static CustomListView listView, listView1;
    private static final int LOAD_DATA_FINISH = 10;

    private List<Practice.MsgBean.ReturnDataBean> praticeList0;
    private Gson gson = new Gson();

    private List<Practice.MsgBean.ReturnDataBean> ExamList1;
    private ExamAdapter examAdapter;
    private PracticeAdapter practiceAdapter;

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

    private void initData() {
        // TODO Auto-generated method stub
        //startProgressDialog();
        SystemUtil su = new SystemUtil(getActivity());
        uid = su.showUid();
        signid = su.showOnlyID();
        if (uid != -1) {

            OkGo.post(WebInterface.get_record_list)
                    .tag(this)
                    .params("signid", signid)
                    .params("subject", 1)
                    .params("type", 3)
                    .params("pagesize", pagesize)
                    .params("page", pageCurrent0)
                    .execute(subjectOneCallBace);
            OkGo.post(WebInterface.get_record_list)
                    .tag(this)
                    .params("signid", signid)
                    .params("subject", 1)
                    .params("type", 2)
                    .params("pagesize", pagesize)
                    .params("page", pageCurrentExam0)
                    .execute(subjectOneCallBaceExam);

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

    // 科目一分页
    private void paging0(String json) {
        praticeList0 = new ArrayList<>();
        Practice practice = gson.fromJson(json, Practice.class);
        if (pageCurrent0 == 1) {// 首次加载
            pageCount0 = practice.getMsg().getPageCount();

            if (pageCount0 >= pageCurrent0) {
                pageCurrent0++;
            } else if (pageCount0 == 0) {
                return;
            }
            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
                praticeList0.addAll(firstList);
                initListView0();
            }

        } else {
            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> otherList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
//                praticeList0.addAll(otherList);
                practiceAdapter.AddFooterItem(otherList);
//                practiceAdapter.notifyDataSetChanged();
            }

            if (pageCurrent0 >= pageCount0) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();

        }
    }

    private void pagingExam0(String json) {
        ExamList1 = new ArrayList<>();
        Practice practice = gson.fromJson(json, Practice.class);
        if (pageCurrentExam0 == 1) {// 首次加载
            pageCountExam0 = practice.getMsg().getPageCount();

            if (pageCountExam0 >= pageCurrentExam0) {
                pageCurrentExam0++;
            } else if (pageCountExam0 == 0) {
                return;
            }
            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
                ExamList1.addAll(firstList);
                initListViewExam0();
            }

        } else {
            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> otherList = practice.getMsg().getReturnData();
                examAdapter.AddFooterItem(otherList);
            }

//            ArrayList<Practice> firstList = JsonUtils.parsePracticeorder(json);
//            ExamList1.addAll(otherList);
//            examAdapter.notifyDataSetChanged();
            if (pageCurrentExam0 > pageCountExam0) {// 如果没有更多页数
                listView1.setCanLoadMore(false);
            }
            listView1.onLoadMoreComplete();

        }
    }

    //      初始化科目一listview
    private void initListView0() {
        if (praticeList0 != null && praticeList0.size() != 0) {
            practiceAdapter = new PracticeAdapter(getActivity(), praticeList0);
            listView.setAdapter(practiceAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    private void initListViewExam0() {
        if (ExamList1 != null && ExamList1.size() != 0) {
            examAdapter = new ExamAdapter(getActivity(), ExamList1);
            listView1.setAdapter(examAdapter);
            noOrderImg.setVisibility(View.GONE);
        } else {
            noOrderImg.setVisibility(View.VISIBLE);
        }
    }

    public void loadData(final int type) {
        new Thread() {
            @Override
            public void run() {
                switch (type) {
                    case 0:// 下拉刷新 暂不适用
                        // refresh = true;
                        // int count = pageCurrent * NumUtil.NUM20;
                        pageCurrent0 = 1;// 如果下拉刷新就从新加载
                        initData();
                        break;
                    case 1:
                        if (pageCurrent0 <= pageCount0) {
                            OkGo.post(WebInterface.get_record_list)
                                    .tag(this)
                                    .params("signid", signid)
                                    .params("subject", 1)
                                    .params("type", 3)
                                    .params("pagesize", pagesize)
                                    .params("page", pageCurrent0)
                                    .execute(subjectOneCallBace);
                            pageCurrent0++;
                        } else {// 如果是首页且数据不足一页 或者已经是最后一页
                            Message _Msg = mHandler
                                    .obtainMessage(LOAD_DATA_FINISH);
                            mHandler.sendMessage(_Msg);
                        }
                        if (pageCurrentExam0 <= pageCountExam0) {
                            OkGo.post(WebInterface.get_record_list)
                                    .tag(this)
                                    .params("signid", signid)
                                    .params("subject", 1)
                                    .params("type", 2)
                                    .params("pagesize", pagesize)
                                    .params("page", pageCurrentExam0)
                                    .execute(subjectOneCallBaceExam);
                            pageCurrentExam0++;
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
}
