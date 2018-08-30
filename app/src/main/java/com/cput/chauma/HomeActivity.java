package com.cput.chauma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shaun.chauma.R;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * This screen will display all the options or actions a user can do.
 * For example the user can select to view contacts, events or FAQ
 *
 * @author  Simone Temmers
 * @version 1.0
 * @since   2018-01-31
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar
    private CardView clinicCard, brochureCard, eventsCard, faqCard, getInvolved, contactsCard;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbar = findViewById(R.id.navigation_action_bar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.homeActivityDrawerLayout);
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
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        openActivity("HomeActivity");break;
                    case R.id.clinic:
                        Toast.makeText(getApplicationContext(), "Clinic", Toast.LENGTH_SHORT).show();
                        openActivity("ClinicActivity");break;
                    case R.id.brochure:
                        Toast.makeText(getApplicationContext(), "Brochure", Toast.LENGTH_SHORT).show();
                        openActivity("BrochureActivity");break;
                    case R.id.events:
                        Toast.makeText(getApplicationContext(), "Events", Toast.LENGTH_SHORT).show();
                        openActivity("EventActivity");break;
                    case R.id.faq:
                        Toast.makeText(getApplicationContext(), "FAQ", Toast.LENGTH_SHORT).show();
                        openActivity("FrequentlyAskedQuestionActivity");break;
                    case R.id.getInvolve:
                        Toast.makeText(getApplicationContext(), "GetInvolveActivity", Toast.LENGTH_SHORT).show();
                        openActivity("GetInvolveActivity");break;
                    case R.id.contacts:
                        Toast.makeText(getApplicationContext(), "Contact", Toast.LENGTH_SHORT).show();
                        openActivity("ContactActivity");break;
                }
                return true;
            }
        });

        clinicCard = findViewById(R.id.homeClinicOption);
        brochureCard = findViewById(R.id.homeBrochureOption);
        eventsCard = findViewById(R.id.homeEventOption);
        faqCard = findViewById(R.id.homeFaqOption);
        getInvolved = findViewById(R.id.homeGetInvolveOption);
        contactsCard = findViewById(R.id.homeContactsOption);

        clinicCard.setOnClickListener(this);
        brochureCard.setOnClickListener(this);
        eventsCard.setOnClickListener(this);
        faqCard.setOnClickListener(this);
        getInvolved.setOnClickListener(this);
        contactsCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeClinicOption:
                startActivity(new Intent(this, ClinicActivity.class));
                //finish();
                break;
            case R.id.homeBrochureOption:
                startActivity(new Intent(this, BrochureActivity.class));
                //finish();
                break;
            case R.id.homeEventOption:
                startActivity(new Intent(this, EventActivity.class));
                //finish();
                break;
            case R.id.homeFaqOption:
                startActivity(new Intent(this, FrequentlyAskedQuestionActivity.class));
                //finish();
                break;
            case R.id.homeGetInvolveOption:
                startActivity(new Intent(this, GetInvolveActivity.class));
                //finish();
                break;
            case R.id.homeContactsOption:
                startActivity(new Intent(this, ContactActivity.class));
                //finish();
                break;
            default:break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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
            //finish();
        }else if(item.getItemId() == R.id.loginButton){
            Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
            openActivity("LoginActivity");
            //finish();
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void openActivity(String value){
        switch (value){
            case "HomeActivity":
                Intent homeActivity = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(homeActivity);
                finish();break;
            case "ClinicActivity":
                Intent clinicsActivity = new Intent(HomeActivity.this, ClinicActivity.class);
                startActivity(clinicsActivity);
                //finish();
                break;
            case "BrochureActivity":
                Intent brochureActivity = new Intent(HomeActivity.this, BrochureActivity.class);
                startActivity(brochureActivity);
                //finish();
                break;
            case "EventActivity":
                Intent eventActivity = new Intent(HomeActivity.this, EventActivity.class);
                startActivity(eventActivity);
                //finish();
                break;
            case "FrequentlyAskedQuestionActivity":
                Intent faqActivity = new Intent(HomeActivity.this, FrequentlyAskedQuestionActivity.class);
                startActivity(faqActivity);
                //finish();
                break;
            case "GetInvolveActivity":
                Intent getInvolveActivity = new Intent(HomeActivity.this, GetInvolveActivity.class);
                startActivity(getInvolveActivity);
                //finish();
                break;
            case "ContactActivity":
                Intent contactActivity = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(contactActivity);
                //finish();
                break;
            case "LoginActivity":
                Intent loginActivity = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                //finish();
                break;
        }
    }
}