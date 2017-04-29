package com.example.administrator.mytest2.ActivityClass;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.mytest2.R;
import com.example.administrator.mytest2.ServiceClass.MyIntentService;
import com.example.administrator.mytest2.ServiceClass.MyService;

import static android.R.attr.start;

public class ServiceTest extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        Button btnStartBind=(Button) findViewById(R.id.btn_startBind);
        Button btnStopBind=(Button) findViewById(R.id.btn_stopBind);
        Button btnStop= (Button) findViewById(R.id.btn_stop);
        Button btnStart= (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStartBind.setOnClickListener(this);
        btnStopBind.setOnClickListener(this);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        MyService.context=ServiceTest.this;
        MyIntentService.context=ServiceTest.this;
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.btn_start:
                 Intent startIntent=new Intent(this, MyService.class);
                 startService(startIntent);
                 break;
             case R.id.btn_stop:
                 Intent stopIntent=new Intent(this,MyService.class);
                 stopService(stopIntent);
                 break; 
             case R.id.btn_startBind:
                 Intent intentService=new Intent(this,MyIntentService.class);
                 Toast.makeText(this,"创建IntentService1",Toast.LENGTH_LONG).show();
                 startService(intentService);
                 break;
             case R.id.btn_stopBind:
                 Intent queryIntent =new Intent(ServiceTest.this,QueryActivity.class);
                 startActivity(queryIntent);
             default:
                 break;
         }
    }
}
