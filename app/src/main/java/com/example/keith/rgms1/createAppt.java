package com.example.keith.rgms1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Keith on 17/3/2015.
 */
public class createAppt extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createappt);

        ArrayList<String> test = new ArrayList<String>();
        test.add("Hi");
        test.add("Hello");
        test.add("testing");
        Spinner spinnerCategory = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, test);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoriesAdapter);
    }



}
