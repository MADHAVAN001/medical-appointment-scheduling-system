package com.example.keith.rgms1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 30/3/2015.
 */
public class notification extends ActionBarActivity{
    TextView t1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        t1 = (TextView) findViewById(R.id.textView57);
        new NotificationGenerator().retrieveUserNotifications(username,new NotificationGenerator.Callback<List<HashMap<String,String>>>() {
            @Override
            public void done (final List<HashMap<String,String>> aList){
                t1.setText("Notifications (" + aList.size()+")");
                String[] from = { "subject", "createat"};
                // Ids of views in listview_layout
                int[] to = { R.id.subject, R.id.createat};
                // Instantiating an adapter to store each items
                // R.layout.listview_layout defines the layout of each item
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), aList, R.layout.listview_layoutfornotifications, from, to);
                // Getting a reference to listview of main.xml layout file
                ListView listView = ( ListView ) findViewById(R.id.listView);
                // Setting the adapter to the listView
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
//                        String abc = aList.get(position).get("objectid").toString();
//                        Log.i("Test", abc);
                        Intent intent = new Intent(getApplicationContext(), notificationDisplay.class);
                        intent.putExtra("objectId", aList.get(position).get("objectid").toString());
                        intent.putExtra("doctorUserName",aList.get(position).get("doctorUserName").toString());
                        intent.putExtra("date",aList.get(position).get("date").toString());
                        intent.putExtra("time",aList.get(position).get("time").toString());
                        intent.putExtra("diagnosis",aList.get(position).get("diagnosis").toString());
                        startActivityForResult(intent, 0);
                    }
                });
            }
        });
    }
}
