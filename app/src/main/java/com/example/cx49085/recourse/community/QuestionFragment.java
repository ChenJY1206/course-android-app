package com.example.cx49085.recourse.community;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.example.cx49085.recourse.MainActivity;
import com.example.cx49085.recourse.community.data.CommunityManager;
import com.example.cx49085.recourse.community.data.entity.QuestionData;
import com.example.cx49085.recourse.community.data.entity.QuestionServiceData;
import com.example.cx49085.recourse.util.DateUtils;
import com.example.cx49085.recourse.util.OnRecyclerviewItemClickListener;
import com.example.cx49085.recourse.util.RandomUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.community.adapter.QuestionAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    private RecyclerView rv;
    private FloatingActionButton fab;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        init(view);
        initRefresh(view);
        getData();
        return view;
    }


    private void initRefresh(final View view) {
        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置 Header 为 MaterialHeader
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout refreshlayout) {
                getData();
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
/*        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                quAdapter.add();
                refreshlayout.finishLoadmore(1000*//*,false*//*);//传入false表示加载失败
            }
        });*/
    }


    private Handler handler = new Handler();
    private static final String TAG = "QuestionFragment";
    String result;

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                    FormBody.Builder params = new FormBody.Builder();
                    Request request = new Request.Builder()
                            .url("http://120.79.40.37:8080/select_topic")
                            .post(params.build())
                            .build();
                    Call call = client.newCall(request);
                    Response response = call.execute();
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, "result-------------" + result);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setRv(getQuestionList());
                    }
                });
            }
        }).start();
    }

    private List<QuestionData> getQuestionList() {
        List<QuestionServiceData> listPerson = new ArrayList<>();
        List<QuestionData> list = new ArrayList<>();
        if (result != null) {
            Map mapTypes = JSON.parseObject(result);
            Log.v(TAG, "map:-----" + mapTypes.get("result"));
            listPerson = JSON.parseArray(mapTypes.get("result") + "", QuestionServiceData.class);
        } else
            Toast.makeText(getActivity(), " result Null", Toast.LENGTH_SHORT).show();
        for (QuestionServiceData x :
                listPerson) {
            QuestionData temp = new QuestionData();
            temp.setTitle(x.getContent());
            temp.setAnswerNum(RandomUtil.getRandom(2));
            temp.setDetail(x.getContent());
            temp.setState("正在解答");
            temp.setUsername(x.getUseraccount());
            temp.setTime(DateUtils.stampToDate(Long.parseLong(x.getTime())));
            temp.setImg(CommunityManager.getDrawable(RandomUtil.getRandom(1)));
            temp.setId(x.getTopicId());
            list.add(temp);
        }
        return list;
    }

    private QuestionAdapter quAdapter;
    private GridLayoutManager rvLayoutManager;

    private void setRv(List<QuestionData> list) {
        rvLayoutManager = new GridLayoutManager(getActivity(), 1);
        rv.setLayoutManager(rvLayoutManager);
        quAdapter = new QuestionAdapter(getActivity(), list);
        quAdapter.setOnRecyclerviewItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                RecyclerView.ViewHolder holder = rv.findViewHolderForAdapterPosition(position);
                String topic_id = ((TextView) holder.itemView.findViewById(R.id.item_question_id)).getText().toString();
                String title = ((TextView) holder.itemView.findViewById(R.id.item_question_title)).getText().toString();
                Log.v(TAG, "topic_id-------------" + topic_id);
                Intent intent = new Intent(getActivity(), OneQuestionActivity.class);
                intent.putExtra("topic_id", topic_id);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        rv.setAdapter(quAdapter);
    }


    private void init(View view) {
        rv = (RecyclerView) view.findViewById(R.id.question_rv);
    }

}
