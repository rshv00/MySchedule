package com.shevroman.android.myschedule.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.shevroman.android.myschedule.R;


public class AboutActivity extends AppCompatActivity {
    private static final String SCHEDULE_URL =
            "https://docs.google.com/spreadsheets/d/15hUfskSi1FD6HKTLkCLE5d6A734ueX1RKeguLPudzk4/edit?usp=sharing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("Про програму");
        LinearLayout sendWishes = (LinearLayout) findViewById(R.id.send_wishes);
        LinearLayout scheduleSite = (LinearLayout) findViewById(R.id.shedule_site);
        sendWishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/shev.roman"));
                startActivity(browserIntent);
            }
        });
        scheduleSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SCHEDULE_URL));
                startActivity(browserIntent);
            }
        });

    }
}
