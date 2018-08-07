package com.cput.chauma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shaun.chauma.R;

/**
 * Show campus clinic and public clinic maps within your rage.
 * Priority clinic are the CPUT campus clinics
 *
 * @author  Banele Mlamleli
 * @version 1.0
 * @since   2018-01-31
 */
public class ClinicActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_activity);

        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.clinicActivityDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //removing the title name(in this case is was the app name)

        final NavigationView navigationView = findViewById(R.id.navigationViewLayout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home){
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                    openActivity("HomeActivity");
                }else if(itemId == R.id.clinic){
                    Toast.makeText(getApplicationContext(), "Clinic", Toast.LENGTH_SHORT).show();
                    openActivity("ClinicActivity");
                }else if(itemId == R.id.brochure){
                    Toast.makeText(getApplicationContext(), "Brochure", Toast.LENGTH_SHORT).show();
                    openActivity("BrochureActivity");
                }else if(itemId == R.id.events){
                    Toast.makeText(getApplicationContext(), "Events", Toast.LENGTH_SHORT).show();
                    openActivity("EventActivity");
                }else if(itemId == R.id.faq){
                    Toast.makeText(getApplicationContext(), "FAQ", Toast.LENGTH_SHORT).show();
                    openActivity("FrequentlyAskedQuestionActivity");
                }else if(itemId == R.id.getInvolve){
                    Toast.makeText(getApplicationContext(), "GetInvolveActivity", Toast.LENGTH_SHORT).show();
                    openActivity("GetInvolveActivity");
                }else if(itemId == R.id.contacts){
                    Toast.makeText(getApplicationContext(), "Contact", Toast.LENGTH_SHORT).show();
                    openActivity("ContactActivity");
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.homeButton){
            Toast.makeText(getApplicationContext(), "Go back Home", Toast.LENGTH_SHORT).show();
            openActivity("HomeActivity");
            finish();
        }else if(item.getItemId() == R.id.loginButton){
            Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
            openActivity("LoginActivity");
            finish();
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void openActivity(String value){
        switch (value){
            case "HomeActivity":
                Intent homeActivity = new Intent(ClinicActivity.this, HomeActivity.class);
                startActivity(homeActivity);
                finish();break;
            case "ClinicActivity":
                Intent clinicActivity = new Intent(ClinicActivity.this, ClinicActivity.class);
                startActivity(clinicActivity);
                finish();break;
            case "BrochureActivity":
                Intent brochureActivity = new Intent(ClinicActivity.this, BrochureActivity.class);
                startActivity(brochureActivity);
                finish();break;
            case "EventActivity":
                Intent eventActivity = new Intent(ClinicActivity.this, EventActivity.class);
                startActivity(eventActivity);
                finish();break;
            case "FrequentlyAskedQuestionActivity":
                Intent faqActivity = new Intent(ClinicActivity.this, FrequentlyAskedQuestionActivity.class);
                startActivity(faqActivity);
                finish();break;
            case "GetInvolveActivity":
                Intent getInvolveActivity = new Intent(ClinicActivity.this, GetInvolveActivity.class);
                startActivity(getInvolveActivity);
                finish();break;
            case "ContactActivity":
                Intent contactActivity = new Intent(ClinicActivity.this, ContactActivity.class);
                startActivity(contactActivity);
                finish();break;
            case "LoginActivity":
                Intent loginActivity = new Intent(ClinicActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                finish();break;
        }
    }
}