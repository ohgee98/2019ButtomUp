package com.example.buttomup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnyNoticeAdapter  extends RecyclerView.Adapter<AnyNoticeAdapter.CustomViewHolder> {
    private ArrayList<AnyNoticeData> mList = null;
    private Activity context = null;

    public AnyNoticeAdapter(Activity context, ArrayList<AnyNoticeData> list){
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView url;
        protected TextView date;
        protected TextView writter;

        public CustomViewHolder(View view){
            super(view);
            this.title = (TextView)view.findViewById(R.id.notice_title);
            this.url = (TextView)view.findViewById(R.id.notice_url);
            this.date = (TextView)view.findViewById(R.id.notice_date);
            this.writter = (TextView)view.findViewById(R.id.notice_writter);
        }
    }

    @Override
    public AnyNoticeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.anynotice_list, null);
        AnyNoticeAdapter.CustomViewHolder viewHolder = new AnyNoticeAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnyNoticeAdapter.CustomViewHolder viewHolder, int position){
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.url.setText(mList.get(position).getUrl());
        viewHolder.date.setText(mList.get(position).getDate());
        viewHolder.writter.setText(mList.get(position).getWritter());
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}
