package com.example.administrator.mytest2.ActivityClass;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mytest2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class ContentProviderActivity extends AppCompatActivity {

    List<String> contentList=new ArrayList<>();
    ArrayAdapter<String> contentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        ListView listView= (ListView) findViewById(R.id.list_content);
        contentAdapter =new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,contentList);
        listView.setAdapter(contentAdapter);

         //检查是否已经授权，没有则询问是否授权此应用，无论是否同意授权结果都会回调在onRequestPermissionsResult方法中
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_CONTACTS},1);
        }
         else{
             readContacts();
        }
}
private void readContacts() {
      Cursor cursor=null;
    try {
           //通过query查询后把手机里联系人信息传递给cursor对象,
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        if (cursor != null)  {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String displayPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contentList.add(displayName + "\n" + displayPhone);
                }
                contentAdapter.notifyDataSetChanged();
            }
    }catch(Exception e){
        e.printStackTrace();

    }finally {
        if (cursor!=null)
        {
            cursor.close();
        }
    }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                //回调结果封装在grantResult中，PERMISSION_GRANTED为准许
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else
                {
                    Toast.makeText(this,"没有成功获取权限",Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }
}