package com.haha.exam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.haha.exam.view.TabPageIndicator;

import java.util.List;


public class FourAdapter extends PagerAdapter implements
		OnPageChangeListener {
	private List<String> titles;
	private List<Fragment> list;
	private ViewPager vp;
	private TabPageIndicator tab;
	private FragmentManager manager;
	private Fragment fromFragment;

	public FourAdapter(List<String> titles,
			TabPageIndicator tab, List<Fragment> list, ViewPager vp,
			FragmentManager manager) {
		this.list = list;
		this.titles = titles;
		this.vp = vp;
		this.tab = tab;
		this.manager = manager;
		fromFragment = new Fragment();
	}

	public void setTabPager() {
		tab.setViewPager(vp);
		tab.setOnPageChangeListener(this);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles != null ? titles.get(position) : null;

	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position).getView());
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = list.get(position);
		if (!fragment.isAdded()) {
			Log.e("tga", "instantiateItem----->没有添加");
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(fragment, fragment.getClass().getSimpleName())
					.commit();
			manager.executePendingTransactions();
		}
		if (fragment.getView().getParent() == null) {
			Log.i("tga", "fragment.getView().getParent() == nul》》》》》进入这里 ");
			container.addView(fragment.getView());
		}

		return fragment.getView();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int location) {
		Fragment to = list.get(location);
		if (fromFragment != to) {
			Log.v("tga", "fromFragment != to》》》》进入这里");
			FragmentTransaction transaction = manager.beginTransaction();
			if (!to.isAdded()) {
				Log.d("tga", "添加 当前的Fragment");
				transaction.hide(fromFragment)
						.add(to, to.getClass().getSimpleName()).commit();
			} else {
				Log.w("tga", "已添加   show当前的Fragment    ");
				transaction.hide(fromFragment).show(to).commit();

			}
			fromFragment = to;
		}
	}
}
