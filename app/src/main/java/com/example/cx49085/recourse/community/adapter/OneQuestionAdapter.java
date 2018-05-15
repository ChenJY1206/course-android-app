package com.example.cx49085.recourse.community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.community.data.entity.OneQuestionData;
import com.example.cx49085.recourse.util.OnRecyclerviewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ChenJY on 2018/5/14.
 */

public class OneQuestionAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<OneQuestionData> l;

    private static final String TAG = "OneQuestionAdapter";

    public OneQuestionAdapter(Context context, List<OneQuestionData> list) {
        Log.v(TAG, "getItemCount ---------------" + list.get(0).toString());
        this.context = context;
        this.l = list;
    }

    public void setOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener onRecyclerviewItemClickListener) {
        this.onRecyclerviewItemClickListener = onRecyclerviewItemClickListener;
    }

    OnRecyclerviewItemClickListener onRecyclerviewItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comments, null);
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
        Log.e(TAG, "onBindViewHolder: excuted", null);
        //将position保存在itemView的Tag中，以便点击时进行获取
        ((ViewHolder) holder).itemView.setTag(position);
        ((ViewHolder) holder).time.setText(String.valueOf(l.get(position).getTime()));
        ((ViewHolder) holder).username.setText(String.valueOf(l.get(position).getUseraccount()));
        ((ViewHolder) holder).detail.setText(String.valueOf(l.get(position).getContent()));
        Log.v(TAG, "imgRes ---------------" + l.get(position).getTopicId());
        ((ViewHolder) holder).img.setImageResource(l.get(position).getTopicId());

    }

    //下面两个方法提供给页面刷新和加载时调用
    public void addlist(List<OneQuestionData> addlist) {
        //增加数据
        int position = addlist.size();
        l.addAll(1, addlist);
        notifyItemInserted(position);
    }

    public void refresh(List<OneQuestionData> newlist) {
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

        private TextView time;
        private TextView username;
        private CircleImageView img;
        private TextView detail;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.comment_item_icon_head);
            username = (TextView) itemView.findViewById(R.id.item_comment_user_id);
            time = (TextView) itemView.findViewById(R.id.item_comment_time);
            detail = (TextView) itemView.findViewById(R.id.item_comment_detail);
        }
    }
}
