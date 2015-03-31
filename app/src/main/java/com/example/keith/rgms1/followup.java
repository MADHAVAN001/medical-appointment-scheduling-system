package com.example.keith.rgms1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 25/3/2015.
 */
public class followup extends ActionBarActivity {
//    NumberPicker np;
    TextView t1,t2,t3,t4,t5;
    EditText e1,e2,e3;
    private Calendar calendar;
    private int year, month, day;
    private DatePicker datePicker;
    private TextView dateView;
    ImageButton b1,b2;
    String id;
    NotificationGenerator ngr = new NotificationGenerator();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followup);
        id = getIntent().getStringExtra("PatientSearchedUserName");
        dateView = (TextView) findViewById(R.id.textView51);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        calendarButton();
        addAlternativeReasons();
        returntoHomePage();
        returntopatientDetails();
        finishButton();
        new AppointmentMgr().getAllTimeSlot(new AppointmentMgr.Callback<ArrayList<String>>() {
            @Override
            public void done(final ArrayList<String> aList) {
                Spinner spinnerCategory = (Spinner)findViewById(R.id.spinner2);
                ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, aList);
                categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(categoriesAdapter);
                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String ab = (String) parent.getAdapter().getItem(position);
                        Log.i("Test:", ab);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        new HealthDataMgr().getAllDiagnosis(new HealthDataMgr.Callback<ArrayList<String>>() {
            @Override
            public void done(final ArrayList<String> aList) {
                Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner3);
                ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, aList);
                categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(categoriesAdapter);

            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }
    public void calendarButton(){
        b1 = (ImageButton) findViewById(R.id.imageButton41);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                setDate(arg0);
            }});
    }
    public void finishButton(){
        b2 = (ImageButton) findViewById(R.id.imageButton42);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                   t2 = (TextView)findViewById(R.id.textView51);
                   String date = t2.getText().toString();
                   Spinner mySpinner=(Spinner) findViewById(R.id.spinner2);
                   String timeslot = mySpinner.getSelectedItem().toString();
                  Spinner mySpinner1=(Spinner) findViewById(R.id.spinner3);
                  String medicalDiagnosis = mySpinner1.getSelectedItem().toString();
                   EditText e10 = (EditText) findViewById(R.id.editText13);
                  String additionalReasons ="";
                  if(e10.getVisibility() == View.VISIBLE){
                      if(!e10.getText().toString().equals("")){
                          additionalReasons = e10.getText().toString();
                      }
                  }
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = pref.getString("username", "");
                String subject = "You have a new message from "+username;
                String message = "Followup appointment allocated, please arrange a date";

                ngr.generateNotification(id,subject,message, date,timeslot,medicalDiagnosis,additionalReasons,username );
                Toast.makeText(getApplicationContext(), "Notification Successfully sent to user!",Toast.LENGTH_LONG).show();
            }});
    }
    public void returntoHomePage(){
        t3 = (TextView) findViewById(R.id.textView41);
        t3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), staffHomePage.class);
                startActivity(intent);
            }});
    }
    public void returntopatientDetails(){
        t4 = (TextView) findViewById(R.id.textView43);
        t4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), patientDetails.class);
                startActivity(intent);
            }});
    }
    public void addAlternativeReasons(){
        t1 = (TextView) findViewById(R.id.textView56);
        e1 = (EditText) findViewById(R.id.editText13);
        e2 = (EditText) findViewById(R.id.editText14);
        e3 = (EditText) findViewById(R.id.editText15);
        t1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (e1.getVisibility() == View.INVISIBLE) {
                    e1.setVisibility(View.VISIBLE);

                }
            }
        });
    }
    private DatePickerDialog.OnDateSetListener myDateListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//        }
        }
