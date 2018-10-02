package com.example.aryamirshafii.hearingcarandroid;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.ParcelUuid;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import android.widget.Button;

import com.budiyev.android.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {


    private BluetoothController myBluetooth;
    private dataManager dataController;


    /**
     * Label declarations
     */
    private TextView  bluetoothStatusLabel;


    private BluetoothDevice hearingBluetooth;




    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        dataController = new dataManager(this.getApplicationContext());


        bluetoothStatusLabel = (TextView) findViewById(R.id.bluetoothLabel);



        configureButtons();


        //TODO Uncomment
        //myBluetooth = new BluetoothController(getApplicationContext(), bluetoothStatusLabel);



        checkWeek();


    }



    private void configureButtons(){

        Button rightButton = (Button) findViewById(R.id.triggerRightHornWarning);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, rightHornWarning.class);
                dataController.incrementRightWarnings();
                startActivity(myIntent);
            }
        });


        Button leftButton = (Button) findViewById(R.id.triggerLeftHornWarning);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, leftHornWarning.class);
                dataController.incrementLeftWarnings();
                startActivity(myIntent);
            }
        });

        Button rightButton2 = (Button) findViewById(R.id.triggerRightSirenWarning);
        rightButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, rightSirenWarning.class);
                dataController.incrementRightWarnings();
                startActivity(myIntent);
            }
        });


        Button leftButton2 = (Button) findViewById(R.id.triggerLeftSirenWarning);
        leftButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, leftSirenWarning.class);
                dataController.incrementLeftWarnings();
                startActivity(myIntent);
            }
        });



        Button leftIncrement = (Button) findViewById(R.id.IncrementLeftCount);
        leftIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataController.incrementLeftWarnings();

            }
        });
        ImageButton helpButton = (ImageButton) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, helpScreen.class);
                startActivity(myIntent);
            }
        });

        /**
        leftButton.setVisibility(View.INVISIBLE);
        leftButton2.setVisibility(View.INVISIBLE);
        rightButton.setVisibility(View.INVISIBLE);
        rightButton2.setVisibility(View.INVISIBLE);
        leftIncrement.setVisibility(View.INVISIBLE);
        helpButton.setVisibility(View.INVISIBLE);
         */
    }




    private void notifyUser(String data) {
        bluetoothStatusLabel.setText("Connected to HearingCar");

        System.out.println("this is being called " + data);
        if (data.equals("1")) {
            // Left horn
            System.out.println("left horn started");
            Intent myIntent = new Intent(MainActivity.this, leftHornWarning.class);
           // myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            dataController.incrementLeftWarnings();
            finish();
            startActivity(myIntent);



        } else if(data.equals("2")) {
            //Left siren
            System.out.println("left siren started");
            Intent myIntent = new Intent(MainActivity.this, leftSirenWarning.class);
            //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            dataController.incrementLeftWarnings();
            finish();
            startActivity(myIntent);


        } else if (data.equals("3")) {

            //right siren
            System.out.println("right siren started");
            Intent myIntent = new Intent(MainActivity.this, rightSirenWarning.class);
            //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            dataController.incrementRightWarnings();
            startActivity(myIntent);
            finish();



        } else if(data.equals("4")) {
            // Right horn
            System.out.println("right horn started");
            Intent myIntent = new Intent(MainActivity.this, rightHornWarning.class);
            //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            dataController.incrementRightWarnings();
            finish();
            startActivity(myIntent);



        }
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    private void checkWeek(){
        Calendar cal = Calendar.getInstance();
        int currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

        SharedPreferences sharedPreferences= this.getSharedPreferences("appInfo", 0);
        int weekOfYear = sharedPreferences.getInt("weekOfYear", 0);

        if(weekOfYear != currentWeekOfYear){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("weekOfYear", currentWeekOfYear);
            editor.commit();
            dataController.clear();
        }
    }


}