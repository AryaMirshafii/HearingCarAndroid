package com.example.aryamirshafii.hearingcarandroid;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class warningPopup extends AppCompatActivity {

    public dataManager dataController;

    public ImageView arrowImage;
    public TextView soundLabel;
    public ImageView backgroundImage;

    public void configureTheme(){
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







    public void setColors(int color){
        if(color == getResources().getColor(R.color.darkModeOrange)){
            arrowImage.setImageDrawable(getResources().getDrawable(R.drawable.rightarroworange));
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.blackbackground));
        }else if(color == getResources().getColor(R.color.white)){
            arrowImage.setImageDrawable(getResources().getDrawable(R.drawable.rightarrowwhite));
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.backgroundimage));

        }

        soundLabel.setTextColor(color);

    }


}
