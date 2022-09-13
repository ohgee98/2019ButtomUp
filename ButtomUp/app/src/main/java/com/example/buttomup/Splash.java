package com.example.buttomup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitiy_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(),3000);
    }

    private class splashhandler implements Runnable {
        public void run(){
            startActivity(new Intent(getApplication(),LoginActivity.class));
            Splash.this.finish();
        }
    }

    @Override
    public void onBackPressed(){

    }

}
