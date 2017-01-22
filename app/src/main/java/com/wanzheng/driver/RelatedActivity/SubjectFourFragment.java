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

public class SubjectFourFragment extends Fragment {
    private Context context;
    private final static int pagesize = 10;// 总页数
    private int position;
    int uid;
    private String signid;

    private ImageView noOrderImg;

    private int pageCurrent4 = 1;// 当前页
    private int pageCount4;// 总页数
    public static CustomListView listView, listView1;
    private static final int LOAD_DATA_FINISH = 10;

    private Gson gson = new Gson();

    private List<Practice.MsgBean.ReturnDataBean> ExamList4;
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

    public void loadData(final int type) {
        new Thread() {
            @Override
            public void run() {
                switch (type) {
                    case 0:// 下拉刷新 暂不适用
                        // refresh = true;
                        // int count = pageCurrent * NumUtil.NUM20;
                        pageCurrent4 = 1;// 如果下拉刷新就从新加载
                        initData();
                        break;
                    case 1:
                        if (pageCurrent4 <= pageCount4) {
                            OkGo.post(WebInterface.get_record_list)
                                    .tag(this)
                                    .params("signid", signid)
                                    .params("subject", 2)
                                    .params("type", 3)
                                    .params("pagesize", pagesize)
                                    .params("page", pageCurrent4)
                                    .execute(subjectFourCallBace);
                            pageCurrent4++;
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

            OkGo.post(WebInterface.get_record_list)
                    .tag(this)
                    .params("signid", signid)
                    .params("subject", 4)
                    .params("type", 2)
                    .params("pagesize", pagesize)
                    .params("page", pageCurrent4)
                    .execute(subjectFourCallBace);
        }
    }

    private StringCallback subjectFourCallBace = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {
                pagingExam4(s);
            }
        }
    };

    // 科目四分页
    private void pagingExam4(String json) {
        ExamList4 = new ArrayList<>();
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
            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> firstList = practice.getMsg().getReturnData();
                ExamList4.addAll(firstList);
                initListViewExam4();
            }
        } else {
//            ArrayList<Practice> otherList = JsonUtils.parsePracticeorder(json);
            if (practice.getMsg().getReturnData() != null) {
                List<Practice.MsgBean.ReturnDataBean> otherList = practice.getMsg().getReturnData();
                examAdapter.AddFooterItem(otherList);
            }
            if (pageCurrent4 > pageCount4) {// 如果没有更多页数
                listView.setCanLoadMore(false);
            }
            listView.onLoadMoreComplete();
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
}
