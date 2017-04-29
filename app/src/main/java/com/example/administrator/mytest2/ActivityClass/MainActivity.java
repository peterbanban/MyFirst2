 package com.example.administrator.mytest2.ActivityClass;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mytest2.Adapter.TestAdapter;
import com.example.administrator.mytest2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

 public class MainActivity extends AppCompatActivity {

      List<Map.Entry<String,Integer>> aList;
      Map<String,Integer> map=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
        ListView listview= (ListView) findViewById(R.id.list_id);
        map.put("apple1",R.drawable.a);
        map.put("apple2",R.drawable.b);
        map.put("apple3",R.drawable.c);
        map.put("apple4",R.drawable.d);
        Set<Map.Entry<String,Integer>> set=map.entrySet();


        aList=new ArrayList(set);
        Toast.makeText(MainActivity.this,aList.get(0).getKey(),Toast.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this,aList.get(1).getKey(),Toast.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this,aList.get(2).getKey(),Toast.LENGTH_LONG).show();

        TestAdapter testAdapter=new TestAdapter(MainActivity.this,R.layout.test_list,aList);
        listview.setAdapter(testAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,RecycleActivity.class);
                startActivity(intent);
            }
        });

    }

}
