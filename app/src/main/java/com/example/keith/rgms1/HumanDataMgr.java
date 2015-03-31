package com.example.keith.rgms1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 17/3/2015.
 */
public class HumanDataMgr {

    DatabaseMgr dbMgr = new DatabaseMgr();
    String patientEntity = "Patient";
    String news = "News";
    String doctorEntity = "Doctor";

    public HumanDataMgr(){

    }
    public void addNewPatient(String userName, String userPassword, String userFName, String userLName, String userEmail, String userMobileNumber, String userAddress, String userDOB){
        final ParseObject newUser = new ParseObject(patientEntity);
        newUser.put("username", userName);
        newUser.put("password", userPassword);
        newUser.put("firstname", userFName);
        newUser.put("lastname", userLName);
        newUser.put("email", userEmail);
        newUser.put("mobilenumber", userMobileNumber);
        newUser.put("address", userAddress);
        newUser.put("dob", userDOB);
        newUser.saveInBackground();

    }

    public interface Callback<T>{
        void done(T result);
    }

    public void checkExistingUserName(String userName, final Callback<Boolean> callback) {
//        return patientEntity;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(patientEntity);
        query.whereEqualTo("username", userName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    callback.done(false);
                } else {
                    callback.done(true);
                }
            }
        });

    }

    public void checkLogin(String userName, String passWord, final Callback<Boolean> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(patientEntity);
        query.whereEqualTo("username", userName);
        query.whereEqualTo("password", passWord);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
               public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            callback.done(true);
                        } else {
                            callback.done(false);
                        }
                    }
                });
                }
    public void checkStaffLogin(String userName, String passWord, final Callback<Boolean> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(doctorEntity);
        query.whereEqualTo("username", userName);
        query.whereEqualTo("password", passWord);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    callback.done(true);
                } else {
                    callback.done(false);
                }
            }
        });
    }

    public void checkPatientNameExist(String userName, final Callback<Boolean> callback) {
//        return patientEntity;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(patientEntity);
        query.whereContains("firstname", userName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    callback.done(true);
                } else {
                    callback.done(false);
                }
            }
        });

    }

    public void getUserInformation(String userName, final Callback<ArrayList<String>> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(patientEntity);
        query.whereEqualTo("username", userName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ArrayList<String> aList = new ArrayList<String>();
                    String fname,lname,dob;
                    fname = object.getString("firstname");
                    lname = object.getString("lastname");
                    dob = object.getString("dob");
                    aList.add(fname);
                    aList.add(lname);
                    aList.add(dob);
                    callback.done(aList);
                } else {

                }
            }
        });
    }
//       return patientEntity;
//   }
//   public String getHumanUser(){
//       return patientEntity;
//   }

    public void getAllUserWithName(String name, final Callback<List<HashMap<String,String>>> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(patientEntity);
        query.whereContains("firstname", name);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                    for(int i=0;i< object.size();i++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("username", object.get(i).getString("username"));
                        hm.put("firstname", object.get(i).getString("firstname"));
                        hm.put("lastname", object.get(i).getString("lastname"));
                        hm.put("address", object.get(i).getString("address"));
                        hm.put("mobilenumber", object.get(i).getString("mobilenumber"));
                        hm.put("dob", object.get(i).getString("dob"));
                        aList.add(hm);
                    }
                    callback.done(aList);
                } else {

                }
            }
        });
    }
}
