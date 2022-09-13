package com.example.buttomup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment1 extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ReportData> arrayList;
    private AssignAdapter usersAdapter;
    private String mJsonString;
    private String stdnt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.assignlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();

        arrayList.clear();
        usersAdapter = new AssignAdapter(arrayList);
        recyclerView.setAdapter(usersAdapter);

        arrayList.clear();
        usersAdapter.notifyDataSetChanged();

        return v;
    }
    public Fragment1(String id){
        this.stdnt = id;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment1.GetData task = new Fragment1.GetData();
        task.execute("http://ec2-54-180-87-74.ap-northeast-2.compute.amazonaws.com/report.php", "");
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);

            mJsonString = result;
            showResult();
        }

        @Override
        protected String doInBackground(String... params){
            String serverURL = params[0];
            String postParameters = params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult(){
        String TAG_STDNT_NO = "stdnt_no";
        String TAG_ID = "id";
        String TAG_JSON="reports";
        String TAG_TITLE = "title";
        String TAG_START = "start_time";
        String TAG_END = "end_time";
        String TAG_TYPE = "type";
        String TAG_SUBJECT = "subject";
        String TAG_NO = "report_no";
        String TAG_SUBMIT = "is_submit";
        String TAG_INCLUDE = "is_include";
        String TAG_PROGRESS = "is_press";
        String TAG_UPDATED = "updated_time";

        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            System.out.println("mJSonString : "+mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i = 0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String stdnt_no = item.getString(TAG_STDNT_NO);
                String id = item.getString(TAG_ID);
                String title = item.getString(TAG_TITLE);
                String start = item.getString(TAG_START);
                String end = item.getString(TAG_END);
                String type = item.getString(TAG_TYPE);
                String subject = item.getString(TAG_SUBJECT);
                String no = item.getString(TAG_NO);
                String submit = item.getString(TAG_SUBMIT);
                String include = item.getString(TAG_INCLUDE);
                String progress = item.getString(TAG_PROGRESS);
                String updated = item.getString(TAG_UPDATED);

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String getTime = sdf.format(date);
                Date now_time = sdf.parse(getTime);
                Date end_time = sdf.parse(end);
                if((stdnt.equals(stdnt_no))&&(now_time.getTime()<=end_time.getTime())) {
                    ReportData reportData = new ReportData();

                    reportData.setTitle(title);
                    reportData.setStart(start);
                    reportData.setEnd(end);
                    reportData.setSubmit(submit);
                    arrayList.add(reportData);
                    usersAdapter.notifyDataSetChanged();
                }
            }
        }catch(JSONException e){
            Log.d("","showResult: ",e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
