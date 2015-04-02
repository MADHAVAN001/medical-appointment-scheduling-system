package com.example.keith.rgms1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class rescheduleApp extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reschedule);

        final AppointmentMgr am = new AppointmentMgr();
        final String id = getIntent().getExtras().getString("AppID");
        final String doc = getIntent().getExtras().getString("DocName");

        ArrayList<String> dates = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar cal = Calendar.getInstance();
        for(int i=1; i<=7; i++){
            cal.add(Calendar.DATE,1);
            dates.add(dateFormat.format(cal.getTime()));
        }
        final Spinner dateSpinner = (Spinner) findViewById(R.id.dateSpinner1);
        final ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateAdapter);

        final Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner1);
        final ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        final Button b1 = (Button) findViewById(R.id.submit);



        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String day = "Day" + (parent.getSelectedItemPosition()+1);
                new AppointmentMgr().getTimeSlot(doc, day, new AppointmentMgr.Callback<ArrayList<String>>() {
                    @Override
                    public void done(ArrayList<String> result) {
                        timeAdapter.clear();
                        timeAdapter.addAll(result);
                        timeAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b1.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                b1.setClickable(false);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeSpinner.getSelectedItem() == null){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(rescheduleApp.this);
                    builder1.setMessage("Please make sure a time slot is selected!")
                            .setTitle("Error: Incomplete Info");
                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                }

                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(rescheduleApp.this);
                    builder1.setMessage("Are you sure you want to reschedule your appointment?")
                            .setTitle("Confirmation");
                    builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            am.updateSlots(timeSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItemPosition(), doc);
                            am.updateApp(id, dateSpinner.getSelectedItem().toString(), timeSpinner.getSelectedItem().toString());
                            Toast.makeText(rescheduleApp.this, "Appointment rescheduled!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(rescheduleApp.this, viewAppt.class);
                            startActivity(intent);
                        }
                    });
                    builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder1.show();
                }
            }
        });
    }
}
