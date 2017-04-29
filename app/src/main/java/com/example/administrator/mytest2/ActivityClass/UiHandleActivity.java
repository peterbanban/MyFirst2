package com.example.administrator.mytest2.ActivityClass;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.mytest2.R;

public class UiHandleActivity extends AppCompatActivity {

    static  int updateData=112;
    private TextView text;
    Button btn1;
    Button btnNext;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_handle);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null)
            actionBar.hide();
        text= (TextView) findViewById(R.id.update_tex);
        btnNext= (Button) findViewById(R.id.btn_next);
        btn1= (Button) findViewById(R.id.update_btn);
        btn2= (Button) findViewById(R.id.btn_content);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=updateData;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UiHandleActivity.this,ServiceTest.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(UiHandleActivity.this,ContentProviderActivity.class);
                startActivity(intent2);
            }
        });

    }
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 112:
                     text.setText("你使用异步进程更新了UI");
                    break;
                 default:
                       break;
            }
        }
    };


}
