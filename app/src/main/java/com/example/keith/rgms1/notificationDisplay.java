package com.example.keith.rgms1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Keith on 30/3/2015.
 */
public class notificationDisplay extends ActionBarActivity {
    TextView t1, t2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationdisplay);
        String objectid = getIntent().getStringExtra("objectId");
        String docUserName = getIntent().getStringExtra("doctorUserName");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String diagnosis = getIntent().getStringExtra("diagnosis");
        t1 = (TextView) findViewById(R.id.textView59);
        t2 = (TextView) findViewById(R.id.textView60);
        new DoctorMgr().retrieveDoctorDetails(docUserName, new DoctorMgr.Callback<ArrayList<String>>() {
            @Override
            public void done(ArrayList<String> result) {
                String a = "Dr. "+ result.get(0).toString() + " " + result.get(1).toString();
                String b = result.get(2).toString();
                t1.setText(a);
                t2.setText(b);
            }
        });

    }
}
