package com.example.keith.rgms1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 24/3/2015.
 */
public class selectedPatient extends ActionBarActivity {
    TextView t1,t2,t3,t4,t5;
    ImageButton ib1;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectedpatient);
        t1 = (TextView)findViewById(R.id.textView38);
        t2 = (TextView)findViewById(R.id.textView40);

        followupPage();
        returntoHomePage();
        returntopatientDetails();
        id = getIntent().getStringExtra("PatientSearchedUserName");
        Log.i("TEST", id);
        new HumanDataMgr().getUserInformation(id, new HumanDataMgr.Callback<ArrayList<String>>() {
            @Override
            public void done(ArrayList<String> result) {
                Log.i("TEST", result.get(0).toString());
                String a = result.get(0).toString() + " " + result.get(1).toString();
                String b = result.get(2).toString();
                t1.setText(a);
                t2.setText(b);
            }
        });

    }
    public void followupPage(){
        ib1 = (ImageButton) findViewById(R.id.imageButton37);
        ib1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), followup.class);
                intent.putExtra("PatientSearchedUserName", id);
                startActivity(intent);
            }});
    }
    public void returntoHomePage(){
        t3 = (TextView) findViewById(R.id.textView30);
        t3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), staffHomePage.class);
                startActivity(intent);
            }});
    }
    public void returntopatientDetails(){
        t4 = (TextView) findViewById(R.id.textView32);
        t4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), patientDetails.class);
                startActivity(intent);
            }});
    }


}
