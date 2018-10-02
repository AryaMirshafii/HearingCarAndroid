package com.example.aryamirshafii.hearingcarandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Vibrator;


public class leftHornWarning extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lefthornwarning);

       ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(6003);// 6 second warning
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                // dont do anything here
            }

            public void onFinish() {
                Intent myIntent = new Intent(leftHornWarning.this, MainActivity.class);
                //finish();
                startActivity(myIntent);

            }
        }.start();

    }
}
