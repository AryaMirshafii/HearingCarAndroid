package com.example.aryamirshafii.hearingcarandroid;
import android.annotation.SuppressLint;
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
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.ParcelUuid;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import android.widget.Button;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.scan.ScanSettings;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {



    private dataManager dataController;


    /**
     * Label declarations
     */
    private TextView  bluetoothStatusLabel;


    /** BLUETOOTH DECLERATIONS BEGIN HERE
     *
     */
    private BluetoothDevice hearingBluetooth;

    private RxBleClient rxBleClient;
    private Context context;

    private UUID uuid;

    private String packetString;
    private String address = "C8:DF:84:2A:56:13";
    private final String deviceName = "NileReverb "; // Name has a space at the end due to weird  behavior from AT commands

    private RxBleDevice device;




    private RxBleConnection bleConnection;
    private io.reactivex.Observable<RxBleConnection> connectionObservable;
    private PublishSubject<Boolean> disconnectTriggerSubject = PublishSubject.create();

    private boolean deviceExists = false;


    private Disposable scanSubscription;


    private Disposable connectionDisposable;


    private ImageView backgroundImage;


    private ImageButton helpButton;
    private  ImageButton settingsButton;
    private TextView mphLabel;
    private TextView directionLabel;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainscreen);
        dataController = new dataManager(this.getApplicationContext());


        bluetoothStatusLabel = (TextView) findViewById(R.id.bluetoothLabel);
        mphLabel = (TextView)  findViewById(R.id.mphLabel);
        directionLabel = (TextView)  findViewById(R.id.directionLabel);
        backgroundImage = (ImageView) findViewById(R.id.backgroundImageView);

        configureButtons();



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
        helpButton = (ImageButton) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, helpScreen.class);
                startActivity(myIntent);
            }
        });


        settingsButton = (ImageButton) findViewById(R.id.settingButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SettingScreen.class);
                startActivity(myIntent);
            }
        });


        leftButton.setVisibility(View.INVISIBLE);
        leftButton2.setVisibility(View.INVISIBLE);
        rightButton.setVisibility(View.INVISIBLE);
        rightButton2.setVisibility(View.INVISIBLE);
        leftIncrement.setVisibility(View.INVISIBLE);






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

        checkTheme();
        super.onResume();

    }





    private void checkTheme(){
        String theme = dataController.getTheme();
        if(theme == null || theme == ""){
            return;
        }

        if(theme.equals("dark")){
            setColors(getResources().getColor(R.color.darkModeOrange));

        }else{
            setColors(getResources().getColor(R.color.white));
        }
    }


    private void setColors(int color){
        if(color == getResources().getColor(R.color.darkModeOrange)){
            helpButton.setImageDrawable(getResources().getDrawable(R.drawable.orangequestionmark));
            settingsButton.setImageDrawable(getResources().getDrawable(R.drawable.darkmodesettingbutton));
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.blackbackground));
        }else if(color == getResources().getColor(R.color.white)){
            helpButton.setImageDrawable(getResources().getDrawable(R.drawable.whitequestionmark));
            settingsButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.backgroundimage));

        }

        bluetoothStatusLabel.setTextColor(color);
        mphLabel.setTextColor(color);
        directionLabel.setTextColor(color);


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


    /** Bluetooth Methods start here*********
     *
     *
     *
     *
     *
     */



    private void startBluetooth(){
        this.context = getApplicationContext();
        this.rxBleClient = RxBleClient.create(context);





        uuid =  UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB");

        packetString = "";
        //device = rxBleClient.getBleDevice(address);
        scan();

    }






    private void scan(){

        System.out.println("Starting to scan");




        scanSubscription = rxBleClient.scanBleDevices(
                new ScanSettings.Builder()
                        //.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                        .build()


        )
                .subscribe(
                        scanResult -> {

                            //System.out.println("THe device name is:" + scanResult.getBleDevice().getName()  + ":");
                            if(scanResult.getBleDevice().getName() != null && scanResult.getBleDevice().getName().equals(deviceName)){

                                device = scanResult.getBleDevice();
                                System.out.println("Found device!");
                                bluetoothStatusLabel.setText("Connected To Device");
                                deviceExists = true;
                                scanAndConnect();


                            }else {
                                bluetoothStatusLabel.setText("Device Not Connected");
                            }


                        },
                        throwable -> {
                            System.out.println("An error occured while trying to scan for devices");
                            throwable.printStackTrace();
                        }
                );

        //scanSubscription.dispose();
    }



    private void scanAndConnect(){
        scanSubscription.dispose();
        System.out.println("Scanning and connecting");
        connectionObservable = prepareConnectionObservable();
        connect();
    }



    private io.reactivex.Observable<RxBleConnection> prepareConnectionObservable() {
        return device
                .establishConnection(false);
    }


    private boolean isConnected() {
        return device.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }


    @SuppressLint("CheckResult")
    private void connect(){

        if (isConnected()) {
            triggerDisconnect();
        } else {
            connectionDisposable = device.establishConnection(false)

                    .doFinally(this::dispose)
                    .subscribe(this::onConnectionReceived, this::onConnectionFailure);
        }





    }



    @SuppressLint("CheckResult")
    public void read(){
        if (isConnected()) {


            bleConnection.setupNotification(uuid)
                    .doOnNext(notificationObservable -> {

                    })
                    .flatMap(notificationObservable -> notificationObservable) // <-- Notification has been set up, now observe value changes.
                    .subscribe(
                            bytes -> {
                                //System.out.println("Recieving data");
                                String encodedString = new String(bytes, StandardCharsets.UTF_8);
                                //encodedString = trimString(encodedString);


                                if(!encodedString.equals("")){

                                    System.out.println("The current read value is " + encodedString.trim());
                                    System.out.println("Executing command.....");


                                }



                            },
                            throwable -> {
                                System.out.println("An error occured");
                                throwable.printStackTrace();

                            }
                    );

        }else {
            connect();
            System.out.println("I am not connected to deviec in reading");
        }



    }




    private void onConnectionFailure(Throwable throwable) {
        //noinspection ConstantConditions
        System.out.println("An error occured while connecting");
        //System.out.println(throwable.));
        //bluetoothLabel.setText("Device Not Connected");
        throwable.printStackTrace();
        connect();
    }


    private void triggerDisconnect() {
        disconnectTriggerSubject.onNext(true);
    }


    private void dispose() {
        connectionDisposable = null;

    }


    private void onConnectionReceived(RxBleConnection connection) {

        System.out.println("A connection has occurred");
        bleConnection = connection;
        read();


    }

}