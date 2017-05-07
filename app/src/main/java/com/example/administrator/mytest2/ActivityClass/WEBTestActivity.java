package com.example.administrator.mytest2.ActivityClass;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.mytest2.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WEBTestActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtest);
        Button btnWebView= (Button) findViewById(R.id.bt_WEbVIEW);
        Button btnHttpUri= (Button) findViewById(R.id.http_uri);
        Button btnOkHttp= (Button) findViewById(R.id.bt_okHttp);
         textPlay= (TextView) findViewById(R.id.tv_play);
        btnWebView.setOnClickListener(this);
        btnHttpUri.setOnClickListener(this);
        btnOkHttp.setOnClickListener(this);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null)
            actionbar.hide();
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case  R.id.bt_WEbVIEW:
                 Intent intent =new Intent(WEBTestActivity.this,WebViewActivity.class);
                 startActivity(intent);
                 break;
             case R.id.http_uri:
                 sendRequestByHttpURLConnection();
                 break;
             case R.id.bt_okHttp:
                 sendRequestByOkHttp();
                 break;
             default:
                 break;
         }
    }

    private void sendRequestByOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    private void sendRequestByHttpURLConnection() {
        //开启线程来发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;

                try {
                    URL url=new URL("https://www.baidu.com");
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    //接下来对获取的输入流进行获取
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
             finally {
                    if (reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }

            }
        }).start();

    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textPlay.setText(response);
            }
        });
    }


}
