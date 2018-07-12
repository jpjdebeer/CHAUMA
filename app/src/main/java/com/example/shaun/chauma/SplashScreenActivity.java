package com.example.shaun.chauma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This is the screen that will be displayed when the user
 * opens the application every time. This screen will display the CPUT HIV/AIDS logo
 *
 * @author  Simone Temmers
 * @version 1.0
 * @since   2018-01-31
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
    }
}
