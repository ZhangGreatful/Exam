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

public class KeSanFragment extends Fragment implements View.OnClickListener {
    private ImageView start;
    private LinearLayout text_subject3, text_subject4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ke_san_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        start = (ImageView) this.getActivity().findViewById(R.id.start1);
        text_subject3 = (LinearLayout) this.getActivity().findViewById(R.id.text_subject3);
        text_subject4 = (LinearLayout) this.getActivity().findViewById(R.id.text_subject4);

        text_subject4.setOnClickListener(this);
        text_subject3.setOnClickListener(this);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimeDialog dialog = new TimeDialog(getActivity());

                dialog.setMessage("是否进行计时培训学习？");
                dialog.setYesMessage("计时");
                dialog.setNoMessage("不计时");
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_subject3:
                Intent intent2 = new Intent(getActivity(), Subject1Activity.class);
                intent2.putExtra("subject", "3");
                startActivity(intent2);
                break;

            case R.id.text_subject4:
                Intent intent3 = new Intent(getActivity(), Subject1Activity.class);
                intent3.putExtra("subject", "4");
                startActivity(intent3);
                break;
        }
    }
}
