package com.example.administrator.mytest2.ServiceClass;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.administrator.mytest2.ActivityClass.ServiceTest;

/**
 * Created by Administrator on 2017/04/17.
 */

public class MyIntentService extends IntentService {
    public  static   Context context;
    public MyIntentService() {
        super("MyIntentService");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Toast.makeText(context,"创建IntentService",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(context,"销毁IntentService",Toast.LENGTH_LONG).show();
        super.onDestroy();

    }
}
