package com.example.keith.rgms1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
/**
 * Created by Madhavan on 27/3/2015.
 */

public class viewAppt extends ActionBarActivity {
    private ViewFlipper viewFlipper;
    private float lastX;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewappt_1);

        final TextView textElement, textElement1, textElement2, textElement3, textElement4;
        viewFlipper = (ViewFlipper) findViewById(R.id.flip);
        final AppointmentMgr am = new AppointmentMgr();
        SharedPreferences pref = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        final String username = pref.getString("username","");

        Button r1 = (Button) findViewById(R.id.reschedule1);
        Button r2 = (Button) findViewById(R.id.reschedule2);
        Button r3 = (Button) findViewById(R.id.reschedule3);
        Button d1 = (Button) findViewById(R.id.delete1);
        Button d2 = (Button) findViewById(R.id.delete2);
        Button d3 = (Button) findViewById(R.id.delete3);

         textElement = (TextView) findViewById(R.id.tp1);
         textElement1 = (TextView) findViewById(R.id.tp2);
         textElement2 = (TextView) findViewById(R.id.tp4);
         textElement3 = (TextView) findViewById(R.id.dc1);
         textElement4 = (TextView) findViewById(R.id.tp3);

        final TextView textElement5 = (TextView) findViewById(R.id.temp4);
        final TextView textElement6 = (TextView) findViewById(R.id.temp5);
        final TextView textElement7 = (TextView) findViewById(R.id.temp7);
        final TextView textElement8 = (TextView) findViewById(R.id.dc2);
        final TextView textElement9 = (TextView) findViewById(R.id.temp6);

        final TextView textElement10 = (TextView) findViewById(R.id.temp8);
        final TextView textElement11 = (TextView) findViewById(R.id.temp9);
        final TextView textElement12 = (TextView) findViewById(R.id.temp11);
        final TextView textElement13 = (TextView) findViewById(R.id.dc3);
        final TextView textElement14 = (TextView) findViewById(R.id.temp10);

        final int a = am.getNumApp(username);

        final int n = 6;

        new AppointmentMgr().getAppointment(username, new AppointmentMgr.Callback<ArrayList<String>>(){

            @Override
            public void done(ArrayList<String> result) {

                for(int i = 0;i<a;i++) {
                    if(i==0) {

                        textElement.setText(result.get(1));

                        textElement1.setText(result.get(2));

                        textElement2.setText(result.get(3));

                        textElement3.setText(result.get(4));

                        textElement4.setText(result.get(5));

                    }
                    if(i==1)
                    {

                        textElement5.setText(result.get(i*n+1));

                        textElement6.setText(result.get(i*n+2));

                        textElement7.setText(result.get(i*n+3));

                        textElement8.setText(result.get(i*n+4));

                        textElement9.setText(result.get(i*n+5));
                    }
                    if(i==2)
                    {

                        textElement10.setText(result.get(i * n + 1));

                        textElement11.setText(result.get(i*n+2));

                        textElement12.setText(result.get(i*n+3));

                        textElement13.setText(result.get(i*n+4));

                        textElement14.setText(result.get(i*n+5));
                    }
                }

            }

        } );

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(viewAppt.this);
                builder1.setMessage("Are you sure you want to delete this appointment?")
                        .setTitle("Alert");
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String appid = textElement2.getText().toString();
                        am.deleteAppointment(appid);
                        Toast.makeText(viewAppt.this, "Appointment Deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(viewAppt.this, viewAppt.class);
                        startActivity(intent);
                    }
                });
                builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder1.show();

            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(viewAppt.this);
                builder1.setMessage("Are you sure you want to delete this appointment?")
                        .setTitle("Alert");
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String appid = textElement7.getText().toString();
                        am.deleteAppointment(appid);
                        Toast.makeText(viewAppt.this, "Appointment Deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(viewAppt.this, viewAppt.class);
                        startActivity(intent);
                    }
                });
                builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder1.show();

            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(viewAppt.this);
                builder1.setMessage("Are you sure you want to delete this appointment?")
                        .setTitle("Alert");
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String appid = textElement12.getText().toString();
                        am.deleteAppointment(appid);
                        Toast.makeText(viewAppt.this, "Appointment Deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(viewAppt.this, viewAppt.class);
                        startActivity(intent);
                    }
                });
                builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder1.show();
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewAppt.this, rescheduleApp.class);
                intent.putExtra("AppID", textElement2.getText().toString());
                intent.putExtra("DocName", textElement3.getText().toString());
                startActivity(intent);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewAppt.this, rescheduleApp.class);
                intent.putExtra("AppID", textElement7.getText().toString());
                intent.putExtra("DocName", textElement8.getText().toString());
                startActivity(intent);
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewAppt.this, rescheduleApp.class);
                intent.putExtra("AppID", textElement13.getText().toString());
                intent.putExtra("DocName", textElement13.getText().toString());
                startActivity(intent);
            }
        });

        for(int i=2; i>=a; i--){
            viewFlipper.removeViewAt(i);
        }

    }
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX)
                {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                    // Show the next Screen
                    viewFlipper.showNext();
                }

                // if right to left swipe on screen
                if (lastX > currentX)
                {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showPrevious();
                }
                break;
            }
        }
        return false;
    }

}
