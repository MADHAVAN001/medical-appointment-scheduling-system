package com.example.keith.rgms1;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 25/3/2015.
 */
public class NotificationGenerator {
    String notification = "Notification";
    public NotificationGenerator(){}
    public interface Callback<T>{
        void done(T result);
    }

    public void generateNotification(String username, String subject, String message, String date, String time, String diagnosis, String additionalinfo,String dusername){
        final ParseObject newUser = new ParseObject(notification);
        newUser.put("username", username);
        newUser.put("subject", subject);
        newUser.put("message", message);
        newUser.put("date", date);
        newUser.put("time", time);
        newUser.put("diagnosis", diagnosis);
        newUser.put("additionalinfo", additionalinfo);
        newUser.put("doctorUserName", dusername);
        newUser.saveInBackground();
    }

    public void retrieveUserNotifications(String username, final Callback<List<HashMap<String,String>>> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(notification);
        query.whereEqualTo("username", username);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                    for(int i=0;i< object.size();i++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("objectid", object.get(i).getObjectId());
                        hm.put("createat", object.get(i).getCreatedAt().toString());
                        hm.put("subject", object.get(i).getString("subject"));
                        hm.put("message", object.get(i).getString("message"));
                        hm.put("date", object.get(i).getString("date"));
                        hm.put("time", object.get(i).getString("time"));
                        hm.put("diagnosis", object.get(i).getString("diagnosis"));
                        hm.put("doctorUserName",object.get(i).getString("doctorUserName"));
                        aList.add(hm);
                    }
                    callback.done(aList);
                } else {
                    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                    callback.done(aList);
                }
            }
        });
    }
    }

