package com.example.keith.rgms1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Keith on 23/3/2015.
 */
public class viewPatientDetails extends ActionBarActivity {
    TextView t1,t2,t3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpatientdetails);
        returntoHomePage();
        returntopatientDetails();
        t1 = (TextView) findViewById(R.id.textView26);
        String id = getIntent().getStringExtra("PatientUserName");
        new HumanDataMgr().getAllUserWithName(id,new HumanDataMgr.Callback<List<HashMap<String,String>>>() {
            @Override
            public void done (final List<HashMap<String,String>> aList){
                t1.setText("Search Results (" + aList.size()+")");
                String[] from = { "firstname","lastname","address","mobilenumber","dob" };
                // Ids of views in listview_layout
                int[] to = { R.id.firstname,R.id.lastname,R.id.address, R.id.mobilenumber, R.id.dob};
                // Instantiating an adapter to store each items
                // R.layout.listview_layout defines the layout of each item
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), aList, R.layout.listview_layoutforpatient, from, to);
                // Getting a reference to listview of main.xml layout file
                ListView listView = ( ListView ) findViewById(R.id.listView1);
                // Setting the adapter to the listView
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String abc = aList.get(position).get("username").toString();

                        Intent intent = new Intent(getApplicationContext(), selectedPatient.class);
                        intent.putExtra("PatientSearchedUserName", abc);

                        startActivityForResult(intent, 0);
                    }
                });
            }
        });
    }
    public void returntoHomePage(){
        t2 = (TextView) findViewById(R.id.textView20);
        t2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), staffHomePage.class);
                startActivity(intent);
            }});
    }
    public void returntopatientDetails(){
        t3 = (TextView) findViewById(R.id.textView24);
        t3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), patientDetails.class);
                startActivity(intent);
            }});
    }
}
