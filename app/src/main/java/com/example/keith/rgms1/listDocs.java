package com.example.keith.rgms1;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class listDocs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_docs);
        final TextView t1 = (TextView) findViewById(R.id.sgTextView);
        final TextView t2 = (TextView) findViewById(R.id.malTextView);
        final TextView t3 = (TextView) findViewById(R.id.thaiTextView);
        t1.setTextColor(Color.rgb(255,0,0));
        final TextView t = (TextView) findViewById(R.id.content);
        t.setText(R.string.Singapore);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setTextColor(Color.rgb(255,0,0));
                t.setText(R.string.Singapore);
                t2.setTextColor(Color.rgb(255,255,255));
                t3.setTextColor(Color.rgb(255,255,255));
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setTextColor(Color.rgb(255,0,0));
                t.setText(R.string.Malaysia);
                t1.setTextColor(Color.rgb(255,255,255));
                t3.setTextColor(Color.rgb(255,255,255));
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t3.setTextColor(Color.rgb(255,0,0));
                t.setText(R.string.Thailand);
                t1.setTextColor(Color.rgb(255,255,255));
                t2.setTextColor(Color.rgb(255,255,255));
            }
        });
    }
}
