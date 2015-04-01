package com.example.keith.rgms1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class news extends ActionBarActivity {

    ImageButton button;
    ImageButton button1;
    NewsMgr newMgr = new NewsMgr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addLogoutButton();
        appointmentButton();
        logsButton();

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        new NewsMgr().getAllNews(new NewsMgr.Callback<List<HashMap<String,String>>>() {
            @Override
            public void done (List<HashMap<String,String>> aList){
                    String[] from = { "flag","txt","cur" };
                    // Ids of views in listview_layout
                    int[] to = { R.id.flag,R.id.txt,R.id.cur};
                    // Instantiating an adapter to store each items
                    // R.layout.listview_layout defines the layout of each item
                    SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);
                    // Getting a reference to listview of main.xml layout file
                    ListView listView = ( ListView ) findViewById(R.id.listView1);
                    // Setting the adapter to the listView
                    listView.setAdapter(adapter);
            }
        });
            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void addLogoutButton() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.settingsButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, login.class);
                startActivity(intent);

            }

        });

    }
    public void appointmentButton() {

        final Context context = this;

        button1 = (ImageButton) findViewById(R.id.apptButton);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, appointment.class);
                startActivity(intent);

            }

        });

    }
    public void logsButton() {

        final Context context = this;

        button1 = (ImageButton) findViewById(R.id.folderButton);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, logs.class);
                startActivity(intent);

            }

        });

    }
}
