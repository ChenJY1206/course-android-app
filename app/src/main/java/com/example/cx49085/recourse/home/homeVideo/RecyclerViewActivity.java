package com.example.cx49085.recourse.home.homeVideo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.cx49085.recourse.R;
import com.example.cx49085.recourse.home.homeVideo.adapter.VideoAdapter;
import com.example.cx49085.recourse.home.homeVideo.adapter.holder.VideoViewHolder;
import com.example.cx49085.recourse.home.homeVideo.bean.CourseDataBean;
import com.example.cx49085.recourse.home.homeVideo.bean.Video;
import com.example.cx49085.recourse.home.homeVideo.util.DataUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerViewActivity extends AppCompatActivity {
    String result;
    private RecyclerView mRecyclerView;
    private static String[] names = {
            "巴黎高等商学院第二届金融与统计学大会",
            "巴黎高等商学院公开课-决策统计学",
            "概率论与数理统计",
            "国际贸易理论与实务",
            "国际投资学",
            "货币银行学"
    };
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        //接收参数
        Intent intent = getIntent();
        int courseNamePos = intent.getIntExtra("courseName", 0);
        Log.v(TAG, "courseNamePos ---------------" + courseNamePos);
        getData(names[courseNamePos]);

    }

    private Handler handler = new Handler();

    private void getData(final String courseName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("courseName", courseName);
                    Request request = new Request.Builder()
                            .url("http://120.25.204.86:9009/course/index")
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

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        VideoAdapter adapter = new VideoAdapter(this, getVideoList());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
    }

    private List<Video> getVideoList() {
        List<CourseDataBean> listPerson = new ArrayList<>();
        if (result != null)
            listPerson = JSON.parseArray(result, CourseDataBean.class);
        else
            Toast.makeText(this, " result Null", Toast.LENGTH_SHORT).show();
        return DataUtil.getVideoListData(listPerson);
    }


    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
