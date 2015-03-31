package com.example.keith.rgms1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Keith on 23/3/2015.
 */
public class patientDetails extends ActionBarActivity  {

    ImageButton button;
    TextView t1;
    EditText e1;
    String userName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientdetails);
        returntoHomePage();
        searchButton();
    }

    public void searchButton(){
        button = (ImageButton) findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg1) {


                e1 = (EditText) findViewById(R.id.editText12);
                userName = e1.getText().toString();

                new HumanDataMgr().checkPatientNameExist(userName, new HumanDataMgr.Callback<Boolean>() {
                    @Override
                    public void done(Boolean result) {
                        if (result) {
                            Intent intent = new Intent(getApplicationContext(), viewPatientDetails.class);
                            intent.putExtra("PatientUserName", userName);
                            startActivityForResult(intent, 0);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Such Patient Exist", Toast.LENGTH_LONG).show();
                        }
                    }


                });

            }
        });
    }

    public void returntoHomePage(){
    t1 = (TextView) findViewById(R.id.textView16);
    t1.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(getApplicationContext(), staffHomePage.class);
            startActivity(intent);
        }});
        }
}
