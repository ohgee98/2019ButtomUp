package com.example.buttomup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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

public class AnyNoticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<AnyNoticeData> arrayList;
    private AnyNoticeAdapter usersAdapter;
    private String mJsonString;
    private String typename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_notice);


        Intent intent = getIntent();
        this.typename = intent.getStringExtra("data");
        recyclerView = (RecyclerView) findViewById(R.id.anynotice_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();

        arrayList.clear();
        usersAdapter = new AnyNoticeAdapter(this, arrayList);
        recyclerView.setAdapter(usersAdapter);

        arrayList.clear();
        usersAdapter.notifyDataSetChanged();

        AnyNoticeActivity.GetData task = new AnyNoticeActivity.GetData();
        task.execute("http://ec2-54-180-87-74.ap-northeast-2.compute.amazonaws.com/notice.php","");
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AnyNoticeActivity.this,"Please Wait", null, true, true);
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
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
        String TAG_JSON="notices";
        String TAG_TYPE = "type";
        String TAG_TITLE = "title";
        String TAG_URL = "url";
        String TAG_WRITTER = "writter";
        String TAG_DATE = "date";
        String TAG_UPDATED_TIME = "updated_time";
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            System.out.println("mJSonString : "+mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i = 0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String type = item.getString(TAG_TYPE);
                String title = item.getString(TAG_TITLE);
                String url = item.getString(TAG_URL);
                String writter = item.getString(TAG_WRITTER);
                String date = item.getString(TAG_DATE);
                String updated_time = item.getString(TAG_UPDATED_TIME);
                if(typename.equals(type)) {
                    AnyNoticeData anyNoticeData = new AnyNoticeData();
                    System.out.println("typename: "+typename);
                    anyNoticeData.setTitle(title);
                    anyNoticeData.setUrl(url);
                    anyNoticeData.setDate(date);
                    anyNoticeData.setWritter(writter);

                    arrayList.add(anyNoticeData);
                    usersAdapter.notifyDataSetChanged();
                }
            }
        }catch(JSONException e){
            Log.d("","showResult: ",e);
        }
    }
}
