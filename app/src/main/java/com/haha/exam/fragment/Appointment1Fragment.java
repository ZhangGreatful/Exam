package com.haha.exam.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haha.exam.R;
import com.haha.exam.adapter.AppointFragList1Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Appointment1Fragment extends Fragment {
    private ListView mListView;
    private AppointFragList1Adapter mAdapter;
    private List<String[]> mlist;

    public Appointment1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_appointment1, container,false);
        mListView= (ListView) view.findViewById(R.id.appointment_tuijian_listview);
        init();
        return view;
    }
    private void init(){

        mlist=new ArrayList<String[]>();
        String[] test1={"驾校1","北京","1000"};
        String[] test2={"驾校2","北京","1000"};
        String[] test3={"驾校2","北京","1000"};
        mlist.add(test1);
        mlist.add(test2);
        mlist.add(test3);
        mAdapter=new AppointFragList1Adapter((ArrayList<String[]>) mlist,getContext());
        mAdapter.getCount();
        mListView.setAdapter(mAdapter);

    }

}
