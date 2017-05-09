package com.example.administrator.mytest2.ActivityClass;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.mytest2.HTTPRequest.HttpCallBackListener;
import com.example.administrator.mytest2.HTTPRequest.HttpRequest;
import com.example.administrator.mytest2.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WEBTestActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textPlay;
    TextView textPlay1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtest);
        Button btnWebView= (Button) findViewById(R.id.bt_WEbVIEW);
        Button btnHttpUri= (Button) findViewById(R.id.http_uri);
        Button btnOkHttp= (Button) findViewById(R.id.bt_okHttp);
         textPlay1= (TextView) findViewById(R.id.tv_play1);
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
                 //使用安卓原生的HttpURLConnection向服务器发出请求
//                 String responseData=HttpRequest.sendRequestByHttpURLConnection("https://www.qq.com");
//                 showResponse(responseData);
                 HttpRequest.sendRequestByHttpURLConnection("https://www.baidu.com", new HttpCallBackListener() {
                     @Override
                     public void onFinish(String response) {
                         showResponse(response);
                     }

                     @Override
                     public void onError(Exception e) {

                     }
                 });
                 break;
             case R.id.bt_okHttp:
//                 使用开源库OKHttp向服务器发出请求
                 HttpRequest.sendRequestByOkHttp("https://www.baidu.com", new okhttp3.Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {

                     }

                     @Override
                     public void onResponse(Call call, Response response) throws IOException {
                         String responseData=response.body().string();
                         showResponse(responseData);
                     }
                 });
                 break;
             default:
                 break;
         }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textPlay.setText(response);
                textPlay1.setText(HttpRequest.content);
            }
        });
    }


}
