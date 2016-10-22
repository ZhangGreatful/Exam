/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haha.exam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.haha.exam.R;
import com.haha.exam.activity.OrderTextActivity;
import com.haha.exam.bean.AnwerInfo;

import java.util.List;


public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<AnwerInfo.DataBean.SubDataBean> datas;
    private OrderTextActivity orderTextActivity;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, choice_a, choice_b, choice_c, choice_d;
        public final TextView answer, is_wrong, answer_explain;
        public final LinearLayout choice1, choice2, choice3, choice4,ll_explain;


        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            choice_a = (TextView) view.findViewById(R.id.choice_a);
            choice_b = (TextView) view.findViewById(R.id.choice_b);
            choice_c = (TextView) view.findViewById(R.id.choice_c);
            choice_d = (TextView) view.findViewById(R.id.choice_d);
            answer = (TextView) view.findViewById(R.id.answer);
            is_wrong = (TextView) view.findViewById(R.id.is_wrong);
            answer_explain = (TextView) view.findViewById(R.id.answer_explain);

            choice1 = (LinearLayout) view.findViewById(R.id.choice_1);
            choice2 = (LinearLayout) view.findViewById(R.id.choice_2);
            choice3 = (LinearLayout) view.findViewById(R.id.choice_3);
            choice4 = (LinearLayout) view.findViewById(R.id.choice_4);
            ll_explain= (LinearLayout) view.findViewById(R.id.ll_anwer);
        }
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView) {
        orderTextActivity=new OrderTextActivity();
        mContext = context;
        mRecyclerView = recyclerView;
    }

    public void setDataList(List<AnwerInfo.DataBean.SubDataBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        AnwerInfo.DataBean.SubDataBean subDataBean = datas.get(position);
        holder.title.setText(subDataBean.getQuestionid() + ". " + subDataBean.getQuestion());
        holder.choice_a.setText(subDataBean.getOptiona());
        holder.choice_b.setText(subDataBean.getOptionb());
        if (subDataBean.getOptionc().equals("null")) {
            holder.choice3.setVisibility(View.GONE);
        } else {
            holder.choice_c.setText(subDataBean.getOptionc());
        }
        if (subDataBean.getOptiond().equals("null") ) {
            holder.choice4.setVisibility(View.GONE);
        } else {
            holder.choice_d.setText(subDataBean.getOptiond());
        }
        holder.answer.setText(subDataBean.getAnswer());
        holder.answer_explain.setText(subDataBean.getExplain());
        if (orderTextActivity.isClicked==true){
            holder.ll_explain.setVisibility(View.VISIBLE);
        }else {
            holder.ll_explain.setVisibility(View.GONE);
        }
//        +"\n\nA." + subDataBean.getOptiona()
//                + "\nB." + subDataBean.getOptionb()
//                + "\nC." + subDataBean.getOptionc()
//                + "\nD." + subDataBean.getOptiond()
//                + "\n\n\n答案解析：" + subDataBean.getExplain()


        final View itemView = holder.itemView;

    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }
}
