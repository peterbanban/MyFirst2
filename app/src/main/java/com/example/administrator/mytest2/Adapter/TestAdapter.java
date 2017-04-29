package com.example.administrator.mytest2.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytest2.R;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/04/16.
 */

  //1.继承ArrayAdapter
public class TestAdapter extends ArrayAdapter {
    List<Map.Entry<String,Integer>>list;
    Context context;
    int  layoutId;

    public TestAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        layoutId=resource;
        list=objects;
        this.context=context;
    }
      //2.继承BaseAdapter
//    public TestAdapter( Context context, int resource, List<Map.Entry<String,Integer>> list) {
//        super(context, resource);
//        this.list = list;
//        layoutId=resource;
//    }
//public class TestAdapter extends BaseAdapter {
//    List<Map.Entry<String,Integer>>list;
//    Context context;
//
//    public TestAdapter(List<Map.Entry<String, Integer>> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
         if (convertView==null){
            viewHolder=new ViewHolder();
              convertView= LayoutInflater.from(context).inflate(layoutId,parent,false);

             viewHolder.testImage= (ImageView) convertView.findViewById(R.id.image_id);
                     viewHolder.testText= (TextView) convertView.findViewById(R.id.text_id);
             convertView.setTag(viewHolder);
         }
          else{
             viewHolder=(ViewHolder)convertView.getTag();
         }
        viewHolder.testImage.setImageResource(list.get(position).getValue());
        viewHolder.testText.setText(list.get(position).getKey());
        return convertView;
    }
    class ViewHolder{
        TextView testText;
        ImageView testImage;
    }
}
