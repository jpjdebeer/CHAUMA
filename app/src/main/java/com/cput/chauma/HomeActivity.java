package com.cput.chauma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shaun.chauma.R;

/**
 * This screen will display all the options or actions a user can do.
 * For example the user can select to view contacts, events or FAQ
 *
 * @author  Simone Temmers
 * @version 1.0
 * @since   2018-01-31
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }
}
