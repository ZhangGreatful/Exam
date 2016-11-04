package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.haha.exam.R;
import com.haha.exam.activity.Subject1Activity;

/**
 * Created by Administrator on 2016/11/3.
 */
public class StudyFragmetn extends Fragment implements View.OnClickListener {

    private RelativeLayout keyi,keer,kesan,kesan_theory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.study_fragment, container, false);
        keyi= (RelativeLayout) view.findViewById(R.id.ke_yi);
        keer= (RelativeLayout) view.findViewById(R.id.ke_er);
        kesan= (RelativeLayout) view.findViewById(R.id.ke_san);
        kesan_theory= (RelativeLayout) view.findViewById(R.id.ke_san_theory);

        keyi.setOnClickListener(this);
        keer.setOnClickListener(this);
        kesan.setOnClickListener(this);
        kesan_theory.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ke_yi:
                Intent intent=new Intent(getActivity(),Subject1Activity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
