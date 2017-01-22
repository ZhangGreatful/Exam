package com.haha.exam.fragment;

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

import com.haha.exam.R;
import com.haha.exam.adapter.FourContentAdapter;
import com.haha.exam.bean.Four;
import com.haha.exam.bean.FourBean;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.socialize.utils.Log;
import com.wanzheng.driver.Entity.BaseResult;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.network.NetWorkCallBack;
import com.wanzheng.driver.order.CustomListView;
import com.wanzheng.driver.util.Constants;
import com.wanzheng.driver.util.SystemUtil;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


public class FourFragment extends Fragment {

    private ArrayList<Four> parseEsoterica = new ArrayList<Four>();
    private static final int LOAD_DATA_FINISH = 10;
    private int pageCurrent = 1;// 当前页
    private FourContentAdapter adapter;
    private CustomListView listView;
    private String openid;
    private int pageCount;// 总页数
    private Context context;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {

                case LOAD_DATA_FINISH:
                /*
				 * if (mAdapter != null) {
				 * mAdapter.mList.addAll((ArrayList<AppInfo>) msg.obj);
				 * mAdapter.notifyDataSetChanged(); }
				 */
                    listView.onLoadMoreCompleteAndRemoveFooter(); // 加载更多完成
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_four, null);
        openid = new SystemUtil(getActivity()).showOpenID();
        listView = (CustomListView) view.findViewById(R.id.list);
        listView.onRefreshComplete();
        listView.setCanRefresh(false);

        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                loadData(1);
            }
        });

        Bundle bundle = getArguments();
        tid = bundle.getInt("tid");

        switch (tid) {
            case 1:

                initData();

                break;
            case 2:

                initData();
                break;
            case 3:
                initData();
                break;
            case 4:
                initData();
                break;
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        context = getContext();
    }

    private NetWorkCallBack<BaseResult> getMiJiByTypeIDcallback = new NetWorkCallBack<BaseResult>() {

        @Override
        public void onComplete(String json) {

            Log.e("tag", "你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜" + json);

            if (pageCurrent == 1) {// 首次加载
                int total = JsonUtils.parseOtherCount(json);// 条数
                Log.e("tag",
                        "totaltotaltotaltotaltotaltotaltotaltotaltotalhaha"
                                + total);
                if (total % Constants.PAGESIZE == 0) {
                    pageCount = total / Constants.PAGESIZE;
                } else {
                    pageCount = total / Constants.PAGESIZE + 1;
                }
                ArrayList<Four> firstList = JsonUtils.parseEsoterica(json);
                parseEsoterica.addAll(firstList);
                Log.e("tag",
                        "parseEsotericaparseEsotericaparseEsotericaparseEsoterica"
                                + parseEsoterica.size());
                initListView();

            } else {
                ArrayList<Four> otherList = JsonUtils.parseEsoterica(json);
                parseEsoterica.addAll(otherList);
                adapter.notifyDataSetChanged();
                if (pageCurrent >= pageCount) {// 如果没有更多页数
                    listView.setCanLoadMore(false);
                }
                listView.onLoadMoreComplete();

            }
        }
    };

    private int tid;

    private void initListView() {

        adapter = new FourContentAdapter(context, parseEsoterica);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (parseEsoterica != null && parseEsoterica.size() != 0) {
//					Intent intent2 = new Intent(getActivity(),
//							ExaminationDetailActivity.class);
//					Four four = parseEsoterica.get(position - 1);
//					intent2.putExtra("DriveHead", four);
//					intent2.putExtra("position", position - 1);
//					// startActivity(intent2);
//					startActivityForResult(intent2, 100);
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            int p = data.getIntExtra("position", 0);
            int c = data.getIntExtra("collected", 0);
            parseEsoterica.get(p).setIscollection(c);
        }
    }


    public void onEventMainThread(FourBean fb) {

        if (fb != null) {
            for (int i = 0; i < parseEsoterica.size(); i++) {
                if (parseEsoterica.get(i).getWid().equals(fb.getWid())) {
                    parseEsoterica.get(i).setIscollection(fb.getCollected());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initData() {
        // TODO Auto-generated method stub

        OkGo.post(WebInterface.exam_info)
                .tag(this)
                .params("typeid", tid)
                .params("openid", openid)
                .params("pagesize", LOAD_DATA_FINISH)
                .params("page", pageCurrent)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("tag", "你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜" + s);

                        if (pageCurrent == 1) {// 首次加载
                            int total = JsonUtils.parseOtherCount(s);// 条数
                            Log.e("tag",
                                    "totaltotaltotaltotaltotaltotaltotaltotaltotalhaha"
                                            + total);
                            if (total % Constants.PAGESIZE == 0) {
                                pageCount = total / Constants.PAGESIZE;
                            } else {
                                pageCount = total / Constants.PAGESIZE + 1;
                            }
                            ArrayList<Four> firstList = JsonUtils.parseEsoterica(s);
                            parseEsoterica.addAll(firstList);
                            Log.e("tag",
                                    "parseEsotericaparseEsotericaparseEsotericaparseEsoterica"
                                            + parseEsoterica.size());
                            initListView();

                        } else {
                            ArrayList<Four> otherList = JsonUtils.parseEsoterica(s);
                            parseEsoterica.addAll(otherList);
                            adapter.notifyDataSetChanged();
                            if (pageCurrent >= pageCount) {// 如果没有更多页数
                                listView.setCanLoadMore(false);
                            }
                            listView.onLoadMoreComplete();

                        }
                    }
                });

//		new NetWorkUtils().getInstance().work(
//				new MineNetInterface().getInstance().getMiJiByTypeID(tid,
//						openid, LOAD_DATA_FINISH, pageCurrent),
//				getMiJiByTypeIDcallback);
    }

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
                        pageCurrent++;
                        if (pageCurrent <= pageCount)

                            OkGo.post(WebInterface.exam_info)
                                    .tag(this)
                                    .params("typeid", tid)
                                    .params("openid", openid)
                                    .params("pagesize", LOAD_DATA_FINISH)
                                    .params("page", pageCurrent)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Log.e("tag", "你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜你猜你猜猜" + s);

                                            if (pageCurrent == 1) {// 首次加载
                                                int total = JsonUtils.parseOtherCount(s);// 条数
                                                Log.e("tag",
                                                        "totaltotaltotaltotaltotaltotaltotaltotaltotalhaha"
                                                                + total);
                                                if (total % Constants.PAGESIZE == 0) {
                                                    pageCount = total / Constants.PAGESIZE;
                                                } else {
                                                    pageCount = total / Constants.PAGESIZE + 1;
                                                }
                                                ArrayList<Four> firstList = JsonUtils.parseEsoterica(s);
                                                parseEsoterica.addAll(firstList);
                                                Log.e("tag",
                                                        "parseEsotericaparseEsotericaparseEsotericaparseEsoterica"
                                                                + parseEsoterica.size());
                                                initListView();

                                            } else {
                                                ArrayList<Four> otherList = JsonUtils.parseEsoterica(s);
                                                parseEsoterica.addAll(otherList);
                                                adapter.notifyDataSetChanged();
                                                if (pageCurrent >= pageCount) {// 如果没有更多页数
                                                    listView.setCanLoadMore(false);
                                                }
                                                listView.onLoadMoreComplete();

                                            }
                                        }
                                    });
//						new NetWorkUtils().getInstance().work(
//								new MineNetInterface().getInstance()
//										.getMiJiByTypeID(tid, openid,
//												LOAD_DATA_FINISH, pageCurrent),
//								getMiJiByTypeIDcallback);

                        else {// 如果是首页且数据不足一页 或者已经是最后一页
                            Message _Msg = mHandler.obtainMessage(LOAD_DATA_FINISH);
                            mHandler.sendMessage(_Msg);
                        }
                        break;
                }
            }

        }.start();
    }
}
