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

public class Login extends AppCompatActivity {

    private EditText login_username;//用户名输入框
    private EditText login_password;//密码输入框
    private Button login_login;//登录跳转按钮
    private TextView login_forgetpassword;//忘记密码跳转按钮
    private TextView login_account;//注册跳转按钮

    private String username;//用户名
    private String password;//密码
    private boolean isRight;//密码是否正确

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        login_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, adduser.class));
            }
        });
    }

    /*初始化控件*/
    private void initLayout() {
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        login_login = (Button) findViewById(R.id.login_login);
        login_login.getBackground().setAlpha(235);//设置透明度
        login_forgetpassword = (TextView) findViewById(R.id.login_forgetpassword);
        login_account = (TextView) findViewById(R.id.login_account);
    }

    /*登录校验*/
    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tologin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void tologin() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://120.79.40.37:8080/doLogin/";
        String username = login_username.getText().toString();
        String password = login_password.getText().toString();
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
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(Login.this, MainActivity.class));
                            Toast.makeText(Login.this, "账号密码有误！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
