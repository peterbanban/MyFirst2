package com.example.administrator.mytest2.ActivityClass;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.administrator.mytest2.Adapter.RecycleAdapter;
import com.example.administrator.mytest2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecycleActivity extends AppCompatActivity {

    List<Map.Entry<String,Integer>> bList;
    Map<String,Integer> map=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }

        map.put("apple1",R.drawable.a);
        map.put("apple2",R.drawable.b);
        map.put("apple3",R.drawable.c);
        map.put("apple4",R.drawable.d);
        Set<Map.Entry<String,Integer>> set=map.entrySet();

        bList=new ArrayList(set);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycle_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);    //设置为水平滚动，删掉即为竖直
        recyclerView.setLayoutManager(layoutManager);
        RecycleAdapter recycleAdapter=new RecycleAdapter(bList,RecycleActivity.this);
        recyclerView.setAdapter(recycleAdapter);

    }
}
