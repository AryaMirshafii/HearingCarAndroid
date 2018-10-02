package com.example.aryamirshafii.hearingcarandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

public class leftSirenWarning extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.leftsirenwarning);

        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(6003);// 6 second warning
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                // dont do anything here
            }

            public void onFinish() {
                Intent myIntent = new Intent(leftSirenWarning.this, MainActivity.class);
                //finish();
                startActivity(myIntent);

            }
        }.start();

    }
}
