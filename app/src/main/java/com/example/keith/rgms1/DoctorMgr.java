package com.example.keith.rgms1;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by Keith on 17/3/2015.
 */
public class DoctorMgr {
    String doctor = "Doctor";
    public DoctorMgr(){}
    public interface Callback<T>{
        void done(T result);
    }
    public void getDoctor(String doc, final Callback<ParseObject> callback){
        int i = doc.indexOf(' ');
        String firstName = doc.substring(0, i);
        String lastName = doc.substring(i);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
        query.whereEqualTo("firstname", firstName);
        query.whereEqualTo("lastname", lastName.trim());
        Log.d("F", firstName);
        Log.d("L",lastName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    callback.done(parseObject);
                    }
                }
        });
    }
    public void retrieveDoctorDetails(String username, final Callback<ArrayList<String>> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(doctor);
        query.whereEqualTo("username", username);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ArrayList<String> aList = new ArrayList<String>();
                    String fname,lname,type;
                    fname = object.getString("firstname");
                    lname = object.getString("lastname");
                    type = object.getString("doctorType");
                    aList.add(fname);
                    aList.add(lname);
                    aList.add(type);
                    callback.done(aList);
                } else {

                }
            }
        });
    }
}
