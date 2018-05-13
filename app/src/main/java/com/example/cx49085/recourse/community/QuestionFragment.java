package com.example.cx49085.recourse.community;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.cx49085.recourse.community.data.entity.QuestionData;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.community.adapter.QuestionAdapter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.cx49085.recourse.community.data.CommunityManager.getQuestionDatas;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    private RecyclerView rv;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        init(view);
        initRefresh(view);
        setRv();
        return view;
    }

    private void initRefresh(View view, List<QuestionData>) {
        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置 Header 为 MaterialHeader
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout refreshlayout) {
                quAdapter.refresh();
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
                        init();
                    }
                });
            }
        }).start();
    }

    private QuestionAdapter quAdapter;

    private void setRv() {
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        quAdapter = new QuestionAdapter(getActivity(), getQuestionDatas());
        rv.setAdapter(quAdapter);
    }

    private void init(View view) {
        rv = (RecyclerView) view.findViewById(R.id.question_rv);
    }

}
