package com.example.keith.rgms1;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Keith on 17/3/2015.
 */
public class createAppt extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createappt);

        final Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);

        new AppointmentMgr().getAllTypes(new AppointmentMgr.Callback<ArrayList<String>>() {
            @Override
            public void done(ArrayList<String> aList) {
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, aList);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(typeAdapter);
            }
        });

        final Spinner doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);

        final ArrayAdapter<String> doctorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpinner.setAdapter(doctorAdapter);

        final Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        final ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        ArrayList<String> dates = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar cal = Calendar.getInstance();
        for(int i=1; i<=7; i++){
            cal.add(Calendar.DATE,1);
            dates.add(dateFormat.format(cal.getTime()));
        }
        final Spinner dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        final ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    new AppointmentMgr().getAllDoctors(new AppointmentMgr.Callback<ArrayList<String>>() {
                        @Override
                        public void done(final ArrayList<String> aList) {

                            doctorAdapter.clear();
                            doctorAdapter.addAll(aList);
                            doctorAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else if(position == 1){

                }
                else if(position == 2){
                    new AppointmentMgr().getAllReferredDoctors("Keith", new AppointmentMgr.Callback<ArrayList<String>>() {
                        @Override
                        public void done(final ArrayList<String> aList) {
                            doctorAdapter.clear();
                            doctorAdapter.addAll(aList);
                            doctorAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String d = "Day"+(dateSpinner.getSelectedItemPosition()+1);
                new AppointmentMgr().getTimeSlot(parent.getSelectedItem().toString(),d, new AppointmentMgr.Callback<ArrayList<String>>() {
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


    }

}