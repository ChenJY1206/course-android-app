package com.example.cx49085.recourse.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.mine.data.entity.Rv23Data;
import com.example.cx49085.recourse.util.OnRecyclerviewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.cx49085.recourse.util.ImageUtil.getRequestOptions;

/**
 * Created by cx49085 on 2018/4/7.
 */

public class Rv23Adapter extends RecyclerView.Adapter {

    private Context context;
    private List<Rv23Data> list = new ArrayList<Rv23Data>();

    public void setmOnRecyclerviewItemClickListener(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;

    public Rv23Adapter(Context context, List<Rv23Data> list) {
        this.context = context;
        this.list = list;
    }
    public Rv23Adapter(Context context, List<Rv23Data> list,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.list = list;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv23_mine, null);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将监听传递给自定义接口
                mOnRecyclerviewItemClickListener.onItemClickListener(v,position);
            }
        });
        ((Rv23Adapter.ViewHolder) holder).tv.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImg()).apply(getRequestOptions()).into(((Rv23Adapter.ViewHolder) holder).iv);
//        ((Rv23Adapter.ViewHolder)holder).tv.setText(R.string.name_hobby);
//        Glide.with(context).load(R.drawable.ic_mine_hobby).apply(getRequestOptions()).into(((Rv23Adapter.ViewHolder)holder).iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_rv23_img);
            tv = (TextView) itemView.findViewById(R.id.item_rv23_title);
        }
    }
}
