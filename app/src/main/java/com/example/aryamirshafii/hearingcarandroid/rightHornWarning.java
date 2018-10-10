package com.example.aryamirshafii.hearingcarandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by aryamirshafii on 2/14/18.
 */

public class rightHornWarning extends warningPopup {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.righthornwarning);
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(6003);// 6 second warning
        dataController = new dataManager(getApplicationContext());
        arrowImage = (ImageView)findViewById(R.id.arrowImageRH);
        soundLabel = (TextView) findViewById(R.id.hornLabelRight);
        backgroundImage = (ImageView) findViewById(R.id.backgroundImageViewRH);
        configureTheme();
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                //dont do anything we dont care
            }

            public void onFinish() {
                Intent myIntent = new Intent(rightHornWarning.this, MainActivity.class);
               // finish();

                startActivity(myIntent);

            }
        }.start();

    }
}
