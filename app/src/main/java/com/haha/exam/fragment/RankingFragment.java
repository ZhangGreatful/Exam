package com.haha.exam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.adapter.RankingAdapter;
import com.haha.exam.bean.Ranking;
import com.haha.exam.utils.SPUtils;
import com.haha.exam.view.circleimageview.CircleImageView;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wanzheng.driver.network.JsonUtils;
import com.wanzheng.driver.util.SystemUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 城市排名
 * Created by Administrator on 2016/11/8.
 */
public class RankingFragment extends Fragment {

    private SystemUtil su;
    private String onlyID;
    private String headerPath, city;
    private SPUtils spUtils = new SPUtils();
    private String subject;
    private String cartype;
    private ListView listView;
    private RankingAdapter adapter;
    private TextView grade, time, info, t1;
    private LinearLayout grade_info;
    private CircleImageView header;
    private Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.grade_list);
        grade_info = (LinearLayout) view.findViewById(R.id.grade_info);
        grade = (TextView) view.findViewById(R.id.grade);
        time = (TextView) view.findViewById(R.id.time);
        info = (TextView) view.findViewById(R.id.info);
        t1 = (TextView) view.findViewById(R.id.t1);
        header = (CircleImageView) view.findViewById(R.id.header);
        subject = (String) spUtils.get(getActivity(), "subject0", "1");
        cartype = (String) spUtils.get(getActivity(), "cartype", "xc");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        su = new SystemUtil(getActivity());
        onlyID = su.showOnlyID();
        city = su.showCity();
        if (city.equals("")){
            city= (String) spUtils.get(getActivity(),"cityname","本地");
        }
        headerPath = su.showUserHeader();
        initData();

    }

    private void initData() {

        OkGo.post(WebInterface.score_ranking)
                .tag(this)
                .params("signid", onlyID)
//                .params("cartype", cartype)
                .params("subject", subject)
//                .params("cityname", city)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int num = JsonUtils.parseNum(s);
                        if (num == 1) {
                            Ranking ranking = gson.fromJson(s, Ranking.class);
                            adapter = new RankingAdapter(getActivity(), ranking.getMsg());
                            listView.setAdapter(adapter);
                            Picasso.with(getActivity()).load(headerPath).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(header);
                            if (ranking.getData().size() != 0 && ranking.getData().get(0).getPersonalmingci() <= ranking.getMsg().size()) {
                                info.setText("恭喜，您上榜了");
                                grade_info.setVisibility(View.VISIBLE);
                                String str1=ranking.getData().get(0).getPersonalscore()!=null?ranking.getData().get(0).getPersonalscore():"";
                                if (!str1.equals("")){
                                    grade.setText(ranking.getData().get(0).getPersonalscore() + "分");
                                }

                                String str = ranking.getData().get(0).getPersonaltime() != null ? ranking.getData().get(0).getPersonaltime() : "";
                                if (!str.equals("")) {
                                    int minite = Integer.valueOf(str) / 60;
                                    int second = Integer.valueOf(str) % 60;
                                    time.setText("用时:" + String.valueOf(minite) + "分" + String.valueOf(second) + "秒");
                                }
                            } else {
                                info.setText("继续努力哦～");
                                grade_info.setVisibility(View.GONE);
                            }
                        }


                        ;
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
