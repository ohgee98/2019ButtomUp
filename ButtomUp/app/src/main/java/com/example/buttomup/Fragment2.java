package com.example.buttomup;

import android.app.ProgressDialog;
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
import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<SubjectData> arrayList;
    private SubjectAdapter subjectAdapter;
    private String mJsonString;
    private String stdnt;
    ArrayList<String> subject = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.subjectlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        arrayList = new ArrayList<>();

        arrayList.clear();
        subjectAdapter = new SubjectAdapter(arrayList);
        recyclerView.setAdapter(subjectAdapter);

        arrayList.clear();
        subjectAdapter.notifyDataSetChanged();
        return v;
    }
    public Fragment2(String id){
        this.stdnt = id;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment2.GetData task = new Fragment2.GetData();
        task.execute("http://ec2-54-180-87-74.ap-northeast-2.compute.amazonaws.com/report.php","");
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
                String temp = item.getString(TAG_SUBJECT);
                String no = item.getString(TAG_NO);
                String submit = item.getString(TAG_SUBMIT);
                String include = item.getString(TAG_INCLUDE);
                String progress = item.getString(TAG_PROGRESS);
                String updated = item.getString(TAG_UPDATED);
                if((stdnt.equals(stdnt_no))&&(!subject.contains(temp))) {
                    subject.add(temp);
                    System.out.println("subject: "+temp);
                    SubjectData subjectData = new SubjectData();
                    subjectData.setSubject(temp);
                    subjectData.setStdnt_no(stdnt_no);
                    arrayList.add(subjectData);
                    subjectAdapter.notifyDataSetChanged();
                }

            }
        }catch(JSONException e){
            Log.d("","showResult: ",e);
        }
    }
}
