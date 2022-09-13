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

public class LSubjectAdapter extends RecyclerView.Adapter<LSubjectAdapter.CustomViewHolder> {
    private ArrayList<L_SubjectData> mList = null;
    private Activity context = null;

    public LSubjectAdapter(Activity context, ArrayList<L_SubjectData> list){
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView subject;
        protected Button btn;
        public CustomViewHolder(View view){
            super(view);
            this.subject = (TextView)view.findViewById(R.id.lec_subject);
            this.btn = (Button)view.findViewById(R.id.in_lecture);
        }
    }

    @Override
    public LSubjectAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lec_subject, null);
        LSubjectAdapter.CustomViewHolder viewHolder = new LSubjectAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LSubjectAdapter.CustomViewHolder viewHolder, final int position){
        viewHolder.subject.setText(mList.get(position).getSubject());
        Button btn = viewHolder.btn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), LectureActivity.class);
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
