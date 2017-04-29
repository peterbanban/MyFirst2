package com.example.administrator.mytest2.GeneralClass;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.mytest2.R;

/**
 * Created by Administrator on 2017/04/15.
 */
     // 自定义控件 统一的标题栏
public class TitleLayout extends LinearLayout{
    public TitleLayout(final Context context, AttributeSet attrs){

        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button titleBack= (Button) findViewById(R.id.title_back);
        Button titleEdit= (Button) findViewById(R.id.title_edit);

        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(getContext(),"hello Word",Toast.LENGTH_LONG).show();
            }
        });
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

    }
}
