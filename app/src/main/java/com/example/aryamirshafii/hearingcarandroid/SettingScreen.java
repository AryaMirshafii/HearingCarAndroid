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
    private TextView theme4;
    private TextView theme5;


    private Switch theme1Switch;
    private Switch theme2Switch;
    private Switch theme3Switch;
    private Switch theme4Switch;
    private Switch theme5Switch;
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
        theme4 = (TextView) findViewById(R.id.theme4);
        theme5 = (TextView) findViewById(R.id.theme5);
        theme1Switch = (Switch) findViewById(R.id.theme1Switch);
        theme2Switch = (Switch) findViewById(R.id.theme2Switch);
        theme3Switch = (Switch) findViewById(R.id.theme3Switch);
        theme4Switch = (Switch) findViewById(R.id.theme4Switch);
        theme5Switch = (Switch) findViewById(R.id.theme5Switch);


        String theme = dataController.getTheme();
        if(theme.equals("dark")){
            theme1Switch.setOnCheckedChangeListener (null);
            theme1Switch.setChecked(true);
            setColors(getResources().getColor(R.color.darkModeOrange));

        }else if(theme.equals("colorblind")){
            theme2Switch.setOnCheckedChangeListener (null);
            theme2Switch.setChecked(true);
            setColors(getResources().getColor(R.color.gray2));

        }else if(theme.equals("night")){
            theme3Switch.setOnCheckedChangeListener (null);
            theme3Switch.setChecked(true);
            setColors(getResources().getColor(R.color.night_purple));

        }else if(theme.equals("tropical")){
            theme4Switch.setOnCheckedChangeListener (null);
            theme4Switch.setChecked(true);
            setColors(getResources().getColor(R.color.tropical_orange));

        }else if(theme.equals("rustic")){
            theme5Switch.setOnCheckedChangeListener (null);
            theme5Switch.setChecked(true);
            setColors(getResources().getColor(R.color.rustic_green));

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

        theme2Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println("Theme has been changed to Colorblind");
                    dataController.setTheme("colorblind");
                    setColors(getResources().getColor(R.color.gray2));
                }else{
                    System.out.println("Theme has been changed to normal");
                    dataController.setTheme("default");
                    setColors(getResources().getColor(R.color.white));


                }



            }
        });

        theme3Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println("Theme has been changed to Night");
                    dataController.setTheme("night");
                    setColors(getResources().getColor(R.color.night_purple));
                }else{
                    System.out.println("Theme has been changed to normal");
                    dataController.setTheme("default");
                    setColors(getResources().getColor(R.color.white));


                }



            }
        });

        theme4Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println("Theme has been changed to Tropical");
                    dataController.setTheme("tropical");
                    setColors(getResources().getColor(R.color.tropical_orange));
                }else{
                    System.out.println("Theme has been changed to normal");
                    dataController.setTheme("default");
                    setColors(getResources().getColor(R.color.white));


                }



            }
        });
        theme5Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println("Theme has been changed to Rustic");
                    dataController.setTheme("rustic");
                    setColors(getResources().getColor(R.color.rustic_green));
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
        }else if(color == getResources().getColor(R.color.night_purple)){
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.blackbackground));
            closeButton.setBackgroundResource(R.drawable.verifiedwhite);
        }else if(color == getResources().getColor(R.color.rustic_green)){
            backgroundImage.setImageDrawable(getResources().getDrawable(R.color.rustic_gray));
            closeButton.setBackgroundResource(R.drawable.verifiedwhite);
        }else if(color == getResources().getColor(R.color.tropical_orange)){
            backgroundImage.setImageDrawable(getResources().getDrawable(R.color.tropical_aqua));
            closeButton.setBackgroundResource(R.drawable.verifiedwhite);
        }else if(color == getResources().getColor(R.color.gray2)){
            backgroundImage.setImageDrawable(getResources().getDrawable(R.drawable.blackbackground));
            closeButton.setBackgroundResource(R.drawable.verifiedwhite);
        }


        fontHeader.setTextColor(color);
        theme1.setTextColor(color);
        theme2.setTextColor(color);
        theme3.setTextColor(color);
        theme4.setTextColor(color);
        theme5.setTextColor(color);



    }
}
