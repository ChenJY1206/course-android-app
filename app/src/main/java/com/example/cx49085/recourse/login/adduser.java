package com.example.cx49085.recourse.login;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.cx49085.recourse.MainActivity;
import com.example.cx49085.recourse.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class adduser extends AppCompatActivity {

    private EditText add_username;//用户名输入框
    private EditText add_password;//密码输入框
    private Button add_add;//登录跳转按钮
    private TextView add_return;//返回跳转按钮
    private String username;//用户名
    private String password;//密码
    private boolean isRight;//密码是否正确

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        initLayout();
        add_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            add();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        add_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adduser.this, Login.class));
            }
        });
    }

    /*初始化控件*/
    private void initLayout() {
        add_username = (EditText) findViewById(R.id.login_username);
        add_password = (EditText) findViewById(R.id.login_password);
        add_add = (Button) findViewById(R.id.login_login);
        add_add.getBackground().setAlpha(235);//设置透明度
        add_return = (TextView) findViewById(R.id.login_account);
    }

    private void add() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://120.79.40.37:8080/doLogin/";
        String username = add_username.getText().toString();
        String password = add_password.getText().toString();
        url = url + username + "/" + password;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        // 1
        call.enqueue(new Callback() {
            // 2
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(adduser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    System.out.println("Main Thread");
                } else {
                    System.out.println("Not Main Thread");
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // 3
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    System.out.println("Main Thread");
                } else {
                    System.out.println("Not Main Thread");
                }
                //NOT UI Thread
                String str = response.body().string();
                System.out.println(response.code());
                System.out.println(str);
                final Map<String, String> map = (Map<String, String>) JSON.parse(str);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (map.get("result").equals("1")) {
                        } else {
                            Toast.makeText(adduser.this, "账号密码有误！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
