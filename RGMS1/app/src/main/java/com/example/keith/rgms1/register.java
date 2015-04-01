package com.example.keith.rgms1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Keith on 9/3/2015.
 */
public class register extends ActionBarActivity {
    ImageButton button;
    ImageButton button1;
    EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9;
    String userName, userPassword, userFName, userLName, userEmail, userAddress, userMobileNumber, userDOB, userRePassword;

    HumanDataMgr humanMgr = new HumanDataMgr();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Parse.initialize(this, "pV6MMT2OsHxlogsALa0CvDZnJfoIfjPkj49vOfoM", "58dqGcJzQlFK0g02kyqkOqicaxunc8I3oOQBpGdx");
        addReset();
        addNewUser();
    }

    public void addReset() {


        edit1 = (EditText) findViewById(R.id.editText3);
        edit2 = (EditText) findViewById(R.id.editText4);
        edit3 = (EditText) findViewById(R.id.editText5);
        edit4 = (EditText) findViewById(R.id.editText6);
        edit5 = (EditText) findViewById(R.id.editText7);
        edit6 = (EditText) findViewById(R.id.editText8);
        edit7 = (EditText) findViewById(R.id.editText9);
        edit8 = (EditText) findViewById(R.id.editText10);
        edit9 = (EditText) findViewById(R.id.editText11);
        button = (ImageButton) findViewById(R.id.imageButton13);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                edit4.setText("");
                edit5.setText("");
                edit6.setText("");
                edit7.setText("");
                edit8.setText("");
                edit9.setText("");
            }

        });



    }


    public void addNewUser() {
        final Context context = this;


        button1 = (ImageButton) findViewById(R.id.imageButton14);



        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                edit1 = (EditText) findViewById(R.id.editText3);
                edit2 = (EditText) findViewById(R.id.editText4);
                edit3 = (EditText) findViewById(R.id.editText5);
                edit4 = (EditText) findViewById(R.id.editText6);
                edit5 = (EditText) findViewById(R.id.editText7);
                edit6 = (EditText) findViewById(R.id.editText8);
                edit7 = (EditText) findViewById(R.id.editText9);
                edit8 = (EditText) findViewById(R.id.editText10);
                edit9 = (EditText) findViewById(R.id.editText11);
                userName = edit1.getText().toString();
                userPassword = edit2.getText().toString();
                userRePassword = edit3.getText().toString();
                userFName = edit4.getText().toString();
                userLName = edit5.getText().toString();
                userEmail = edit6.getText().toString();
                userMobileNumber = edit7.getText().toString();
                userAddress = edit8.getText().toString();
                userDOB = edit9.getText().toString();

                if(!userPassword.equals(userRePassword)){
                    Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_LONG).show();
                }else if(!userEmail.contains("@") || !userEmail.contains(".com")){
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email", Toast.LENGTH_LONG).show();
                }else if( userMobileNumber.length() != 8){
                    Toast.makeText(getApplicationContext(), "Please enter a valid Number", Toast.LENGTH_LONG).show();
                }
                else{
                    new HumanDataMgr().checkExistingUserName(userName, new HumanDataMgr.Callback<Boolean>() {
                        @Override
                        public void done(Boolean result) {
                            if(result){
                                humanMgr.addNewPatient(userName, userPassword, userFName, userLName, userEmail, userMobileNumber, userAddress, userDOB);
                                Toast.makeText(getApplicationContext(),"Account created Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Username Exist, Please enter another username!", Toast.LENGTH_LONG).show();
                            }
                        }


                    });
//                    String table = humanMgr.checkExistingUserName();
//                    ParseQuery<ParseObject> query = ParseQuery.getQuery(table);
//                    query.whereEqualTo("username", userName);
//                    query.getFirstInBackground(new GetCallback<ParseObject>() {
//                        public void done(ParseObject object, ParseException e) {
//                            if (e == null) {
//                                Toast.makeText(getApplicationContext(), "Username Exist, Please enter another username!", Toast.LENGTH_LONG).show();
//                            } else {
//                                humanMgr.addNewPatient(userName, userPassword, userFName, userLName, userEmail, userMobileNumber, userAddress, userDOB);
//                                Toast.makeText(getApplicationContext(),"Account created Successfully", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(context, login.class);
//                                startActivity(intent);
//                            }
//                        }
//                    });
                }

            }

        });

    }
}
