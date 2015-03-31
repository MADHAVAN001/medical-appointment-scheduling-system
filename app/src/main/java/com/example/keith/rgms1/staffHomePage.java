package com.example.keith.rgms1;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Keith on 23/3/2015.
 */
public class staffHomePage extends ActionBarActivity {
    TextView welcomeMsg;
    ImageButton button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffhomepage);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        welcomeMsg = (TextView) findViewById(R.id.welcomeMessage);
        welcomeMsg.setText("Welcome Back " + username +"!");
        patientDetails();


    }

    public void patientDetails(){


        button = (ImageButton) findViewById(R.id.imageButton22);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), patientDetails.class);
                startActivity(intent);
            }});
    }
}
