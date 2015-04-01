package com.example.keith.rgms1;

import android.util.Log;

import com.parse.FindCallback;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by Keith on 17/3/2015.
 */
public class AppointmentMgr {
    String timeslot = "Timing";
    ArrayList<String> aList = new ArrayList<String>();

    public AppointmentMgr() {
    }

    public interface Callback<T> {
        void done(T result);
    }

    public void getAllTimeSlot(final Callback<ArrayList<String>> callback) {
        aList.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(timeslot);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        aList.add(object.get(i).getString("timeslot"));
                    }
                    callback.done(aList);
                } else {

                }
            }
        });
    }

    public void getAllDoctors(final Callback<ArrayList<String>> callback) {
        aList.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        aList.add(object.get(i).getString("firstname") + " " + object.get(i).getString("lastname"));
                    }
                    callback.done(aList);
                } else {

                }
            }
        });
    }
    public void getAppointment(String patientname, final Callback<ArrayList<String>> callback){
        aList.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Appointment");
        query.whereEqualTo("Patientname",patientname);
        query.include("Doctor");
        query.findInBackground(new FindCallback<ParseObject>(){
            public void done(List<ParseObject> object, ParseException e){
                if (e == null) {

                    for (int i = 0; i < object.size(); i++) {
                        aList.add((object.get(i).getObjectId()));
                        aList.add(object.get(i).getString("Time"));
                        aList.add(object.get(i).getString("Date"));
                        aList.add((object.get(i).getObjectId()));
                        ParseObject doctorObject = object.get(i).getParseObject("Doctor");
                        aList.add(doctorObject.getString("firstname") + " " + doctorObject.getString("lastname"));
                        aList.add(doctorObject.getString("Location"));
                        callback.done(aList);
                    }

                } else {

                }
            }});

    }
    public void deleteAppointment(String a)
    {
        aList.clear();
        ParseObject.createWithoutData("Appointment", a).deleteEventually();
    }
    public void getAllReferredDoctors(String patientname, final Callback<ArrayList<String>> callback) {
        aList.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Referral");
        query.whereEqualTo("Patientname", patientname);
        query.include("doctor2");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        ParseObject doctorObject = object.get(i).getParseObject("doctor2");
                        aList.add(doctorObject.getString("firstname") + " " + doctorObject.getString("lastname"));
                    }
                    callback.done(aList);

                } else {

                }
            }
        });
    }

    public void getAllTypes(final Callback<ArrayList<String>> callback) {
        aList.clear();
        aList.add("Fresh");
        aList.add("FollowUp");
        aList.add("Referral");
        callback.done(aList);
    }

    public void getTimeSlot(String doc, final String day, final Callback<ArrayList<String>> callback){
        int i = doc.indexOf(' ');
        String firstName = doc.substring(0,i);
        String lastName = doc.substring(i);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
        query.whereEqualTo("firstname", firstName);
        query.include("slot");
       // query.whereEqualTo("lastname", lastName);

        aList.clear();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null){

                    ParseObject ob = parseObject.getParseObject("slot");
                    String d = ob.getString(day);
                    for(int i=0; i<d.length(); i++){
                        aList.add((Character.getNumericValue(d.charAt(i))+9)+":00 hrs");
                    }
                    callback.done(aList);
                }
            }
        });

    }

}
