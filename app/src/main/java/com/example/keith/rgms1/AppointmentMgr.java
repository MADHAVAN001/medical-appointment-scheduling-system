package com.example.keith.rgms1;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 17/3/2015.
 */
public class AppointmentMgr {
    String timeslot = "Timing";
    ArrayList<String> aList = new ArrayList<String>();
    public AppointmentMgr(){}

    public interface Callback<T>{
        void done(T result);
    }

    public void getAllTimeSlot(final Callback<ArrayList<String>> callback){
//        return news;
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

}
