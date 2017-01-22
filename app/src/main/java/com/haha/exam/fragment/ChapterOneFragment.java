package com.haha.exam.fragment;

/**
 * 科目一视频列表
 * <p/>
 * Created by Administrator on 2016/11/4.
 */
//public class ChapterOneFragment extends Fragment {
//
//    private TimeLineView timeLine;
//    private ListView listView;
//    private VideoInfo videoInfo;
//    private Gson gson=new Gson();
//
//    private VideoAdapter adapter;
//    private Subject1Activity subject1Activity;
//    private List<VideoInfo.DataBean> datas=new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.chapter_one_fragment, container, false);
//
//        listView = (ListView) view.findViewById(R.id.list_order_logistics);
//        timeLine = (TimeLineView) view.findViewById(R.id.tv_timelines);
//        return view;
//    }
//
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        OkGo.get(WebInterface.video)
//                .tag(this)
////                .params("subject", "1")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        videoInfo = gson.fromJson(s, VideoInfo.class);
////                        for (int i = 0; i < videoInfo.getData().size(); i++) {
////                            if (videoInfo.getData().get(i).getChapterid().equals("1")&&videoInfo.getData().get(i).getVideoinfo()!=null) {
////                                datas.add(videoInfo.getData().get(i));
////                            }
////
////                        }
//                        adapter = new VideoAdapter(getActivity(), datas);
//                        listView.setAdapter(adapter);
//                        timeLine.setTimelineCount(datas.size());//设置显示多少个时间轴
//                    }
//                });
//
//
//
//
//        timeLine.setTimelineHeadRadius(10);
//        timeLine.setTimelineRadius(20);//设置下面那些轴的圆点直径
//        timeLine.setTimelineWidth(5);//设置时间轴的宽度
//        timeLine.setTimelineRadiusDistance(145);//设置时间轴的高度
//        timeLine.setMarginTop(100);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), SubjectOneActivity.class);
//                intent.putExtra("chapter",0);
//                intent.putExtra("position",i);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//            }
//        });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        OkGo.getInstance().cancelTag(this);
//    }
//}
