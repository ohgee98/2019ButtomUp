package com.example.buttomup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {
    private ArrayList<ReportData> mList = null;
    private Activity context = null;

    public UsersAdapter(Activity context, ArrayList<ReportData> list){
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView start;
        protected TextView end;
        protected TextView submit;

        public CustomViewHolder(View view){
            super(view);
            this.title = (TextView)view.findViewById(R.id.textView_list_title);
            this.start = (TextView)view.findViewById(R.id.textView_list_startTime);
            this.end = (TextView)view.findViewById(R.id.textView_list_endTime);
            this.submit = (TextView)view.findViewById(R.id.textView_list_submit);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position){
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.start.setText(mList.get(position).getStart());
        viewHolder.end.setText(mList.get(position).getEnd());
        viewHolder.submit.setText(mList.get(position).getSubmit());
    }

    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}
