package com.cput.chauma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shaun.chauma.R;

public class NavigationBoilerplateCode extends AppCompatActivity {
    public void navigationView(){
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
    }

    public void openActivity(String value){
        switch (value){
            case "HomeActivity":
                Intent homeActivity = new Intent(this, HomeActivity.class);
                startActivity(homeActivity);
                //finish();
                break;
            case "ClinicActivity":
                Intent clinicsActivity = new Intent(this, ClinicActivity.class);
                startActivity(clinicsActivity);
                //finish();
                break;
            case "BrochureActivity":
                Intent brochureActivity = new Intent(this, BrochureActivity.class);
                startActivity(brochureActivity);
                //finish();
                break;
            case "EventActivity":
                Intent eventActivity = new Intent(this, EventActivity.class);
                startActivity(eventActivity);
                //finish();
                break;
            case "FrequentlyAskedQuestionActivity":
                Intent faqActivity = new Intent(this, FrequentlyAskedQuestionActivity.class);
                startActivity(faqActivity);
                //finish();
                break;
            case "GetInvolveActivity":
                Intent getInvolveActivity = new Intent(this, GetInvolveActivity.class);
                startActivity(getInvolveActivity);
                //finish();
                break;
            case "ContactActivity":
                Intent contactActivity = new Intent(this, ContactActivity.class);
                startActivity(contactActivity);
                //finish();
                break;
            case "LoginActivity":
                Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                //finish();
                break;
        }
    }
}
