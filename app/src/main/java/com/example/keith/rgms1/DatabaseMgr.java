package com.example.keith.rgms1;
import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.*;
import java.util.*;

public class DatabaseMgr {

    String patientEntity = "Patient";
    String news = "News";


    public DatabaseMgr() {

    }
    public interface Callback<T>{
        void done(T result);
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

    public void checkExistingUserName(String userName, final Callback<Boolean> callback) {
//        return patientEntity;


    }

    public String getNews(){
        return news;
    }
}