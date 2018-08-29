package com.cput.chauma;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.shaun.chauma.R;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

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
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash_screen_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeActivityIntent);
                //finish();
            }
        },2000);
    }
}
