package com.haha.exam.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haha.exam.R;

/**
 * Created by Administrator on 2016/10/29.
 */
public class MyToggleButton extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private boolean isOpen = false;
    private ImageView left, right;
    private Animation push_in, push_out;
    private onSelectListener l;

    public MyToggleButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    public void setonSelectListener(onSelectListener l) {
        this.l = l;
    }

    public void setSelected(boolean isSelect) {
        if (isSelect) {
            right.setVisibility(View.VISIBLE);
            left.setVisibility(View.GONE);
            MyToggleButton.this.setBackgroundResource(R.drawable.timeon);
            if (l != null) {
                l.onSelected();
            }
            isOpen = true;
        } else {
            right.setVisibility(View.GONE);
            left.setVisibility(View.VISIBLE);
            MyToggleButton.this.setBackgroundResource(R.drawable.timeoff);
            if (l != null) {
                l.onUnSelected();
            }
            isOpen = false;
        }
    }

    public interface onSelectListener {
        public void onSelected();

        public void onUnSelected();
    }

    public boolean isChecked() {
        return isOpen;
    }

    public void init() {
        push_in = AnimationUtils.loadAnimation(mContext, R.anim.push_right_out);
        push_out = AnimationUtils.loadAnimation(mContext, R.anim.push_left_in);
        View view = LayoutInflater.from(mContext).inflate(R.layout.toggle, null);
        MyToggleButton.this.setBackgroundResource(R.drawable.timeoff);
        left = (ImageView) view.findViewById(R.id.left);
        right = (ImageView) view.findViewById(R.id.right);
        LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        this.addView(view, parmas);
        this.setOnClickListener(this);

    }

    public void startOpen() {
        left.startAnimation(push_in);
        push_in.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                left.setVisibility(View.INVISIBLE);
                right.setVisibility(View.VISIBLE);
                MyToggleButton.this.setBackgroundResource(R.drawable.timeon);
                isOpen = true;
                if (l != null) {
                    l.onSelected();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void startClose() {
        right.startAnimation(push_out);
        push_out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                right.setVisibility(View.INVISIBLE);
                left.setVisibility(View.VISIBLE);
                MyToggleButton.this.setBackgroundResource(R.drawable.timeoff);
                isOpen = false;
                if (l != null) {
                    l.onUnSelected();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (!isOpen) {
            startOpen();
        } else {
            startClose();
        }
    }
}
