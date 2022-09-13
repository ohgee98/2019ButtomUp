package com.example.buttomup;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.CustomViewHolder> {
    private ArrayList<SubjectData> mList = null;

    public SubjectAdapter(ArrayList<SubjectData> list){
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView subject;
        protected  Button btn;
        public CustomViewHolder(View view){
            super(view);
            this.subject = (TextView)view.findViewById(R.id.subject);
            this.btn = (Button)view.findViewById(R.id.in_item);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, final int position){
        viewHolder.subject.setText(mList.get(position).getSubject());
        Button btn = viewHolder.btn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ReportActivity.class);
                intent.putExtra("data",mList.get(position).getSubject());
                intent.putExtra("id",mList.get(position).getStdnt_no());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}
