package com.haha.exam.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haha.exam.R;
import com.haha.exam.adapter.AppointFragList2Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Appointment2Fragment extends Fragment {

     private ListView mListView;
    private AppointFragList2Adapter mAdapter;
    private List<String[]> mlist;
    private  View view;
    public Appointment2Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_appointment2, container, false);
        init();
        return view;
    }
    private void init(){
        mListView= (ListView) view.findViewById(R.id.appointment_haooping_listview);
        mlist=new ArrayList<String[]>();
        String[] test1={"元芳","驾校","1000次","10年","1000元","2000元","1km"};
        String[] test2={"元芳","驾校","1000次","10年","1000元","2000元","1km"};
        String[] test3={"元芳","驾校","1000次","10年","1000元","2000元","1km"};
        mlist.add(test1);
        mlist.add(test2);
        mlist.add(test3);
        mAdapter=new AppointFragList2Adapter((ArrayList<String[]>) mlist,getContext());
        mListView.setAdapter(mAdapter);
    }

}
