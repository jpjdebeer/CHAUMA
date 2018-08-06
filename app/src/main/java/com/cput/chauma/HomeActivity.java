package com.cput.chauma;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    private DrawerLayout drawerLayout;  //This is the  layout for the navigation bar
    private ActionBarDrawerToggle actionBarDrawerToggle; //This is the button that will be used to show and hide Navigation bar
    private Toolbar toolbar;    //This instance is for the navigation toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                int itemId = item.getItemId();

                if (itemId == R.id.home){
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.clinic){
                    Toast.makeText(getApplicationContext(), "Clinic", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.brochure){
                    Toast.makeText(getApplicationContext(), "Brochure", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.events){
                    Toast.makeText(getApplicationContext(), "Events", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.faq){
                    Toast.makeText(getApplicationContext(), "FAQ", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.joinus){
                    Toast.makeText(getApplicationContext(), "Join Us", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.contacts){
                    Toast.makeText(getApplicationContext(), "Contact", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.layoutLoginIcon){
                    Toast.makeText(getApplicationContext(), "Go back Home", Toast.LENGTH_SHORT).show();
                }else if(itemId == R.id.layoutHomeIcon){
                    Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
