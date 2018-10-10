package com.example.aryamirshafii.hearingcarandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;


public class leftHornWarning extends warningPopup {
    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lefthornwarning);

       ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(6003);// 6 second warning


        dataController = new dataManager(getApplicationContext());
        arrowImage = (ImageView)findViewById(R.id.arrowImageLH);
        soundLabel = (TextView) findViewById(R.id.hornLabelLeft);
        backgroundImage = (ImageView) findViewById(R.id.backgroundImageViewLH);
        configureTheme();

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


    @Override
    public void setColors(int color){
        if(color == getResources().getColor(R.color.darkModeOrange)){
            arrowImage.setImageDrawable(getResources().getDrawable(R.drawable.leftarroworange));
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.blackbackground));
        }else if(color == getResources().getColor(R.color.white)){
            arrowImage.setImageDrawable(getResources().getDrawable(R.drawable.leftarrowwhite));
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.backgroundimage));

        }

        soundLabel.setTextColor(color);

    }
}
