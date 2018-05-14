package com.example.cx49085.recourse.community.data.entity;

/**
 * Created by ChenJY on 2018/5/14.
 */

public class QuestionServiceData {
    String topicId;
    String content;
    String useraccount;
    String time;

    @Override
    public String toString() {
        return "QuestionServiceData{" +
                "topicId='" + topicId + '\'' +
                ", content='" + content + '\'' +
                ", useraccount='" + useraccount + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
