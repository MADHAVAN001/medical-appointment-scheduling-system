package com.example.keith.rgms1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Keith on 9/3/2015.
 */
public class login extends ActionBarActivity {
    ImageButton button;
    ImageButton button1;
    DatabaseMgr test;
    EditText test1;
    EditText test2;
    String userName, passWord;
    Connection dbConnection = null;
    Statement statement = null;
    DatabaseMgr dbmgr = new DatabaseMgr();
    HumanDataMgr humanMgr = new HumanDataMgr();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginButton();
        addRegisterOnButton();
        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "pV6MMT2OsHxlogsALa0CvDZnJfoIfjPkj49vOfoM", "58dqGcJzQlFK0g02kyqkOqicaxunc8I3oOQBpGdx");


    }

    public void loginButton() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.imageButton10);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                test1 = (EditText) findViewById(R.id.editText);
                test2 = (EditText) findViewById(R.id.editText2);
                userName = test1.getText().toString();
                passWord = test2.getText().toString();
                new HumanDataMgr().checkLogin(userName, passWord, new HumanDataMgr.Callback<Boolean>() {
                    @Override
                    public void done(Boolean result) {
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Welcome Back " + userName + "!", Toast.LENGTH_LONG).show();
                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putString("username", userName);
                            edit.commit();
                            Intent intent = new Intent(context, news.class);
                            startActivity(intent);
                        } else {
                            new HumanDataMgr().checkStaffLogin(userName, passWord, new HumanDataMgr.Callback<Boolean>() {
                                @Override
                                public void done(Boolean result) {
                                    if (result) {
                                        Toast.makeText(getApplicationContext(), "Welcome Back " + userName + "!", Toast.LENGTH_LONG).show();
                                        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putString("username", userName);
                                        edit.commit();
                                        Intent intent = new Intent(context, staffHomePage.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Login Credentials!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }

                    }
                });
            }
        });
    }
    public void addRegisterOnButton() {

        final Context context = this;

        button1 = (ImageButton) findViewById(R.id.imageButton11);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, register.class);
                startActivity(intent);

            }

        });

    }

}

