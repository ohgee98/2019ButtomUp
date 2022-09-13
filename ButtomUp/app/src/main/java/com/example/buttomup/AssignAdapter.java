package com.example.buttomup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.MyViewHolder>{
    private ArrayList<ReportData> mDataset;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, start, end, submit;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView_list_title);
            start = (TextView) view.findViewById(R.id.textView_list_startTime);
            end = (TextView) view.findViewById(R.id.textView_list_endTime);
            submit = (TextView) view.findViewById(R.id.textView_list_submit);
        }
    }

    public AssignAdapter(ArrayList<ReportData> myData){
        this.mDataset = myData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignAdapter.MyViewHolder holder, int position) {

        holder.title.setText(mDataset.get(position).getTitle());
        holder.start.setText(mDataset.get(position).getStart());
        holder.end.setText(mDataset.get(position).getEnd());
        holder.submit.setText(mDataset.get(position).getSubmit());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
