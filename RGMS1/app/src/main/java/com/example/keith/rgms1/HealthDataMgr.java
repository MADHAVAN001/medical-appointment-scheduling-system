package com.example.keith.rgms1;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keith on 17/3/2015.
 */
public class HealthDataMgr {

    String medicalConditions = "MedicalCondi";
    ArrayList<String> medical = new ArrayList<String>();

    public HealthDataMgr(){

    }

    public ArrayList<String> getAllMedicalConditions(){
        medical.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.whereEqualTo("playerName", "Dan Stemkoski");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> medicalList, ParseException e) {
                if (e == null) {
                    for(int i = 0; i<medicalList.size(); i++) {
                        medical.add(medicalList.get(i).getString("name"));
                    }
                } else {

                }
            }
        });
        return medical;
    }
}
