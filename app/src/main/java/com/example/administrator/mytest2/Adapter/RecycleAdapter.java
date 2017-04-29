package com.example.administrator.mytest2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytest2.ActivityClass.UiHandleActivity;
import com.example.administrator.mytest2.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/04/16.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<Map.Entry<String,Integer>> recycleList;
    Context context;

    public RecycleAdapter(List<Map.Entry<String, Integer>> recycleList, Context context) {
        this.recycleList = recycleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(context).inflate(R.layout.recycle_test,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.recycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UiHandleActivity.class);
                context.startActivity(intent);
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.testText.setText(recycleList.get(position).getKey());
        holder.testImage.setImageResource(recycleList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return recycleList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView testImage;
        TextView testText;
        View recycleView;
        public ViewHolder(View view){
            super(view);
            testImage= (ImageView) view.findViewById(R.id.image_recycle);
            testText= (TextView) view.findViewById(R.id.text_recycle);
            recycleView=view;
        }
    }
}
