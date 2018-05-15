package com.example.cx49085.recourse.community;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.community.adapter.OneQuestionAdapter;
import com.example.cx49085.recourse.community.adapter.QuestionAdapter;
import com.example.cx49085.recourse.community.data.CommunityManager;
import com.example.cx49085.recourse.community.data.entity.OneQuestionData;
import com.example.cx49085.recourse.community.data.entity.QuestionData;
import com.example.cx49085.recourse.community.data.entity.QuestionServiceData;
import com.example.cx49085.recourse.util.DateUtils;
import com.example.cx49085.recourse.util.OnRecyclerviewItemClickListener;
import com.example.cx49085.recourse.util.RandomUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

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

public class OneQuestionActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TextView titlev;
    private static final String TAG = "OneQuestionActivity";
    String topic_id;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_question);
        init();
        //接收参数
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        Log.v(TAG, "title ---------------" + title);

        topic_id = intent.getStringExtra("topic_id");
        Log.v(TAG, "topic_id ---------------" + topic_id);
        titlev.setText("问题：" + title);
        initRefresh();
        getData();
    }

    private void initRefresh() {
        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.comment_refreshLayout);
        //设置 Header 为 MaterialHeader
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
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

    String result;
    private Handler handler = new Handler();

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                    FormBody.Builder params = new FormBody.Builder();
                    Request request = new Request.Builder()
                            .url("http://120.79.40.37:8080/select_content/" + topic_id)
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
                        setRv(getCommentList());
                    }
                });
            }
        }).start();
    }


    private List<OneQuestionData> getCommentList() {
        List<OneQuestionData> listPerson = new ArrayList<>();
        List<QuestionData> list = new ArrayList<>();
        if (result != null) {
            Map mapTypes = JSON.parseObject(result);
            Log.v(TAG, "map:-----" + mapTypes.get("result"));
            listPerson = JSON.parseArray(mapTypes.get("result") + "", OneQuestionData.class);
        } else {
            Log.v(TAG, "map:-----NULL result");
            Toast.makeText(this, " result Null", Toast.LENGTH_SHORT).show();
        }
        for (OneQuestionData x :
                listPerson) {
            x.setTime(DateUtils.stampToDate(Long.parseLong(x.getTime())));
            x.setTopicId(CommunityManager.getDrawable(RandomUtil.getRandom(1)));
            Log.v(TAG, "temp:-----" + x.toString());
        }
      /*  for (OneQuestionData x :
                listPerson) {
            QuestionData temp = new QuestionData();
            temp.setTitle(x.getContent());
            temp.setDetail(x.getContent());
            temp.setUsername(x.getUseraccount());
            temp.setTime(DateUtils.stampToDate(Long.parseLong(x.getTime())));
            temp.setImg(CommunityManager.getDrawable(RandomUtil.getRandom(1)));
            temp.setAnswerNum(RandomUtil.getRandom(2));
            temp.setState("正在解答");
            temp.setId(x.getTopicId());
            Log.v(TAG, "temp:-----" + temp.toString());
            list.add(temp);
        }*/

        Log.v(TAG, "list:-----" + listPerson.size());
        return listPerson;
    }


    private void setRv(List<OneQuestionData> list) {

        OneQuestionAdapter quAdapter = new OneQuestionAdapter(this, list);
        GridLayoutManager rvLayoutManager = new GridLayoutManager(this, 1);
        Log.v(TAG, "setRvlist:-----" + list.size());
        rv.setLayoutManager(rvLayoutManager);
        rv.setAdapter(quAdapter);
    }

    private void init() {
        rv = (RecyclerView) findViewById(R.id.comment_rv);
        titlev = (TextView) findViewById(R.id.comment_title);
    }

}
