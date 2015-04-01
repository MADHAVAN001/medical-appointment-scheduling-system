package com.example.keith.rgms1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.ViewGroup;
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
    SharedPreferences pref = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
    final String username = pref.getString("username","");
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewappt_1);
        int a = generateLayout();
        viewFlipper = (ViewFlipper) findViewById(R.id.flip);
        for(int i = a;i<3;i++)
        {
            viewFlipper.removeViewAt(i);
        }

    }
    public Integer generateLayout()
    {
        final ArrayList<Integer> ret = new ArrayList<>();
        final int n = 6;
        new AppointmentMgr().getAppointment(username, new AppointmentMgr.Callback<ArrayList<String>>(){

            @Override
            public void done(ArrayList<String> result) {
                ret.add(result.size()/n);
                for(int i = 0;i<result.size()/n;i++) {
                    if(i==0) {
                        TextView textElement = (TextView) findViewById(R.id.tp1);
                        textElement.setText(result.get(1)); //leave this line to assign a string resource
                        TextView textElement1 = (TextView) findViewById(R.id.tp2);
                        textElement1.setText(result.get(2)); //leave this line to assign a string resource
                        TextView textElement2 = (TextView) findViewById(R.id.tp4);
                        textElement2.setText(result.get(3)); //leave this line to assign a string resource
                        TextView textElement3 = (TextView) findViewById(R.id.dc1);
                        textElement3.setText(result.get(4));
                        TextView textElement4 = (TextView) findViewById(R.id.tp3);
                        textElement4.setText(result.get(5));
                        final String objectid = result.get(0);
                        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout42);
                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                            new AppointmentMgr().deleteAppointment(objectid);
                            }
                        });
                    }
                        if(i==1)
                    {
                        TextView textElement = (TextView) findViewById(R.id.temp4);
                        textElement.setText(result.get(i*n+1)); //leave this line to assign a string resource
                        TextView textElement1 = (TextView) findViewById(R.id.temp5);
                        textElement1.setText(result.get(i*n+2)); //leave this line to assign a string resource
                        TextView textElement2 = (TextView) findViewById(R.id.temp7);
                        textElement2.setText(result.get(i*n+3)); //leave this line to assign a string resource
                        TextView textElement3 = (TextView) findViewById(R.id.dc2);
                        textElement3.setText(result.get(i*n+4));

                        TextView textElement4 = (TextView) findViewById(R.id.temp6);
                        textElement4.setText(result.get(i*n+5));
                    }
                    if(i==2)
                    {
                        TextView textElement = (TextView) findViewById(R.id.temp8);
                        textElement.setText(result.get(i*n+1)); //leave this line to assign a string resource
                        TextView textElement1 = (TextView) findViewById(R.id.temp9);
                        textElement1.setText(result.get(i*n+2)); //leave this line to assign a string resource
                        TextView textElement2 = (TextView) findViewById(R.id.temp11);
                        textElement2.setText(result.get(i*n+3)); //leave this line to assign a string resource
                        TextView textElement3 = (TextView) findViewById(R.id.dc3);
                        textElement3.setText(result.get(i*n+4));

                        TextView textElement4 = (TextView) findViewById(R.id.temp10);
                        textElement4.setText(result.get(i*n+5));
                    }
                }

            }

        } );
        return ret.get(0);
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

