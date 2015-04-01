package com.example.keith.rgms1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.*;
public class ViewPrescription extends ActionBarActivity {
    private int current;
    private ArrayList<HashMap<String,Object>> healthdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        current = 0;
        setFirstView();
        rightButton();
        leftButton();

    }

    private void setText( HashMap<String,Object> medicine){
        TextView medicinename = (TextView)findViewById(R.id.medicine);
        medicinename.setText((String)medicine.get("name"));
        TextView morning = (TextView)findViewById(R.id.morning);
        TextView noon = (TextView)findViewById(R.id.noon);
        TextView night = (TextView)findViewById(R.id.night);
        ArrayList<String> intaketime= (ArrayList<String>)(medicine.get("intaketime"));
        morning.setText(intaketime.get(0));
        noon.setText(intaketime.get(1));
        night.setText(intaketime.get(2));
        TextView dosage = (TextView)findViewById(R.id.dosage);
        dosage.setText((String)medicine.get(dosage));
    }

    private void setFirstView(){
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String credentials = pref.getString("username", "");
        healthdata = (new HealthDataMgr()).getMedicines(credentials);

        if(healthdata.size() == 0){
            findViewById(R.id.medicineUI).setVisibility(View.GONE);
            return;
        }
        current = 0;
        HashMap<String,Object> medicine = healthdata.get(current);
        setText(medicine);

    }
    private void rightButton(){
    final ImageButton button = (ImageButton) findViewById(R.id.right);
    button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            current += 1;
            if(current >= healthdata.size()){
                current = 0;
            }
            HashMap<String,Object> medicine = healthdata.get(current);
            setText(medicine);
        }
    });
    }
    private void leftButton(){
        final ImageButton button = (ImageButton) findViewById(R.id.left);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                current -= 1;
                if(current <0){
                    current = healthdata.size()-1;
                }
                HashMap<String,Object> medicine = healthdata.get(current);
                setText(medicine);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_prescription, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
