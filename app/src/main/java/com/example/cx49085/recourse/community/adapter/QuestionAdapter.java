package com.example.cx49085.recourse.community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.community.data.entity.QuestionData;
import com.example.cx49085.recourse.util.OnRecyclerviewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 10919 on 2018/4/6/006.
 */

public class QuestionAdapter extends RecyclerView.Adapter {

    public void setContext(Context context) {
        this.context = context;
    }

    public void setL(List<QuestionData> l) {
        this.l = l;
    }

    private Context context;
    private List<QuestionData> l;
    private final static String TAG = "QuestionAdpater";

    public void setOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener onRecyclerviewItemClickListener) {
        this.onRecyclerviewItemClickListener = onRecyclerviewItemClickListener;
    }

    OnRecyclerviewItemClickListener onRecyclerviewItemClickListener;

    public QuestionAdapter(Context context, List<QuestionData> l) {
        this.context = context;
        this.l = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, null);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerviewItemClickListener.onItemClickListener(v, (Integer) v.getTag());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取
        ((ViewHolder) holder).itemView.setTag(position);
        ((ViewHolder) holder).title.setText(l.get(position).getTitle());
        ((ViewHolder) holder).state.setText(l.get(position).getState());
        ((ViewHolder) holder).answerNum.setText(String.valueOf(l.get(position).getAnswerNum()));
        ((ViewHolder) holder).username.setText(String.valueOf(l.get(position).getUsername()));
        ((ViewHolder) holder).time.setText(String.valueOf(l.get(position).getTime()));
        ((ViewHolder) holder).username.setText(String.valueOf(l.get(position).getUsername()));
        ((ViewHolder) holder).detail.setText(String.valueOf(l.get(position).getDetail()));
        ((ViewHolder) holder).img.setImageResource(l.get(position).getImg());

        Log.e(TAG, "imgRes ---------------" + l.get(position).getImg());
        ((ViewHolder) holder).id.setText(String.valueOf(l.get(position).getId()));

    }


    //下面两个方法提供给页面刷新和加载时调用
    public void add(List<QuestionData> addlist) {
        //增加数据
        int position = addlist.size();
        l.addAll(1, addlist);
        notifyItemInserted(position);
    }

    public void refresh(List<QuestionData> newlist) {
        //刷新数据
        l.removeAll(l);
        l.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView title;
        private TextView state;
        private TextView answerNum;
        private TextView time;
        private TextView username;
        private CircleImageView img;
        private TextView detail;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.item_question_id);
            img = (CircleImageView) itemView.findViewById(R.id.item_icon_head);
            username = (TextView) itemView.findViewById(R.id.item_question_user_id);
            title = (TextView) itemView.findViewById(R.id.item_question_title);
            state = (TextView) itemView.findViewById(R.id.item_question_state);
            time = (TextView) itemView.findViewById(R.id.item_question_time);
            detail = (TextView) itemView.findViewById(R.id.item_question_detail);
            answerNum = (TextView) itemView.findViewById(R.id.item_question_answer_num);
        }
    }
}
