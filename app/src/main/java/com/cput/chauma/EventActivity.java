package com.cput.chauma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shaun.chauma.R;

/**
 * Display all the events in a calendar
 *
 * @author  Nelson Mpyana
 * @version 1.0
 * @since   2018-01-31
 */
public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
    }
}
