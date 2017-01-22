package com.haha.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haha.exam.R;
import com.haha.exam.activity.Subject1Activity;
import com.haha.exam.dialog.TimeDialog;

/**
 * Created by Administrator on 2016/12/3.
 */

public class KeErFragment extends Fragment {

    private ImageView start;
    private LinearLayout text_subject2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ke_er_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        start = (ImageView) this.getActivity().findViewById(R.id.start);
        text_subject2 = (LinearLayout) this.getActivity().findViewById(R.id.text_subject2);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimeDialog dialog = new TimeDialog(getActivity());

                dialog.setMessage("是否进行计时培训学习？");
                dialog.setNoMessage("不计时");
                dialog.setYesMessage("计时");
                dialog.show();
                dialog.setNoOnclickListener(new TimeDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setYesOnclickListener(new TimeDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        //                        开始计时

                        dialog.dismiss();
                    }
                });
            }
        });
        text_subject2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(),Subject1Activity.class);
                intent1.putExtra("subject","2");
                startActivity(intent1);
            }
        });
    }
}
