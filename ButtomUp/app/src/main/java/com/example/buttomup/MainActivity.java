package com.example.buttomup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(!task.isSuccessful()){
                            Log.w("FCM Log", "getInstanceId failed",task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM Log","FCM 토큰: "+token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        Intent intent = getIntent();
        id = intent.getStringExtra("data");
        Button report = (Button)findViewById(R.id.btn_report);
        Button lecture = (Button)findViewById(R.id.btn_lecture);
        Button alarm = (Button)findViewById(R.id.btn_alarm);
        Button notice = (Button)findViewById(R.id.btn_notice);

        notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent noticeintent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(noticeintent);
            }
        });
        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent reportintent = new Intent(MainActivity.this, AssignActivity.class);
                reportintent.putExtra("data",id);
                startActivity(reportintent);
            }
        });
        lecture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent lectureintent = new Intent(MainActivity.this, L_SubjectActivity.class);
                lectureintent.putExtra("data",id);
                startActivity(lectureintent);
            }
        });
        alarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent alarmintent = new Intent(MainActivity.this, AlarmActivity.class);
                alarmintent.putExtra("data",id);
                startActivity(alarmintent);
            }
        });
    }
}
