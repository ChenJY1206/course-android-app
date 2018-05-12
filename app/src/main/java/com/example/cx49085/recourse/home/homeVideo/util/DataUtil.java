package com.example.cx49085.recourse.home.homeVideo.util;


import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.example.cx49085.recourse.home.homeVideo.bean.CourseDataBean;
import com.example.cx49085.recourse.home.homeVideo.bean.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by XiaoJianjun on 2017/7/7.
 */

public class DataUtil {
    public static List<Video> getVideoListData(List<CourseDataBean> list) {
        final String TAG = "DataUtil";
        String videoUrlPre = "http://120.25.204.86:9009/file/1/";
        List<Video> videoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CourseDataBean courseDataBean = list.get(i);
            String videoUrl = videoUrlPre + courseDataBean.getUrl() + "/" + courseDataBean.getName();
            videoUrl = Uri.encode(videoUrl, "-![.:/,%?&=]");
//            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//            mmr.setDataSource(videoUrl);
//            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
            Video video = new Video(
                    courseDataBean.getName(),
                    99999999,
                    videoUrlPre + courseDataBean.getUrl() + "/img2.jpg",
                    videoUrl
            );
            videoList.add(video);

            Log.v(TAG, "result-------------" + video.toString());
        }
        return videoList;
    }
}
