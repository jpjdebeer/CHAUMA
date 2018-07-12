package com.cput.chauma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shaun.chauma.R;

/**
 * This is the screen that is only accessable by the admin person.
 * This will allow the admin to edit accounts and set events.
 *
 * @author  Simone Temmers
 * @version 1.0
 * @since   2018-01-31
 */
public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
    }
}
