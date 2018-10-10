package com.example.aryamirshafii.hearingcarandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class SettingScreen extends AppCompatActivity {
    private dataManager dataController;

    private ImageView backgroundImage;
    private ImageButton closeButton;

    private TextView fontHeader;

    private TextView theme1;
    private TextView theme2;
    private TextView theme3;


    private Switch theme1Switch;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingscreen);
        dataController = new dataManager(getApplicationContext());
        closeButton= (ImageButton) findViewById(R.id.closeButton);

        backgroundImage = (ImageView) findViewById(R.id.backgroundImageView);




        fontHeader = (TextView) findViewById(R.id.fontHeader);

        theme1 = (TextView) findViewById(R.id.theme1);
        theme2 = (TextView) findViewById(R.id.theme2);
        theme3 = (TextView) findViewById(R.id.theme3);
        theme1Switch = (Switch) findViewById(R.id.theme1Switch);

        String theme = dataController.getTheme();
        if(theme.equals("dark")){
            theme1Switch.setOnCheckedChangeListener (null);
            theme1Switch.setChecked(true);
            setColors(getResources().getColor(R.color.darkModeOrange));

        }else{
            setColors(getResources().getColor(R.color.white));
        }
        configureSwitches();
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingScreen.this, MainActivity.class);
                //finish();
                startActivity(myIntent);
            }
        });

    }


    private void configureSwitches(){


        theme1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println("Theme has been changed to Dark");
                    dataController.setTheme("dark");
                    setColors(getResources().getColor(R.color.darkModeOrange));
                }else{
                    System.out.println("Theme has been changed to normal");
                    dataController.setTheme("default");
                    setColors(getResources().getColor(R.color.white));


                }



            }
        });
    }



    private void setColors(int color){
        if(color == getResources().getColor(R.color.darkModeOrange)){
            closeButton.setBackgroundResource(R.drawable.verifiedorange);
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.blackbackground));
        }else if(color == getResources().getColor(R.color.white)){
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.backgroundimage));
            closeButton.setBackgroundResource(R.drawable.verifiedwhite);
        }

        fontHeader.setTextColor(color);
        theme1.setTextColor(color);
        theme2.setTextColor(color);
        theme3.setTextColor(color);


    }
}
