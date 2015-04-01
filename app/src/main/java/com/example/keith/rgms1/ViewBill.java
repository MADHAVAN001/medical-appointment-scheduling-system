package com.example.keith.rgms1;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewBill extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "pV6MMT2OsHxlogsALa0CvDZnJfoIfjPkj49vOfoM", "58dqGcJzQlFK0g02kyqkOqicaxunc8I3oOQBpGdx");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        generateLayout();
    }

    public void generateLayout(){
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String credentials = pref.getString("username", "");
        LinearLayout displaybill = new LinearLayout(this);
        displaybill.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW,R.id.patientui);
        displaybill.setLayoutParams(lp);


        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ArrayList<HashMap<String,Object>> healthdata = (new HealthDataMgr()).getHealthAnalysis(credentials);
        float total = 0;
        float billdue;
        for(HashMap<String,Object> health : healthdata){
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(lp2);
            ll.setPadding(5,20,5,10);
            ll.setBackgroundColor(Color.parseColor("#5582FA58"));
            TextView text = new TextView(this);
            text.setLayoutParams(lp2);
            String bill = "";
            boolean billpaid = (Boolean)health.get("billpaid");
            if(!billpaid){
                bill += (String)health.get("title");
                bill += " \nDate        : ";
                bill += (String)health.get("date");
                bill+= "  \n";
                bill +=    "Payment Due : ";
                billdue = (float)health.get("bill");
                total += billdue;
                bill += String.format("%.2f",billdue);
                bill += "\n\n";
            }
            text.setText(bill);
            text.setTextSize(24);
            text.setTextColor(Color.parseColor("#ffffff"));
            ll.addView(text);
            displaybill.addView(ll);

        }
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lp2);
        ll.setPadding(5,28,5,10);
        TextView text = new TextView(this);
        text.setLayoutParams(lp2);
        String bill = "Total   = "+ String.format("%.2f",total);
        text.setText(bill);
        text.setTextSize(24);
        text.setTextColor(Color.parseColor("#ffffff"));
        ll.addView(text);
        displaybill.addView(ll);

        RelativeLayout r = (RelativeLayout)findViewById(R.id.viewBillUI);
        r.addView(displaybill);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_bill, menu);
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
