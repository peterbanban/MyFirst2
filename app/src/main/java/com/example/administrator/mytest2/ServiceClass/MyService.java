package com.example.administrator.mytest2.ServiceClass;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.administrator.mytest2.ActivityClass.ServiceTest;
import com.example.administrator.mytest2.R;

import static android.R.attr.start;

public class MyService extends Service {
   public static Context context;



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //下面代码用于在通知栏显示服务的信息 pendingIntent用于设置下一步执行的意向
        Toast.makeText(context,"创建服务",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this, ServiceTest.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("标题")
                .setContentText("文本")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.a)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.a))
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(context,"启动服务",Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        Toast.makeText(context,"销毁服务",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
