package com.example.keith.rgms1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Keith on 10/3/2015.
 */
public class appointment extends ActionBarActivity {
    ImageButton Createbutton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);
        createApptInterface();
    }

    public void createApptInterface() {
        Createbutton = (ImageButton) findViewById(R.id.imageButton18);

        Createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), createAppt.class);
                startActivity(intent);
            }
        });
    }
}