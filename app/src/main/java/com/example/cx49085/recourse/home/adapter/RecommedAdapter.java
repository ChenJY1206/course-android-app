package com.example.cx49085.recourse.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.home.data.entity.RecommendData;
import com.example.cx49085.recourse.mine.adapter.Rv23Adapter;
import com.example.cx49085.recourse.util.GlideRoundTransform;
import com.example.cx49085.recourse.util.OnRecyclerviewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.cx49085.recourse.util.ImageUtil.getRequestOptions;

/**
 * Created by cx49085 on 2018/4/5.
 */

public class RecommedAdapter extends RecyclerView.Adapter {

    private Context context = null;
    private List<RecommendData> recommendDatas = new ArrayList<RecommendData>();

    OnRecyclerviewItemClickListener monRecyclerviewItemClickListener;

    public void setMonRecyclerviewItemClickListener(OnRecyclerviewItemClickListener monRecyclerviewItemClickListener) {
        this.monRecyclerviewItemClickListener = monRecyclerviewItemClickListener;
    }


    public RecommedAdapter(Context context, List<RecommendData> l) {
        this.context = context;
        this.recommendDatas = l;
    }

    public RecommedAdapter(Context context, List<RecommendData> recommendDatas, OnRecyclerviewItemClickListener monRecyclerviewItemClickListener) {
        this.context = context;
        this.recommendDatas = recommendDatas;
        this.monRecyclerviewItemClickListener = monRecyclerviewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_main, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monRecyclerviewItemClickListener.onItemClickListener(v, (Integer) v.getTag());
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {//将position保存在itemView的Tag中，以便点击时进行获取
        ((ViewHolder) holder).itemView.setTag(position);
        ((ViewHolder) holder).tv.setText(recommendDatas.get(position).getTitle());
        ((ViewHolder) holder).course_num_tv.setText(recommendDatas.get(position).getCourse_num());
        ((ViewHolder) holder).course_detail_tv.setText(recommendDatas.get(position).getCourse_introduction());
        RequestOptions requestOptions = getRequestOptions();
        Glide.with(context).load(recommendDatas.get(position).getImg()).apply(getRequestOptions().bitmapTransform(new GlideRoundTransform(4))).into(((ViewHolder) holder).iv);
    }

    @Override
    public int getItemCount() {
        return recommendDatas.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView bgiv;
        ImageView iv;
        TextView tv;
        TextView course_num_tv;
        TextView course_detail_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_recommend_image);
            //bgiv = (ImageView)itemView.findViewById(R.id.item_recommend_bgimage);
            tv = (TextView) itemView.findViewById(R.id.item_recommend_title);
            course_num_tv = (TextView) itemView.findViewById(R.id.item_recommend_courseNum);
            course_detail_tv = (TextView) itemView.findViewById(R.id.item_recommend_detail);

        }
    }
}
