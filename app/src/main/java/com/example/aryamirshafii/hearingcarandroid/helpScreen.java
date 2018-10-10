package com.example.aryamirshafii.hearingcarandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class helpScreen extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpscreen);

        ImageButton btn= (ImageButton) findViewById(R.id.closeButton);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(helpScreen.this, MainActivity.class);
                //finish();
                startActivity(myIntent);
            }
        });

    }
}
