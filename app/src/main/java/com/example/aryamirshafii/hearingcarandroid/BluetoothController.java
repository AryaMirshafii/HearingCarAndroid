package com.example.aryamirshafii.hearingcarandroid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.scan.ScanSettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class BluetoothController {

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
    private Disposable connectionDisposable;

    private String previousPacketString = "";

    private boolean deviceExists = false;


    private Disposable scanSubscription;


    private TextView bluetoothLabel;

    private PopupWindow popupWindow;

    private BluetoothDevice audioBluetooth;
    private UUID audioUUID = UUID.fromString("0000110A-0000-1000-8000-00805F9B34FB");

    private RxBleConnection.LongWriteOperationBuilder longWriteConnection;

    private String currentCommand = "";





    public BluetoothController(Context appContext, TextView btLabel){
        this.context = appContext;
        this.rxBleClient = RxBleClient.create(appContext);

        this.bluetoothLabel = btLabel;



        uuid =  UUID.fromString("0000FFE1-0000-1000-8000-00805F9B34FB");

        packetString = "";
        //device = rxBleClient.getBleDevice(address);
        scan();
        if(deviceExists){
            System.out.println("The device exists");
            scanAndConnect();

        } else{

            System.out.println("The device does not exist");
        }






    }










    private void scanAndConnect(){
        scanSubscription.dispose();
        System.out.println("Scanning and connecting");
        connectionObservable = prepareConnectionObservable();
        connect();
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
                                bluetoothLabel.setText("Connected To Device");
                                deviceExists = true;
                                scanAndConnect();


                            }else {
                                bluetoothLabel.setText("Device Not Connected");
                            }


                        },
                        throwable -> {
                            System.out.println("An error occured while trying to scan for devices");
                            throwable.printStackTrace();
                        }
                );

        //scanSubscription.dispose();
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
                                encodedString = trimString(encodedString);


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

    /**
     * Trims a string depending on the characters it contains
     * More specific then the normal trim function
     * @param string the string that needs to be trimmed
     * @return
     */
    public String trimString(String string){
        //string = string.replace("\n", "");
        string = string.replaceAll("[^\\x00-\\x7F]", "");
        string = string.replaceAll("[\u0000-\u001f]", "");
        string = string.trim();
        string = string.replace("*", " ");
        System.out.println("The trimmed string is1:" + string + ":");


        /**
        //string = string.trim();
        if(string.length() > 3 && string.charAt(string.length() -2)== '_'){
            System.out.println("Created substring");
            string = string.substring(0,string.length() -1);
        }
         */
        //string = string.replace("\n", "");


        return string;


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