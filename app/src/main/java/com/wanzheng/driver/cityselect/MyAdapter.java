package com.wanzheng.driver.cityselect;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haha.exam.R;

public class MyAdapter extends BaseAdapter {
	
	private Context context;
	private List<MyListItem> myList;
	
	public MyAdapter(Context context, List<MyListItem> myList) { 
		this.context = context; 
		this.myList = myList;
	}
	
	public int getCount() {
		return myList.size(); 
	} 
	public Object getItem(int position) {
		return myList.get(position);
	} 
	public long getItemId(int position) {
		return position;
	} 
	
	public View getView(int position, View convertView, ViewGroup parent)
	{ 
		 ViewHolder holder;
         //观察convertView随ListView滚动情况             
         if (convertView == null) {
                  convertView = LayoutInflater.from(context).inflate(R.layout.item_li,null);
                  holder = new ViewHolder();
                 /**得到各个控件的对象*/                     
                 holder.title = (TextView) convertView.findViewById(R.id.itemlist);
                 convertView.setTag(holder);//绑定ViewHolder对象                    
         }
         else{
                 holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象                   
         }
         /**设置TextView显示的内容，即我们存放在动态数组中的数据*/             
         holder.title.setText(myList.get(position).getName());
         
         
         return convertView;
	}

	
	/**存放控件*/ 
	public final class ViewHolder{
	    public TextView title;
	    public TextView text;
    }
}