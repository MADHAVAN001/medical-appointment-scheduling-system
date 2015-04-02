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
    final String appointment = "Appointment";


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

    public void getAllFollowUpDoctors(String patientname, final Callback<ArrayList<String>> callback) {
        aList.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Appointment");
        query.whereEqualTo("Patientname", patientname);
        query.include("Doctor");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        ParseObject doctorObject = object.get(i).getParseObject("Doctor");
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

    public void getTimeSlot(String doc, final String day, final Callback<ArrayList<String>> callback) {
        int i = doc.indexOf(' ');
        String firstName = doc.substring(0, i);
        String lastName = doc.substring(i);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
        query.whereEqualTo("firstname", firstName);
        query.include("slot");
        query.whereEqualTo("lastname", lastName.trim());

        aList.clear();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {

                    ParseObject ob = parseObject.getParseObject("slot");
                    String d = ob.getString(day);
                    for (int i = 0; i < d.length(); i++) {
                        aList.add((Character.getNumericValue(d.charAt(i)) + 9) + ":00 hrs");
                    }
                    callback.done(aList);
                }
            }
        });
    }

        public void addApp(final String patient,final String type,final String doctor,final String date,final String time,final String health){
            new DoctorMgr().getDoctor(doctor, new DoctorMgr.Callback<ParseObject>() {
                        @Override
                        public void done(ParseObject p) {
                            ParseObject app = new ParseObject(appointment);
                            app.put("Patientname", patient);
                            app.put("Type", type);
                            app.put("Doctor", p);
                            app.put("Date", date);
                            app.put("Time", time);
                            app.put("Description", health);
                            app.saveInBackground();
                        }

        });
        }

    public void checkConstraint(String username, String doc, final Callback<String> callb){
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Constraint");
        query.whereEqualTo("Patient", username);
        query.whereEqualTo("Doctor", doc.trim());
        try{
            if(query.count() == 0){
                callb.done(null);
                Log.d("Count1", "ZERO");
            }
            else{
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        String c = parseObject.getString("Constraint");
                        callb.done(c);
                    }
                });

            }
        }catch(ParseException p){
            p.printStackTrace();
        }
    }

    public void getAppointment(String patientname, final Callback<ArrayList<String>> callback){
        aList.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Appointment");
        query.whereEqualTo("Patientname",patientname);
        query.include("Doctor");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        aList.add((object.get(i).getObjectId()));
                        aList.add(object.get(i).getString("Time"));
                        aList.add(object.get(i).getString("Date"));
                        aList.add((object.get(i).getObjectId()));
                        ParseObject doctorObject = object.get(i).getParseObject("Doctor");
                        aList.add(doctorObject.getString("firstname") + " " + doctorObject.getString("lastname"));
                        aList.add(doctorObject.getString("Location"));
                        }
                    callback.done(aList);
                } else {
                    }
            }
        });
    }


    public int getNumApp(String username){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Appointment");
        query.whereEqualTo("Patientname",username);
        try{
            return query.count();
        }catch(Exception e){
            return 0;
        }

    }
    public void deleteAppointment(String a)
    {
        aList.clear();
        ParseObject.createWithoutData("Appointment", a).deleteEventually();
    }

    public void updateSlots(String time, int date, String doc){
        int i = time.indexOf(':');
        int hr = Integer.parseInt(time.substring(0,i)) - 9;
        final String hour = Integer.toString(hr);
        Log.d("HOUR", hour);
        i = doc.indexOf(' ');
        String firstName = doc.substring(0, i);
        String lastName = doc.substring(i);
        final String day = "Day"+(date+1);
        Log.d("DATE",day);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
        query.whereEqualTo("firstname", firstName);
        query.include("slot");
        query.whereEqualTo("lastname", lastName.trim());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {

                    ParseObject ob = parseObject.getParseObject("slot");
                    String newString = ob.getString(day).replace(hour.trim(), "");
                    Log.d("NEW", newString);
                    ob.put(day, newString.trim());
                    ob.saveInBackground();
                }
            }
        });
    }

    public void updateApp(String id, final String date, final String time){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Appointment");
        query.whereEqualTo("objectId", id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                parseObject.put("Date", date);
                parseObject.put("Time", time);
                parseObject.saveInBackground();
            }
        });
    }
}
