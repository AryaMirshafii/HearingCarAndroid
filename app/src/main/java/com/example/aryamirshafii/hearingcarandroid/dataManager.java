package com.example.aryamirshafii.hearingcarandroid;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class dataManager {
    private Context context;

    private String leftWarningFileName = "leftWarnings.txt";

    private String rightWarningFileName = "rightWarnings.txt";






    public  dataManager(Context context){
        this.context = context;


    }


    public void incrementLeftWarnings(){
        int previousNumberofLeftWarnings = Integer.parseInt(getLeftWarnings());
        previousNumberofLeftWarnings++;

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(leftWarningFileName , Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(previousNumberofLeftWarnings).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getLeftWarnings(){
        FileInputStream fis;
        int n;
        try {
            fis = context.openFileInput(leftWarningFileName);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];



            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }

            return fileContent.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "0";

    }

    public void incrementRightWarnings(){
        int previousNumberofWarnings = Integer.parseInt(getRightWarnings());
        previousNumberofWarnings++;

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(rightWarningFileName , Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(previousNumberofWarnings).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getRightWarnings(){
        FileInputStream fis;
        int n;
        try {
            fis = context.openFileInput(rightWarningFileName);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];


            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }

            return fileContent.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "0";

    }

    public void clear() {

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(leftWarningFileName , Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(0).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }






        try {
            outputStream = context.openFileOutput(rightWarningFileName , Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(0).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTotalWarnings() {
        int leftCount =  Integer.parseInt(getLeftWarnings());
        int rightCount =  Integer.parseInt(getRightWarnings());
        return Integer.toString(leftCount + rightCount);
    }
}
