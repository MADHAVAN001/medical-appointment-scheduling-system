package com.example.keith.rgms1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Keith on 17/3/2015.
 */
public class HumanDataMgr {

    DatabaseMgr dbMgr = new DatabaseMgr();
    String patientEntity = "Patient";
    String news = "News";

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

//       return patientEntity;
//   }
//   public String getHumanUser(){
//       return patientEntity;
//   }
}
