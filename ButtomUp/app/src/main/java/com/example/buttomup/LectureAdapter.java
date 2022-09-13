package com.example.buttomup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.CustomViewHolder> {
private ArrayList<LectureData> mList = null;
private Activity context = null;

public LectureAdapter(Activity context, ArrayList<LectureData> list){
        this.context = context;
        this.mList = list;
        }

class CustomViewHolder extends RecyclerView.ViewHolder{
    protected TextView title;
    protected TextView start;
    protected TextView end;
    protected TextView state;

    public CustomViewHolder(View view){
        super(view);
        this.title = (TextView)view.findViewById(R.id.lecture_title);
        this.start = (TextView)view.findViewById(R.id.lecture_startTime);
        this.end = (TextView)view.findViewById(R.id.lecture_endTime);
        this.state = (TextView)view.findViewById(R.id.lecture_state);
    }
}

    @Override
    public LectureAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lecture_list, null);
        LectureAdapter.CustomViewHolder viewHolder = new LectureAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LectureAdapter.CustomViewHolder viewHolder, int position){
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.start.setText(mList.get(position).getStart());
        viewHolder.end.setText(mList.get(position).getEnd());
        viewHolder.state.setText(mList.get(position).getState());
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}
