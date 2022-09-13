package com.example.buttomup;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.CustomViewHolder> {
    private ArrayList<NoticeData> mList = null;
    private Activity context = null;

    public NoticeAdapter(Activity context, ArrayList<NoticeData> list){
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView notice;
        protected Button btn;
        public CustomViewHolder(View view){
            super(view);
            this.notice = (TextView)view.findViewById(R.id.notices);
            this.btn = (Button)view.findViewById(R.id.in_notice);
        }
    }

    @Override
    public NoticeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noticelist, null);
        NoticeAdapter.CustomViewHolder viewHolder = new NoticeAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.CustomViewHolder viewHolder, final int position){
        viewHolder.notice.setText(mList.get(position).getType());
        Button btn = viewHolder.btn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), AnyNoticeActivity.class);
                intent.putExtra("data",mList.get(position).getType());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}
