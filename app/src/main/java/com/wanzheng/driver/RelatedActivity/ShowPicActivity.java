package com.wanzheng.driver.RelatedActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.activity.BaseActivity;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanzheng.driver.Entity.Picture;
import com.wanzheng.driver.network.JsonUtils;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 显示图片列表的界面
 */
public class ShowPicActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private PictureAdapter picAdapter;
    private String id;
    private int PAGE_SIZE = 10;
    private int Current_Page = 1;
    private int Count_Page = 0;
    private Gson gson = new Gson();
    Picture picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        id = intent.getStringExtra("xueshiid");
        initTitle();
        initView();
        initData();
        initLoadMoreListener();
    }

    private void initTitle() {
        setTitleColor(getResources().getColor(R.color.title_color));
        setTitlebarBackground(R.color.white);
        setLeftBtnDrawable();
        setTitle("抓拍照片");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_show_pic;
    }

    private void initData() {

        OkGo.post(WebInterface.get_record_detail)
                .tag(this)
                .params("recordid", id)
                .params("pagesize", PAGE_SIZE)
                .params("page", Current_Page)
                .execute(getPicCallBack);
    }

    private StringCallback getPicCallBack = new StringCallback() {
        @Override
        public void onSuccess(String s, Call call, Response response) {
            int num = JsonUtils.parseNum(s);
            if (num == 1) {

                Current_Page++;
                picture = gson.fromJson(s, Picture.class);
                Count_Page = Integer.valueOf(picture.getMsg().getPageCount());
                if (picture.getMsg().getReturnData()!=null){
                    picAdapter = new PictureAdapter(ShowPicActivity.this, picture);
                    recyclerView.setAdapter(picAdapter);
                    picAdapter.setOnItemClickLitener(new PictureAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(ShowPicActivity.this, PictureDetail.class);
                            intent.putExtra("bitmap", picture.getMsg().getReturnData().get(position).getPhoto_url());
                            startActivity(intent);
                        }

                    });
                }

            }
        }
    };

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        refreshLayout.setEnabled(false);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));//显示两列
    }

    private void initLoadMoreListener() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == picAdapter.getItemCount()) {

                    if (Current_Page < Count_Page) {
                        initData();
                        picAdapter.AddFooterItem(picture);
                    } else {
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

    }
}
