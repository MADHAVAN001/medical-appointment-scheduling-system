package com.example.keith.rgms1;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

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
    static boolean doctorSelected = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createappt);
        final NotificationGenerator ng = new NotificationGenerator();
        final AppointmentMgr am = new AppointmentMgr();
        final DoctorMgr dm = new DoctorMgr();
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String username = pref.getString("username","");

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
                    new AppointmentMgr().getAllFollowUpDoctors(username, new AppointmentMgr.Callback<ArrayList<String>>() {
                        @Override
                        public void done(ArrayList<String> aList) {
                            doctorAdapter.clear();
                            doctorAdapter.addAll(aList);
                            doctorAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else if(position == 2){
                    new AppointmentMgr().getAllReferredDoctors(username, new AppointmentMgr.Callback<ArrayList<String>>() {
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
                doctorSelected = true;
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
                doctorSelected = false;
            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(doctorSpinner.getSelectedItem() == null)
                    return;
                String d = doctorSpinner.getSelectedItem().toString();
                String day = "Day" + (parent.getSelectedItemPosition()+1);

                new AppointmentMgr().getTimeSlot(d, day, new AppointmentMgr.Callback<ArrayList<String>>() {
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

        final EditText e = (EditText)findViewById(R.id.problemEditText);

        Button b1 = (Button) findViewById(R.id.createButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doctorSelected == false){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(createAppt.this);
                    builder1.setMessage("Please select a doctor in the list above. Change Type to Fresh if list is empty")
                            .setTitle("No Doctor Selected!");
                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    builder1.create();

                }
                else if(timeSpinner.getSelectedItem() == null){

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(createAppt.this);
                    builder1.setMessage("All time slots booked. Try choosing another date!")
                            .setTitle("No Time Slot Selected!");
                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    builder1.show();
                }
                else{

                    if(typeSpinner.getSelectedItemPosition() == 1){
                        am.checkConstraint(username, doctorSpinner.getSelectedItem().toString(), new AppointmentMgr.Callback<String>() {
                            @Override
                            public void done(String result) {
                                if(result == null){
                                    am.updateSlots(timeSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItemPosition(), doctorSpinner.getSelectedItem().toString());
                                    am.addApp(username, typeSpinner.getSelectedItem().toString(), doctorSpinner.getSelectedItem().toString(),
                                            dateSpinner.getSelectedItem().toString(), timeSpinner.getSelectedItem().toString(),
                                            e.getText().toString());
                                    dm.getDoctor(doctorSpinner.getSelectedItem().toString(), new DoctorMgr.Callback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject result) {
                                            ng.generateNotification(username, "You have an appointment!", "Appointment at " + timeSpinner.getSelectedItem().toString() +
                                                            " on " + dateSpinner.getSelectedItem().toString() + " with Dr. " + doctorSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItem().toString(),
                                                    timeSpinner.getSelectedItem().toString(), "", "", result.getString("username"));
                                        }
                                    });

                                    Toast.makeText(createAppt.this, "Appointment created!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(createAppt.this, appointment.class);
                                    startActivity(intent);

                                }
                                else{
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(createAppt.this);
                                    builder1.setMessage(result)
                                            .setTitle("Constraint");
                                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            am.addApp(username, typeSpinner.getSelectedItem().toString(), doctorSpinner.getSelectedItem().toString(),
                                                    dateSpinner.getSelectedItem().toString(), timeSpinner.getSelectedItem().toString(),
                                                    e.getText().toString());
                                            am.updateSlots(timeSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItemPosition(), doctorSpinner.getSelectedItem().toString());
                                            Toast.makeText(createAppt.this, "Appointment created!", Toast.LENGTH_SHORT).show();
                                            dm.getDoctor(doctorSpinner.getSelectedItem().toString(), new DoctorMgr.Callback<ParseObject>() {
                                                @Override
                                                public void done(ParseObject result) {
                                                    ng.generateNotification(username, "You have an appointment!", "Appointment at " + timeSpinner.getSelectedItem().toString() +
                                                                    " on " + dateSpinner.getSelectedItem().toString() + " with Dr. " + doctorSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItem().toString(),
                                                            timeSpinner.getSelectedItem().toString(), "", "", result.getString("username"));
                                                }
                                            });
                                            Intent intent = new Intent(createAppt.this, appointment.class);
                                            startActivity(intent);
                                        }
                                    });
                                    builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
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
                    else{
                        am.updateSlots(timeSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItemPosition(), doctorSpinner.getSelectedItem().toString());
                        am.addApp(username, typeSpinner.getSelectedItem().toString(), doctorSpinner.getSelectedItem().toString(),
                                dateSpinner.getSelectedItem().toString(), timeSpinner.getSelectedItem().toString(),
                                e.getText().toString());
                        Toast.makeText(createAppt.this, "Appointment created!", Toast.LENGTH_SHORT).show();
                        dm.getDoctor(doctorSpinner.getSelectedItem().toString(), new DoctorMgr.Callback<ParseObject>() {
                            @Override
                            public void done(ParseObject result) {
                                ng.generateNotification(username, "You have an appointment!", "Appointment at " + timeSpinner.getSelectedItem().toString() +
                                                " on " + dateSpinner.getSelectedItem().toString() + " with Dr. " + doctorSpinner.getSelectedItem().toString(), dateSpinner.getSelectedItem().toString(),
                                        timeSpinner.getSelectedItem().toString(), "", "", result.getString("username"));
                            }
                        });
                        Intent intent = new Intent(createAppt.this, appointment.class);
                        startActivity(intent);
                    }

                }
            }
        });

    }

}
