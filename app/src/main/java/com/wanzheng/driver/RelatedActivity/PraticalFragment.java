package com.wanzheng.driver.RelatedActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.haha.exam.R;

import java.lang.reflect.Field;

/**
 * 实操学时信息展示界面
 * Created by Administrator on 2017/1/9.
 */

public class PraticalFragment extends Fragment {

    private FrameLayout frameLayout;
    private int type;
    private int position;
    private Context context;
    private FragmentManager fragmentManager;
    private TheoryFragment theoryFragment;
    private PeriodFragment periodFragment;
    private Fragment fromFragment = new Fragment();


    public PraticalFragment() {
    }
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    ;

    public PraticalFragment(int type, int position, Context context) {
        this.type = type;
        this.position = position;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.practical_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFragment();
    }

    private void initFragment() {
        fragmentManager = getChildFragmentManager();
        theoryFragment = new TheoryFragment();
        if (type == 2) {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.content_framelayout, theoryFragment).commit();
            fromFragment = theoryFragment;
        } else {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.content_framelayout, periodFragment).commit();
            fromFragment = periodFragment;
        }

    }

    // 通过hide，show来减少内存
    @SuppressLint("NewApi")
    public void switchContent(FragmentManager fragmentManager,Fragment to) {
        Log.d("PraticalFragment", "执行了切换方法");
            Log.d("PraticalFragment1", "执行了切换方法");
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            transaction.replace(R.id.content_framelayout, to).commit();
//            if (!to.isAdded()) { // 先判断是否被add过
//                // Log.e("AGT", "add");
//                transaction.hide(fromFragment).add(R.id.content_framelayout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
//            } else {
//                // Log.e("AGT", "show");
//                transaction.hide(fromFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
//            }
    }
}
